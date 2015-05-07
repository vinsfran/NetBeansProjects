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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
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
}
