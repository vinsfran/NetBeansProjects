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
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
import py.gov.mca.reclamosmca.sessionbeans.EstadosReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSEstadosReclamos")
@SessionScoped
public class MbSEstadosReclamos implements Serializable {

    @EJB
    private EstadosReclamosSB estadosReclamosSB;

    private DataModel estadosReclamos;

    private EstadosReclamos estadoReclamo;

    public MbSEstadosReclamos() {

    }

    public String btnAgregar() {
        this.estadoReclamo = null;
        this.estadoReclamo = new EstadosReclamos();
        return "/admin_form_estados_reclamos";
    }

    public String btnModificar(EstadosReclamos estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
        return "/admin_form_estados_reclamos";
    }

    public String btnCancelar() {
        this.estadoReclamo = null;
        this.estadoReclamo = new EstadosReclamos();
        return "/admin_matenimiento_estados_reclamos";
    }

    public String btnCrear() {
        if (estadoReclamo.getNombreEstadoReclamo() == null || estadoReclamo.getNombreEstadoReclamo().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_dependencias";
        } else {
            String mensaje = estadosReclamosSB.crearEstadosReclamos(estadoReclamo);
            if (mensaje.equals("OK")) {
                this.estadoReclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado.", ""));
                return "/admin_matenimiento_estados_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear.", mensaje));
                return "/admin_form_estados_reclamos";
            }
        }
    }

    public String btnActualizar() {
        if (estadoReclamo.getNombreEstadoReclamo() == null || estadoReclamo.getNombreEstadoReclamo().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_estados_reclamos";
        } else {
            String mensaje = estadosReclamosSB.actualizarEstadosReclamos(estadoReclamo);
            if (mensaje.equals("OK")) {
                this.estadoReclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado.", ""));
                return "/admin_matenimiento_estados_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar.", mensaje));
                return "/admin_form_estados_reclamos";
            }
        }
    }

    /**
     * @return the estadosReclamos
     */
    public DataModel getEstadosReclamos() {
        List<EstadosReclamos> lista = estadosReclamosSB.listarEstadosReclamos();
        estadosReclamos = new ListDataModel(lista);
        return estadosReclamos;
    }

    /**
     * @param estadosReclamos the estadosReclamos to set
     */
    public void setEstadosReclamos(DataModel estadosReclamos) {
        this.estadosReclamos = estadosReclamos;
    }

    /**
     * @return the estadoReclamo
     */
    public EstadosReclamos getEstadoReclamo() {
        return estadoReclamo;
    }

    /**
     * @param estadoReclamo the estadoReclamo to set
     */
    public void setEstadoReclamo(EstadosReclamos estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
    }

}
