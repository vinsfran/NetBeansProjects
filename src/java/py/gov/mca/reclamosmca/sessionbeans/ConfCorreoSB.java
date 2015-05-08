package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.ConfCorreo;

/**
 *
 * @author vinsfran
 */
@Stateless
public class ConfCorreoSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearConfCorreo(ConfCorreo objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreProveedor() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreProveedor() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarConfCorreo(ConfCorreo objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreProveedor() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreProveedor() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarConfCorreo(ConfCorreo objeto) {
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
    public ConfCorreo consultarPorCodCodConfCorreo(Integer codConfCorreo) {
        Query q = em.createNamedQuery("ConfCorreo.findByCodConfCorreo");
        q.setParameter("codConfCorreo", codConfCorreo);
        System.out.println("ENTRO A SBCORREO");
        return (ConfCorreo) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<ConfCorreo> listarConfCorreo() {
        Query q = em.createNamedQuery("ConfCorreo.findAll");
        return q.getResultList();
    }
}
