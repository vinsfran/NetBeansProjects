/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "tiposReclamosMB")
@SessionScoped
public class TiposReclamosMB implements Serializable {

    private DataModel listarTiposReclamos;
    @EJB
    private TiposReclamosSB tiposReclamosSB;

    public TiposReclamos consultarTiposReclamos(Integer codTipoReclamo) {
        return tiposReclamosSB.consultarTipoReclamo(codTipoReclamo);
    }

    public DataModel getListarTiposReclamos() {
        List<TiposReclamos> lista = tiposReclamosSB.listarTiposReclamos();
        listarTiposReclamos = new ListDataModel(lista);
        return listarTiposReclamos;
    }

    public List<TiposReclamos> getListarTiposReclamos2() {

        return tiposReclamosSB.listarTiposReclamos();
    }

    public void pdfReclamosPorTipo() throws JRException, IOException {
        List<TiposReclamos> listaTiposReclamos = getListarTiposReclamos2();

        Map parametros = new HashMap();
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
}
