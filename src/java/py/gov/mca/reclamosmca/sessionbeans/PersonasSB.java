package py.gov.mca.reclamosmca.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Personas;

/**
 *
 * @author vinsfran
 */
@Stateless
public class PersonasSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearPersonas(Personas objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarPersonas(Personas objeto) {
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

    public String eliminarPersonas(Personas objeto) {
        mensajes = "";
        try {
            Personas personaEliminar = em.find(Personas.class, objeto.getCodPersona());
            em.remove(personaEliminar);
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = "(" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    @SuppressWarnings("unchecked")
    public Personas consultarPersona(Integer codPersona) {
        Query q = em.createNamedQuery("Personas.findByCodPersona");
        q.setParameter("codPersona", codPersona);
        return (Personas) q.getResultList().get(0);
    }
    
    @SuppressWarnings("unchecked")
    public Personas consultarPersonaCedula(String cedulaPersona) {
        Query q = em.createNamedQuery("Personas.findByCedulaPersona");
        q.setParameter("cedulaPersona", cedulaPersona);
        if(q.getResultList().isEmpty()){
            return null;
        }else{
            return (Personas) q.getResultList().get(0);
        }
        
        
    }

    @SuppressWarnings("unchecked")
    public List<Personas> listarPersonas() {
        Query q = em.createNamedQuery("Personas.findAll");
        return q.getResultList();
    }
}
