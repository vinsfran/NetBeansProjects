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
@ManagedBean(name = "mbSAdminPersonas")
@SessionScoped
public class MbSAdminPersonas implements Serializable {

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

    private List<Usuarios> usuarios;
    private List<Personas> personas;
    private List<Roles> roles;
    private List<Dependencias> dependencias;
    private List<EstadosUsuarios> estadosUsuarios;

    private Personas persona;

    public MbSAdminPersonas() {

    }

    public String btnAgregar() {
        this.persona = null;
        this.persona = new Personas();
        return "/admin_form_personas";
    }

    public String btnModificar(Personas persona) {
        this.persona = persona;
        return "/admin_form_personas";
    }

    
     public String formatearFecha(Date fecha) {
        // formateo de fechas
        String patron = "dd-MM-yyyy";
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        if (fecha == null) {
            return "";
        } else {
            return formato.format(fecha);
        }
    }

    /**
     * @return the usuarios
     */
    public List<Usuarios> getUsuarios() {
        usuarios = adminUsuariosSB.listarUsuarios();
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the persona
     */
    public Personas getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    /**
     * @return the personas
     */
    public List<Personas> getPersonas() {
        personas = personasSB.listarPersonas();
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
