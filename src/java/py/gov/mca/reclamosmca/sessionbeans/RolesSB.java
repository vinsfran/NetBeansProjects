package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Roles;

/**
 *
 * @author vinsfran
 */
@Stateless
public class RolesSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearRoles(Roles objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarRoles(Roles objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String eliminarRoles(Roles objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreRol() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreRol() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Roles consultarRol(Integer codRol) {        
        Query q = em.createNamedQuery("Roles.findByCodRol");
        q.setParameter("codRol", codRol);
        return (Roles) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Roles> listarRoles() {
        Query q = em.createNamedQuery("Roles.findAll");
        return q.getResultList();
    }
}
