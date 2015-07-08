package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.ElementosWeb;

/**
 *
 * @author vinsfran
 */
@Stateless
public class ElementosWebSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearElementosWeb(ElementosWeb objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarElementosWeb(ElementosWeb objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String eliminarElementosWeb(ElementosWeb objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public ElementosWeb consultarElementoWeb(Integer codElementoWeb) {
        Query q = em.createNamedQuery("ElementosWeb.findByCodElementoWeb");
        q.setParameter("codElementoWeb", codElementoWeb);
        return (ElementosWeb) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<ElementosWeb> listarElementosWeb() {
        Query q = em.createNamedQuery("ElementosWeb.findAll");
        return q.getResultList();
    }
}
