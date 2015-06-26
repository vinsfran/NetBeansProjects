package py.gov.mca.reclamosmca.managedbeans;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "mbReclamos")
@SessionScoped
public class MbReclamos {

    @EJB
    private TiposReclamosSB tiposReclamosSB;
    @EJB
    private ReclamosSB reclamosSB;

    private DataModel misReclamos;

    public MbReclamos() {

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
        UsuariosWebMB login = (UsuariosWebMB) session.getAttribute("usuariosWebMB");
        return login.getUsuario();
    }

    /**
     * @param misReclamos the misReclamos to set
     */
    public void setMisReclamos(DataModel misReclamos) {
        this.misReclamos = misReclamos;
    }

}
