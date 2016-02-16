package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Paises05Direcciones;

/**
 *
 * @author vinsfran
 */
@Stateless
public class DireccionesSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearDireccion(Paises05Direcciones objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public Paises05Direcciones actualizarDireccion(Paises05Direcciones objeto) {
        try {
            em.merge(objeto);
            return objeto;
        } catch (Exception ex) {
            return null;
        }
    }

    public String eliminarDireccion(Paises05Direcciones objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getDireccionNombre() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getDireccionNombre() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Paises05Direcciones consultarDrireccionPorCodigo(Integer codDireccion) {
        Query q = em.createNamedQuery("Paises05Direcciones.findByCodDireccion");
        q.setParameter("codDireccion", codDireccion);
        return (Paises05Direcciones) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public Paises05Direcciones consultarDrireccionPorLatitudLongitud(Double direccionLatitud, Double direccionLongitud) {
        System.out.println("ENTRO: " + direccionLatitud + ", " + direccionLongitud);
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Paises05Direcciones e ");
        jpql.append("WHERE e.direccionLatitud  = :direccionLatitud ");
        jpql.append("AND e.direccionLongitud = :direccionLongitud");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("direccionLatitud", direccionLatitud);
        q.setParameter("direccionLongitud", direccionLongitud);
        if (q.getResultList().isEmpty()) {
            System.out.println("ENTRO: null");
            return null;
        } else {
            System.out.println("ENTRO: no null");
            return (Paises05Direcciones) q.getResultList().get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Paises05Direcciones> listarDirecciones() {
        Query q = em.createNamedQuery("Paises05Direcciones.findAll");
        return q.getResultList();
    }
}
