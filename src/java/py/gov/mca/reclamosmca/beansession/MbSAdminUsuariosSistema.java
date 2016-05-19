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
@ManagedBean(name = "mbSAdminUsuariosSistema")
@SessionScoped
public class MbSAdminUsuariosSistema implements Serializable {

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

    private Usuarios nuevoUsuario;
    private Personas nuevaPersona;

    private boolean activarCamposNuevoUsuario;
    private boolean activarCampoCorreo;
    private boolean activarCampoDireccion;
    private boolean activarCamposCuenta;
    private boolean activarCamposTelefono;
    private boolean marcaParaNuevoUsuario;

    public MbSAdminUsuariosSistema() {

    }

    public String btnAgregar() {
        this.nuevoUsuario = null;
        this.nuevoUsuario = new Usuarios();
        this.nuevaPersona = new Personas();
        this.activarCamposNuevoUsuario = true;
        return "/admin_form_usuarios_sistema";
    }

    public String btnModificar(Usuarios usuario) {
        this.nuevoUsuario = usuario;
        return "/admin_form_usuarios_sistema";
    }

    public String btnResetClave(Usuarios usuario) {
        String resetClave = usuario.getLoginUsuario() + "01";
        Converciones c = new Converciones();
        String contrasenaMD5 = c.getMD5(resetClave);
        usuario.setClaveUsuario(contrasenaMD5);
        String mensaje = adminUsuariosSB.actualizarUsuarios(usuario);
        if (mensaje.equals("OK")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave reestablecida.", ""));
            return "/admin_matenimiento_usuarios_sistema";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo reestablecida.", mensaje));
            return "/admin_matenimiento_usuarios_sistema";
        }
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

    public void buscarPorCedula() {
        Personas personaBuscada = personasSB.consultarPersonaCedula(nuevaPersona.getCedulaPersona());
        if (personaBuscada != null) {
            this.setActivarCamposNuevoUsuario(true);
            nuevaPersona = personaBuscada;
            int banderaUsuarioBuscado = 0;
            for (int i = 0; personaBuscada.getUsuariosList().size() > i; i++) {
                if (personaBuscada.getUsuariosList().get(i).getFkCodRol().getCodRol().equals(6)) {
                    banderaUsuarioBuscado = 1;
                    nuevoUsuario = personaBuscada.getUsuariosList().get(i);
                }
            }
            if (banderaUsuarioBuscado == 1) {
                this.setActivarCampoCorreo(true);
                this.setMarcaParaNuevoUsuario(false);
            } else {
                this.setActivarCampoCorreo(false);
                this.setMarcaParaNuevoUsuario(true);
            }
            nuevoUsuario.setFkCodPersona(personaBuscada);
            if (personaBuscada.getDireccionPersona() == null || personaBuscada.getDireccionPersona().isEmpty()) {
                this.setActivarCampoDireccion(false);
            } else {
                this.setActivarCampoDireccion(true);
            }
            if (personaBuscada.getCtaCtePersona() == null || personaBuscada.getCtaCtePersona().isEmpty()) {
                this.setActivarCamposCuenta(false);
            } else {
                this.setActivarCamposCuenta(true);
            }
            if (personaBuscada.getTelefonoPersona() == null || personaBuscada.getTelefonoPersona().isEmpty()) {
                this.setActivarCamposTelefono(false);
            } else {
                this.setActivarCamposTelefono(true);
            }

        } else {
            this.setMarcaParaNuevoUsuario(true);
            this.setActivarCamposNuevoUsuario(false);
            this.setActivarCampoDireccion(false);
            this.setActivarCamposCuenta(false);
            this.setActivarCamposTelefono(false);
            nuevaPersona.setNombrePersona("");
            nuevaPersona.setApellidoPersona("");
            nuevoUsuario.setFkCodPersona(nuevaPersona);
        }
    }

    /**
     * @return the usuarios
     */
    public List<Usuarios> getUsuarios() {
        usuarios = adminUsuariosSB.listarUsuariosSistema();
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the nuevoUsuario
     */
    public Usuarios getNuevoUsuario() {
        return nuevoUsuario;
    }

    /**
     * @param nuevoUsuario the nuevoUsuario to set
     */
    public void setNuevoUsuario(Usuarios nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
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
        roles = rolesSB.listarRolesUsuariosSistema();
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

    /**
     * @return the activarCamposNuevoUsuario
     */
    public boolean isActivarCamposNuevoUsuario() {
        return activarCamposNuevoUsuario;
    }

    /**
     * @param activarCamposNuevoUsuario the activarCamposNuevoUsuario to set
     */
    public void setActivarCamposNuevoUsuario(boolean activarCamposNuevoUsuario) {
        this.activarCamposNuevoUsuario = activarCamposNuevoUsuario;
    }

    /**
     * @return the activarCampoCorreo
     */
    public boolean isActivarCampoCorreo() {
        return activarCampoCorreo;
    }

    /**
     * @param activarCampoCorreo the activarCampoCorreo to set
     */
    public void setActivarCampoCorreo(boolean activarCampoCorreo) {
        this.activarCampoCorreo = activarCampoCorreo;
    }

    /**
     * @return the activarCampoDireccion
     */
    public boolean isActivarCampoDireccion() {
        return activarCampoDireccion;
    }

    /**
     * @param activarCampoDireccion the activarCampoDireccion to set
     */
    public void setActivarCampoDireccion(boolean activarCampoDireccion) {
        this.activarCampoDireccion = activarCampoDireccion;
    }

    /**
     * @return the activarCamposCuenta
     */
    public boolean isActivarCamposCuenta() {
        return activarCamposCuenta;
    }

    /**
     * @param activarCamposCuenta the activarCamposCuenta to set
     */
    public void setActivarCamposCuenta(boolean activarCamposCuenta) {
        this.activarCamposCuenta = activarCamposCuenta;
    }

    /**
     * @return the activarCamposTelefono
     */
    public boolean isActivarCamposTelefono() {
        return activarCamposTelefono;
    }

    /**
     * @param activarCamposTelefono the activarCamposTelefono to set
     */
    public void setActivarCamposTelefono(boolean activarCamposTelefono) {
        this.activarCamposTelefono = activarCamposTelefono;
    }

    /**
     * @return the marcaParaNuevoUsuario
     */
    public boolean isMarcaParaNuevoUsuario() {
        return marcaParaNuevoUsuario;
    }

    /**
     * @param marcaParaNuevoUsuario the marcaParaNuevoUsuario to set
     */
    public void setMarcaParaNuevoUsuario(boolean marcaParaNuevoUsuario) {
        this.marcaParaNuevoUsuario = marcaParaNuevoUsuario;
    }

    /**
     * @return the nuevaPersona
     */
    public Personas getNuevaPersona() {
        return nuevaPersona;
    }

    /**
     * @param nuevaPersona the nuevaPersona to set
     */
    public void setNuevaPersona(Personas nuevaPersona) {
        this.nuevaPersona = nuevaPersona;
    }

}
