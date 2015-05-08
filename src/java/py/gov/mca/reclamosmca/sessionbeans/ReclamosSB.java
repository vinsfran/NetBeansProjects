package py.gov.mca.reclamosmca.sessionbeans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.utiles.EnviarCorreos;

/**
 *
 * @author vinsfran
 */
@Stateless
@SuppressWarnings("unchecked")
public class ReclamosSB {
    @EJB
    private EnviarCorreos enviarCorreos1;
    @EJB
    private EnviarCorreos enviarCorreos;

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearReclamos(Reclamos objeto) {
        mensajes = "";
        try {
            em.persist(objeto);
            em.flush();

            System.out.println("CODIGO DE ROL: " + objeto.getFkCodUsuario().getFkCodRol().getCodRol());
            if (objeto.getFkCodUsuario().getFkCodRol().getCodRol().equals(6)) {
                String asunto = "RESPUESTA SOBRE RECLAMO " + objeto.getFkCodTipoReclamo().getNombreTipoReclamo();
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
                        + "                Estimado/a: <i>" + objeto.getFkCodUsuario().getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodUsuario().getFkCodPersona().getApellidoPersona() + "</i>"
                        + "             </p> "
                        + "             <p>"
                        + "                Le informamos que su reclamo sobre "
                        + "                <strong>" + objeto.getFkCodTipoReclamo().getNombreTipoReclamo() + "</strong>, "
                        + "                realizado en fecha "
                        + "                <strong>" + formatearFecha(objeto.getFechaReclamo()) + "</strong> "
                        + "                fue ingresado a nuestro sistema de reclamos, se encuentra en la "
                        + "                <strong>" + objeto.getFkCodTipoReclamo().getFkCodDependencia().getNombreDependencia() + "</strong>, "
                        + "                y en la brevedad recibira informes sobre la situación del mismo."
                        + "             </p> "
                        + "             <p>"
                        + "                Gracias por utilizar al Sistema de Reclamos de la Municipalidad de Asunción."
                        + "             </p>"
                        + "        </div>"
                        + "     </body>"
                        + "</html>";

                enviarCorreos.enviarMail(objeto.getFkCodUsuario().getLoginUsuario(), asunto, mensaje);
            }
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = "No se pudo crear. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public String actualizarReclamos(Reclamos objeto) {
        mensajes = "";
        try {
            System.out.println("CODESTA: " + objeto.getFkCodEstadoReclamo().getCodEstadoReclamo());
            System.out.println("CODROL: " + objeto.getFkCodUsuario().getFkCodRol().getCodRol());
            //em.find(Reclamos.class, objeto.getCodReclamo());
            em.merge(objeto);
           // em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }

        if (mensajes.equals("OK")) {
            if (objeto.getFkCodUsuario().getFkCodRol().getCodRol().equals(6)) {
                System.out.println("CODESTA2222: ");
                String asunto = "RESPUESTA SOBRE RECLAMO " + objeto.getFkCodTipoReclamo().getNombreTipoReclamo();
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
                        + "                Estimado/a: <i>" + objeto.getFkCodUsuario().getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodUsuario().getFkCodPersona().getApellidoPersona() + "</i>"
                        + "             </p> "
                        + "             <p>"
                        + "                Le informamos mediante este medio la situaciación actual de su reclamo."
                        + "             </p> "
                        + "             <p>"
                        + "                 <table border='0'> "
                        + "                     <tr>"
                        + "                         <td colspan='2' style='text-align: center'><strong>DATOS DE SU RECLAMO.</strong></td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Fecha de realización:</strong></td>"
                        + "                         <td>" + formatearFecha(objeto.getFechaReclamo()) + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Nro. de reclamo:</strong></td>"
                        + "                         <td>" + objeto.getCodReclamo() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Tipo de reclamo:</strong></td>"
                        + "                         <td>" + objeto.getFkCodTipoReclamo().getNombreTipoReclamo() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>A cargo de:</strong></td>"
                        + "                         <td>" + objeto.getFkCodTipoReclamo().getFkCodDependencia().getNombreDependencia() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Dirección de reclamo:</strong></td>"
                        + "                         <td>" + objeto.getDireccionReclamo() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Detalle adicional del reclamo:</strong></td>"
                        + "                         <td>" + objeto.getDescripcionReclamoContribuyente() + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td colspan='2' style='text-align: center'><strong>DATOS DE RECEPCIÓN.</strong></td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Fecha de recepción:</strong></td>"
                        + "                         <td>" + formatearFecha(objeto.getFechaAtencionReclamo()) + "</td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Recepcionado por:</strong></td>"
                        + "                         <td>" + objeto.getFkCodUsuarioAtencion().getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodUsuarioAtencion().getFkCodPersona().getApellidoPersona() + " </td>"
                        + "                     </tr>"
                        + "                     <tr>"
                        + "                         <td><strong>Tarea a realizar:</strong></td>"
                        + "                         <td>" + objeto.getDescripcionAtencionReclamo() + " </td>"
                        + "                     </tr>";

                if (objeto.getFkCodEstadoReclamo().getCodEstadoReclamo().equals(3)) {
                    mensaje = mensaje + "<tr>"
                            + "             <td colspan='2' style='text-align: center'><strong>DATOS DE FINALIZACIÓN.</strong></td>"
                            + "         </tr>"
                            + "         <tr>"
                            + "             <td><strong>Fecha de finalización:</strong></td>"
                            + "             <td>" + formatearFecha(objeto.getFechaCulminacionReclamo()) + "</td>"
                            + "         </tr>"
                            + "         <tr>"
                            + "             <td><strong>Finalizado por:</strong></td>"
                            + "             <td>" + objeto.getFkCodUsuarioCulminacion().getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodUsuarioCulminacion().getFkCodPersona().getApellidoPersona() + "</td>"
                            + "         </tr>"
                            + "         <tr>"
                            + "             <td><strong>Cantidad de dias en el sistema:</strong></td>"
                            + "             <td>" + objeto.getCantidadDiasProceso() + "</td>"
                            + "         </tr>"
                            + "         <tr>"
                            + "             <td><strong>Detalle del trabajo realizado:</strong></td>"
                            + "             <td>" + objeto.getDescripcionCulminacionReclamo() + "</td>"
                            + "         </tr>";
                }

                mensaje = mensaje + "</table>"
                        + "         </p>"
                        + "         <p>"
                        + "             Gracias por utilizar al Sistema de Reclamos de la Municipalidad de Asunción."
                        + "         </p>"
                        + "     </div>"
                        + "     </body>"
                        + "</html>";
                enviarCorreos.enviarMail(objeto.getFkCodUsuario().getLoginUsuario(), asunto, mensaje);
            }
        }

        return mensajes;
    }

    public String eliminarReclamos(Reclamos objeto) {
        mensajes = "";
        try {
            em.remove(objeto);
            mensajes = objeto.getCodReclamo() + " se elimino con exito!";
        } catch (Exception ex) {
            mensajes = objeto.getCodReclamo() + " no se pudo eliminar. (" + ex.getMessage() + ")";
        }
        return mensajes;
    }

    public Reclamos consultarReclamo(Integer codReclamo) {
        Query q = em.createNamedQuery("Reclamos.findByCodReclamo");
        q.setParameter("codReclamo", codReclamo);
        return (Reclamos) q.getResultList().get(0);
    }

    public List<Reclamos> listarReclamos() {
        System.out.println("LISTA RECLAMOS");
        Query q = em.createNamedQuery("Reclamos.findAll");
        return q.getResultList();
    }

    public List<Reclamos> listarPorUsuario(String loginUsuario) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodUsuario.loginUsuario = :paramLoginUsuario");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramLoginUsuario", loginUsuario);
        return q.getResultList();
    }

    public List<Reclamos> listarPorUsuarioEstado(Integer codUsuario, Integer codEstadoReclamo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodUsuario.codUsuario = :paramCodUsuario ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodUsuario", codUsuario);
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        return q.getResultList();
    }

    public List<Reclamos> listarPorLoginUsuarioEstadoReclamo(String loginUsuario, Integer codEstadoReclamo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodUsuario.loginUsuario = :paramLoginUsuario ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramLoginUsuario", loginUsuario);
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        return q.getResultList();
    }

    public List<Reclamos> listarPorDependenciaEstado(Integer codDependencia, Integer codEstadoReclamo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.fkCodDependencia.codDependencia = :paramCodDependencia ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodDependencia", codDependencia);
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        return q.getResultList();
    }

    public List<Reclamos> burcarPorDependencia(Integer codDependencia, String expresion) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.fkCodDependencia.codDependencia = :paramCodDependencia ");
        jpql.append("AND (lower(e.direccionReclamo) like :paramExpresion ");
        jpql.append("OR lower(e.fkCodTipoReclamo.nombreTipoReclamo) like :paramExpresion ");
        jpql.append("OR lower(e.fkCodUsuario.loginUsuario) like :paramExpresion ");
        jpql.append("OR lower(e.fkCodUsuario.fkCodPersona.cedulaPersona) like :paramExpresion ");
        jpql.append("OR lower(e.fkCodUsuario.fkCodPersona.nombrePersona) like :paramExpresion ");
        jpql.append("OR lower(e.fkCodUsuario.fkCodPersona.apellidoPersona) like :paramExpresion) ");
        jpql.append("ORDER BY e.codReclamo");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodDependencia", codDependencia);
        q.setParameter("paramExpresion", "%" + expresion.toLowerCase().trim() + "%");
//        q.setParameter("paramNombreTipoReclamo", "%" + expresion.trim() + "%");
//        q.setParameter("paramLoginUsuario", "%" + expresion.trim() + "%");
//        q.setParameter("paramCedulaPersona", "%" + expresion.trim() + "%");
//        q.setParameter("paramNombrePersona", "%" + expresion.trim() + "%");
//        q.setParameter("paramApellidoPersona", "%" + expresion.trim() + "%");
        return q.getResultList();
    }

    public String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            // formateo
            String patron = "dd/MM/yyyy";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            return formato.format(fecha).toString();
        }
    }
}
