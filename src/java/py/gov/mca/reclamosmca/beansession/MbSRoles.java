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
import py.gov.mca.reclamosmca.entitys.ElementosWeb;
import py.gov.mca.reclamosmca.entitys.PermisosElementosWeb;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.sessionbeans.ElementosWebSB;
import py.gov.mca.reclamosmca.sessionbeans.PermisosElementosWebSB;
import py.gov.mca.reclamosmca.sessionbeans.RolesSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSRoles")
@SessionScoped
public class MbSRoles implements Serializable {

    @EJB
    private RolesSB rolesSB;

    @EJB
    private ElementosWebSB elementosWebSB;

    @EJB
    private PermisosElementosWebSB permisosElementosWebSB;

    private DataModel roles;

    private List<ElementosWeb> elementosWeb;

    private Roles rol;
    private ElementosWeb elementoWeb;
    private PermisosElementosWeb permisosElementosWeb;

    private boolean elementoVisible;
    private boolean elementoDesactivado;
    private boolean estadoBtnActualizar;

    public MbSRoles() {

    }

    public String btnAgregar() {
        this.rol = null;
        this.rol = new Roles();
        return "/admin_form_roles";
    }

    public String btnModificar(Roles rol) {
        this.rol = rol;
        this.rol = rolesSB.consultarRol(rol.getCodRol());
        this.elementoWeb = new ElementosWeb();
        this.elementoVisible = false;
        this.elementoDesactivado = false;
        this.estadoBtnActualizar = true;
        this.permisosElementosWeb = new PermisosElementosWeb();

        return "/admin_form_roles";
    }

    public String btnCancelar() {
        this.rol = null;
        this.rol = new Roles();
        return "/admin_matenimiento_roles";
    }

    public String btnCrear() {
        if (this.rol.getNombreRol() == null || this.rol.getNombreRol().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_roles";
        } else {

            String mensaje = rolesSB.crearRoles(this.rol);
            if (mensaje.equals("OK")) {
                this.rol = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado.", ""));
                return "/admin_matenimiento_roles";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear.", mensaje));
                return "/admin_form_roles";
            }
        }
    }

    public String btnActualizarPermisos() {
        if (this.rol == null || this.rol.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_roles";
        } else {

            permisosElementosWeb.setValorVisible(elementoVisible + "");
            permisosElementosWeb.setValorDesactivado(elementoDesactivado + "");

            String mensaje = permisosElementosWebSB.actualizarPermisosElementosWeb(permisosElementosWeb);
            if (mensaje.equals("OK")) {
                setElementosWeb(null);
                getElementosWeb();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado.", ""));
                return "/admin_form_roles";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar.", mensaje));
                return "/admin_form_roles";
            }

        }
    }

    public void buscarEstadoPermisos() {
        
        System.out.println("MbSRoles buscarEstadoPermisos rol " + rol.getCodRol());
        System.out.println("MbSRoles buscarEstadoPermisos elemento " + elementoWeb.getCodElementoWeb());
        this.elementoWeb = elementosWebSB.consultarElementoWeb(elementoWeb.getCodElementoWeb());
        String detalleDelPermiso = "Permiso para elemento: " + elementoWeb.getDescripcionDelElementoWeb() + " - Rol: " + this.rol.getNombreRol();
        this.elementoVisible = false;
        this.elementoDesactivado = true;
        this.estadoBtnActualizar = false;
        permisosElementosWeb = permisosElementosWebSB.consultarCodRolCodElementoWeb(rol.getCodRol(), elementoWeb.getCodElementoWeb());
        if (permisosElementosWeb == null) {
            System.out.println("MbSRoles buscarEstadoPermisos Null");
            permisosElementosWeb = new PermisosElementosWeb();
            permisosElementosWeb.setFkCodRol(rol);
            permisosElementosWeb.setFkCodElementoWeb(elementoWeb);
            permisosElementosWeb.setValorVisible("false");
            permisosElementosWeb.setValorDesactivado("true");
            permisosElementosWeb.setDetalleDelPermiso(detalleDelPermiso);

        } else {
            System.out.println("MbSRoles buscarEstadoPermisos NO Null");
            permisosElementosWeb.setDetalleDelPermiso(detalleDelPermiso);
            if (permisosElementosWeb.getValorVisible().trim().equals("true")) {
                this.elementoVisible = true;
            }

            if (permisosElementosWeb.getValorDesactivado().trim().equals("false")) {
                this.elementoDesactivado = false;
            }
        }

    }

    /**
     * @return the roles
     */
    public DataModel getRoles() {
        List<Roles> lista = rolesSB.listarRoles();
        roles = new ListDataModel(lista);
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(DataModel roles) {
        this.roles = roles;
    }

    /**
     * @return the rol
     */
    public Roles getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Roles rol) {
        this.rol = rol;
    }

    /**
     * @return the elementoVisible
     */
    public boolean isElementoVisible() {
        return elementoVisible;
    }

    /**
     * @param elementoVisible the elementoVisible to set
     */
    public void setElementoVisible(boolean elementoVisible) {
        this.elementoVisible = elementoVisible;
    }

    /**
     * @return the elementoDesactivado
     */
    public boolean isElementoDesactivado() {
        return elementoDesactivado;
    }

    /**
     * @param elementoDesactivado the elementoDesactivado to set
     */
    public void setElementoDesactivado(boolean elementoDesactivado) {
        this.elementoDesactivado = elementoDesactivado;
    }

    /**
     * @return the elementosWeb
     */
    public List<ElementosWeb> getElementosWeb() {
        elementosWeb = elementosWebSB.listarElementosWeb();
        return elementosWeb;
    }

    /**
     * @param elementosWeb the elementosWeb to set
     */
    public void setElementosWeb(List<ElementosWeb> elementosWeb) {
        this.elementosWeb = elementosWeb;
    }

    /**
     * @return the elementoWeb
     */
    public ElementosWeb getElementoWeb() {
        return elementoWeb;
    }

    /**
     * @param elementoWeb the elementoWeb to set
     */
    public void setElementoWeb(ElementosWeb elementoWeb) {
        this.elementoWeb = elementoWeb;
    }

    /**
     * @return the permisosElementosWeb
     */
    public PermisosElementosWeb getPermisosElementosWeb() {
        return permisosElementosWeb;
    }

    /**
     * @param permisosElementosWeb the permisosElementosWeb to set
     */
    public void setPermisosElementosWeb(PermisosElementosWeb permisosElementosWeb) {
        this.permisosElementosWeb = permisosElementosWeb;
    }

    /**
     * @return the estadoBtnActualizar
     */
    public boolean isEstadoBtnActualizar() {
        return estadoBtnActualizar;
    }

    /**
     * @param estadoBtnActualizar the estadoBtnActualizar to set
     */
    public void setEstadoBtnActualizar(boolean estadoBtnActualizar) {
        this.estadoBtnActualizar = estadoBtnActualizar;
    }

}
