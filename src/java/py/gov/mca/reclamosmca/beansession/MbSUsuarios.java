package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;
import py.gov.mca.reclamosmca.entitys.PermisosElementosWeb;
import py.gov.mca.reclamosmca.entitys.Personas;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.PersonasSB;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;
import py.gov.mca.reclamosmca.utiles.Converciones;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSUsuarios")
@SessionScoped

public class MbSUsuarios implements Serializable {

    @EJB
    private UsuariosSB usuariosSB;

    @EJB
    private PersonasSB personasSB;

    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String telefono;
    private String cuentaCorriente;

    private String loginUsuario;
    private String claveUsuario;
    private String contrasenaActual;
    private String contrasena1;
    private String contrasena2;
    private String linkExpediente;

    private Usuarios usuario;

    public MbSUsuarios() {
        //Control de tiempo de session de usuario, en segundos seteado en 300 segundos equivalentes a 5 minutos
        HttpSession sessionUsuario = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessionUsuario.setMaxInactiveInterval(300);
    }

    public String prepararRegistro() {
        this.cedula = "";
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.direccion = "";
        this.telefono = "";
        this.cuentaCorriente = "";
        this.loginUsuario = "";
        this.claveUsuario = "";
        this.contrasenaActual = "";
        this.contrasena1 = "";
        this.contrasena2 = "";
        this.linkExpediente = "";
        return "/registro";
    }

    public String btnRegistrar() {
        if (getCedula().equals("") || getNombre().equals("") || getApellido().equals("") || getCorreo().equals("") || contrasena1.equals("") || contrasena2.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/registro";
        } else {
            if (contrasena1.equals(contrasena2)) {
                Converciones c = new Converciones();
                String contrasenaMD5 = c.getMD5(contrasena1);
                if (contrasenaMD5 == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR de Registro, intente de nuevo", ""));
                    return "/registro";
                } else {
                    int banderaRegistro = 0;
                    //Consultar por cedula
                    Personas personaExistente = personasSB.consultarPersonaCedula(getCedula());
                    //Consultar por login
                    Usuarios usuarioExistente = usuariosSB.consultarUsuarios(loginUsuario);

                    if (personaExistente == null) {
                        if (usuarioExistente == null) {
                            banderaRegistro = 1;
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo ya esta registrado.", ""));
                            return "/registro";
                        }
                    } else {
                        if (personaExistente.getUsuariosList().isEmpty()) {
                            if (usuarioExistente == null) {
                                banderaRegistro = 1;
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo ya esta registrado.", ""));
                                return "/registro";
                            }
                        } else {
                            int banderaUsuarioWeb = 0;
                            for (int i = 0; personaExistente.getUsuariosList().size() > i; i++) {
                                if (personaExistente.getUsuariosList().get(i).getFkCodRol().getCodRol().equals(6)) {
                                    banderaUsuarioWeb = 1;
                                }
                            }
                            if (banderaUsuarioWeb == 1) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo ya esta registrado.", ""));
                                return "/registro";
                            } else {
                                banderaRegistro = 1;
                            }
                        }
                    }

                    if (banderaRegistro == 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cedula ya existe.", ""));
                        return "/registro";
                    } else {
                        usuario = new Usuarios();
                        usuario.setLoginUsuario(getCorreo());
                        usuario.setClaveUsuario(contrasenaMD5);
                        usuario.setFkCodPersona(new Personas());
                        usuario.getFkCodPersona().setCedulaPersona(getCedula());
                        usuario.getFkCodPersona().setNombrePersona(getNombre());
                        usuario.getFkCodPersona().setApellidoPersona(getApellido());
                        usuario.getFkCodPersona().setFechaRegistroPersona(new Date());
                        usuario.getFkCodPersona().setDireccionPersona(getDireccion());
                        usuario.getFkCodPersona().setTelefonoPersona(getTelefono());
                        usuario.getFkCodPersona().setCtaCtePersona(getCuentaCorriente());
                        usuario.getFkCodPersona().setOrigenRegistro("appWeb");
                        usuario.setFkCodEstadoUsuario(new EstadosUsuarios());
                        usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(2);
                        usuario.setFkCodRol(new Roles());
                        usuario.getFkCodRol().setCodRol(6);
                        String resultado = usuariosSB.crearUsuariosWeb(usuario);
                        if (resultado.equals("OK")) {
                            FacesMessage message = new FacesMessage();
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            message.setSummary("Gracias por registrarte.");
                            message.setDetail("Para activar tu cuenta te enviamos un correo electrónico a " + this.correo + "."
                                    + "\n Tienes 48 hrs. para verificar tu correo, de lo contrario tus datos seran borrados de nuestro sistema.");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                            return "/login";
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR de Registro en BD, intente de nuevo", resultado));
                            return "/registro";
                        }
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no son iguales.", ""));
                return "/registro";
            }

        }
    }

    public String prepararIngreso() {
        this.cedula = "";
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.direccion = "";
        this.telefono = "";
        this.cuentaCorriente = "";
        this.loginUsuario = "";
        this.claveUsuario = "";
        this.contrasenaActual = "";
        this.contrasena1 = "";
        this.contrasena2 = "";
        this.linkExpediente = "";
        return "/login";
    }

    public String btnIngresar() {
        if (loginUsuario.equals("") || claveUsuario.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/login";
        } else {
            usuario = null;
            usuario = usuariosSB.consultarUsuarios(loginUsuario.trim());
            if (usuario != null) {
                if (usuario.getClaveUsuario().substring(0, 6).equals(claveUsuario) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(2)) {
                    EstadosUsuarios estadoUsuario = new EstadosUsuarios();
                    estadoUsuario.setCodEstadoUsuario(1);
                    usuario.setFkCodEstadoUsuario(estadoUsuario);
                    if (usuariosSB.actualizarUsuarios(usuario).equals("OK")) {

                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        httpSession.setAttribute("loginUsuario", this.loginUsuario);

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta activada.", ""));
                        return "/admin_mis_reclamos";
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo activar su cuenta, intentelo de nuevo.", ""));
                        return "/login";
                    }
                } else if (usuario.getClaveUsuario().substring(0, 6).equals(claveUsuario) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {

                    HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    httpSession.setAttribute("loginUsuario", this.loginUsuario);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña reestablecida.", "Por favor cambie su contraseña."));
                    return "/admin_cambiar_contrasenia";
                } else {
                    Converciones c = new Converciones();
                    String contrasenaMD5 = c.getMD5(claveUsuario);
                    /// System.out.println("Clave" +contrasenaMD5);
                    if (contrasenaMD5 == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo ingresar, intentelo de nuevo.", ""));
                        return "/login";
                    } else if (usuario.getClaveUsuario().equals(contrasenaMD5) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {

                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        httpSession.setAttribute("loginUsuario", this.loginUsuario);

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido!", ""));
                        String pagina;
                        if (usuario.getFkCodRol().getCodRol().equals(1)) {
                            pagina = "/admin_gestion_reclamos_pendientes";
                        } else if (usuario.getFkCodRol().getCodRol().equals(2)) {
                            pagina = "/admin_gestion_reclamos_pendientes";
                        } else if (usuario.getFkCodRol().getCodRol().equals(3)) {
                            pagina = "/admin_gestion_reclamos_pendientes";
                        } else if (usuario.getFkCodRol().getCodRol().equals(4)) {
                            pagina = "/admin_gestion_reclamos_pendientes";
                        } else if (usuario.getFkCodRol().getCodRol().equals(5)) {
                            pagina = "/admin_gestion_reclamos_pendientes";
                        } else {
                            pagina = "/admin_mis_reclamos";
                        }
                        return pagina;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña no validos, intentelo de nuevo.", ""));
                        return "/login";
                    }

                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña no validos, intentelo de nuevo.", ""));
                return "/login";
            }
        }
    }

    public String btnSalir() {
        this.usuario = null;
        this.loginUsuario = null;
        this.claveUsuario = null;

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();

//        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
//        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
//        HttpSession session = (HttpSession) ctx.getSession(false);
//        if (session != null) {
//            this.setUsuario(null);
//            session.invalidate();
//        }
        return "/index";
    }

    public String btnActualizarDatos() {
        Personas persona = usuario.getFkCodPersona();
        String mensaje = personasSB.actualizarPersonas(persona);

        if (mensaje.equals("OK")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados correctamente.", ""));
            return "/admin_mis_reclamos";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion de actializacion.", mensaje));
            return "/admin_actualizar_datos";
        }
    }

    public String btnCambioContrasena() {
        Converciones c = new Converciones();
        String contrasenaMD5 = c.getMD5(contrasenaActual);
        String contrasenaMD5Nueva = c.getMD5(contrasena1);
        if (contrasenaActual.equals("") || contrasena1.equals("") || contrasena2.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacios.", ""));
            return "/admin_cambiar_contrasenia";
        } else if (contrasena1.equals(contrasena2)) {
            if (contrasenaMD5 == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cambiar su contraseña, intentelo de nuevo.", ""));
                return "/admin_cambiar_contrasenia";
            } else {
                if (contrasenaMD5Nueva == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cambiar su contraseña, intentelo de nuevo.", ""));
                    return "/admin_cambiar_contrasenia";
                } else {
                    //Si estado es ACTIVO 
                    if (usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {
                        //Comprueba si contraseña actual en MD5 es igual a la contraseña de session
                        if (contrasenaMD5.equals(usuario.getClaveUsuario())) {
                            //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5
                            System.out.println("MbSLogin ConAntes: " + usuario.getClaveUsuario());
                            usuario.setClaveUsuario(contrasenaMD5Nueva);
                            System.out.println("MbSLogin ConDespues: " + usuario.getClaveUsuario());
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña Actual no es valida.", ""));
                            return "/admin_cambiar_contrasenia";
                        }
                        //Si estado es RESETCLAVE
                    } else if (usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {
                        //Comprueba si contraseña actual sin MD5 es igual a la contraseña de session recortada en 6 digitos
                        if (usuario.getClaveUsuario().substring(0, 6).equals(contrasenaActual)) {
                            //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5 y se setea el estado a ACTIVO
                            usuario.setClaveUsuario(contrasenaMD5Nueva);
                            //Cambia a estado ACTIVO
                            EstadosUsuarios estadoUsuario = new EstadosUsuarios();
                            estadoUsuario.setCodEstadoUsuario(1);
                            usuario.setFkCodEstadoUsuario(estadoUsuario);

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña Actual no es valida.", ""));
                            return "/admin_cambiar_contrasenia";
                        }
                        String mensaje = usuariosSB.actualizarUsuarios(usuario);
                        if (mensaje.equals("OK")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña cambiada.", ""));
                            return "/admin_mis_reclamos";
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cambiar su contraseña, intentelo de nuevo.", ""));
                            return "/admin_cambiar_contrasenia";
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cambiar su contraseña, intentelo de nuevo.", ""));
                        return "/admin_cambiar_contrasenia";
                    }
                    String mensaje = usuariosSB.actualizarUsuarios(usuario);
                    if (mensaje.equals("OK")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña cambiada.", ""));
                        return "/admin_mis_reclamos";
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo cambiar su contraseña, intentelo de nuevo.", ""));
                        return "/admin_cambiar_contrasenia";
                    }
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseñas actuales no son iguales.", ""));
            return "/admin_cambiar_contrasenia";
        }

    }

    public String btnRecuperarContrasena() {
        if (loginUsuario.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacios.", ""));
            return "/recuperarContrasenia";
        } else {
            usuario = null;
            usuario = usuariosSB.consultarUsuarios(loginUsuario);
            if (usuario != null) {
                //Cambia a estado RESETCLAVE
                EstadosUsuarios estadoUsuario = new EstadosUsuarios();
                estadoUsuario.setCodEstadoUsuario(3);
                usuario.setFkCodEstadoUsuario(estadoUsuario);
                String mensaje = usuariosSB.actualizarUsuarios(usuario);
                if (mensaje.equals("OK")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su nueva contraseña ha sido enviada a " + loginUsuario, ""));
                    return "/login";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo enviar la nueva contraseña, intentelo de nuevo.", ""));
                    return "/recuperarContrasenia";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo enviar la nueva contraseña, intentelo de nuevo.", ""));
                return "/recuperarContrasenia";
            }
        }
    }

    public String obtenerPermisoVisibleElemento(String nombreElemento) {
        List<PermisosElementosWeb> listaDePermisosDeElementos = usuario.getFkCodRol().getPermisosElementosWebList();
        String valorRetorno = "false";
        for (int i = 0; i < listaDePermisosDeElementos.size(); i++) {
            PermisosElementosWeb per = listaDePermisosDeElementos.get(i);
            if (per.getFkCodElementoWeb().getNombreElementoWeb().equals(nombreElemento)) {
                valorRetorno = per.getValorVisible();
            }
        }
        return valorRetorno;
    }

    public String generarLinkExpediente(String direccion) {
        String patron = "yyyyMMddHH";
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        String md5 = usuario.getLoginUsuario() + formato.format(new Date()) + "mca";
        System.out.println("md5 antes: " + md5);
        Converciones c = new Converciones();
        md5 = c.getMD5(md5);
        System.out.println("md5 despues: " + md5);
        //this.linkExpediente = "http://126.10.10.33:8080/MCA_InicioExpedientes2/servlet/mcalogin?" + usuario.getLoginUsuario() + "," + direccion + "," + md5;
        this.linkExpediente = "http://expediente.mca.gov.py/portal/mcalogin?" + usuario.getLoginUsuario() + "," + direccion + "," + md5;
        System.out.println("LINK: " + linkExpediente);
        return "admin_expediente";
    }

    /**
     * @return the loginUsuario
     */
    public String getLoginUsuario() {
        return loginUsuario;
    }

    /**
     * @param loginUsuario the loginUsuario to set
     */
    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    /**
     * @return the claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * @param claveUsuario the claveUsuario to set
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    /**
     * @return the contrasena1
     */
    public String getContrasena1() {
        return contrasena1;
    }

    /**
     * @param contrasena1 the contrasena1 to set
     */
    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    /**
     * @return the contrasena2
     */
    public String getContrasena2() {
        return contrasena2;
    }

    /**
     * @param contrasena2 the contrasena2 to set
     */
    public void setContrasena2(String contrasena2) {
        this.contrasena2 = contrasena2;
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasenaActual
     */
    public String getContrasenaActual() {
        return contrasenaActual;
    }

    /**
     * @param contrasenaActual the contrasenaActual to set
     */
    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    /**
     * @return the linkExpediente
     */
    public String getLinkExpediente() {
        return linkExpediente;
    }

    /**
     * @param linkExpediente the linkExpediente to set
     */
    public void setLinkExpediente(String linkExpediente) {
        this.linkExpediente = linkExpediente;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the cuentaCorriente
     */
    public String getCuentaCorriente() {
        return cuentaCorriente;
    }

    /**
     * @param cuentaCorriente the cuentaCorriente to set
     */
    public void setCuentaCorriente(String cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

}
