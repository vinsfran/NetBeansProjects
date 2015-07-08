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
import py.gov.mca.reclamosmca.sessionbeans.DependenciasSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSDependencias")
@SessionScoped
public class MbSDependencias implements Serializable {

    @EJB
    private DependenciasSB dependenciasSB;

    private DataModel dependencias;

    private Dependencias dependencia;

    public MbSDependencias() {

    }

    public String btnAgregar() {
        this.dependencia = null;
        this.dependencia = new Dependencias();
        return "/admin_form_dependencias";
    }

    public String btnModificar(Dependencias dependencia) {
        this.dependencia = dependencia;
        return "/admin_form_dependencias";
    }

    public String btnCancelar() {
        this.dependencia = null;
        this.dependencia = new Dependencias();
        return "/admin_matenimiento_dependencias";
    }

    public String btnCrear() {
        if (dependencia.getNombreDependencia() == null || dependencia.getNombreDependencia().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_dependencias";
        } else {
            String mensaje = dependenciasSB.crearDependencias(dependencia);
            if (mensaje.equals("OK")) {
                this.dependencia = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado.", ""));
                return "/admin_matenimiento_dependencias";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear.", mensaje));
                return "/admin_form_dependencias";
            }
        }
    }

    public String btnActualizar() {
        if (dependencia.getNombreDependencia() == null || dependencia.getNombreDependencia().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_dependencias";
        } else {
            String mensaje = dependenciasSB.actualizarDependencias(dependencia);
            if (mensaje.equals("OK")) {
                this.dependencia = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado.", ""));
                return "/admin_matenimiento_dependencias";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar.", mensaje));
                return "/admin_form_dependencias";
            }
        }
    }

    /**
     * @return the dependencia
     */
    public Dependencias getDependencia() {
        return dependencia;
    }

    /**
     * @param dependencia the dependencia to set
     */
    public void setDependencia(Dependencias dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * @return the dependencias
     */
    public DataModel getDependencias() {
        List<Dependencias> lista = dependenciasSB.listarDependencias();
        dependencias = new ListDataModel(lista);
        return dependencias;
    }

    /**
     * @param dependencias the dependencias to set
     */
    public void setDependencias(DataModel dependencias) {
        this.dependencias = dependencias;
    }
}
