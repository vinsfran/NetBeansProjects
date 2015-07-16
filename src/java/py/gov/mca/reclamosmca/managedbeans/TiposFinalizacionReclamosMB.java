/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.TiposFinalizacionReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.TiposFinalizacionReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "tiposFinalizacionReclamosMB")
@SessionScoped
public class TiposFinalizacionReclamosMB implements Serializable {

    private DataModel<TiposFinalizacionReclamos> listarTiposFinalizacionReclamos;
    @EJB
    private TiposFinalizacionReclamosSB tiposFinalizacionReclamosSB;

    public TiposFinalizacionReclamos consultariposFinalizacionReclamos(Integer codTipoFinalizacionReclamo) {
        return tiposFinalizacionReclamosSB.consultarTiposFinalizacionReclamos(codTipoFinalizacionReclamo);
    }

    public DataModel getListariposFinalizacionReclamos() {
        List<TiposFinalizacionReclamos> lista = tiposFinalizacionReclamosSB.listarTiposFinalizacionReclamos();
        listarTiposFinalizacionReclamos = new ListDataModel<>(lista);
        return listarTiposFinalizacionReclamos;
    }

    public List<TiposFinalizacionReclamos> getListariposFinalizacionReclamos2() {
        return tiposFinalizacionReclamosSB.listarTiposFinalizacionReclamos();
    }
    
    public List<TiposFinalizacionReclamos> getListariposFinalizacionReclamosPorDependencia() {
        return tiposFinalizacionReclamosSB.listarTiposFinalizacionReclamosPorDependencia(recuperarUsuarioSession().getFkCodPersona().getFkCodDependencia().getCodDependencia());
    }
    
    
    public Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosMB login = (UsuariosMB) session.getAttribute("usuariosMB");
        return login.getUsuario();
    }
}
