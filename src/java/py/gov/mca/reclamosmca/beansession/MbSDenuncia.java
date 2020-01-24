package py.gov.mca.reclamosmca.beansession;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.gov.mca.reclamosmca.entitys.Denuncia;
import py.gov.mca.reclamosmca.entitys.DenunciaDetalle;
import py.gov.mca.reclamosmca.entitys.DenunciaEstado;
import py.gov.mca.reclamosmca.sessionbeans.DenunciaDetalleSB;
import py.gov.mca.reclamosmca.sessionbeans.DenunciaSB;
import py.gov.mca.reclamosmca.utiles.DataUtil;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbSDenuncia")
@SessionScoped
public class MbSDenuncia implements Serializable {

    @EJB
    private DenunciaSB denunciaSB;

    @EJB
    private DenunciaDetalleSB denunciaDetalleSB;

    private Denuncia denuncia;
    private DenunciaDetalle denunciaDetalle;
    private List<Denuncia> denuncias;

    public MbSDenuncia() {

    }

    @PostConstruct
    public void init() {

    }

    public String nuevaDenuncia() {
        denuncia = null;
        denuncia = new Denuncia();
        denuncia.setFechaInicio(new Date());
        denuncia.setIdDenunciaEstado(new DenunciaEstado(1));
        denuncia.setIdUsuario(DataUtil.recuperarUsuarioSession());

        denunciaDetalle = new DenunciaDetalle();
        denunciaDetalle.setFecha(new Date());
        denunciaDetalle.setIdUsuario(DataUtil.recuperarUsuarioSession());
        return "denuncia_form";
    }

    public String guardar() throws Exception {
        boolean noError = false;
        String retorno = "";

        denuncia = denunciaSB.add(denuncia);
        if (denuncia != null && denuncia.getId() > 0) {
            denunciaDetalle.setIdDenuncia(denuncia);
            denunciaDetalle = denunciaDetalleSB.add(denunciaDetalle);
            if (denunciaDetalle != null && denunciaDetalle.getId() > 0) {
                noError = true;
            }
        }

        if (noError) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gracias!", "Su denuncia " + denuncia.getId() + " fue enviada."));
            retorno = "denuncia_list";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención!", "Ocurrio un problema al enviar su denuncia, intente de nuevo."));
            retorno = "denuncia_form";
        }
        return retorno;
    }

    public List<Denuncia> getListDenunciasByUsuario() {
        denuncias = denunciaSB.listByUsuario(DataUtil.recuperarUsuarioSession().getCodUsuario());
        return denuncias;
    }

    public String formatearFecha(Date fecha) {
        String patron = "dd-MM-yyyy";
        return DataUtil.dateToString(fecha, patron);
    }

    /**
     * @return the denuncia
     */
    public Denuncia getDenuncia() {
        return denuncia;
    }

    /**
     * @param denuncia the denuncia to set
     */
    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    /**
     * @return the denunciaDetalle
     */
    public DenunciaDetalle getDenunciaDetalle() {
        return denunciaDetalle;
    }

    /**
     * @param denunciaDetalle the denunciaDetalle to set
     */
    public void setDenunciaDetalle(DenunciaDetalle denunciaDetalle) {
        this.denunciaDetalle = denunciaDetalle;
    }

}
