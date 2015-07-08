package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Personas;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.utiles.EnviarCorreos;

/**
 *
 * @author vinsfran
 */
@Stateless
public class AdminUsuariosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }



    public String actualizarUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String eliminarUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Usuarios consultarUsuariosPorCodigo(Integer codUsuario) {
        Query q = em.createNamedQuery("Usuarios.findByCodUsuario");
        q.setParameter("codUsuario", codUsuario);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Usuarios) q.getResultList().get(0);
        }

    }

    @SuppressWarnings("unchecked")
    public Usuarios consultarUsuarios(String loginUsuario) {
        Query q = em.createNamedQuery("Usuarios.findByLoginUsuario");
        q.setParameter("loginUsuario", loginUsuario);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Usuarios) q.getResultList().get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public String consultarUsuariosPorCedula(String cedulaPersona) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Usuarios e ");
        jpql.append("WHERE e.fkCodPersona.cedulaPersona = :paramCedulaPersona ");
        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCedulaPersona", cedulaPersona);
        if (q.getResultList().isEmpty()) {
            return "false";
        } else {
            return "true";
        }

    }

    @SuppressWarnings("unchecked")
    public List<Usuarios> listarUsuarios() {
        Query q = em.createNamedQuery("Usuarios.findAll");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuarios> listarUsuariosInactivos(Integer codEstadoUsuario) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Usuarios e ");
        jpql.append("WHERE e.fkCodEstadoUsuario.codEstadoUsuario = :paramcodEstadoUsuario ");
        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramcodEstadoUsuario", codEstadoUsuario);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return q.getResultList();
        }
    }
}
