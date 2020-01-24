package py.gov.mca.reclamosmca.beansession;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import py.gov.mca.reclamosmca.entitys.Denuncia;
import py.gov.mca.reclamosmca.entitys.DenunciaDocumento;
import py.gov.mca.reclamosmca.entitys.DenunciaEstado;
import py.gov.mca.reclamosmca.entitys.Imagenes;
import py.gov.mca.reclamosmca.sessionbeans.DenunciaDocumentoSB;
import py.gov.mca.reclamosmca.utiles.DataUtil;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSDenunciaDocumento")
@SessionScoped
public class MbSDenunciaDocumento implements Serializable {

    @EJB
    private DenunciaDocumentoSB denunciaDocumentoSB;

    private String tituloDenuncia;
    private String extension;
    private Integer denunciaId;
    private DenunciaDocumento denunciaDocumento;
    private List<DenunciaDocumento> denunciaDocumentos;

    private DefaultStreamedContent defaultStreamedContent;

    public MbSDenunciaDocumento() {

    }

    @PostConstruct
    public void init() {

    }

    public String nuevoDocumento() {
        denunciaDocumento = new DenunciaDocumento();
        denunciaDocumento.setIdDenuncia(new Denuncia(denunciaId));
        denunciaDocumento.setTitulo("");
        denunciaDocumento.setIdUsuario(DataUtil.recuperarUsuarioSession());
        denunciaDocumento.setFecha(new Date());

        return "denuncia_documento_form";
    }

    public void cargarDocumento(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
//        BufferedImage src = ImageIO.read(file.getInputstream());
//        int valor1 = 1024;
//        int valor2 = 768;
//        int nAlto;
//        int nAncho;
//        if (src.getHeight() > src.getWidth()) {
//            nAlto = valor1;
//            nAncho = valor2;
//        } else if (src.getHeight() < src.getWidth()) {
//            nAlto = valor2;
//            nAncho = valor1;
//        } else {
//            nAlto = valor2;
//            nAncho = valor2;
//        }
        try {
            //Se obtine la imagen que se va a guardar en la Base de datos
            //this.imagenParaGuardar.setArchivoImagen(resize(file.getInputstream(), nAncho, nAlto));
            this.denunciaDocumento.setArchivo(IOUtils.toByteArray(file.getInputstream()));
            this.denunciaDocumento.setContentType(file.getContentType());
            String nombreArchivo = DataUtil.armarNombreDeArchivo(file.getFileName());
            this.denunciaDocumento.setNombre(nombreArchivo);
            //Se convierte la imagen obtenida para mostrar como previa
            this.defaultStreamedContent = null;
            this.defaultStreamedContent = new DefaultStreamedContent(new ByteArrayInputStream(this.denunciaDocumento.getArchivo()), this.denunciaDocumento.getContentType());
            this.defaultStreamedContent.setName(this.denunciaDocumento.getNombre());
            this.defaultStreamedContent.setContentType(this.denunciaDocumento.getContentType());

            String content = this.denunciaDocumento.getContentType();
            extension = "";

            int i = content.lastIndexOf('/');

            extension = content.substring(i + 1);
            System.out.println("Exten : " + extension);
//            this.setMostrarGraphicImage(true);

        } catch (Exception ex) {
            Logger.getLogger(MbSReclamos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public String guardar() throws Exception {
//        boolean noError = false;
//        String retorno = "";
//
//        denuncia = denunciaSB.add(denuncia);
//        if (denuncia != null && denuncia.getId() > 0) {
//            denunciaDetalle.setIdDenuncia(denuncia);
//            denunciaDetalle = denunciaDetalleSB.add(denunciaDetalle);
//            if (denunciaDetalle != null && denunciaDetalle.getId() > 0) {
//                noError = true;
//            }
//        }
//
//        if (noError) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gracias!", "Su denuncia " + denuncia.getId() + " fue enviada."));
//            retorno = "denuncia_list";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención!", "Ocurrio un problema al enviar su denuncia, intente de nuevo."));
//            retorno = "denuncia_form";
//        }
//        return retorno;
//    }
    public String showListDenunciaDocumento(Integer denunciaId, String tituloDenuncia) {
        this.denunciaId = denunciaId;
        this.tituloDenuncia = tituloDenuncia;
        denunciaDocumentos = denunciaDocumentoSB.listDenunciaDocumentoByDenunciaId(denunciaId);
        return "denuncia_documento_list";
    }

    public String formatearFecha(Date fecha) {
        String patron = "dd-MM-yyyy";
        return DataUtil.dateToString(fecha, patron);
    }

    /**
     * @return the denunciaDocumento
     */
    public DenunciaDocumento getDenunciaDocumento() {
        return denunciaDocumento;
    }

    /**
     * @param denunciaDocumento the denunciaDocumento to set
     */
    public void setdenunciaDocumento(DenunciaDocumento denunciaDocumento) {
        this.denunciaDocumento = denunciaDocumento;
    }

    /**
     * @return the denunciaDocumentos
     */
    public List<DenunciaDocumento> getDenunciaDocumentos() {
        return denunciaDocumentos;
    }

    /**
     * @param denunciaDocumentos the denunciaDocumentos to set
     */
    public void setDenunciaDocumentos(List<DenunciaDocumento> denunciaDocumentos) {
        this.denunciaDocumentos = denunciaDocumentos;
    }

    /**
     * @return the tituloDenuncia
     */
    public String getTituloDenuncia() {
        return tituloDenuncia;
    }

    /**
     * @param tituloDenuncia the tituloDenuncia to set
     */
    public void setTituloDenuncia(String tituloDenuncia) {
        this.tituloDenuncia = tituloDenuncia;
    }

    /**
     * @return the defaultStreamedContent
     */
    public DefaultStreamedContent getDefaultStreamedContent() {
        return defaultStreamedContent;
    }

    /**
     * @param defaultStreamedContent the defaultStreamedContent to set
     */
    public void setDefaultStreamedContent(DefaultStreamedContent defaultStreamedContent) {
        this.defaultStreamedContent = defaultStreamedContent;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
