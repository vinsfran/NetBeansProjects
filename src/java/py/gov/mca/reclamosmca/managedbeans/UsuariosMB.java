package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.utiles.Converciones;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "usuariosMB")
@SessionScoped
public class UsuariosMB implements Serializable {

    private String loginUsuario;
    private String claveUsuario;
    private String contrasena1;
    private String contrasena2;
    private Usuarios usuario;
    private boolean sessionIniciada;
    private String redireccion;
    @EJB
    private UsuariosSB usuariosSB;

    public UsuariosMB() {
        this.redireccion = "?faces-redirect=true";
        this.loginUsuario = "";
        this.claveUsuario = "";
        this.usuario = new Usuarios();
        this.sessionIniciada = false;
    }

    public String btnIngresarGestion() {
        String pagina;
        if (getLoginUsuario().equals("") || getClaveUsuario().equals("")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Los campos con (*) no pueden estar vacios."));
            pagina = "login" + getRedireccion();
        } else {
            Converciones c = new Converciones();
            String contrasenaMD5 = c.getMD5(getClaveUsuario());
            if (contrasenaMD5 == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "No se pudo crear. (MD5)"));

                pagina = "login" + getRedireccion();
            } else {
                setUsuario(null);
                setUsuario(usuariosSB.consultarUsuarios(getLoginUsuario()));
                if (getUsuario() != null) {
                    if (getUsuario().getClaveUsuario().equals(contrasenaMD5) && getUsuario().getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {
                        setSessionIniciada(true);
                        pagina = "listarreclamos" + getRedireccion();
                    } else if (getUsuario().getClaveUsuario().equals(contrasenaMD5) && getUsuario().getFkCodEstadoUsuario().getCodEstadoUsuario().equals(2)) {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        context.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Usuario Inactivo"));
                        pagina = "login" + getRedireccion();
                    } else {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        context.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Contraseña no valida."));
                        pagina = "login" + getRedireccion();
                    }
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    context.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Usuario no valido"));
                    pagina = "login" + getRedireccion();
                }
            }
        }
        return pagina;
    }

//    public String btnActualizar() {
//        String pagina;
//        String mensaje1 = "Validación de Actualización: ";
//        FacesMessage message = new FacesMessage();
//        if (usuariosWebSB.actualizarUsuariosWeb(usuarioWeb).equals("OK")) {
//            message.setSeverity(FacesMessage.SEVERITY_INFO);
//            message.setSummary("Felicidades: ");
//            message.setDetail("Sus datos fueron actualizados correctamente.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            setSessionIniciada(true);
//            pagina = "menuUsuario" + redireccion;
//        } else {
//            message.setSeverity(FacesMessage.SEVERITY_WARN);
//            message.setSummary(mensaje1);
//            message.setDetail("No se pudo actualizar sus datos, intentelo de nuevo.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            pagina = "ajustesUsuario" + redireccion;
//        }
//        return pagina;
//    }
//    public String btnCambioContrasena() {
//        String pagina = "ajustesUsuario";
//        String mensaje1 = "Validación de cambio contraseña: ";
//        Converciones c = new Converciones();
//        int banderaActualizacion = 0;
//        FacesMessage message = new FacesMessage();
//        message.setSeverity(FacesMessage.SEVERITY_ERROR);
//        message.setSummary(mensaje1);
//        if (contrasenaUsuario.equals("") || contrasena1.equals("") || contrasena2.equals("")) {
//            message.setDetail("Los campos con (*) no pueden estar vacios.");
//            pagina = "cambioCantrasena" + redireccion;
//        } else {
//            if (contrasena1.equals(contrasena2)) {
//                String contrasenaMD5 = c.getMD5(contrasenaUsuario);
//                if (contrasenaMD5 == null) {
//                    message.setDetail("No se pudo crear ACTUAL. (MD5)");
//                    pagina = "cambioCantrasena" + redireccion;
//                } else {
//                    String contrasenaMD5Nueva = c.getMD5(contrasena1);
//                    if (contrasenaMD5Nueva == null) {
//                        message.setDetail("No se pudo crear NUEVA. (MD5)");
//                        pagina = "cambioCantrasena" + redireccion;
//                    } else {
//                        //Si estado es ACTIVO 
//                        if (usuarioWeb.getEstado().equals("ACTIVO")) {
//                            //Comprueba si contraseña actual en MD5 es igual a la contraseña de session
//                            if (contrasenaMD5.equals(usuarioWeb.getContrasenaWeb())) {
//                                //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5
//                                usuarioWeb.setContrasenaWeb(contrasenaMD5Nueva);
//                                banderaActualizacion = 1;
//                            } else {
//                                message.setDetail("Contraseña Actual no es valida");
//                                pagina = "cambioCantrasena" + redireccion;
//                            }
//                            //Si estado es RESETCLAVE
//                        } else if (usuarioWeb.getEstado().equals("RESETCLAVE")) {
//                            //Comprueba si contraseña actual sin MD5 es igual a la contraseña de session recortada en 6 digitos
//                            if (usuarioWeb.getContrasenaWeb().substring(0, 6).equals(contrasenaUsuario)) {
//                                //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5 y se setea el estado a ACTIVO
//                                usuarioWeb.setContrasenaWeb(contrasenaMD5Nueva);
//                                usuarioWeb.setEstado("ACTIVO");
//                                banderaActualizacion = 1;
//                            } else {
//                                message.setDetail("Contraseña Actual no es valida");
//                                pagina = "cambioCantrasena" + redireccion;
//                            }
//                        } else {
//                            message.setDetail("No se pudo cambiar su contraseña, intentelo de nuevo.");
//                            pagina = "cambioCantrasena" + redireccion;
//                        }
//                        //Para ambos casos actualizamos el UsuarioWeb
//                        if (banderaActualizacion == 1) {
//                            if (usuariosWebSB.actualizarUsuariosWeb(usuarioWeb).equals("OK")) {
//                                message.setSeverity(FacesMessage.SEVERITY_INFO);
//                                message.setSummary("Felicidades: ");
//                                message.setDetail("Su contraseña ha cambiado.");
//                            } else {
//                                message.setDetail("No se pudo cambiar su contraseña, intentelo de nuevo.");
//                                pagina = "cambioCantrasena" + redireccion;
//                            }
//                        }
//                    }
//                }
//            } else {
//                message.setDetail("Contraseñas actuales no son iguales.");
//                pagina = "cambioCantrasena" + redireccion;
//            }
//        }
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        return pagina;
//    }
    public String btnSalirGestion() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        HttpSession session = (HttpSession) ctx.getSession(false);
        this.setUsuario(null);
        if (session != null) {
            this.setUsuario(null);
            session.invalidate();
        }
        return "login" + getRedireccion();
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
     * @return the sessionIniciada
     */
    public boolean isSessionIniciada() {
        return sessionIniciada;
    }

    /**
     * @param sessionIniciada the sessionIniciada to set
     */
    public void setSessionIniciada(boolean sessionIniciada) {
        this.sessionIniciada = sessionIniciada;
    }

    /**
     * @return the redireccion
     */
    public String getRedireccion() {
        return redireccion;
    }

    /**
     * @param redireccion the redireccion to set
     */
    public void setRedireccion(String redireccion) {
        this.redireccion = redireccion;
    }

}
