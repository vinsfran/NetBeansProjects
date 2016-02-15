package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.gov.mca.reclamosmca.entitys.Dependencias;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.sessionbeans.DependenciasSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSTiposReclamos")
@SessionScoped
public class MbSTiposReclamos implements Serializable {
    
    @EJB
    private TiposReclamosSB tiposReclamosSB;
    
    @EJB
    private DependenciasSB dependenciasSB;
    
    private List<TiposReclamos> tiposReclamos;    
    private List<Dependencias> dependencias;
    
    private TiposReclamos tipoReclamo;
    
    public MbSTiposReclamos() {
        
    }
    
    public String btnAgregar() {
        this.tipoReclamo = null;
        this.tipoReclamo = new TiposReclamos();
        this.tipoReclamo.setTopTipoReclamo(0.0);
        this.tipoReclamo.setFkCodDependencia(new Dependencias());
        return "/admin_form_tipos_reclamos";
    }
    
    public String btnModificar(TiposReclamos tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
        return "/admin_form_tipos_reclamos";
    }
    
    public String btnCancelar() {
        tipoReclamo = null;
        tipoReclamo = new TiposReclamos();
        return "/admin_matenimiento_tipos_reclamos";
    }
    
    public String btnCrear() {
        if (tipoReclamo.getNombreTipoReclamo() == null || tipoReclamo.getNombreTipoReclamo().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_tipos_reclamos";
        } else {
            String nombreMayuscula = tipoReclamo.getNombreTipoReclamo().toUpperCase();
            tipoReclamo.setNombreTipoReclamo(nombreMayuscula);
            String mensaje = tiposReclamosSB.crearTiposReclamos(tipoReclamo);
            if (mensaje.equals("OK")) {
                this.tipoReclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Reclamo creado.", ""));
                return "/admin_matenimiento_tipos_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se creo el Tipo de Reclamo.", mensaje));
                return "/admin_form_tipos_reclamos";
            }
        }
    }
    
    public String btnActualizar() {
        if (tipoReclamo.getNombreTipoReclamo() == null || tipoReclamo.getNombreTipoReclamo().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_tipos_reclamos";
        } else {
            Dependencias nuevaDependencia = new Dependencias();
            nuevaDependencia.setCodDependencia(tipoReclamo.getFkCodDependencia().getCodDependencia());
            tipoReclamo.setFkCodDependencia(nuevaDependencia);
            String nombreMayuscula = tipoReclamo.getNombreTipoReclamo().toUpperCase();
            tipoReclamo.setNombreTipoReclamo(nombreMayuscula);
            String mensaje = tiposReclamosSB.actualizarTiposReclamos(tipoReclamo);
            if (mensaje.equals("OK")) {
                this.tipoReclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Reclamo actualizado.", ""));
                return "/admin_matenimiento_tipos_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se actualizo Tipo de Reclamo.", mensaje));
                return "/admin_form_tipos_reclamos";
            }
        }
    }

    /**
     * @return the tiposReclamos
     */
    public List<TiposReclamos> getTiposReclamos() { 
        tiposReclamos = tiposReclamosSB.listarTiposReclamos();
        return tiposReclamos;
    }

    /**
     * @param tiposReclamos the tiposReclamos to set
     */
    public void setTiposReclamos(List<TiposReclamos> tiposReclamos) {
        this.tiposReclamos = tiposReclamos;
    }

    /**
     * @return the tipoReclamo
     */
    public TiposReclamos getTipoReclamo() {
        return tipoReclamo;
    }

    /**
     * @param tipoReclamo the tipoReclamo to set
     */
    public void setTipoReclamo(TiposReclamos tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
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
    
}
