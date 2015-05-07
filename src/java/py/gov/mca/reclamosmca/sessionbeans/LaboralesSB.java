package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Laborales;

/**
 *
 * @author vinsfran
 */
@Stateless
public class LaboralesSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearLaborales(Laborales objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreLaboral() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreLaboral() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarLaborales(Laborales objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreLaboral() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreLaboral() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarLaborales(Laborales objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreLaboral() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreLaboral() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Laborales consultarLaboral(Integer codLaboral) {
        Query q = em.createNamedQuery("Laborales.findByCodLaboral");
        q.setParameter("codLaboral", codLaboral);
        return (Laborales) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Laborales> listarLaborales() {
        Query q = em.createNamedQuery("Laborales.findAll");
        return q.getResultList();
    }
}
