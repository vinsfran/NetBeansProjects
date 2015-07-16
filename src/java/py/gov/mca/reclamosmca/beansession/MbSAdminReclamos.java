package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSAdminReclamos")
@SessionScoped
public class MbSAdminReclamos implements Serializable {

    @EJB
    private ReclamosSB reclamosSB;

    private List<Reclamos> reclamos;

    private Reclamos reclamo;

    public MbSAdminReclamos() {

    }

    public String btnAgregar() {
        this.setReclamo(null);
        this.setReclamo(new Reclamos());
        return "/admin_form_reclamos";
    }

    public String btnModificar(Reclamos reclamo) {
        this.setReclamo(reclamo);
        return "/admin_form_reclamos";
    }

    public String btnCancelar() {
        this.setReclamo(null);
        this.setReclamo(new Reclamos());
        return "/admin_matenimiento_reclamos";
    }

    public String btnCrear() {
        if (reclamo.getCodReclamo() == null || reclamo.getDescripcionReclamoContribuyente().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_reclamos";
        } else {
            String mensaje = reclamosSB.crearReclamos(reclamo);
            if (mensaje.equals("OK")) {
                this.reclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado.", ""));
                return "/admin_matenimiento_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear.", mensaje));
                return "/admin_form_reclamos";
            }
        }
    }

    public String btnActualizar() {
        if (reclamo.getCodReclamo() == null || reclamo.getDescripcionReclamoContribuyente().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_reclamos";
        } else {
            String mensaje = reclamosSB.actualizarReclamosAdmin(reclamo);
            if (mensaje.equals("OK")) {
                this.reclamo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado.", ""));
                return "/admin_matenimiento_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar.", mensaje));
                return "/admin_form_reclamos";
            }
        }
    }

    /**
     * @return the reclamos
     */
    public List<Reclamos> getReclamos() {
        reclamos = reclamosSB.listarReclamos();
        return reclamos;
    }

    /**
     * @param reclamos the reclamos to set
     */
    public void setReclamos(List<Reclamos> reclamos) {
        this.reclamos = reclamos;
    }

    /**
     * @return the reclamo
     */
    public Reclamos getReclamo() {
        return reclamo;
    }

    /**
     * @param reclamo the reclamo to set
     */
    public void setReclamo(Reclamos reclamo) {
        this.reclamo = reclamo;
    }

}
