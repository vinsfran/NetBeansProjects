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
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarTiposReclamos(TiposReclamos objeto) {
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
    public List<TiposReclamos> listarTiposReclamosTodos() {
        Query q = em.createNamedQuery("TiposReclamos.findAll");
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<TiposReclamos> listarTiposReclamos() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM TiposReclamos e ");
         jpql.append("ORDER BY e.topTipoReclamo DESC");
        Query q = em.createQuery(jpql.toString());
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<TiposReclamos> listarTiposReclamosPorDependencia(Integer codDependencia) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM TiposReclamos e ");
        jpql.append("WHERE e.fkCodDependencia.codDependencia = :paramCodDependencia ");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodDependencia", codDependencia);
        return q.getResultList();
    }
    
    
}