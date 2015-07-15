package py.gov.mca.reclamosmca.managedbeans;

import java.io.IOException;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import maps.java.Geocoding;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.TiposFinalizacionReclamos;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposFinalizacionReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "reclamosMB")
@SessionScoped
public class ReclamosMB implements Serializable {

    private String estadoReclamos;
    private String imagenSemaforo;
    private Boolean pendientes;
    private Boolean enProceso;
    private Boolean finalizados;
    private Boolean busqueda;
    private Reclamos reclamos;
    private Reclamos reclamoSeleccionado;
    private DataModel listarReclamos;
    private DataModel listarReclamosPorZona;
    private int cantidadDeReclamosPorZona;
    private int currentTab;
    private String redireccion;
    private LatLng latituteLongitude;
    private String dirReclamo;
    private TiposReclamos tipoReclamo;
    private String procedenciaReclamo;
    private List<Reclamos> lista;
    private Boolean mostrarBotonProcesar;
    private Boolean mostrarBotonFinalizar;
    private Boolean mostrarBotonImprimir;
    private Boolean mostrarBotonExportarPDF;
    private Boolean mostrarDescriAtencion;
    private Boolean mostrarDescriFin;
    private Boolean mostrarDialogoBuscar;
    private Boolean sololecturaDescriAtencion;
    private Boolean sololecturaDescriFin;
    private String palabraBuscada;
    private String nuevoMotivoFin;
    private String mensajeAjax;
    private Date fechaInicio;
    private Date fechaFin;

    private MapModel emptyModel;
    private int zoom;

    @EJB
    private TiposReclamosSB tiposReclamosSB;

    @EJB
    private TiposFinalizacionReclamosSB tiposFinalizacionReclamosSB;

    @EJB
    private ReclamosSB reclamosSB;

    public ReclamosMB() {
        this.mensajeAjax = "";
        this.nuevoMotivoFin = "";
        this.redireccion = "?faces-redirect=true";
        prepararReclamo();
    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
        mostrarDialogoBuscar = false;
        listarReclamosUsuariosGestion(1);
        setLista(reclamosSB.listarPorDependenciaEstado(recuperarUsuarioSession().getFkCodPersona().getFkCodDependencia().getCodDependencia(), 2));
//        List<Reclamos> lista = reclamosSB.listarPorDependenciaEstado(recuperarUsuarioSession().getFkCodPersona().getFkCodDependencia().getCodDependencia(), 1);
//        listarReclamos = new ListDataModel(lista);
    }

    public void onRowSelect(SelectEvent event) {

        String destino;
        if (currentTab == 0) {
            setMostrarBotonProcesar(true);
            setMostrarBotonFinalizar(false);
            setMostrarDescriAtencion(true);
            setMostrarDescriFin(false);
            setSololecturaDescriAtencion(false);
            setSololecturaDescriFin(true);
            setBusqueda(false);
            destino = "verReclamoAdminPendiente.xhtml";
        } else if (currentTab == 1) {
            setMostrarBotonProcesar(false);
            setMostrarBotonFinalizar(true);
            setMostrarDescriAtencion(false);
            setMostrarDescriFin(true);
            setSololecturaDescriAtencion(true);
            setSololecturaDescriFin(false);
            setBusqueda(false);
            destino = "verReclamoAdminProceso.xhtml";
        } else if (currentTab == 2) {
            setBusqueda(false);
            destino = "verReclamoAdminFinalizado.xhtml";
        } else {
            setBusqueda(true);
            destino = "verReclamoAdminBuscado.xhtml";
        }

        setMostrarBotonImprimir(false);
        setMostrarBotonExportarPDF(false);
        reclamoSeleccionado = (Reclamos) event.getObject();
        reclamoSeleccionado.setFkCodTipoFinalizacionReclamo(new TiposFinalizacionReclamos());
        emptyModel = new DefaultMapModel();
        emptyModel.addOverlay(null);
        LatLng latiLongi = new LatLng(reclamoSeleccionado.getLatitud(), reclamoSeleccionado.getLongitud());
        Marker marca = new Marker(latiLongi);
        marca.setTitle(reclamoSeleccionado.getFkCodTipoReclamo().getNombreTipoReclamo());
        //marca.setIcon("../resources/images/exit.png");
        //marca.setCursor("CURSOR");

        //Buscar reclamos por tipo, que cumpla condicion de distancia maxima de 10 metros y que no tengan estado Finalizado
        if (!reclamoSeleccionado.getFkCodEstadoReclamo().getCodEstadoReclamo().equals(3)) {
            buscarReclamosPorZona(reclamoSeleccionado, reclamoSeleccionado.getFkCodTipoReclamo().getCodTipoReclamo());
        }

        if (reclamoSeleccionado.getFkCodUsuario().getFkCodRol().getCodRol().equals(6)) {
            procedenciaReclamo = "(RECLAMO EXTERNO)";
        } else {
            procedenciaReclamo = "(RECLAMO INTERNO)";
        }

        marca.setDraggable(false);
        emptyModel.addOverlay(marca);

        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();

        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/administracion/" + destino));

        try {
            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
//        FacesMessage msg = new FacesMessage("Reclamo seleccionado: ", ((Reclamos) event.getObject()).getCodReclamo().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        return "verReclamoAdmin" + redireccion;
    }

    public String actualizarReclamoPendiente() {
        String pagina;
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (reclamoSeleccionado.getDescripcionAtencionReclamo().equals("") || reclamoSeleccionado.getDescripcionAtencionReclamo().isEmpty()) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary("Campo requerido");
            message.setDetail("Debe completar el campo Descripción Atencion.");
        } else {
            //Se completa el reclamo
            reclamoSeleccionado.setFkCodEstadoReclamo(new EstadosReclamos());
            reclamoSeleccionado.getFkCodEstadoReclamo().setCodEstadoReclamo(2);
            reclamoSeleccionado.getFkCodEstadoReclamo().setNombreEstadoReclamo("EN_PROCESO");
            reclamoSeleccionado.setFkCodUsuarioAtencion(new Usuarios());
            reclamoSeleccionado.setFkCodUsuarioAtencion(recuperarUsuarioSession());
            reclamoSeleccionado.setFechaAtencionReclamo(new Date());
            reclamoSeleccionado.setFkCodTipoFinalizacionReclamo(null);
            String mensaje = reclamosSB.actualizarReclamos(reclamoSeleccionado);
            if (mensaje.equals("OK")) {
                System.out.println("ENTRO");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                message.setSummary("");
                message.setDetail("Reclamo actualizado.");
                setMostrarBotonProcesar(false);
                setMostrarBotonFinalizar(false);
                setMostrarBotonImprimir(true);
                setMostrarBotonExportarPDF(true);
                setSololecturaDescriAtencion(true);
            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary("");
                message.setDetail("Reclamo no actualizado. " + mensaje);
            }
        }
        pagina = "verReclamoAdminPendiente" + redireccion;
        context.addMessage(null, message);
        return pagina;
    }

    public String actualizarReclamoProceso() {
        String pagina = "verReclamoAdminProceso" + redireccion;
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        if (reclamoSeleccionado.getFkCodTipoFinalizacionReclamo().getCodTipoFinalizacionReclamo().equals(0)) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary("Campo requerido");
            message.setDetail("Debe seleccionar un motivo de finalización.");
        } else if (reclamoSeleccionado.getDescripcionCulminacionReclamo().equals("") || reclamoSeleccionado.getDescripcionCulminacionReclamo().isEmpty()) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary("Campo requerido");
            message.setDetail("Debe completar el campo de Trabajo realizado.");
        } else {
            reclamoSeleccionado.setFkCodEstadoReclamo(new EstadosReclamos());
            reclamoSeleccionado.getFkCodEstadoReclamo().setCodEstadoReclamo(3);
            reclamoSeleccionado.getFkCodEstadoReclamo().setNombreEstadoReclamo("FINALIZADO");
            reclamoSeleccionado.setFkCodUsuarioCulminacion(new Usuarios());
            reclamoSeleccionado.setFkCodUsuarioCulminacion(recuperarUsuarioSession());
            reclamoSeleccionado.setFechaCulminacionReclamo(new Date());
            reclamoSeleccionado.setCantidadDiasProceso(cantidadDias(reclamoSeleccionado.getFechaAtencionReclamo()));
            String mensaje = reclamosSB.actualizarReclamos(reclamoSeleccionado);
            if (mensaje.equals("OK")) {
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                message.setSummary("");
                message.setDetail("Reclamo actualizado.");
                setMostrarBotonProcesar(false);
                setMostrarBotonFinalizar(false);
                setMostrarBotonImprimir(true);
                setMostrarBotonExportarPDF(true);
                setSololecturaDescriFin(true);
                pagina = "verReclamoAdminFinalizado" + redireccion;
            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary("");
                message.setDetail("Reclamo no actualizado. " + mensaje);
            }
        }

        context.addMessage(null, message);
        return pagina;
    }

    public void consultarPorRangoDeFecha() throws JRException, IOException {
        JasperReport jasper;
        Usuarios usu = recuperarUsuarioSession();
        List<Reclamos> lista = reclamosSB.listarPorRangoDeFecha(usu.getFkCodPersona().getFkCodDependencia().getCodDependencia(), getFechaInicio(), getFechaFin());
        String depen = usu.getFkCodPersona().getFkCodDependencia().getNombreDependencia();
        int cantidadReclamos = lista.size();
        int cantidadSinAtender = 0;
        int cantidadAtendidos = 0;
        int cantidadFinalizados = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo() == 1) {
                cantidadSinAtender = cantidadSinAtender + 1;
            } else if (lista.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo() == 2) {
                cantidadAtendidos = cantidadAtendidos + 1;
            } else if (lista.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo() == 3) {
                cantidadFinalizados = cantidadFinalizados + 1;
            }
        }

        Map parametros = new HashMap();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
        parametros.put("urlImagen", urlImagen);
        parametros.put("dependencia", depen);
        parametros.put("fechaDesde", getFechaInicio());
        parametros.put("fechaHasta", getFechaFin());
        parametros.put("totalReclamos", cantidadReclamos);
        parametros.put("totalReclamosSinAtender", cantidadSinAtender);
        parametros.put("totalReclamosAtendidos", cantidadAtendidos);
        parametros.put("totalReclamosFinalizados", cantidadFinalizados);

        jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamoPorRangoDeFecha.jasper"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JREmptyDataSource());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=RECLAMOS_" + usu.getFkCodPersona().getFkCodDependencia().getNombreDependencia() + ".pdf");
        //response.
        //Response.Write("<script>window.print();</script>"); 

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarPDF() throws JRException, IOException {
        JasperReport jasper;
        Map parametros = new HashMap();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
        parametros.put("urlImagen", urlImagen);
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

        parametros.put("fechaAtencionReclamo", reclamoSeleccionado.getFechaAtencionReclamo());
        parametros.put("usuarioAtencion", reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona());
        parametros.put("descripcionAtencionReclamo", reclamoSeleccionado.getDescripcionAtencionReclamo());

        if (currentTab == 0) {
            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/RecepcionReclamo.jasper"));
        } else {
            parametros.put("fechaCulminacionReclamo", reclamoSeleccionado.getFechaCulminacionReclamo());
            parametros.put("usuarioFin", reclamoSeleccionado.getFkCodUsuarioCulminacion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioCulminacion().getFkCodPersona().getApellidoPersona());
            parametros.put("descripcionCulminacionReclamo", reclamoSeleccionado.getDescripcionCulminacionReclamo());

            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamoFinalizado.jasper"));
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

    public void imprimirPDF() throws JRException, IOException {
        JasperReport jasper;
        Map parametros = new HashMap();

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
        parametros.put("urlImagen", urlImagen);
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
        parametros.put("fechaAtencionReclamo", reclamoSeleccionado.getFechaAtencionReclamo());
        parametros.put("usuarioAtencion", reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona());

        jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/RecepcionReclamo.jasper"));

        // File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/src/java/py/gov/mca/reclamosmca/reportes/mapas.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JREmptyDataSource());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "inline;<script>window.print();</script>");

        ServletOutputStream stream = response.getOutputStream();
        System.out.println("Stream: " + response.getHeaderNames());
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();

        FacesContext.getCurrentInstance().responseComplete();

    }

    public void imprimirReporte() {

        try {

            JasperReport jasper;
            Map parametros = new HashMap();

            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
            parametros.put("urlImagen", urlImagen);
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
            parametros.put("fechaAtencionReclamo", reclamoSeleccionado.getFechaAtencionReclamo());
            parametros.put("usuarioAtencion", reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + reclamoSeleccionado.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona());

            jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/RecepcionReclamo.jasper"));

            JasperPrint masterPrint;
            JasperViewer ventana;
            boolean dialogoImpresion;
            //Imprimir directo a impresora con Dialogo de impresion
            dialogoImpresion = true;
            masterPrint = JasperFillManager.fillReport(jasper, parametros, new JREmptyDataSource());
            JasperPrintManager.printReport(masterPrint, dialogoImpresion);

        } catch (Exception e) {
            System.out.println("ERROR DE IMPRESION");
        }
    }

    public void buscarMotivoFin() {
        setBusqueda(true);
        Usuarios usu = recuperarUsuarioSession();
        Integer codDepenUsu = usu.getFkCodPersona().getFkCodDependencia().getCodDependencia();
        //Busca si existe el motivo de fin en esa dependencia
        if (tiposFinalizacionReclamosSB.consultarTiposFinalizacionReclamosPorNombreDependencia(nuevoMotivoFin, codDepenUsu)) {
            setMensajeAjax("Motivo de fin ya existe.");
        } else {
            setMensajeAjax("");
        }

    }

    public void addMotivoFin() {
        String pagina;
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        if (getNuevoMotivoFin().equals("")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            message.setSummary("Campo requerido");
            message.setDetail("El campo no puede estar vacio.");
        } else {
            Usuarios usu = recuperarUsuarioSession();
            Integer codDepenUsu = usu.getFkCodPersona().getFkCodDependencia().getCodDependencia();
            //Busca si existe el motivo de fin en esa dependencia
            if (!tiposFinalizacionReclamosSB.consultarTiposFinalizacionReclamosPorNombreDependencia(nuevoMotivoFin, codDepenUsu)) {
                TiposFinalizacionReclamos tiposFinalizacionReclamos = new TiposFinalizacionReclamos();
                tiposFinalizacionReclamos.setNombreTipoFinalizacionReclamo(nuevoMotivoFin);
               // tiposFinalizacionReclamos.setFkDependenciaTipoFinalizacionReclamo(usu.getFkCodPersona().getFkCodDependencia());
                String resultado = tiposFinalizacionReclamosSB.crearTiposFinalizacionReclamos(tiposFinalizacionReclamos);
                if (resultado.equals("OK")) {
                    this.nuevoMotivoFin = "";
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    message.setSummary("");
                    message.setDetail("Motivo de fin agregado");
                } else {
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    message.setSummary("Ocurrio un error");
                    message.setDetail(resultado);
                }

            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary("");
                message.setDetail("Motivo de fin ya existe.");
            }
        }

        pagina = "verReclamoAdminProceso" + redireccion;
        context.addMessage(null, message);
        // return pagina;
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
        Geocoding objGeocod = new Geocoding();
        if (objGeocod.getAddress(latituteLongitude.getLat(), latituteLongitude.getLng()).get(0).toUpperCase().contains("ASUNCIÓN")) {
            dirReclamo = objGeocod.getAddress(latituteLongitude.getLat(), latituteLongitude.getLng()).get(0);

        } else {
            dirReclamo = "DIR_FALSE";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encuentra en Asunción", "Seleccione una ubicación valida.")
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
            pagina = "nuevoReclamoAdmin" + redireccion;
        } else if (emptyModel.getMarkers().isEmpty()) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("Por favor!");
            message.setDetail("Seleccione la ubicación de su reclamo.");
            pagina = "nuevoReclamoAdmin" + redireccion;
        } else if (dirReclamo.equals("DIR_FALSE")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("No se encuentra en Asunción");
            message.setDetail("Seleccione una ubicación valida.");
            pagina = "nuevoReclamoAdmin" + redireccion;
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
                pagina = changeActiveIndex(0);
            } else {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("Atención!");
                message.setDetail("Ocurrio un problema al enviar su reclamo, intente de nuevo.");
                pagina = "nuevoReclamoAdmin" + redireccion;
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
                setBusqueda(false);
                return listarReclamosUsuariosGestion(1);
            case 1:
                setBusqueda(false);
                return listarReclamosUsuariosGestion(2);
            case 2:
                setBusqueda(false);
                return listarReclamosUsuariosGestion(3);
            case 3:
                setBusqueda(false);
                prepararReclamo();
                return "nuevoReclamoAdmin?faces-redirect=true";
            default:
                return "page5";
        }
    }

    public String listarReclamosUsuariosGestion(Integer codEstadoReclamo) {
        if (codEstadoReclamo.equals(1)) {
            setEstadoReclamos("RECLAMOS PENDIENTES");
            setPendientes(true);
            setEnProceso(false);
            setFinalizados(false);
        } else if (codEstadoReclamo.equals(2)) {
            setEstadoReclamos("RECLAMOS EN PROCESO");
            setPendientes(false);
            setEnProceso(true);
            setFinalizados(false);
        } else if (codEstadoReclamo.equals(3)) {
            setEstadoReclamos("RECLAMOS FINALIZADOS");
            setPendientes(false);
            setEnProceso(false);
            setFinalizados(true);
        }
        List<Reclamos> lista1 = reclamosSB.listarPorDependenciaEstado(recuperarUsuarioSession().getFkCodPersona().getFkCodDependencia().getCodDependencia(), codEstadoReclamo);
        listarReclamos = new ListDataModel(lista1);
        return "listarreclamos?faces-redirect=true";
    }

    public void buscarReclamosPorZona(Reclamos reclamos, Integer codTipoReclamo) {
        List<Reclamos> lista1 = reclamosSB.listarPorTiposReclamos(codTipoReclamo);
        List<Reclamos> lista2 = new ArrayList<>();
        for (Reclamos reclamo : lista1) {
            if (!reclamo.getFkCodEstadoReclamo().getCodEstadoReclamo().equals(3) && !reclamos.getCodReclamo().equals(reclamo.getCodReclamo())) {
                double distancia = distanciaEntrePuntos(reclamos.getLatitud(), reclamos.getLongitud(), reclamo.getLatitud(), reclamo.getLongitud());
                if (distancia < 11) {
                    lista2.add(reclamo);
                }

            }
        }
        setCantidadDeReclamosPorZona(lista2.size());
        listarReclamosPorZona = new ListDataModel(lista2);
    }

    public String verReclamosPorZona() {
        setEstadoReclamos("RECLAMOS POR ZONA");
        return "listarreclamosPorZona?faces-redirect=true";
    }

    public void abrirDialogoBuscar() {
        mostrarDialogoBuscar = true;
        RequestContext.getCurrentInstance().openDialog("dialogoBuscar");
    }

    public void cerrarDialogoBuscar() {
        mostrarDialogoBuscar = true;
        RequestContext.getCurrentInstance().closeDialog("dialogoBuscar");

    }

    public String buscarReclamos() {
        setEstadoReclamos("RESULTADO DE LA BUSQUEDA");
        setPendientes(false);
        setEnProceso(false);
        setFinalizados(false);
        setBusqueda(true);
        this.currentTab = 4;
        List<Reclamos> lista1 = reclamosSB.burcarPorDependencia(recuperarUsuarioSession().getFkCodPersona().getFkCodDependencia().getCodDependencia(), palabraBuscada);
        listarReclamos = new ListDataModel(lista1);
        mostrarDialogoBuscar = false;
        return "listarreclamos?faces-redirect=true";
    }

    public Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosMB login = (UsuariosMB) session.getAttribute("usuariosMB");
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

    private double distanciaEntrePuntos(double lat1, double lon1, double lat2, double lon2) {
        // Formula de Haversine para obtener la distancia entre dos puntos geográficos (longitud y latitud)
        // Radio de la Tierra: 6378 km.
        // R = earth’s radius (mean radius = 6,378km)
        // Δlat = lat2− lat1
        // Δlong = long2− long1
        // a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
        // c = 2.atan2(√a, √(1−a))
        // d = R.c
        double R = 6378; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c * 1000;
        return d;
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
    public void setListarReclamos(DataModel listarReclamos) {
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

    /**
     * @return the reclamoSeleccionado
     */
    public Reclamos getReclamoSeleccionado() {
        return reclamoSeleccionado;
    }

    /**
     * @param reclamoSeleccionado the reclamoSeleccionado to set
     */
    public void setReclamoSeleccionado(Reclamos reclamoSeleccionado) {
        this.reclamoSeleccionado = reclamoSeleccionado;
    }

    /**
     * @return the procedenciaReclamo
     */
    public String getProcedenciaReclamo() {
        return procedenciaReclamo;
    }

    /**
     * @param procedenciaReclamo the procedenciaReclamo to set
     */
    public void setProcedenciaReclamo(String procedenciaReclamo) {
        this.procedenciaReclamo = procedenciaReclamo;
    }

    /**
     * @return the lista
     */
    public List<Reclamos> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<Reclamos> lista) {
        this.lista = lista;
    }

    /**
     * @return the mostrarBotonProcesar
     */
    public Boolean getMostrarBotonProcesar() {
        return mostrarBotonProcesar;
    }

    /**
     * @param mostrarBotonProcesar the mostrarBotonProcesar to set
     */
    public void setMostrarBotonProcesar(Boolean mostrarBotonProcesar) {
        this.mostrarBotonProcesar = mostrarBotonProcesar;
    }

    /**
     * @return the mostrarBotonFinalizar
     */
    public Boolean getMostrarBotonFinalizar() {
        return mostrarBotonFinalizar;
    }

    /**
     * @param mostrarBotonFinalizar the mostrarBotonFinalizar to set
     */
    public void setMostrarBotonFinalizar(Boolean mostrarBotonFinalizar) {
        this.mostrarBotonFinalizar = mostrarBotonFinalizar;
    }

    /**
     * @return the mostrarBotonImprimir
     */
    public Boolean getMostrarBotonImprimir() {
        return mostrarBotonImprimir;
    }

    /**
     * @param mostrarBotonImprimir the mostrarBotonImprimir to set
     */
    public void setMostrarBotonImprimir(Boolean mostrarBotonImprimir) {
        this.mostrarBotonImprimir = mostrarBotonImprimir;
    }

    /**
     * @return the mostrarBotonExportarPDF
     */
    public Boolean getMostrarBotonExportarPDF() {
        return mostrarBotonExportarPDF;
    }

    /**
     * @param mostrarBotonExportarPDF the mostrarBotonExportarPDF to set
     */
    public void setMostrarBotonExportarPDF(Boolean mostrarBotonExportarPDF) {
        this.mostrarBotonExportarPDF = mostrarBotonExportarPDF;
    }

    /**
     * @return the sololecturaDescriAtencion
     */
    public Boolean getSololecturaDescriAtencion() {
        return sololecturaDescriAtencion;
    }

    /**
     * @param sololecturaDescriAtencion the sololecturaDescriAtencion to set
     */
    public void setSololecturaDescriAtencion(Boolean sololecturaDescriAtencion) {
        this.sololecturaDescriAtencion = sololecturaDescriAtencion;
    }

    /**
     * @return the mostrarDescriAtencion
     */
    public Boolean getMostrarDescriAtencion() {
        return mostrarDescriAtencion;
    }

    /**
     * @param mostrarDescriAtencion the mostrarDescriAtencion to set
     */
    public void setMostrarDescriAtencion(Boolean mostrarDescriAtencion) {
        this.mostrarDescriAtencion = mostrarDescriAtencion;
    }

    /**
     * @return the mostrarDescriFin
     */
    public Boolean getMostrarDescriFin() {
        return mostrarDescriFin;
    }

    /**
     * @param mostrarDescriFin the mostrarDescriFin to set
     */
    public void setMostrarDescriFin(Boolean mostrarDescriFin) {
        this.mostrarDescriFin = mostrarDescriFin;
    }

    /**
     * @return the sololecturaDescriFin
     */
    public Boolean getSololecturaDescriFin() {
        return sololecturaDescriFin;
    }

    /**
     * @param sololecturaDescriFin the sololecturaDescriFin to set
     */
    public void setSololecturaDescriFin(Boolean sololecturaDescriFin) {
        this.sololecturaDescriFin = sololecturaDescriFin;
    }

    /**
     * @return the mostrarDialogoBuscar
     */
    public Boolean getMostrarDialogoBuscar() {
        return mostrarDialogoBuscar;
    }

    /**
     * @param mostrarDialogoBuscar the mostrarDialogoBuscar to set
     */
    public void setMostrarDialogoBuscar(Boolean mostrarDialogoBuscar) {
        this.mostrarDialogoBuscar = mostrarDialogoBuscar;
    }

    /**
     * @return the palabraBuscada
     */
    public String getPalabraBuscada() {
        return palabraBuscada;
    }

    /**
     * @param palabraBuscada the palabraBuscada to set
     */
    public void setPalabraBuscada(String palabraBuscada) {
        this.palabraBuscada = palabraBuscada;
    }

    /**
     * @return the nuevoMotivoFin
     */
    public String getNuevoMotivoFin() {
        return nuevoMotivoFin;
    }

    /**
     * @param nuevoMotivoFin the nuevoMotivoFin to set
     */
    public void setNuevoMotivoFin(String nuevoMotivoFin) {
        this.nuevoMotivoFin = nuevoMotivoFin;
    }

    /**
     * @return the mensajeAjax
     */
    public String getMensajeAjax() {
        return mensajeAjax;
    }

    /**
     * @param mensajeAjax the mensajeAjax to set
     */
    public void setMensajeAjax(String mensajeAjax) {
        this.mensajeAjax = mensajeAjax;
    }

    /**
     * @return the busqueda
     */
    public Boolean getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(Boolean busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * @return the listarReclamosPorZona
     */
    public DataModel getListarReclamosPorZona() {
        return listarReclamosPorZona;
    }

    /**
     * @param listarReclamosPorZona the listarReclamosPorZona to set
     */
    public void setListarReclamosPorZona(DataModel listarReclamosPorZona) {
        this.listarReclamosPorZona = listarReclamosPorZona;
    }

    /**
     * @return the cantidadDeReclamosPorZona
     */
    public int getCantidadDeReclamosPorZona() {
        return cantidadDeReclamosPorZona;
    }

    /**
     * @param cantidadDeReclamosPorZona the cantidadDeReclamosPorZona to set
     */
    public void setCantidadDeReclamosPorZona(int cantidadDeReclamosPorZona) {
        this.cantidadDeReclamosPorZona = cantidadDeReclamosPorZona;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
