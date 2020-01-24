package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.DenunciaDetalle;
import py.gov.mca.reclamosmca.utiles.EnviarCorreos;

/**
 *
 * @author vinsfran
 */
@Stateless
@SuppressWarnings("unchecked")
public class DenunciaDetalleSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;

    @EJB
    private EnviarCorreos enviarCorreos;

    public DenunciaDetalle add(DenunciaDetalle denunciaDetalle) {
        return em.merge(denunciaDetalle);
    }
    
    public List<DenunciaDetalle> listDenunciaDetalleByDenunciaId(Integer denunciaId) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM DenunciaDetalle e ");
        jpql.append("WHERE e.idDenuncia.id = :denunciaId");

        Query q = em.createQuery(jpql.toString());
        q.setParameter("denunciaId", denunciaId);
        return q.getResultList();
    }
    
   
    
}
