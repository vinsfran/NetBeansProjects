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

    private DataModel tiposReclamos;

    private List<Dependencias> dependencias;

    private TiposReclamos tipoReclamo;

    public MbSTiposReclamos() {

    }

    public String btnAgregar() {
        this.tipoReclamo = null;
        this.tipoReclamo = new TiposReclamos();
        this.tipoReclamo.setFkCodDependencia(new Dependencias());
        return "/admin_form_tipos_reclamos";
    }

    public String btnModificar(TiposReclamos tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
        System.out.println("NOM " + tipoReclamo.getNombreTipoReclamo());
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
            System.out.println("DEPE " + tipoReclamo.getFkCodDependencia().getNombreDependencia());

            String mensaje = tiposReclamosSB.crearTiposReclamos(tipoReclamo);
            if (mensaje.equals("OK")) {
                this.tipoReclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de Reclamo creada.", ""));
                return "/admin_matenimiento_tipos_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se creo el Tipo de Reclamo.", mensaje));
                return "/admin_form_tipos_reclamos";
            }
        }
    }

    public String btnAcualizar() {
        if (tipoReclamo.getNombreTipoReclamo() == null || tipoReclamo.getNombreTipoReclamo().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_tipos_reclamos";
        } else {
            System.out.println("DEPE " + tipoReclamo.getFkCodDependencia().getNombreDependencia());

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
    public DataModel getTiposReclamos() {
        List<TiposReclamos> lista = tiposReclamosSB.listarTiposReclamos();
        tiposReclamos = new ListDataModel(lista);
        return tiposReclamos;
    }

    /**
     * @param tiposReclamos the tiposReclamos to set
     */
    public void setTiposReclamos(DataModel tiposReclamos) {
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
