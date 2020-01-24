package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.gov.mca.reclamosmca.entitys.ElementosWeb;
import py.gov.mca.reclamosmca.sessionbeans.ElementosWebSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSElementosWeb")
@SessionScoped
public class MbSElementosWeb implements Serializable{
    
    @EJB
    private ElementosWebSB elementosWebSB;
    
    private List<ElementosWeb> elementosWeb;
    
    private ElementosWeb elementoWeb;
    
    public MbSElementosWeb(){
        
    }
    
    public String btnAgregar() {
        this.setElementoWeb(null);
        this.setElementoWeb(new ElementosWeb());
        return "/admin_form_elementos_web";
    }
    
    public String btnModificar(ElementosWeb elementoWeb) {
        this.setElementoWeb(elementoWeb);
        return "/admin_form_elementos_web";
    }

    public String btnCancelar() {
        this.setElementoWeb(null);
        this.setElementoWeb(new ElementosWeb());
        return "/admin_matenimiento_elementos_web";
    }

    public String btnCrear() {
        if (getElementoWeb().getNombreElementoWeb() == null || getElementoWeb().getNombreElementoWeb().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_elementos_web";
        } else {
            String mensaje = elementosWebSB.crearElementosWeb(getElementoWeb());
            if (mensaje.equals("OK")) {
                this.setElementoWeb(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Creado.", ""));
                return "/admin_matenimiento_elementos_web";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo crear.", mensaje));
                return "/admin_form_elementos_web";
            }
        }
    }

    public String btnActualizar() {
        if (getElementoWeb().getCodElementoWeb() == null || getElementoWeb().getNombreElementoWeb().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos con (*) no pueden estar vacio.", ""));
            return "/admin_form_elementos_web";
        } else {
            String mensaje = elementosWebSB.actualizarElementosWeb(getElementoWeb());
            if (mensaje.equals("OK")) {
                this.setElementoWeb(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado.", ""));
                return "/admin_matenimiento_elementos_web";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo actualizar.", mensaje));
                return "/admin_form_elementos_web";
            }
        }
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
    
}
