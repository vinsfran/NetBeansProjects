package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;

/**
 *
 * @author vinsfran
 */
@Stateless
public class TiposReclamosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearTiposReclamos(TiposReclamos objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreTipoReclamo() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreTipoReclamo() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarTiposReclamos(TiposReclamos objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreTipoReclamo() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreTipoReclamo() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarTiposReclamos(TiposReclamos objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreTipoReclamo() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreTipoReclamo() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public TiposReclamos consultarTipoReclamo(Integer codTipoReclamo) {
        Query q = em.createNamedQuery("TiposReclamos.findByCodTipoReclamo");
        q.setParameter("codTipoReclamo", codTipoReclamo);
        return (TiposReclamos) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<TiposReclamos> listarTiposReclamos() {
        Query q = em.createNamedQuery("TiposReclamos.findAll");
        return q.getResultList();
    }
}