package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;

/**
 *
 * @author vinsfran
 */
@Stateless
public class EstadosUsuariosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearEstadosUsuarios(EstadosUsuarios objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreEstadoUsuario() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoUsuario() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarEstadosUsuarios(EstadosUsuarios objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreEstadoUsuario() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoUsuario() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarEstadosUsuarios(EstadosUsuarios objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreEstadoUsuario() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoUsuario() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public EstadosUsuarios consultarEstadoUsuario(Integer codEstadoUsuario) {
        Query q = em.createNamedQuery("EstadosUsuarios.findByCodEstadoUsuario");
        q.setParameter("codEstadoUsuario", codEstadoUsuario);
        return (EstadosUsuarios) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<EstadosUsuarios> listarEstadosUsuarios() {
        Query q = em.createNamedQuery("EstadosUsuarios.findAll");
        return q.getResultList();
    }
}
