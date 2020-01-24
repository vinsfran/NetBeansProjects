package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.CambiosEstadosUsuarios;

/**
 *
 * @author vinsfran
 */
@Stateless
public class CambiosEstadosUsuariosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearCambiosEstadosUsuarios(CambiosEstadosUsuarios objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreEstadoUsuario() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoUsuario() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarCambiosEstadosUsuarios(CambiosEstadosUsuarios objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreEstadoUsuario() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreEstadoUsuario() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarCambiosEstadosUsuarios(CambiosEstadosUsuarios objeto) {
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
    public CambiosEstadosUsuarios consultarPorCodCambioEstadoUsuario(Integer codCambioEstadoUsuario) {
        Query q = em.createNamedQuery("CambiosEstadosUsuarios.findByCodCambioEstadoUsuario");
        q.setParameter("codCambioEstadoUsuario", codCambioEstadoUsuario);
        return (CambiosEstadosUsuarios) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<CambiosEstadosUsuarios> listarCambiosEstadosUsuarios() {
        Query q = em.createNamedQuery("CambiosEstadosUsuarios.findAll");
        return q.getResultList();
    }
}
