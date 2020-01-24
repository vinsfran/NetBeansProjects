/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.TiposFinalizacionReclamos;

/**
 *
 * @author vinsfran
 */
@Stateless
public class TiposFinalizacionReclamosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearTiposFinalizacionReclamos(TiposFinalizacionReclamos objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarTiposFinalizacionReclamos(TiposFinalizacionReclamos objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            mensajes = objeto.getNombreTipoFinalizacionReclamo() + " se actualizó con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreTipoFinalizacionReclamo() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarTiposFinalizacionReclamos(TiposFinalizacionReclamos objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getNombreTipoFinalizacionReclamo() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getNombreTipoFinalizacionReclamo() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public TiposFinalizacionReclamos consultarTiposFinalizacionReclamos(Integer codTipoFinalizacionReclamo) {
        Query q = em.createNamedQuery("TiposFinalizacionReclamos.findByCodTipoFinalizacionReclamo");
        q.setParameter("codTipoFinalizacionReclamo", codTipoFinalizacionReclamo);
        return (TiposFinalizacionReclamos) q.getResultList().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<TiposFinalizacionReclamos> listarTiposFinalizacionReclamos() {
        Query q = em.createNamedQuery("TiposFinalizacionReclamos.findAll");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<TiposFinalizacionReclamos> listarTiposFinalizacionReclamosPorDependencia(Integer codDependencia) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM TiposFinalizacionReclamos e ");
        jpql.append("WHERE e.fkDependenciaTipoFinalizacionReclamo.codDependencia = :paramCodDependencia ");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodDependencia", codDependencia);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public Boolean consultarTiposFinalizacionReclamosPorNombre(String nombreTipoFinalizacionReclamo) {
        Query q = em.createNamedQuery("TiposFinalizacionReclamos.findByNombreTipoFinalizacionReclamo");
        q.setParameter("nombreTipoFinalizacionReclamo", nombreTipoFinalizacionReclamo);
        return !q.getResultList().isEmpty();

    }

    @SuppressWarnings("unchecked")
    public Boolean consultarTiposFinalizacionReclamosPorNombreDependencia(String nombreTipoFinalizacionReclamo, Integer codDependencia) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM TiposFinalizacionReclamos e ");
        jpql.append("WHERE e.nombreTipoFinalizacionReclamo = :paramNombreTipoFinalizacionReclamo ");
        jpql.append("AND e.fkDependenciaTipoFinalizacionReclamo.codDependencia = :paramCodDependencia ");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramNombreTipoFinalizacionReclamo", nombreTipoFinalizacionReclamo);
        q.setParameter("paramCodDependencia", codDependencia);
        return !q.getResultList().isEmpty();

    }

}
