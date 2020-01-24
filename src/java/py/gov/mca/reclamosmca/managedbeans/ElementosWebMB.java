package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.PermisosElementosWeb;
import py.gov.mca.reclamosmca.entitys.Usuarios;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "elementosWebMB")
@SessionScoped
public class ElementosWebMB implements Serializable {

    private List<PermisosElementosWeb> listaDePermisosDeElementos;

    public ElementosWebMB() {
        listaDePermisosDeElementos = recuperarSessionUsuario().getFkCodRol().getPermisosElementosWebList();

    }

    public String obtenerPermisoVisibleElemento(String nombreElemento) {
        String valorRetorno = "false";
        for (int i = 0; i < listaDePermisosDeElementos.size(); i++) {
            PermisosElementosWeb per = listaDePermisosDeElementos.get(i);
            if (per.getFkCodElementoWeb().getNombreElementoWeb().equals(nombreElemento)) {
                valorRetorno = per.getValorVisible();
            }
        }
        return valorRetorno;
    }

    public String obtenerPermisoDesactivadoElemento(String nombreElemento) {
        String valorRetorno = "false";
        for (int i = 0; i < listaDePermisosDeElementos.size(); i++) {
            PermisosElementosWeb per = listaDePermisosDeElementos.get(i);
            if (per.getFkCodElementoWeb().getNombreElementoWeb().equals(nombreElemento)) {
                valorRetorno = per.getValorDesactivado();
            }
        }
        return valorRetorno;
    }

    private Usuarios recuperarSessionUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosMB login = (UsuariosMB) session.getAttribute("usuariosMB");
        return login.getUsuario();
    }

    /**
     * @return the listaDePermisosDeElementos
     */
    public List<PermisosElementosWeb> getListaDePermisosDeElementos() {
        return listaDePermisosDeElementos;
    }

    /**
     * @param listaDePermisosDeElementos the listaDePermisosDeElementos to set
     */
    public void setListaDePermisosDeElementos(List<PermisosElementosWeb> listaDePermisosDeElementos) {
        this.listaDePermisosDeElementos = listaDePermisosDeElementos;
    }

}
