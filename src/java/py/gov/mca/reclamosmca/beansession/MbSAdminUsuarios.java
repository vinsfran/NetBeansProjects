package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import py.gov.mca.reclamosmca.entitys.Dependencias;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;
import py.gov.mca.reclamosmca.entitys.Personas;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.AdminUsuariosSB;
import py.gov.mca.reclamosmca.sessionbeans.DependenciasSB;
import py.gov.mca.reclamosmca.sessionbeans.EstadosUsuariosSB;
import py.gov.mca.reclamosmca.sessionbeans.PersonasSB;
import py.gov.mca.reclamosmca.sessionbeans.RolesSB;
import py.gov.mca.reclamosmca.utiles.Converciones;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSAdminUsuarios")
@SessionScoped
public class MbSAdminUsuarios implements Serializable {

    @EJB
    private AdminUsuariosSB adminUsuariosSB;
    
    @EJB
    private PersonasSB personasSB;
    
    @EJB
    private RolesSB rolesSB;
    
    @EJB
    private DependenciasSB dependenciasSB;
    
    @EJB
    private EstadosUsuariosSB estadosUsuariosSB;

    private DataModel usuarios;
    
    private List<Personas> personas;
    private List<Roles> roles;
    private List<Dependencias> dependencias;
    private List<EstadosUsuarios> estadosUsuarios;

    private Usuarios usuario;

    public MbSAdminUsuarios() {

    }

    public String btnAgregar() {
        this.usuario = null;
        this.usuario = new Usuarios();
        return "/admin_form_usuarios";
    }

    public String btnModificar(Usuarios usuario) {
        this.usuario = usuario;
        return "/admin_form_usuarios";
    }

    public String btnResetClave(Usuarios usuario) {
        String resetClave = usuario.getLoginUsuario() + "01";
        Converciones c = new Converciones();
        String contrasenaMD5 = c.getMD5(resetClave);
        usuario.setClaveUsuario(contrasenaMD5);
        String mensaje = adminUsuariosSB.actualizarUsuarios(usuario);
        if (mensaje.equals("OK")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave reestablecida.", ""));
            return "/admin_matenimiento_usuarios";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo reestablecida.", mensaje));
            return "/admin_matenimiento_usuarios";
        }
    }

    /**
     * @return the usuarios
     */
    public DataModel getUsuarios() {
        List<Usuarios> lista = adminUsuariosSB.listarUsuarios();
        usuarios = new ListDataModel(lista);
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(DataModel usuarios) {
        this.usuarios = usuarios;
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
     * @return the personas
     */
    public List<Personas> getPersonas() {
        return personas;
    }

    /**
     * @param personas the personas to set
     */
    public void setPersonas(List<Personas> personas) {
        this.personas = personas;
    }

    /**
     * @return the roles
     */
    public List<Roles> getRoles() {        
        roles = rolesSB.listarRoles();
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    /**
     * @return the dependencias
     */
    public List<Dependencias> getDependencias() {
        return dependencias;
    }

    /**
     * @param dependencias the dependencias to set
     */
    public void setDependencias(List<Dependencias> dependencias) {
        this.dependencias = dependencias;
    }

    /**
     * @return the estadosUsuarios
     */
    public List<EstadosUsuarios> getEstadosUsuarios() {
        return estadosUsuarios;
    }

    /**
     * @param estadosUsuarios the estadosUsuarios to set
     */
    public void setEstadosUsuarios(List<EstadosUsuarios> estadosUsuarios) {
        this.estadosUsuarios = estadosUsuarios;
    }

}
