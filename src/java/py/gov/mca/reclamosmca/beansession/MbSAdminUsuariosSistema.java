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
import py.gov.mca.reclamosmca.entitys.Dependencias;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;
import py.gov.mca.reclamosmca.entitys.Laborales;
import py.gov.mca.reclamosmca.entitys.Personas;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.entitys.Sexos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.AdminUsuariosSB;
import py.gov.mca.reclamosmca.sessionbeans.DependenciasSB;
import py.gov.mca.reclamosmca.sessionbeans.EstadosUsuariosSB;
import py.gov.mca.reclamosmca.sessionbeans.LaboralesSB;
import py.gov.mca.reclamosmca.sessionbeans.PersonasSB;
import py.gov.mca.reclamosmca.sessionbeans.RolesSB;
import py.gov.mca.reclamosmca.sessionbeans.SexosSB;
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
    private SexosSB sexosSB;
    @EJB
    private LaboralesSB laboralesSB;
    @EJB
    private DependenciasSB dependenciasSB;

    @EJB
    private EstadosUsuariosSB estadosUsuariosSB;

    private List<Usuarios> usuarios;
    private List<Personas> personas;
    private List<Roles> roles;
    private List<Sexos> sexos;
    private List<Laborales> laborales;
    private List<Dependencias> dependencias;
    private List<EstadosUsuarios> estadosUsuarios;

    private Usuarios nuevoUsuario;
    private Personas nuevaPersona;

    private boolean activarCampoPersonaCedula;
    private boolean activarCampoPersonaNombres;
    private boolean activarCampoPersonaApellidos;
    private boolean activarCampoPersonaTelefono;
    private boolean activarCampoPersonaSexo;
    private boolean activarCampoPersonaLaboral;
    private boolean activarCampoPersonaDependencia;
    private boolean activarCampoUsuarioLogin;
    private boolean activarCampoUsuarioRoles;
    private boolean marcaParaUsuarioExistente;
    private boolean crearPersona;

    public MbSAdminUsuariosSistema() {

    }

    public String btnAgregar() {
        this.nuevoUsuario = null;
        this.nuevoUsuario = new Usuarios();
        this.nuevoUsuario.setFkCodRol(new Roles());
        this.nuevaPersona = new Personas();
        this.nuevaPersona.setFkCodLaboral(new Laborales());
        this.nuevaPersona.setFkCodSexo(new Sexos());
        desActivarCamposFormularioPersonaCrear();
        this.setActivarCampoPersonaCedula(false);
        this.marcaParaUsuarioExistente = false;
        return "/admin_form_usuarios_sistema";
    }

    public String btnModificar(Usuarios usuario) {
        this.nuevoUsuario = usuario;
        this.nuevaPersona = nuevoUsuario.getFkCodPersona();
        desActivarCamposFormularioPersonaCrear();
        this.setActivarCampoPersonaCedula(true);
        this.setActivarCampoPersonaNombres(false);
        this.setActivarCampoPersonaApellidos(false);
        this.setActivarCampoPersonaSexo(false);
        this.setActivarCampoPersonaDependencia(false);
        this.setActivarCampoPersonaLaboral(false);
        this.setActivarCampoUsuarioRoles(false);
        this.marcaParaUsuarioExistente = false;
        return "/admin_form_usuarios_sistema";
    }

    public String btnCrear() {
        if (nuevaPersona.getNombrePersona() == null || nuevaPersona.getNombrePersona().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_usuarios_sistema";
        } else {
            Converciones c = new Converciones();
            String password = nuevoUsuario.getLoginUsuario() + "01";
            nuevoUsuario.setClaveUsuario(c.getMD5(password));
            nuevoUsuario.setFkCodEstadoUsuario(new EstadosUsuarios());
            nuevoUsuario.getFkCodEstadoUsuario().setCodEstadoUsuario(1);
            String mensaje = adminUsuariosSB.crearUsuariosSistema(nuevoUsuario, nuevaPersona, isCrearPersona());
            if (mensaje.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado.", ""));
                return "/admin_matenimiento_usuarios_sistema";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se creo el Usuario.", mensaje));
                return "/admin_form_usuarios_sistema";
            }
        }
    }

    public String btnActualizar() {
        if (nuevaPersona.getNombrePersona() == null || nuevaPersona.getNombrePersona().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_usuarios_sistema";
        } else {
            Sexos nuevaSexo = new Sexos();
            nuevaSexo.setCodSexo(nuevaPersona.getFkCodSexo().getCodSexo());
            nuevaPersona.setFkCodSexo(nuevaSexo);

            Laborales nuevoLaborales = new Laborales();
            nuevoLaborales.setCodLaboral(nuevaPersona.getFkCodLaboral().getCodLaboral());
            nuevaPersona.setFkCodLaboral(nuevoLaborales);

            Dependencias nuevaDependencia = new Dependencias();
            nuevaDependencia.setCodDependencia(nuevaPersona.getFkCodDependencia().getCodDependencia());
            nuevaPersona.setFkCodDependencia(nuevaDependencia);

            nuevoUsuario.setFkCodPersona(nuevaPersona);
            
            Roles nuevoRoles = new Roles();
            nuevoRoles.setCodRol(nuevoUsuario.getFkCodRol().getCodRol());
            nuevoUsuario.setFkCodRol(nuevoRoles);            
            
            String mensaje = adminUsuariosSB.actualizarUsuarios(nuevoUsuario);
            if (mensaje.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario actualizado.", ""));
                return "/admin_matenimiento_usuarios_sistema";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se actualizo Usuario.", mensaje));
                return "/admin_form_usuarios_sistema";
            }
        }
    }

    public String btnCancelar() {
        nuevoUsuario = null;
        nuevaPersona = null;
        return "/admin_matenimiento_usuarios_sistema";
    }

    public Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        MbSUsuarios usuario = (MbSUsuarios) session.getAttribute("mbSUsuarios");
        return usuario.getUsuario();
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
        this.setActivarCampoUsuarioLogin(false);
        roles = rolesSB.listarRolesUsuariosSistema();
        if (personaBuscada != null) {
            nuevaPersona = personaBuscada;
            for (int i = 0; i < roles.size(); i++) {
                for (int j = 0; j < nuevaPersona.getUsuariosList().size(); j++) {
                    if (roles.get(i).equals(nuevaPersona.getUsuariosList().get(j).getFkCodRol())) {
                        roles.remove(i);
                    }
                }
            }
            this.setActivarCampoPersonaCedula(true);
            this.setActivarCampoPersonaNombres(true);
            this.setActivarCampoPersonaApellidos(true);
            this.setActivarCampoPersonaTelefono(true);
            this.setActivarCampoPersonaSexo(true);
            this.setActivarCampoPersonaLaboral(true);
            this.setActivarCampoPersonaDependencia(true);
            this.setCrearPersona(false);
        } else {
            this.setActivarCampoPersonaCedula(false);
            this.setActivarCampoPersonaNombres(false);
            this.setActivarCampoPersonaApellidos(false);
            this.setActivarCampoPersonaTelefono(false);
            this.setActivarCampoPersonaSexo(false);
            this.setActivarCampoPersonaLaboral(false);
            this.setActivarCampoPersonaDependencia(false);
            this.setCrearPersona(true);
            roles = rolesSB.listarRolesUsuariosSistema();
            nuevaPersona.setNombrePersona("");
            nuevaPersona.setApellidoPersona("");
            nuevaPersona.setTelefonoPersona("");
            nuevaPersona.setFechaRegistroPersona(new Date());
            nuevaPersona.setOrigenRegistro("appWeb_" + recuperarUsuarioSession().getLoginUsuario());
            nuevaPersona.setFkCodSexo(new Sexos());
            nuevaPersona.setFkCodLaboral(new Laborales());
            nuevaPersona.setFkCodDependencia(new Dependencias());
        }
    }

    public void buscarPorLogin() {
        Usuarios usuarioBuscado = adminUsuariosSB.consultarUsuarios(nuevoUsuario.getLoginUsuario());
        this.setActivarCampoUsuarioLogin(false);
        if (usuarioBuscado != null) {
            this.setActivarCampoUsuarioRoles(true);
            this.marcaParaUsuarioExistente = true;
        } else {
            this.setActivarCampoUsuarioRoles(false);
            this.marcaParaUsuarioExistente = false;
        }
    }

    private void activarCamposFormularioPersonaCrear() {
        this.setActivarCampoPersonaCedula(false);
        this.setActivarCampoPersonaNombres(false);
        this.setActivarCampoPersonaApellidos(false);
        this.setActivarCampoPersonaTelefono(false);
        this.setActivarCampoPersonaSexo(false);
        this.setActivarCampoPersonaLaboral(false);
        this.setActivarCampoPersonaDependencia(false);
        this.setActivarCampoUsuarioLogin(false);
    }

    private void desActivarCamposFormularioPersonaCrear() {
        this.setActivarCampoPersonaCedula(true);
        this.setActivarCampoPersonaNombres(true);
        this.setActivarCampoPersonaApellidos(true);
        this.setActivarCampoPersonaTelefono(true);
        this.setActivarCampoPersonaSexo(true);
        this.setActivarCampoPersonaLaboral(true);
        this.setActivarCampoPersonaDependencia(true);
        activarCamposFormularioUsuarioCrear(true);
    }

    private void activarCamposFormularioUsuarioCrear(boolean estado) {
        this.setActivarCampoUsuarioLogin(estado);
        this.setActivarCampoUsuarioRoles(estado);
        this.marcaParaUsuarioExistente = estado;
    }

    private void activarCamposFormularioModificacion(boolean estado) {
        this.setActivarCampoPersonaCedula(estado);
        this.setActivarCampoPersonaNombres(estado);
        this.setActivarCampoPersonaApellidos(estado);
        this.setActivarCampoPersonaTelefono(estado);
        this.setActivarCampoPersonaSexo(estado);
        this.setActivarCampoPersonaLaboral(estado);
        this.setActivarCampoPersonaDependencia(estado);
        this.setActivarCampoUsuarioLogin(estado);
        this.setActivarCampoUsuarioRoles(estado);
        this.marcaParaUsuarioExistente = estado;
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
        dependencias = dependenciasSB.listarDependencias();
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
     * @return the marcaParaUsuarioExistente
     */
    public boolean isMarcaParaUsuarioExistente() {
        return marcaParaUsuarioExistente;
    }

    /**
     * @param marcaParaUsuarioExistente the marcaParaUsuarioExistente to set
     */
    public void setMarcaParaUsuarioExistente(boolean marcaParaUsuarioExistente) {
        this.marcaParaUsuarioExistente = marcaParaUsuarioExistente;
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

    /**
     * @return the activarCampoPersonaCedula
     */
    public boolean isActivarCampoPersonaCedula() {
        return activarCampoPersonaCedula;
    }

    /**
     * @param activarCampoPersonaCedula the activarCampoPersonaCedula to set
     */
    public void setActivarCampoPersonaCedula(boolean activarCampoPersonaCedula) {
        this.activarCampoPersonaCedula = activarCampoPersonaCedula;
    }

    /**
     * @return the activarCampoPersonaNombres
     */
    public boolean isActivarCampoPersonaNombres() {
        return activarCampoPersonaNombres;
    }

    /**
     * @param activarCampoPersonaNombres the activarCampoPersonaNombres to set
     */
    public void setActivarCampoPersonaNombres(boolean activarCampoPersonaNombres) {
        this.activarCampoPersonaNombres = activarCampoPersonaNombres;
    }

    /**
     * @return the activarCampoPersonaApellidos
     */
    public boolean isActivarCampoPersonaApellidos() {
        return activarCampoPersonaApellidos;
    }

    /**
     * @param activarCampoPersonaApellidos the activarCampoPersonaApellidos to
     * set
     */
    public void setActivarCampoPersonaApellidos(boolean activarCampoPersonaApellidos) {
        this.activarCampoPersonaApellidos = activarCampoPersonaApellidos;
    }

    /**
     * @return the activarCampoPersonaTelefono
     */
    public boolean isActivarCampoPersonaTelefono() {
        return activarCampoPersonaTelefono;
    }

    /**
     * @param activarCampoPersonaTelefono the activarCampoPersonaTelefono to set
     */
    public void setActivarCampoPersonaTelefono(boolean activarCampoPersonaTelefono) {
        this.activarCampoPersonaTelefono = activarCampoPersonaTelefono;
    }

    /**
     * @return the activarCampoPersonaDependencia
     */
    public boolean isActivarCampoPersonaDependencia() {
        return activarCampoPersonaDependencia;
    }

    /**
     * @param activarCampoPersonaDependencia the activarCampoPersonaDependencia
     * to set
     */
    public void setActivarCampoPersonaDependencia(boolean activarCampoPersonaDependencia) {
        this.activarCampoPersonaDependencia = activarCampoPersonaDependencia;
    }

    /**
     * @return the activarCampoUsuarioLogin
     */
    public boolean isActivarCampoUsuarioLogin() {
        return activarCampoUsuarioLogin;
    }

    /**
     * @param activarCampoUsuarioLogin the activarCampoUsuarioLogin to set
     */
    public void setActivarCampoUsuarioLogin(boolean activarCampoUsuarioLogin) {
        this.activarCampoUsuarioLogin = activarCampoUsuarioLogin;
    }

    /**
     * @return the activarCampoUsuarioRoles
     */
    public boolean isActivarCampoUsuarioRoles() {
        return activarCampoUsuarioRoles;
    }

    /**
     * @param activarCampoUsuarioRoles the activarCampoUsuarioRoles to set
     */
    public void setActivarCampoUsuarioRoles(boolean activarCampoUsuarioRoles) {
        this.activarCampoUsuarioRoles = activarCampoUsuarioRoles;
    }

    /**
     * @return the activarCampoPersonaSexo
     */
    public boolean isActivarCampoPersonaSexo() {
        return activarCampoPersonaSexo;
    }

    /**
     * @param activarCampoPersonaSexo the activarCampoPersonaSexo to set
     */
    public void setActivarCampoPersonaSexo(boolean activarCampoPersonaSexo) {
        this.activarCampoPersonaSexo = activarCampoPersonaSexo;
    }

    /**
     * @return the activarCampoPersonaLaboral
     */
    public boolean isActivarCampoPersonaLaboral() {
        return activarCampoPersonaLaboral;
    }

    /**
     * @param activarCampoPersonaLaboral the activarCampoPersonaLaboral to set
     */
    public void setActivarCampoPersonaLaboral(boolean activarCampoPersonaLaboral) {
        this.activarCampoPersonaLaboral = activarCampoPersonaLaboral;
    }

    /**
     * @return the sexos
     */
    public List<Sexos> getSexos() {
        sexos = sexosSB.listarSexos();
        return sexos;
    }

    /**
     * @param sexos the sexos to set
     */
    public void setSexos(List<Sexos> sexos) {
        this.sexos = sexos;
    }

    /**
     * @return the laborales
     */
    public List<Laborales> getLaborales() {
        laborales = laboralesSB.listarLaborales();
        return laborales;
    }

    /**
     * @param laborales the laborales to set
     */
    public void setLaborales(List<Laborales> laborales) {
        this.laborales = laborales;
    }

    /**
     * @return the crearPersona
     */
    public boolean isCrearPersona() {
        return crearPersona;
    }

    /**
     * @param crearPersona the crearPersona to set
     */
    public void setCrearPersona(boolean crearPersona) {
        this.crearPersona = crearPersona;
    }

}
