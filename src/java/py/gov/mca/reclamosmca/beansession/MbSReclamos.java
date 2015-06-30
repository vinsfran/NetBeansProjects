package py.gov.mca.reclamosmca.beansession;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import maps.java.Geocoding;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
import py.gov.mca.reclamosmca.entitys.Imagenes;
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
    private String dirReclamo;
    private LatLng latituteLongitude;
    private Part cargarImagen;
    private Part imagen;

    private String fileContent;

    public MbSReclamos() {

    }

    public String prepararNuevoReclamo() {
        this.emptyModel = null;
        this.emptyModel = new DefaultMapModel();
        this.nuevoReclamo = null;
        this.nuevoReclamo = new Reclamos();
        this.nuevoReclamo.setFkCodTipoReclamo(new TiposReclamos());
        this.nuevoReclamo.setLatitud(-25.3041049263554);
        this.nuevoReclamo.setLongitud(-57.5597266852856);
        //this.tipoDeReclamosSeleccionado = new TiposReclamos();
        this.cargarImagen = null;
        this.setZoom(15);
        return "admin_nuevo_reclamo";
    }

    public void seleccionarTipoDeReclamo(AjaxBehaviorEvent event) {
        this.tipoDeReclamosSeleccionado = tiposReclamosSB.consultarTipoReclamo(getNuevoReclamo().getFkCodTipoReclamo().getCodTipoReclamo());
        //tipoReclamo = tiposReclamosSB.consultarTipoReclamo(getReclamos().getFkCodTipoReclamo().getCodTipoReclamo());
        System.out.println("Tipo: " + getTipoDeReclamosSeleccionado().getNombreTipoReclamo());
        if (!emptyModel.getMarkers().isEmpty()) {
            emptyModel.getMarkers().get(0).setTitle(getTipoDeReclamosSeleccionado().getNombreTipoReclamo());
        }
    }

    public void puntoSelecionado(PointSelectEvent event) throws UnsupportedEncodingException, MalformedURLException {
        setLatituteLongitude(event.getLatLng());
        emptyModel = null;
        emptyModel = new DefaultMapModel();
        emptyModel.addOverlay(null);
        Marker marca = new Marker(getLatituteLongitude());
        marca.setTitle(getTipoDeReclamosSeleccionado().getNombreTipoReclamo());
        marca.setDraggable(false);
        emptyModel.addOverlay(marca);
        this.nuevoReclamo.setLatitud(getLatituteLongitude().getLat());
        this.nuevoReclamo.setLongitud(getLatituteLongitude().getLng());
        Geocoding ObjGeocod = new Geocoding();
        ArrayList<String> resultadoCI = ObjGeocod.getAddress(getLatituteLongitude().getLat(), getLatituteLongitude().getLng());
        for (String dir : resultadoCI) {
            System.out.println(dir);
        }
        if (ObjGeocod.getAddress(getLatituteLongitude().getLat(), getLatituteLongitude().getLng()).get(0).toUpperCase().contains("ASUNCIÓN")) {
            setDirReclamo(ObjGeocod.getAddress(getLatituteLongitude().getLat(), getLatituteLongitude().getLng()).get(0));

        } else {
            setDirReclamo("DIR_FALSE");
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

    public String enviarReclamo() throws Exception {
        Usuarios usu = recuperarUsuarioSession();
        if (cargarImagen != null) {
            this.nuevoReclamo.setFkImagen(new Imagenes());
            System.out.println("Nombre " + cargarImagen.getSubmittedFileName() + cargarImagen.getInputStream());
            this.nuevoReclamo.getFkImagen().setNombreImagen(cargarImagen.getSubmittedFileName());
            //        getImagen().setArchivoImagen(event.getFile().getContents());
            this.nuevoReclamo.getFkImagen().setArchivoImagen(ajustaImagen(cargarImagen.getInputStream(), 640, 480, cargarImagen.getContentType()));
            this.nuevoReclamo.getFkImagen().setTipoImagen(cargarImagen.getContentType());
        }

        if (tipoDeReclamosSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione un tipo de reclamo."));
            return "admin_nuevo_reclamo";
        } else if (emptyModel.getMarkers().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione la ubicación de su reclamo."));
            return "admin_nuevo_reclamo";
        } else if (dirReclamo.equals("DIR_FALSE")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione una ubicación valida."));
            return "admin_nuevo_reclamo";
        } else {
            nuevoReclamo.setFkCodUsuario(usu);
            nuevoReclamo.getFkCodUsuario().setFkCodRol(usu.getFkCodRol());
            nuevoReclamo.setFkCodEstadoReclamo(new EstadosReclamos());
            nuevoReclamo.getFkCodEstadoReclamo().setCodEstadoReclamo(1);
            nuevoReclamo.setFechaReclamo(new Date());
            nuevoReclamo.setLatitud(latituteLongitude.getLat());
            nuevoReclamo.setLongitud(latituteLongitude.getLng());
            nuevoReclamo.setDireccionReclamo(dirReclamo);
            nuevoReclamo.setFkCodTipoReclamo(tipoDeReclamosSeleccionado);
            nuevoReclamo.setOrigenReclamo("appWeb");
            if (reclamosSB.crearReclamos(nuevoReclamo).equals("OK")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gracias!", "Su reclamo fue enviado."));
                return "admin_mis_reclamos";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención!", "Ocurrio un problema al enviar su reclamo, intente de nuevo."));
                return "admin_nuevo_reclamo";
            }
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

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void upload() {
        System.out.println("ENNNNNTRRR");
        setFileContent(cargarImagen.getSubmittedFileName());

    }

    public void adicionarImagen(FileUploadEvent event) {
//        String codAutoparteW = event.getComponent().getAttributes().get("codAutoparte").toString();
//        Integer codAutoparteTemp = Integer.parseInt(codAutoparteW);
        System.out.println("canti: " + event.getFile().getFileName());
//        setImagen(new Imagenes());
        try {
            this.nuevoReclamo.getFkImagen().setNombreImagen(event.getFile().getFileName());
            //        getImagen().setArchivoImagen(event.getFile().getContents());
            ///   this.nuevoReclamo.getFkImagen().setArchivoImagen(ajustaImagen(event.getFile().getContents(), 640, 480, event.getFile().getContentType()));
            this.nuevoReclamo.getFkImagen().setTipoImagen(event.getFile().getContentType());
            //getImagen().setFkCodAutoparte(codAutoparteTemp);
//            ImagenesDao dao = new ImagenesDaoImpl();
//            dao.guardar(getImagen(), getAutoparte().getCantidadImagen());
//            getAutoparte().setCantidadImagen(getAutoparte().getCantidadImagen() + 1);
//            mostrarImagenesPorCodAutoparte(getAutoparte());
            FacesMessage msg = new FacesMessage("El archivo ", this.nuevoReclamo.getFkImagen().getNombreImagen() + " se cargo.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            //Logger.getLogger(ImagenesController.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("El archivo ", this.nuevoReclamo.getFkImagen().getNombreImagen() + " no se cargo.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private byte[] ajustaImagen(InputStream imagen, int IMG_WIDTH, int IMG_HEIGHT, String tipoImagen) throws Exception {
        String tipo = "";
        if (tipoImagen.equals("image/jpeg") || tipoImagen.equals("image/jpg")) {
            tipo = "jpg";
        } else if (tipoImagen.equals("image/png")) {
            tipo = "png";
        } else if (tipoImagen.equals("image/gif")) {
            tipo = "gif";
        }

        //InputStream inputStream = new ByteArrayInputStream(imagen);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage originalImage = ImageIO.read(imagen);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            ImageIO.write(resizedImage, tipo, baos);
        } catch (Throwable ex) {
            throw new Exception("Error proceso Tamaño Imagen " + ex.toString(), ex);
        }
        return baos.toByteArray();
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
     * @return the cargarImagen
     */
    public Part getCargarImagen() {

        return cargarImagen;
    }

    /**
     * @param cargarImagen the cargarImagen to set
     */
    public void setCargarImagen(Part cargarImagen) {
        this.cargarImagen = cargarImagen;
    }

    /**
     * @return the imagen
     */
    public Part getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Part imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the fileContent
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent the fileContent to set
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

}
