package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSReclamos")
@SessionScoped
public class MbSReclamos implements Serializable {

    @EJB
    private TiposReclamosSB tiposReclamosSB;
    @EJB
    private ReclamosSB reclamosSB;

    private DataModel misReclamos;
    private List<TiposReclamos> tiposDeReclamos;
    private TiposReclamos tipoDeReclamosSeleccionado;
    private Reclamos nuevoReclamo;

    private MapModel emptyModel;
    private int zoom;
    private String latitud;
    private String longitud;

    public MbSReclamos() {

    }

    public String prepararNuevoReclamo() {
        this.emptyModel = null;
        this.emptyModel = new DefaultMapModel();
        this.nuevoReclamo = null;
        this.nuevoReclamo = new Reclamos();
        this.nuevoReclamo.setFkCodTipoReclamo(new TiposReclamos());
        this.latitud = "-25.3041049263554";
        this.longitud = "-57.5597266852856";
        this.setZoom(15);
        return "admin_nuevo_reclamo";
    }
    
    public void seleccionarTipoDeReclamo(AjaxBehaviorEvent event) {
        this.tipoDeReclamosSeleccionado = (TiposReclamos) event.getComponent().getAttributes().get("value");
        //tipoReclamo = tiposReclamosSB.consultarTipoReclamo(getReclamos().getFkCodTipoReclamo().getCodTipoReclamo());
        System.out.println("Tipo: " + getTipoDeReclamosSeleccionado().getNombreTipoReclamo());
        if (!emptyModel.getMarkers().isEmpty()) {
            emptyModel.getMarkers().get(0).setTitle(getTipoDeReclamosSeleccionado().getNombreTipoReclamo());
        }
    }
    
    public void onStateChange(StateChangeEvent event) {
        setZoom(event.getZoomLevel());
    }

    /**
     * @return the misReclamos
     */
    public DataModel getMisReclamos() {
        List<Reclamos> lista = reclamosSB.listarPorUsuario(recuperarUsuarioSession().getLoginUsuario());
        misReclamos = new ListDataModel(lista);
        return misReclamos;
    }

    public Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        MbSLogin login = (MbSLogin) session.getAttribute("mbSLogin");
        return login.getUsuario();
    }

    /**
     * @param misReclamos the misReclamos to set
     */
    public void setMisReclamos(DataModel misReclamos) {
        this.misReclamos = misReclamos;
    }

    /**
     * @return the tiposDeReclamos
     */
    public List<TiposReclamos> getTiposDeReclamos() {
        tiposDeReclamos = tiposReclamosSB.listarTiposReclamos();
        return tiposDeReclamos;
    }

    /**
     * @param tiposDeReclamos the tiposDeReclamos to set
     */
    public void setTiposDeReclamos(List<TiposReclamos> tiposDeReclamos) {
        this.tiposDeReclamos = tiposDeReclamos;
    }

    /**
     * @return the latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the nuevoReclamo
     */
    public Reclamos getNuevoReclamo() {
        return nuevoReclamo;
    }

    /**
     * @param nuevoReclamo the nuevoReclamo to set
     */
    public void setNuevoReclamo(Reclamos nuevoReclamo) {
        this.nuevoReclamo = nuevoReclamo;
    }

    /**
     * @return the emptyModel
     */
    public MapModel getEmptyModel() {
        return emptyModel;
    }

    /**
     * @param emptyModel the emptyModel to set
     */
    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    /**
     * @return the zoom
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    /**
     * @return the tipoDeReclamosSeleccionado
     */
    public TiposReclamos getTipoDeReclamosSeleccionado() {
        return tipoDeReclamosSeleccionado;
    }

    /**
     * @param tipoDeReclamosSeleccionado the tipoDeReclamosSeleccionado to set
     */
    public void setTipoDeReclamosSeleccionado(TiposReclamos tipoDeReclamosSeleccionado) {
        this.tipoDeReclamosSeleccionado = tipoDeReclamosSeleccionado;
    }

}
