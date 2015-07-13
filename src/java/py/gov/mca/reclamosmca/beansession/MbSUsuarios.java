package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;
import py.gov.mca.reclamosmca.entitys.PermisosElementosWeb;
import py.gov.mca.reclamosmca.entitys.Personas;
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

    private String loginUsuario;
    private String claveUsuario;
    private String contrasenaActual;
    private String contrasena1;
    private String contrasena2;

    private Usuarios usuario;

    public MbSUsuarios() {
        //Control de tiempo de session de usuario, en segundos seteado en 300 segundos equivalentes a 5 minutos
        HttpSession sessionUsuario = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessionUsuario.setMaxInactiveInterval(300);
    }

    public String btnIngresar() {
        if (loginUsuario.equals("") || claveUsuario.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/login";
        } else {
            usuario = null;
            usuario = usuariosSB.consultarUsuarios(loginUsuario);
            if (usuario != null) {
                if (usuario.getClaveUsuario().substring(0, 6).equals(claveUsuario) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(2)) {
                    usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(1);
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
                        
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido!", usuario.getFkCodPersona().getNombrePersona()));
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
                            EstadosUsuarios estado = new EstadosUsuarios();
                            estado.setCodEstadoUsuario(1);
                            usuario.setFkCodEstadoUsuario(estado);

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
                EstadosUsuarios estado = new EstadosUsuarios();
                estado.setCodEstadoUsuario(3);
                usuario.setFkCodEstadoUsuario(estado);
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

}
