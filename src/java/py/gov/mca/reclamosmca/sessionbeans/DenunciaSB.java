package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Denuncia;
import py.gov.mca.reclamosmca.utiles.EnviarCorreos;

/**
 *
 * @author vinsfran
 */
@Stateless
@SuppressWarnings("unchecked")
public class DenunciaSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;

    @EJB
    private EnviarCorreos enviarCorreos;

    public Denuncia add(Denuncia denuncia) {
        return em.merge(denuncia);
    }

    public List<Denuncia> listByUsuario(Integer usuarioId) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Denuncia e ");
        jpql.append("WHERE e.idUsuario.codUsuario = :usuarioId");

        Query q = em.createQuery(jpql.toString());
        q.setParameter("usuarioId", usuarioId);
        return q.getResultList();
    }

}
