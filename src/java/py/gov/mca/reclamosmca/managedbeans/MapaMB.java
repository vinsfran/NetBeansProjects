package py.gov.mca.reclamosmca.managedbeans;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import maps.java.Geocoding;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mapaMB")
@SessionScoped
public class MapaMB implements Serializable {

    private MapModel emptyModel;
    private double latitud;
    private double longitud;
    private int zoom;
    private String direccion;

    public MapaMB() {
        this.latitud = -25.3041049263554;
        this.longitud = -57.5597266852856;
        this.zoom = 15;
    }

    @PostConstruct
    public void init() {

        emptyModel = new DefaultMapModel();
    }

    public void puntoSelecionado(PointSelectEvent event) throws UnsupportedEncodingException, MalformedURLException {

        LatLng latituteLongitude = event.getLatLng();

        emptyModel = null;
        emptyModel = new DefaultMapModel();

        emptyModel.addOverlay(null);
        Marker marca = new Marker(latituteLongitude);
        marca.setTitle("CALLE");
        marca.setDraggable(false);
        emptyModel.addOverlay(marca);
        setLatitud(latituteLongitude.getLat());
        setLongitud(latituteLongitude.getLng());
        Geocoding ObjGeocod = new Geocoding();
        ArrayList<String> resultadoCI = ObjGeocod.getAddress(getLatitud(), getLongitud());
        for (String dir : resultadoCI) {
            
            System.out.println(dir);
            setDireccion(dir);
        }
        setDireccion(ObjGeocod.getAddress(getLatitud(), getLongitud()).get(0));
        if(getDireccion().toUpperCase().contains("ASUNCIÓN")){
            setDireccion(getDireccion() + " Esta en Asu");
            
        }else{
            setDireccion(getDireccion() + " No Esta en Asu");
        }
        
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Punto selecionado",
                        "Lat: " + latituteLongitude.getLat() + ", Long: " + latituteLongitude.getLng()
                        + ", Zoom: " + getZoom() + ", Dir: " + getDireccion()
                )
        );
    }

    public void onStateChange(StateChangeEvent event) {

        setZoom(event.getZoomLevel());
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitude
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
