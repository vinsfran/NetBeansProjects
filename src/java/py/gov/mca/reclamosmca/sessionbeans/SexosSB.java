package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Sexos;

/**
 *
 * @author vinsfran
 */
@Stateless
public class SexosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearSexos(Sexos objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getNombreSexo() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreSexo() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarSexos(Sexos objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreSexo() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreSexo() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarSexos(Sexos objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreSexo() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreSexo() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Sexos consultarPorCodigoSexo(Integer codSexo) {
        Query q = em.createNamedQuery("Sexos.findByCodSexo");
        q.setParameter("codSexo", codSexo);
        return (Sexos) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Sexos> listarSexos() {
        Query q = em.createNamedQuery("Sexos.findAll");
        return q.getResultList();
    }
}
