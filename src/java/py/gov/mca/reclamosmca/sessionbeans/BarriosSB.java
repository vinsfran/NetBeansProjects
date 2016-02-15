package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Paises04Barrios;

/**
 *
 * @author vinsfran
 */
@Stateless
public class BarriosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearBarrio(Paises04Barrios objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarBarrio(Paises04Barrios objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String eliminarBarrio(Paises04Barrios objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getBarrioNombre() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getBarrioNombre() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Paises04Barrios consultarBarrio(Integer codBarrio) {
        Query q = em.createNamedQuery("Paises04Barrios.findByCodBarrio");
        q.setParameter("codBarrio", codBarrio);
        return (Paises04Barrios) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Paises04Barrios> listarBarrios() {
        //Query q = em.createNamedQuery("Paises04Barrios.findAll");
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Paises04Barrios e ");
        jpql.append("ORDER BY e.barrioNombre");
        Query q = em.createQuery(jpql.toString());
                
        return q.getResultList();
    }
    
    
    
}
