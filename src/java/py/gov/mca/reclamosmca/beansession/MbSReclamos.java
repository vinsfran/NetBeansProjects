package py.gov.mca.reclamosmca.beansession;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import maps.java.Geocoding;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
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

    private Imagenes imagenParaGuardar;

    private String dirReclamo;
    private String imagenSemaforo;

    private MapModel emptyModel;

    private int zoom;

    private LatLng latituteLongitude;

    private boolean mostrarGraphicImage;

    private DefaultStreamedContent imagenCargada;

    public MbSReclamos() {

    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public String prepararNuevoReclamo() {
        this.emptyModel = null;
        this.emptyModel = new DefaultMapModel();
        this.nuevoReclamo = null;
        this.nuevoReclamo = new Reclamos();
        this.nuevoReclamo.setFkCodTipoReclamo(new TiposReclamos());
        this.nuevoReclamo.setLatitud(-25.3041049263554);
        this.nuevoReclamo.setLongitud(-57.5597266852856);
        this.tipoDeReclamosSeleccionado = null;
        this.setMostrarGraphicImage(false);
        this.setZoom(15);
        this.imagenParaGuardar = null;
        this.imagenCargada = null;
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
        System.out.println("ENTRE EN PUNTO");
        if (this.tipoDeReclamosSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione un tipo de reclamo."));
        } else {
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
            if (ObjGeocod.getAddress(getLatituteLongitude().getLat(), getLatituteLongitude().getLng()).get(0).toUpperCase().contains("ASUNCIÓN")) {
                setDirReclamo(ObjGeocod.getAddress(getLatituteLongitude().getLat(), getLatituteLongitude().getLng()).get(0));
            } else {
                setDirReclamo("DIR_FALSE");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encuentra en Asunción", "Seleccione una ubicación valida."));
            }
        }
    }

    public String enviarReclamo() throws Exception {
        Usuarios usu = recuperarUsuarioSession();
        if (this.imagenParaGuardar != null) {
            this.nuevoReclamo.setFkImagen(new Imagenes());
            this.nuevoReclamo.setFkImagen(this.imagenParaGuardar);
        }
        if (this.tipoDeReclamosSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione un tipo de reclamo."));
            return "admin_nuevo_reclamo";
        } else if (emptyModel.getMarkers().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione la ubicación de su reclamo."));
            return "admin_nuevo_reclamo";
        } else if (dirReclamo.equals("DIR_FALSE")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor!", "Seleccione una ubicación valida."));
            return "admin_nuevo_reclamo";
        } else {
            System.out.println("Des: " + nuevoReclamo.getDescripcionReclamoContribuyente());
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

    public void cargarImagen(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        BufferedImage src = ImageIO.read(file.getInputstream());
        int valor1 = 1024;
        int valor2 = 768;
        int nAlto;
        int nAncho;
        if (src.getHeight() > src.getWidth()) {
            nAlto = valor1;
            nAncho = valor2;
        } else if (src.getHeight() < src.getWidth()) {
            nAlto = valor2;
            nAncho = valor1;
        } else {
            nAlto = valor2;
            nAncho = valor2;
        }
        try {
            //Se obtine la imagen que se va a guardar en la Base de datos
            this.imagenParaGuardar = new Imagenes();
            //this.imagenParaGuardar.setArchivoImagen(resize(file.getInputstream(), nAncho, nAlto));
            this.imagenParaGuardar.setArchivoImagen(ajustarImagen(file.getInputstream(), nAncho, nAlto, file.getContentType()));
            this.imagenParaGuardar.setTipoImagen(file.getContentType());
            this.imagenParaGuardar.setNombreImagen(file.getFileName());
            //Se convierte la imagen obtenida para mostrar como previa
            this.imagenCargada = null;
            this.imagenCargada = new DefaultStreamedContent(new ByteArrayInputStream(this.imagenParaGuardar.getArchivoImagen()), this.imagenParaGuardar.getTipoImagen());
            this.imagenCargada.setName(this.imagenParaGuardar.getNombreImagen());
            this.imagenCargada.setContentType(this.imagenParaGuardar.getTipoImagen());
            this.setMostrarGraphicImage(true);
        } catch (Exception ex) {
            Logger.getLogger(MbSReclamos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelarImagenCargada() {
        this.setMostrarGraphicImage(false);
        this.imagenParaGuardar = null;
        this.imagenCargada = null;
    }

    private byte[] ajustarImagen(InputStream imagen, int IMG_WIDTH, int IMG_HEIGHT, String tipoImagen) throws Exception {
        String tipo = tipoImagen.substring(6, tipoImagen.length());
        // InputStream inputStream = new ByteArrayInputStream(imagen);
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

    public byte[] resize(InputStream input, int width, int height) throws Exception {
        BufferedImage src = ImageIO.read(input);
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance((double) width / src.getWidth(), (double) height / src.getHeight());
        g.drawRenderedImage(src, at);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(dest, "JPG", output);
        return output.toByteArray();
    }

    public String formatearFecha(Date fecha) {
        // formateo de fechas
        String patron = "dd-MM-yyyy";
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        if (fecha == null) {
            return "";
        } else {
            return formato.format(fecha);
        }
    }

    public int tiempoTranscurrido(Reclamos reclamo) {
        //Se crean objetos calendario con la fecha actual
        Calendar hoy = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        int dias = 0;
        int ban = 0;
        int diasMaximo = 0;
        //Se crea un objeto calendario con la fecha del inicio del reclamo
        Calendar fechaInicio = new GregorianCalendar();
        if (reclamo.getFkCodEstadoReclamo().getNombreEstadoReclamo().equals("PENDIENTE")) {
            fechaInicio.setTime(reclamo.getFechaReclamo());
            diasMaximo = reclamo.getFkCodTipoReclamo().getDiasMaximoPendientes();
            ban = 1;
        } else if (reclamo.getFkCodEstadoReclamo().getNombreEstadoReclamo().equals("EN PROCESO")) {
            fechaInicio.setTime(reclamo.getFechaAtencionReclamo());
            diasMaximo = reclamo.getFkCodTipoReclamo().getDiasMaximoFinalizados();
            ban = 1;
        } else if (reclamo.getFkCodEstadoReclamo().getNombreEstadoReclamo().equals("FINALIZADO")) {
            diasMaximo = reclamo.getCantidadDiasProceso();
        }
        if (ban == 1) {
            //obtiene el dia
            c.setTimeInMillis(hoy.getTime().getTime() - fechaInicio.getTime().getTime());
            dias = c.get(Calendar.DAY_OF_YEAR);

        }
        mostrarSemaforo(dias, diasMaximo);
        if (ban == 0) {
            dias = diasMaximo;
        }
        return dias;
    }

    public void mostrarSemaforo(Integer dias, Integer diasMaximo) {
        if (dias == 0) {
            System.out.println("ENTRA EN NULL");
            setImagenSemaforo(null);
        } else if (dias < diasMaximo) {
            setImagenSemaforo("verde20.jpg");
        } else if (dias >= diasMaximo && dias < diasMaximo) {
            setImagenSemaforo("amarillo20.jpg");
        } else if (dias >= diasMaximo) {
            setImagenSemaforo("rojo20.gif");
        }
    }

    public void exportarPDF(Integer codReclamo) throws JRException, IOException, Exception {
        System.out.println("CodReclamo: " + codReclamo);
        Reclamos reclamoSeleccionado = reclamosSB.consultarReclamo(codReclamo);
        JasperReport jasper;
        Map parametros = new HashMap();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
        String urlImagen2 = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/asu128.png");

        parametros.put("urlImagen", urlImagen);
        parametros.put("urlImagen2", urlImagen2);
        parametros.put("nombreDependencia", reclamoSeleccionado.getFkCodTipoReclamo().getFkCodDependencia().getNombreDependencia());
        parametros.put("nombreTipoReclamo", reclamoSeleccionado.getFkCodTipoReclamo().getNombreTipoReclamo());
        parametros.put("cedulaPersona", reclamoSeleccionado.getFkCodUsuario().getFkCodPersona().getCedulaPersona());
        parametros.put("nombrePersona", reclamoSeleccionado.getFkCodUsuario().getFkCodPersona().getNombrePersona());
        parametros.put("apellidoPersona", reclamoSeleccionado.getFkCodUsuario().getFkCodPersona().getApellidoPersona());
        parametros.put("direccionPersona", reclamoSeleccionado.getFkCodUsuario().getFkCodPersona().getDireccionPersona());
        parametros.put("telefonoPersona", reclamoSeleccionado.getFkCodUsuario().getFkCodPersona().getTelefonoPersona());

        parametros.put("codReclamo", reclamoSeleccionado.getCodReclamo());
        parametros.put("fechaReclamo", reclamoSeleccionado.getFechaReclamo());
        parametros.put("direccionReclamo", reclamoSeleccionado.getDireccionReclamo());
        parametros.put("latitud", reclamoSeleccionado.getLatitud());
        parametros.put("longitud", reclamoSeleccionado.getLongitud());
        parametros.put("direccionReclamo", reclamoSeleccionado.getDireccionReclamo());
        parametros.put("descripcionReclamoContribuyente", reclamoSeleccionado.getDescripcionReclamoContribuyente());
        parametros.put("estadoReclamo", reclamoSeleccionado.getFkCodEstadoReclamo().getNombreEstadoReclamo());

        if (reclamoSeleccionado.getFkImagen() == null) {
            String urlImagen3 = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/blanco.png");
            File imageFile = new File(urlImagen3);
            InputStream is = new FileInputStream(imageFile);
            parametros.put("imagenReclamo", ajustarImagen(is, 640, 480, "image/png"));
        } else {
            parametros.put("imagenReclamo", reclamoSeleccionado.getFkImagen().getArchivoImagen());
        }

        if (reclamoSeleccionado.getFkCodEstadoReclamo().getNombreEstadoReclamo().equals("PENDIENTE")) {
            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamoPendienteCiudadano.jasper"));
        } else if (reclamoSeleccionado.getFkCodEstadoReclamo().getNombreEstadoReclamo().equals("EN PROCESO")) {
            parametros.put("fechaAtencion", reclamoSeleccionado.getFechaAtencionReclamo());
            parametros.put("usuarioAtencion", reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona());
            parametros.put("descripcionAtencion", reclamoSeleccionado.getDescripcionAtencionReclamo());
            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamoAtendidoCiudadano.jasper"));
        } else {
            parametros.put("fechaAtencion", reclamoSeleccionado.getFechaAtencionReclamo());
            parametros.put("usuarioAtencion", reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona());
            parametros.put("descripcionAtencion", reclamoSeleccionado.getDescripcionAtencionReclamo());
            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamoAtendidoCiudadano.jasper"));
        }

        // File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/src/java/py/gov/mca/reclamosmca/reportes/mapas.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JREmptyDataSource());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=RECLAMO_" + reclamoSeleccionado.getCodReclamo() + "_" + reclamoSeleccionado.getFkCodEstadoReclamo().getNombreEstadoReclamo() + ".pdf");
        //response.
        //Response.Write("<script>window.print();</script>"); 

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();

    }

    /**
     * @return the misReclamos
     */
    public DataModel getMisReclamos() {
        List<Reclamos> lista = reclamosSB.listarPorUsuario(recuperarUsuarioSession().getLoginUsuario());
        misReclamos = new ListDataModel(lista);
        return misReclamos;
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
     * @return the imagenCargada
     */
    public DefaultStreamedContent getImagenCargada() {
        return imagenCargada;
    }

    /**
     * @param imagenCargada the imagenCargada to set
     */
    public void setImagenCargada(DefaultStreamedContent imagenCargada) {
        this.imagenCargada = imagenCargada;
    }

    /**
     * @return the imagenParaGuardar
     */
    public Imagenes getImagenParaGuardar() {
        return imagenParaGuardar;
    }

    /**
     * @param imagenParaGuardar the imagenParaGuardar to set
     */
    public void setImagenParaGuardar(Imagenes imagenParaGuardar) {
        this.imagenParaGuardar = imagenParaGuardar;
    }

    /**
     * @return the mostrarGraphicImage
     */
    public boolean isMostrarGraphicImage() {
        return mostrarGraphicImage;
    }

    /**
     * @param mostrarGraphicImage the mostrarGraphicImage to set
     */
    public void setMostrarGraphicImage(boolean mostrarGraphicImage) {
        this.mostrarGraphicImage = mostrarGraphicImage;
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

}
