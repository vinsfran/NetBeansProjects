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
//@ManagedBean(name = "loginUsuarioMB")
//@SessionScoped
public class LoginUsuarioMB implements Serializable {

    private String correoUsuario;
    private String contrasenaUsuario;
    private String contrasena1;
    private String contrasena2;
    private Usuarios usuario;
    private boolean sessionIniciada;
    private String redireccion;
    @EJB
    private UsuariosSB usuariosSB;

    public LoginUsuarioMB() {
        this.redireccion = "?faces-redirect=true";
        this.correoUsuario = "";
        this.contrasenaUsuario = "";
        this.usuario = new Usuarios();
        this.sessionIniciada = false;
        this.usuario = null;
    }

    public String btnIngresar() {
        String pagina;
        String mensaje1 = "Validación de ingreso:";
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (correoUsuario.equals("") || contrasenaUsuario.equals("")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary(mensaje1);
            message.setDetail("El campo con (*) no puede estar vacio.");
            pagina = "loginUsuario" + redireccion;
        } else {
            usuario = null;
            usuario = usuariosSB.consultarUsuarios(correoUsuario);
            if (usuario != null) {
                if (usuario.getClaveUsuario().substring(0, 6).equals(contrasenaUsuario) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(2)) {
                    usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(1);
                    if (usuariosSB.actualizarUsuarios(usuario).equals("OK")) {
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        message.setSummary("Felicidades:");
                        message.setDetail("Su cuenta esta activa.");
                        setSessionIniciada(true);
                        pagina = "menuUsuario" + redireccion;
                    } else {
                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                        message.setSummary(mensaje1);
                        message.setDetail("No se pudo activar su cuenta, intentelo de nuevo.");
                        pagina = "loginUsuario" + redireccion;
                    }
                } else if (usuario.getClaveUsuario().substring(0, 6).equals(contrasenaUsuario) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    message.setSummary("Contraseña reestablecida:");
                    message.setDetail("Por favor cambie su contraseña.");
                    setSessionIniciada(true);
                    pagina = "cambioContrasena" + redireccion;
                } else {
                    Converciones c = new Converciones();
                    String contrasenaMD5 = c.getMD5(contrasenaUsuario);
                    if (contrasenaMD5 == null) {
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        message.setSummary(mensaje1);
                        message.setDetail("No se pudo crear. (MD5)");
                        pagina = "loginUsuario" + redireccion;
                    } else {
                        if (usuario.getClaveUsuario().equals(contrasenaMD5) && usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {
                            setSessionIniciada(true);
                            pagina = "menuUsuario" + redireccion;
                        } else {
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            message.setSummary(mensaje1);
                            message.setDetail("Contraseña no valida.");
                            pagina = "loginUsuario" + redireccion;
                        }
                    }
                }
            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary(mensaje1);
                message.setDetail("Usuario no valido.");
                pagina = "loginUsuario" + redireccion;
            }
        }
        context.addMessage(null, message);
        return pagina;
    }

    public String btnRecuperar() {
        String pagina;
        String mensaje1 = "Validación de envio: ";
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (correoUsuario.equals("")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(mensaje1);
            message.setDetail("El campo con (*) no puede estar vacio.");
            pagina = "resetContrasena" + redireccion;
        } else {
            usuario = null;
            usuario = usuariosSB.consultarUsuarios(correoUsuario);
            if (usuario != null) {
                usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(3);;
                if (usuariosSB.actualizarUsuarios(usuario).equals("OK")) {
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    message.setSummary("Felicidades: ");
                    message.setDetail("Su nueva contraseña ha sido enviada a " + correoUsuario);
                    pagina = "loginUsuario" + redireccion;
                } else {
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    message.setSummary(mensaje1);
                    message.setDetail("No se pudo enviar la nueva contraseña, intentelo de nuevo.");
                    pagina = "resetContrasena" + redireccion;
                }
            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary(mensaje1);
                message.setDetail("Usuario no valido.");
                pagina = "resetContrasena" + redireccion;
            }
        }
        context.addMessage(null, message);
        return pagina;
    }

    public String btnActualizar() {
        String pagina;
        String mensaje1 = "Validación de Actualización: ";
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (usuariosSB.actualizarUsuarios(usuario).equals("OK")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("Felicidades: ");
            message.setDetail("Sus datos fueron actualizados correctamente.");
            setSessionIniciada(true);
            pagina = "menuUsuario" + redireccion;
        } else {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary(mensaje1);
            message.setDetail("No se pudo actualizar sus datos, intentelo de nuevo.");
            pagina = "ajustesUsuario" + redireccion;
        }
        context.addMessage(null, message);
        return pagina;
    }

    public String btnCambioContrasena() {
        String pagina = "ajustesUsuario";
        String mensaje1 = "Validación de cambio contraseña: ";
        Converciones c = new Converciones();
        int banderaActualizacion = 0;
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        message.setSummary(mensaje1);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (contrasenaUsuario.equals("") || contrasena1.equals("") || contrasena2.equals("")) {
            message.setDetail("Los campos con (*) no pueden estar vacios.");
            pagina = "cambioCantrasena" + redireccion;
        } else {
            if (contrasena1.equals(contrasena2)) {
                String contrasenaMD5 = c.getMD5(contrasenaUsuario);
                if (contrasenaMD5 == null) {
                    message.setDetail("No se pudo crear ACTUAL. (MD5)");
                    pagina = "cambioCantrasena" + redireccion;
                } else {
                    String contrasenaMD5Nueva = c.getMD5(contrasena1);
                    if (contrasenaMD5Nueva == null) {
                        message.setDetail("No se pudo crear NUEVA. (MD5)");
                        pagina = "cambioCantrasena" + redireccion;
                    } else {
                        //Si estado es ACTIVO 
                        if (usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {
                            //Comprueba si contraseña actual en MD5 es igual a la contraseña de session
                            if (contrasenaMD5.equals(usuario.getClaveUsuario())) {
                                //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5
                                usuario.setClaveUsuario(contrasenaMD5Nueva);
                                banderaActualizacion = 1;
                            } else {
                                message.setDetail("Contraseña Actual no es valida");
                                pagina = "cambioCantrasena" + redireccion;
                            }
                            //Si estado es RESETCLAVE
                        } else if (usuario.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {
                            //Comprueba si contraseña actual sin MD5 es igual a la contraseña de session recortada en 6 digitos
                            if (usuario.getClaveUsuario().substring(0, 6).equals(contrasenaUsuario)) {
                                //Si cumple la condicion setea la contraseña de session por la contraseña nueva en md5 y se setea el estado a ACTIVO
                                usuario.setClaveUsuario(contrasenaMD5Nueva);
                                usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(1);
                                banderaActualizacion = 1;
                            } else {
                                message.setDetail("Contraseña Actual no es valida");
                                pagina = "cambioCantrasena" + redireccion;
                            }
                        } else {
                            message.setDetail("No se pudo cambiar su contraseña, intentelo de nuevo.");
                            pagina = "cambioCantrasena" + redireccion;
                        }
                        //Para ambos casos actualizamos el UsuarioWeb
                        if (banderaActualizacion == 1) {
                            if (usuariosSB.actualizarUsuariosWeb(usuario).equals("OK")) {
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                message.setSummary("Felicidades: ");
                                message.setDetail("Su contraseña ha cambiado.");
                            } else {
                                message.setDetail("No se pudo cambiar su contraseña, intentelo de nuevo.");
                                pagina = "cambioCantrasena" + redireccion;
                            }
                        }
                    }
                }
            } else {
                message.setDetail("Contraseñas actuales no son iguales.");
                pagina = "cambioCantrasena" + redireccion;
            }
        }
        context.addMessage(null, message);
        return pagina;
    }

    public String btnAjustes() {
        return "ajustesUsuario" + redireccion;
    }

    public String btnSalir() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        HttpSession session = (HttpSession) ctx.getSession(false);
        this.usuario = null;
        if (session != null) {
            this.usuario = null;
            session.invalidate();
            System.out.println("Hay session");
        }
        return "index" + redireccion;
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuarioWeb() {
        return usuario;
    }

    /**
     * @param usuarioWeb the usuario to set
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
     * @return the correoUsuario
     */
    public String getCorreoUsuario() {
        return correoUsuario;
    }

    /**
     * @param correoUsuario the mailUsuario to set
     */
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    /**
     * @return the contrasenaUsuario
     */
    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    /**
     * @param contrasenaUsuario the contrasenaUsuario to set
     */
    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
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
}
