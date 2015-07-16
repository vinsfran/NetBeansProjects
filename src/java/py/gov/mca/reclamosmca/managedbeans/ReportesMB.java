package py.gov.mca.reclamosmca.managedbeans;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "reportesMB")
@SessionScoped
public class ReportesMB {
    
    @EJB
    private TiposReclamosSB tiposReclamosSB;
    
    public void pdfTiposReclamosPorDependencia() throws JRException, IOException {
        Usuarios usu = recuperarSessionUsuario();
        List<TiposReclamos> listaTiposReclamos = tiposReclamosSB.listarTiposReclamosPorDependencia(usu.getFkCodPersona().getFkCodDependencia().getCodDependencia());

        Map<String, Object> parametros = new HashMap<>();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/escudo.gif");
        parametros.put("urlImagen", urlImagen);
        parametros.put("nombreDependencia", listaTiposReclamos.get(0).getFkCodDependencia().getNombreDependencia());
        parametros.put("SUBREPORT_DIR", "py/gov/mca/reclamosmca/reportes/");

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaTiposReclamos);
        JasperReport jasper = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream("py/gov/mca/reclamosmca/reportes/ReclamosPorTipo.jasper"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, beanCollectionDataSource);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=RECLAMO_POR_TIPOS.pdf");

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();

    }
    
    public Usuarios recuperarSessionUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosMB login = (UsuariosMB) session.getAttribute("usuariosMB");
        return login.getUsuario();
    }
    
}
