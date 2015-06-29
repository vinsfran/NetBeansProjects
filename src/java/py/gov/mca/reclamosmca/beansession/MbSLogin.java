package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;
import py.gov.mca.reclamosmca.utiles.Converciones;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSLogin")
@SessionScoped

public class MbSLogin implements Serializable {

    @EJB
    private UsuariosSB usuariosSB;

    private String loginUsuario;
    private String claveUsuario;
    private String contrasena1;
    private String contrasena2;

    private Usuarios usuario;

    public MbSLogin() {

    }

    public String btnIngresar() {

        Converciones c = new Converciones();
        String contrasenaMD5 = c.getMD5(getClaveUsuario());
        if (contrasenaMD5 == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validación de ingreso:", "No se pudo crear. (MD5)"));
            return "/login";
        } else {
            this.usuario = usuariosSB.consultarUsuarios(getLoginUsuario());
            if (getUsuario() != null) {
                if (getUsuario().getClaveUsuario().equals(contrasenaMD5) && getUsuario().getFkCodEstadoUsuario().getCodEstadoUsuario().equals(1)) {

                    return "/admin_mis_reclamos";

                } else if (getUsuario().getClaveUsuario().equals(contrasenaMD5) && getUsuario().getFkCodEstadoUsuario().getCodEstadoUsuario().equals(2)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Usuario Inactivo"));
                    return "/login";
                }
            }
        }
        this.loginUsuario = null;
        this.claveUsuario = null;
        setUsuario(null);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación de ingreso:", "Usuario o contraseña no valido."));
        return "/login";
    }
    
    public String btnSalir() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        HttpSession session = (HttpSession) ctx.getSession(false);
        this.setUsuario(null);
        if (session != null) {
            this.setUsuario(null);
            session.invalidate();
        }
        return "/index2";
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

}
