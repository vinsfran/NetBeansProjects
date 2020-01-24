package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;

/**
 *
 * @author vinsfran
 */
@Stateless
public class EstadosReclamosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearEstadosReclamos(EstadosReclamos objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarEstadosReclamos(EstadosReclamos objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String eliminarEstadosReclamos(EstadosReclamos objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreEstadoReclamo() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoReclamo() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public EstadosReclamos consultarEstadoReclamo(Integer codEstadoReclamo) {
        Query q = em.createNamedQuery("EstadosReclamos.findByCodEstadoReclamo");
        q.setParameter("codEstadoReclamo", codEstadoReclamo);
        return (EstadosReclamos) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<EstadosReclamos> listarEstadosReclamos() {
        Query q = em.createNamedQuery("EstadosReclamos.findAll");
        return q.getResultList();
    }
}
