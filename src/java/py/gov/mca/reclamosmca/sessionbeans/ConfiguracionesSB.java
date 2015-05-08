package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Configuraciones;

/**
 *
 * @author vinsfran
 */
@Stateless
public class ConfiguracionesSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearConfiguraciones(Configuraciones objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreProveedor() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreProveedor() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarConfiguraciones(Configuraciones objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreProveedor() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreProveedor() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarConfiguraciones(Configuraciones objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreProveedor() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreProveedor() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Configuraciones consultarPorCodCodConfiguraciones(Integer codConfiguracion) {
        Query q = em.createNamedQuery("Configuraciones.findByCodConfiguracion");
        q.setParameter("codConfCorreo", codConfiguracion);
        return (Configuraciones) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Configuraciones> listarConfiguraciones() {
        Query q = em.createNamedQuery("Configuraciones.findAll");
        return q.getResultList();
    }
}
