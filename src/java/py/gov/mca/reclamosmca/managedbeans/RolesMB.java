package py.gov.mca.reclamosmca.managedbeans;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.sessionbeans.RolesSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "rolesMB")
@SessionScoped
public class RolesMB {

    @EJB
    private RolesSB rolesSB;

    private DataModel listarRoles;

    public DataModel getListarRoles() {
        List<Roles> lista = rolesSB.listarRoles();
        listarRoles = new ListDataModel(lista);
        return listarRoles;
    }

    public void setListarRoles(DataModel listarRoles) {
        this.listarRoles = listarRoles;
    }

}
