package py.gov.mca.reclamosmca.sessionbeans;

import java.util.Date;
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
public class UsuariosSB {

    @EJB
    private EnviarCorreos enviarCorreos;

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            mensajes = objeto.getLoginUsuario() + " se creo con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getLoginUsuario() + " no se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String crearUsuariosWeb(Usuarios objeto) {
        if (objeto.getFkCodPersona().getDireccionPersona().isEmpty()) {
            objeto.getFkCodPersona().setDireccionPersona("");
        }
        if (objeto.getFkCodPersona().getTelefonoPersona().isEmpty()) {
            objeto.getFkCodPersona().setTelefonoPersona("");
        }
        if (objeto.getFkCodPersona().getCtaCtePersona().isEmpty()) {
            objeto.getFkCodPersona().setCtaCtePersona("");
        }
        if (objeto.getFkCodPersona().getCedulaPersona().isEmpty()) {
            objeto.getFkCodPersona().setCedulaPersona("");
        }
        mensajes = "";
        try {
            Personas persona = new Personas();
            persona = objeto.getFkCodPersona();
            em.persist(persona);
            objeto.setFkCodPersona(persona);
            //  em.persist(objeto.getFkCodPersona());
            em.merge(objeto);
            em.flush();
            if (objeto.getFkCodRol().getCodRol().equals(6)) {
                String asunto = "DATOS DE ACCESO";
                String mensaje = "<html>"
                        + "     <head>"
                        + "         <meta charset=\"UTF-8\">"
                        + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "     </head>"
                        + "     <body style='background-color: #ffffff'>"
                        + "       <div style='text-align: center;'>"
                        + "            <img alt='logo' src=\"http://appserver.mca.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
                        + "       </div> "
                        + "       <div> "
                        + "             <p>"
                        + "                Estimado/a: <i>" + objeto.getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodPersona().getApellidoPersona() + "</i>"
                        + "             </p> "
                        + "             <p>"
                        + "                Le informamos que para poder acceder al sistema por primera vez, deberá hacerlo con las siguiente credenciales:"
                        + "             </p>   "
                        + "             <p>"
                        + "                 <table border='0'> "
                        + "                     <tr>"
                        + "                         <td><strong>Correo Electrónico:</strong></td>"
                        + "                         <td>" + objeto.getLoginUsuario() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Contraseña:</strong></td>"
                        + "                         <td>" + objeto.getClaveUsuario().substring(0, 6) + "</td>"
                        + "                     </tr>"
                        + "                 </table>"
                        + "             </p>"
                        + "             <p>"
                        + "                Gracias por registrarse al Sistema de Reclamos de la Municipalidad de Asunción."
                        + "             </p>"
                        + "        </div>"
                        + "     </body>"
                        + "</html>";
                enviarCorreos.enviarMail(objeto.getLoginUsuario(), asunto, mensaje);
            }
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = "Ocurrio una excepcion " + ex.getMessage();
        }
        return mensajes;
    }

    public String crearUsuariosExterno(Usuarios objeto) {
        if (objeto.getFkCodPersona().getDireccionPersona().isEmpty()) {
            objeto.getFkCodPersona().setDireccionPersona("");
        }
        if (objeto.getFkCodPersona().getTelefonoPersona().isEmpty()) {
            objeto.getFkCodPersona().setTelefonoPersona("");
        }
        if (objeto.getFkCodPersona().getCtaCtePersona().isEmpty()) {
            objeto.getFkCodPersona().setCtaCtePersona("");
        }
        if (objeto.getFkCodPersona().getCedulaPersona().isEmpty()) {
            objeto.getFkCodPersona().setCedulaPersona("");
        }

        mensajes = "";
         try {
            Personas persona = new Personas();
            persona = objeto.getFkCodPersona();
            persona.setFechaRegistroPersona(new Date());
            em.merge(persona);
            objeto.setFkCodPersona(persona);
            //  em.persist(objeto.getFkCodPersona());
            em.merge(objeto);
            em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = "Ocurrio una excepcion " + ex.getMessage();
        }
        return mensajes;
    }

    public String actualizarUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.merge(objeto);
            em.flush();
            if (objeto.getFkCodRol().getCodRol().equals(6) && objeto.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {
                String asunto = "Datos de nueva contraseña";
                String mensaje = "<html>"
                        + "     <head>"
                        + "         <meta charset=\"UTF-8\">"
                        + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "     </head>"
                        + "     <body style='background-color: #ffffff'>"
                        + "       <div style='text-align: center;'>"
                        + "            <img alt='logo' src=\"http://appserver.mca.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
                        + "       </div> "
                        + "       <div> "
                        + "             <p>"
                        + "                Estimado/a: <i>" + objeto.getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodPersona().getApellidoPersona() + "</i>"
                        + "             </p> "
                        + "             <p>"
                        + "                Le informamos que poder acceder al sistema por primera vez debera hacerlo con las siguiente credenciales:"
                        + "             </p>   "
                        + "             <p>"
                        + "                 <table border='0'> "
                        + "                     <tr>"
                        + "                         <td><strong>Correo Electrónico:</strong></td>"
                        + "                         <td>" + objeto.getLoginUsuario() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Contraseña:</strong></td>"
                        + "                         <td>" + objeto.getClaveUsuario().substring(0, 6) + "</td>"
                        + "                     </tr>"
                        + "                 </table>"
                        + "             </p>"
                        + "             <p>"
                        + "                Gracias por utilizar al Sistema de Reclamos de la Municipalidad de Asunción."
                        + "             </p>"
                        + "        </div>"
                        + "     </body>"
                        + "</html>";

                enviarCorreos.enviarMail(objeto.getLoginUsuario(), asunto, mensaje);
            }
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = objeto.getLoginUsuario() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarUsuariosWeb(Usuarios objeto) {
        mensajes = "";
        try {
            em.find(Usuarios.class, objeto.getCodUsuario());
            em.merge(objeto);
//            em.merge(objeto.getFkCodPersona());
//            em.merge(objeto.getFkCodEstadoUsuario());
//            em.merge(objeto.getFkCodRol());
            em.flush();
            if (objeto.getFkCodRol().getCodRol().equals(6) && objeto.getFkCodEstadoUsuario().getCodEstadoUsuario().equals(3)) {
                String asunto = "Datos de nueva contraseña";
                String mensaje = "<html>"
                        + "     <head>"
                        + "         <meta charset=\"UTF-8\">"
                        + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "     </head>"
                        + "     <body style='background-color: #ffffff'>"
                        + "       <div style='text-align: center;'>"
                        + "            <img alt='logo' src=\"http://appserver.mca.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
                        + "       </div> "
                        + "       <div> "
                        + "             <p>"
                        + "                Estimado/a: <i>" + objeto.getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodPersona().getApellidoPersona() + "</i>"
                        + "             </p> "
                        + "             <p>"
                        + "                Le informamos que poder acceder al sistema por primera vez debera hacerlo con las siguiente credenciales:"
                        + "             </p>   "
                        + "             <p>"
                        + "                 <table border='0'> "
                        + "                     <tr>"
                        + "                         <td><strong>Correo Electrónico:</strong></td>"
                        + "                         <td>" + objeto.getLoginUsuario() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Contraseña:</strong></td>"
                        + "                         <td>" + objeto.getClaveUsuario().substring(0, 6) + "</td>"
                        + "                     </tr>"
                        + "                 </table>"
                        + "             </p>"
                        + "             <p>"
                        + "                Gracias por utilizar al Sistema de Reclamos de la Municipalidad de Asunción."
                        + "             </p>"
                        + "        </div>"
                        + "     </body>"
                        + "</html>";

                enviarCorreos.enviarMail(objeto.getLoginUsuario(), asunto, mensaje);
            }
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = objeto.getLoginUsuario() + " no se pudo actualizar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String eliminarUsuarios(Usuarios objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getLoginUsuario() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getLoginUsuario() + " no se pudo eliminar. (" + ex.getMessage() + ")";
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
    public Personas consultarUsuariosPorCedulaPersona(String cedulaPersona) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Personas e ");
        jpql.append("WHERE e.cedulaPersona = :paramCedulaPersona ");
        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCedulaPersona", cedulaPersona);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {            
            return (Personas) q.getResultList().get(0);
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
