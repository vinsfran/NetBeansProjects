package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Dependencias;

/**
 *
 * @author vinsfran
 */
@Stateless
public class DependenciasSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearDependencias(Dependencias objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreDependencia() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreDependencia() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarDependencias(Dependencias objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreDependencia() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreDependencia() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarDependencias(Dependencias objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreDependencia() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreDependencia() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Dependencias consultarDependencia(Integer codDependencia) {
        Query q = em.createNamedQuery("Dependencias.findByCodDependencia");
        q.setParameter("codDependencia", codDependencia);
        return (Dependencias) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Dependencias> listarDependencias() {
        Query q = em.createNamedQuery("Dependencias.findAll");
        return q.getResultList();
    }
}
