package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import maps.java.Geocoding;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "reclamosWebMB")
@SessionScoped
public class ReclamosWebMB implements Serializable {

    private String estadoReclamos;
    private String imagenSemaforo;
    private Boolean pendientes;
    private Boolean enProceso;
    private Boolean finalizados;
    private Reclamos reclamos;
    private DataModel<Reclamos> listarReclamos;
    private int currentTab;
    private String redireccion;
    private LatLng latituteLongitude;
    private String dirReclamo;
    private TiposReclamos tipoReclamo;

    private MapModel emptyModel;
    private int zoom;

    @EJB
    private TiposReclamosSB tiposReclamosSB;

    @EJB
    private ReclamosSB reclamosSB;

    public ReclamosWebMB() {
        this.redireccion = "?faces-redirect=true";
        prepararReclamo();
    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
        listarReclamosUsuariosWeb(1);
    }

    public void puntoSelecionado(PointSelectEvent event) throws UnsupportedEncodingException, MalformedURLException {
        latituteLongitude = event.getLatLng();

        emptyModel = null;
        emptyModel = new DefaultMapModel();
        emptyModel.addOverlay(null);
        Marker marca = new Marker(latituteLongitude);
        marca.setTitle(tipoReclamo.getNombreTipoReclamo());
        marca.setDraggable(false);
        emptyModel.addOverlay(marca);
        reclamos.setLatitud(latituteLongitude.getLat());
        reclamos.setLongitud(latituteLongitude.getLng());
        Geocoding ObjGeocod = new Geocoding();
        ArrayList<String> resultadoCI = ObjGeocod.getAddress(latituteLongitude.getLat(), latituteLongitude.getLng());
        for (String dir : resultadoCI) {
            System.out.println(dir);
        }
        if (ObjGeocod.getAddress(latituteLongitude.getLat(), latituteLongitude.getLng()).get(0).toUpperCase().contains("ASUNCIÓN")) {
            dirReclamo = ObjGeocod.getAddress(latituteLongitude.getLat(), latituteLongitude.getLng()).get(0);

        } else {
            dirReclamo = "DIR_FALSE";
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "No se encuentra en Asunción",
                            "Seleccione una ubicación valida."
                    )
            );
        }

    }

    public void onStateChange(StateChangeEvent event) {
        setZoom(event.getZoomLevel());
    }

    public void seleccionarReclamo() {
        tipoReclamo = tiposReclamosSB.consultarTipoReclamo(getReclamos().getFkCodTipoReclamo().getCodTipoReclamo());
        if (!emptyModel.getMarkers().isEmpty()) {
            emptyModel.getMarkers().get(0).setTitle(tipoReclamo.getNombreTipoReclamo());
        }
    }

    public String enviarReclamo() {
        Usuarios usu = recuperarUsuarioSession();
        String pagina;
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        if (tipoReclamo == null) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("Por favor!");
            message.setDetail("Seleccione un tipo de reclamo.");
            pagina = "nuevoReclamo" + redireccion;
        } else if (emptyModel.getMarkers().isEmpty()) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("Por favor!");
            message.setDetail("Seleccione la ubicación de su reclamo.");
            pagina = "nuevoReclamo" + redireccion;
        } else if (dirReclamo.equals("DIR_FALSE")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("No se encuentra en Asunción");
            message.setDetail("Seleccione una ubicación valida.");
            pagina = "nuevoReclamo" + redireccion;
        } else {
            reclamos.setFkCodUsuario(usu);
            reclamos.getFkCodUsuario().setFkCodRol(usu.getFkCodRol());
            reclamos.setFkCodEstadoReclamo(new EstadosReclamos());
            reclamos.getFkCodEstadoReclamo().setCodEstadoReclamo(1);
            reclamos.setFechaReclamo(new Date());
            reclamos.setLatitud(latituteLongitude.getLat());
            reclamos.setLongitud(latituteLongitude.getLng());
            reclamos.setDireccionReclamo(dirReclamo);
            reclamos.setFkCodTipoReclamo(tipoReclamo);
            reclamos.setOrigenReclamo("appWeb");
            if (reclamosSB.crearReclamos(reclamos).equals("OK")) {
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                message.setSummary("Gracias!");
                message.setDetail("Su reclamo fue enviado.");
                pagina = listarReclamosUsuariosWeb(1);
            } else {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("Atención!");
                message.setDetail("Ocurrio un problema al enviar su reclamo, intente de nuevo.");
                pagina = "nuevoReclamo" + redireccion;
            }
        }
        context.addMessage(null, message);
        return pagina;
    }

    public String nuevoReclamo() {
        prepararReclamo();
        return "nuevoReclamo?faces-redirect=true";
    }

    private void prepararReclamo() {
        this.emptyModel = null;
        this.emptyModel = new DefaultMapModel();
        this.reclamos = null;
        this.reclamos = new Reclamos();
        this.reclamos.setFkCodTipoReclamo(new TiposReclamos());
        this.reclamos.setLatitud(-25.3041049263554);
        this.reclamos.setLongitud(-57.5597266852856);
        this.zoom = 15;
    }

    public String changeActiveIndex(int currentTab) {
        this.currentTab = currentTab;
        switch (currentTab) {
            case 0:
                return listarReclamosUsuariosWeb(1);
            case 1:
                return listarReclamosUsuariosWeb(2);
            case 2:
                return listarReclamosUsuariosWeb(3);
            default:
                return "page5";
        }
    }

    public String listarReclamosUsuariosWeb(Integer codEstadoReclamo) {
        if (codEstadoReclamo.equals(1)) {
            setEstadoReclamos("PENDIENTES");
            setEnProceso(false);
            setFinalizados(false);
        } else if (codEstadoReclamo.equals(2)) {
            setEstadoReclamos("EN PROCESO");
            setEnProceso(true);
            setFinalizados(false);
        } else if (codEstadoReclamo.equals(3)) {
            setEstadoReclamos("FINALIZADOS");
            setEnProceso(false);
            setFinalizados(true);
        }
        List<Reclamos> lista = reclamosSB.listarPorUsuarioEstado(recuperarUsuarioSession().getCodUsuario(), codEstadoReclamo);
        listarReclamos = new ListDataModel<>(lista);
        return "listarreclamosusarios?faces-redirect=true";
    }

    public Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosWebMB login = (UsuariosWebMB) session.getAttribute("usuariosWebMB");
        return login.getUsuario();
    }

    public String formatearFecha(Date fecha) {
        // formateo
        String patron = "dd-MM-yyyy";
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        if (fecha == null) {
            return formato.format(new Date());
        } else {
            return formato.format(fecha);
        }
    }

    public int cantidadDias(Date fecha) {
        Calendar c = Calendar.getInstance();
        //Se crea un objeto calendario con la fecha del inicio del reclamo
        Calendar fechaInicio = new GregorianCalendar();
        fechaInicio.setTime(fecha);
        //Se crea un objeto calendario con la fecha actual
        Calendar hoy = Calendar.getInstance();
        //obtiene el dia
        c.setTimeInMillis(hoy.getTime().getTime() - fechaInicio.getTime().getTime());
        int dias = c.get(Calendar.DAY_OF_YEAR);
        return dias;
    }

    public int cantidadDiasPendientes(Reclamos reclamo) {
        Calendar c = Calendar.getInstance();
        //Se crea un objeto calendario con la fecha del inicio del reclamo
        Calendar fechaInicio = new GregorianCalendar();
        fechaInicio.setTime(reclamo.getFechaReclamo());
        //Se crea un objeto calendario con la fecha actual
        Calendar hoy = Calendar.getInstance();
        //obtiene el dia
        c.setTimeInMillis(hoy.getTime().getTime() - fechaInicio.getTime().getTime());
        int dias = c.get(Calendar.DAY_OF_YEAR);
        mostrarSemaforoPendientes(dias, reclamo);
        return dias;
    }

    public void mostrarSemaforoPendientes(Integer dias, Reclamos reclamo) {
        float resultado = reclamo.getFkCodTipoReclamo().getDiasMaximoPendientes();

        if (dias < resultado) {
            setImagenSemaforo("verde20.jpg");
        } else if (dias >= resultado && dias < reclamo.getFkCodTipoReclamo().getDiasMaximoPendientes()) {
            setImagenSemaforo("amarillo20.jpg");
        } else if (dias >= reclamo.getFkCodTipoReclamo().getDiasMaximoPendientes()) {
            setImagenSemaforo("rojo20.gif");
        }
    }

    public int cantidadDiasProceso(Reclamos reclamo) {
        Calendar c = Calendar.getInstance();
        //Se crea un objeto calendario con la fecha del inicio del reclamo
        Calendar fechaInicio = new GregorianCalendar();
        fechaInicio.setTime(reclamo.getFechaAtencionReclamo());
        //Se crea un objeto calendario con la fecha actual
        Calendar hoy = Calendar.getInstance();
        //obtiene el dia
        c.setTimeInMillis(hoy.getTime().getTime() - fechaInicio.getTime().getTime());
        int dias = c.get(Calendar.DAY_OF_YEAR);
        mostrarSemaforoProceso(dias, reclamo);
        return dias;
    }

    public void mostrarSemaforoProceso(Integer dias, Reclamos reclamo) {
        float resultado = reclamo.getFkCodTipoReclamo().getDiasMaximoFinalizados();

        if (dias < resultado) {
            setImagenSemaforo("verde20.jpg");
        } else if (dias >= resultado && dias < reclamo.getFkCodTipoReclamo().getDiasMaximoFinalizados()) {
            setImagenSemaforo("amarillo20.jpg");
        } else if (dias >= reclamo.getFkCodTipoReclamo().getDiasMaximoFinalizados()) {
            setImagenSemaforo("rojo20.gif");
        }
    }

    /**
     * @return the reclamos
     */
    public Reclamos getReclamos() {
        return reclamos;
    }

    /**
     * @param reclamos the reclamos to set
     */
    public void setReclamos(Reclamos reclamos) {
        this.reclamos = reclamos;
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
     * @return the listarReclamos
     */
    public DataModel getListarReclamos() {

        return listarReclamos;
    }

    /**
     * @param listarReclamos the listarReclamos to set
     */
    public void setListarReclamos(DataModel<Reclamos> listarReclamos) {
        this.listarReclamos = listarReclamos;
    }

    /**
     * @return the estadoReclamos
     */
    public String getEstadoReclamos() {
        return estadoReclamos;
    }

    /**
     * @param estadoReclamos the estadoReclamos to set
     */
    public void setEstadoReclamos(String estadoReclamos) {
        this.estadoReclamos = estadoReclamos;
    }

    /**
     * @return the enProceso
     */
    public Boolean getEnProceso() {
        return enProceso;
    }

    /**
     * @param enProceso the enProceso to set
     */
    public void setEnProceso(Boolean enProceso) {
        this.enProceso = enProceso;
    }

    /**
     * @return the finalizados
     */
    public Boolean getFinalizados() {
        return finalizados;
    }

    /**
     * @param finalizados the finalizados to set
     */
    public void setFinalizados(Boolean finalizados) {
        this.finalizados = finalizados;
    }

    /**
     * @return the imagenSemaforo
     */
    public String getImagenSemaforo() {
        return imagenSemaforo;
    }

    /**
     * @param imagenSemaforo the imagenSemaforo to set
     */
    public void setImagenSemaforo(String imagenSemaforo) {
        this.imagenSemaforo = imagenSemaforo;
    }

    /**
     * @return the currentTab
     */
    public int getCurrentTab() {
        return currentTab;
    }

    /**
     * @param currentTab the currentTab to set
     */
    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    /**
     * @return the pendientes
     */
    public Boolean getPendientes() {
        return pendientes;
    }

    /**
     * @param pendientes the pendientes to set
     */
    public void setPendientes(Boolean pendientes) {
        this.pendientes = pendientes;
    }

    /**
     * @return the latituteLongitude
     */
    public LatLng getLatituteLongitude() {
        return latituteLongitude;
    }

    /**
     * @param latituteLongitude the latituteLongitude to set
     */
    public void setLatituteLongitude(LatLng latituteLongitude) {
        this.latituteLongitude = latituteLongitude;
    }

    /**
     * @return the dirReclamo
     */
    public String getDirReclamo() {
        return dirReclamo;
    }

    /**
     * @param dirReclamo the dirReclamo to set
     */
    public void setDirReclamo(String dirReclamo) {
        this.dirReclamo = dirReclamo;
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
}
