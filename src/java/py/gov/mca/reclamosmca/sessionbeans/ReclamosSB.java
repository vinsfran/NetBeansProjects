package py.gov.mca.reclamosmca.sessionbeans;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.mca.reclamosmca.entitys.Imagenes;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.utiles.EnviarCorreos;

/**
 *
 * @author vinsfran
 */
@Stateless
@SuppressWarnings("unchecked")
public class ReclamosSB {

    @EJB
    private EnviarCorreos enviarCorreos;

    @EJB
    private TiposReclamosSB tiposReclamosSB;

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;
    private String mensajes = "";

    public String crearReclamos(Reclamos objeto) {
        mensajes = "";
        //    EntityTransaction tx = em.getTransaction();

        //    tx.begin();
        try {
            if (objeto.getDescripcionReclamoContribuyente() == null) {
                objeto.setDescripcionReclamoContribuyente("NULL");
            }
            if (objeto.getDescripcionReclamoContribuyente().equals("")) {
                objeto.setDescripcionReclamoContribuyente("SIN DESCRIPCION");
            }
            if (objeto.getFkImagen() != null) {
                Imagenes imagen = new Imagenes();
                imagen.setArchivoImagen(ajustarImagen(objeto.getFkImagen().getArchivoImagen(), objeto.getFkImagen().getTipoImagen()));
                imagen.setNombreImagen(objeto.getFkImagen().getNombreImagen());
                imagen.setTipoImagen(objeto.getFkImagen().getTipoImagen());
                em.persist(imagen);
                objeto.setFkImagen(imagen);
                //  em.persist(objeto.getFkCodPersona());
                em.persist(objeto);
                em.flush();
            } else {
                em.persist(objeto);
                em.flush();
            }

            TiposReclamos tipoReclamo = tiposReclamosSB.consultarTipoReclamo(objeto.getFkCodTipoReclamo().getCodTipoReclamo());
            tipoReclamo.setTopTipoReclamo(tipoReclamo.getTopTipoReclamo() + 1);

            em.merge(tipoReclamo);

            if (objeto.getFkCodUsuario().getFkCodRol().getCodRol().equals(6)) {
                String asunto = "RESPUESTA SOBRE RECLAMO " + objeto.getFkCodTipoReclamo().getNombreTipoReclamo();
                String mensaje = "<html>"
                        + "     <head>"
                        + "         <meta charset=\"UTF-8\">"
                        + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "     </head>"
                        + "     <body style='background-color: #ffffff'>"
                        + "       <div style='text-align: center;'>"
                        + "            <img alt='logo' src=\"http://appserver.asuncion.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
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
            //        tx.commit();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = "No se pudo crear. (" + ex.getMessage() + ")";
            //       tx.rollback();
        }
        //    em.close();
        return mensajes;
    }

    private byte[] ajustarImagen(byte[] imagen, String tipoImagen) throws Exception {
        String tipo = tipoImagen.substring(6, tipoImagen.length());
        InputStream inputStream = new ByteArrayInputStream(imagen);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage originalImage = ImageIO.read(inputStream);
            int valor1 = 1024;
            int valor2 = 768;
            int nAlto;
            int nAncho;
            if (originalImage.getHeight() > originalImage.getWidth()) {
                nAlto = valor1;
                nAncho = valor2;
            } else if (originalImage.getHeight() < originalImage.getWidth()) {
                nAlto = valor2;
                nAncho = valor1;
            } else {
                nAlto = valor2;
                nAncho = valor2;
            }
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(nAncho, nAlto, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, nAncho, nAlto, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ImageIO.write(resizedImage, tipo, baos);
        } catch (Throwable ex) {
            throw new Exception("Error proceso Tamaño Imagen " + ex.toString(), ex);
        }
        return baos.toByteArray();
    }

    public String actualizarReclamos(Reclamos objeto) {
        mensajes = "";
        try {
            //em.find(Reclamos.class, objeto.getCodReclamo());
            em.merge(objeto);
            // em.flush();
            mensajes = "OK";
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }

        if (mensajes.equals("OK")) {
            if (objeto.getFkCodUsuario().getFkCodRol().getCodRol().equals(6)) {
                String asunto = "RESPUESTA SOBRE RECLAMO " + objeto.getFkCodTipoReclamo().getNombreTipoReclamo();
                String mensaje = "<html>"
                        + "     <head>"
                        + "         <meta charset=\"UTF-8\">"
                        + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "     </head>"
                        + "     <body style='background-color: #ffffff'>"
                        + "       <div style='text-align: center;'>"
                        + "            <img alt='logo' src=\"http://appserver.asuncion.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
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

    public String actualizarReclamosDerivacion(Reclamos objeto, TiposReclamos tipoDeReclamosAnterior) {
        mensajes = "";
        TiposReclamos tipoReclamoNuevo = tiposReclamosSB.consultarTipoReclamo(objeto.getFkCodTipoReclamo().getCodTipoReclamo());
        TiposReclamos tipoReclamoAnte = tiposReclamosSB.consultarTipoReclamoPorNombre(tipoDeReclamosAnterior.getNombreTipoReclamo());
        try {
            objeto.setFkCodTipoReclamo(tipoReclamoNuevo);
            em.merge(objeto);
            //em.flush();
            try {
                tipoReclamoNuevo.setTopTipoReclamo(tipoReclamoNuevo.getTopTipoReclamo() + 1);
                em.merge(tipoReclamoNuevo);
                try {
                    tipoReclamoAnte.setTopTipoReclamo(tipoReclamoAnte.getTopTipoReclamo() - 1);
                    em.merge(tipoReclamoAnte);
                    mensajes = "OK";
                } catch (Exception ex) {
                    mensajes = ex.getMessage();
                }
            } catch (Exception ex) {
                mensajes = ex.getMessage();
            }
        } catch (Exception ex) {
            mensajes = ex.getMessage();
        }

        if (mensajes.equals("OK")) {
            String asunto = "RESPUESTA SOBRE RECLAMO " + tipoReclamoAnte.getNombreTipoReclamo();
            String mensaje = "<html>"
                    + "     <head>"
                    + "         <meta charset=\"UTF-8\">"
                    + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "     </head>"
                    + "     <body style='background-color: #ffffff'>"
                    + "       <div style='text-align: center;'>"
                    + "            <img alt='logo' src=\"http://appserver.asuncion.gov.py/reclamosmca/faces/resources/images/logo_3.jpg\"> "
                    + "       </div> "
                    + "       <div> "
                    + "         <p>"
                    + "             Estimado/a: <i>" + objeto.getFkCodUsuario().getFkCodPersona().getNombrePersona() + " " + objeto.getFkCodUsuario().getFkCodPersona().getApellidoPersona() + "</i>"
                    + "         </p> "
                    + "         <p>"
                    + "             Le informamos mediante este medio que su reclamo realizado en fecha " + formatearFecha(objeto.getFechaReclamo())
                    + "             fue derivado a " + objeto.getFkCodTipoReclamo().getFkCodDependencia().getNombreDependencia()
                    + "             y se encuentra tipificado como " + objeto.getFkCodTipoReclamo().getNombreTipoReclamo() + ", en la brevedad le estaremos informando de la situación del mismo."
                    + "         </p> "
                    + "         <p>"
                    + "             Gracias por utilizar al Sistema de Reclamos de la Municipalidad de Asunción."
                    + "         </p>"
                    + "       </div>"
                    + "     </body>"
                    + "</html>";
            enviarCorreos.enviarMail(objeto.getFkCodUsuario().getLoginUsuario(), asunto, mensaje);
        }
        return mensajes;
    }

    public String actualizarReclamosAdmin(Reclamos objeto) {
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
        Query q = em.createNamedQuery("Reclamos.findAll");
        return q.getResultList();
    }

    public List<Reclamos> listarReclamos2() {
        List<Reclamos> listaRetorno = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e.codReclamo, e.fkCodTipoReclamo.nombreTipoReclamo, e.fkCodTipoReclamo.fkCodDependencia.nombreDependencia, "
                + "e.fkCodEstadoReclamo.nombreEstadoReclamo, e.fechaReclamo, e.direccionReclamo, e.fkCodTipoReclamo.diasMaximoFinalizados, "
                + "e.descripcionReclamoContribuyente, e.fechaAtencionReclamo, e.fkCodUsuarioAtencion.fkCodPersona.nombrePersona, e.fkCodUsuarioAtencion.fkCodPersona.apellidoPersona, "
                + "e.descripcionAtencionReclamo, e.fechaCulminacionReclamo, e.fkCodUsuarioCulminacion.fkCodPersona.nombrePersona, e.fkCodUsuarioCulminacion.fkCodPersona.apellidoPersona, "
                + "e.descripcionCulminacionReclamo ");
        jpql.append("FROM Reclamos e ");
        Query q = em.createQuery(jpql.toString());

        for (int i = 0; i < q.getResultList().size(); i++) {
            Reclamos recla = (Reclamos) q.getResultList().get(i);
            System.out.println("COD: " + q.getResultList().get(i).toString());

        }

        return q.getResultList();

    }

    public List<Reclamos> listarPorUsuario(String loginUsuario) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodUsuario.loginUsuario = :paramLoginUsuario");

        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramLoginUsuario", loginUsuario);
        return q.getResultList();
    }

    public List<Reclamos> listarPorUsuarioEstado(Integer codUsuario, Integer codEstadoReclamo) {
        System.out.println("ReclamosSB entra listarPorUsuarioEstado");
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodUsuario.codUsuario = :paramCodUsuario ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");

        jpql.append("ORDER BY e.fechaReclamo DESC");

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

        jpql.append("ORDER BY e.fechaReclamo DESC");

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

        List<Reclamos> reclaAux = q.getResultList();
        if (reclaAux != null && !reclaAux.isEmpty()) {
            List<Reclamos> reclaAux2 = new ArrayList<>();
            for (int i = 0; i < reclaAux.size(); i++) {
//                System.out.println("reclaAux.get(" + i + ") " + reclaAux.get(i).getCodReclamo());
                reclaAux2.add(reclaAux.get(i));

            }
            System.out.println("listarPorDependenciaEstado " + reclaAux.size() + " Estado " + codEstadoReclamo);
            reclaAux = reclaAux2;
        }
        return reclaAux;
    }

    public List<Reclamos> listarPorDependenciaEstado2(Integer codDependencia, Integer codEstadoReclamo) {
        StringBuilder jpql = new StringBuilder();
//        Reclamos e = new Reclamos();
//        e.getCodReclamo();
//        e.getFkCodTipoReclamo();
//        e.getFechaReclamo();
//        e.getFechaAtencionReclamo();
//        jpql.append("SELECT e.cod_reclamo, e.fk_cod_tipo_reclamo, e.fecha_reclamo, e.fecha_atencion_reclamo, e.fk_cod_estado_reclamo ");
//        jpql.append("FROM reclamos e ");
        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.fkCodDependencia.codDependencia = :paramCodDependencia ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString(), Reclamos.class);
        q.setParameter("paramCodDependencia", codDependencia);
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);

        List<Reclamos> reclaAux = q.getResultList();
        if (reclaAux != null && !reclaAux.isEmpty()) {
            List<Reclamos> reclaAux2 = new ArrayList<>();
            for (int i = 0; i < reclaAux.size(); i++) {
                Reclamos re = new Reclamos();
                re.setCodReclamo(reclaAux.get(i).getCodReclamo());
                re.setFkCodTipoReclamo(reclaAux.get(i).getFkCodTipoReclamo());
                re.setFechaReclamo(reclaAux.get(i).getFechaReclamo());
                re.setFechaAtencionReclamo(reclaAux.get(i).getFechaAtencionReclamo());
                re.setFkCodEstadoReclamo(reclaAux.get(i).getFkCodEstadoReclamo());
//                System.out.println("reclaAux.get(" + i + ") " + reclaAux.get(i).getCodReclamo());
                reclaAux2.add(re);

            }
            //System.out.println("listarPorDependenciaEstado " + reclaAux.size() + " Estado " + codEstadoReclamo);
            reclaAux = reclaAux2;
        }
        return reclaAux;
    }

    public List<Reclamos> listarPorDependenciaRangoDeFecha(Integer codDependencia, Date fechaInicio, Date fechaFin) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.fkCodDependencia.codDependencia = :paramCodDependencia ");
        jpql.append("AND e.fechaReclamo BETWEEN :paramFechaInicio AND :paramFechaFin ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodDependencia", codDependencia);
        q.setParameter("paramFechaInicio", fechaInicio);
        q.setParameter("paramFechaFin", fechaFin);
        return q.getResultList();
    }

    public List<Reclamos> listarPorTiposReclamos(Integer codTipoReclamo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.codTipoReclamo = :paramCodTipoReclamo ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodTipoReclamo", codTipoReclamo);
        return q.getResultList();
    }

    public List<Reclamos> listarPorTiposReclamosEstado(Integer codTipoReclamo, Integer codEstadoReclamo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.codTipoReclamo = :paramCodTipoReclamo ");
        jpql.append("AND e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");
        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodTipoReclamo", codTipoReclamo);
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        return q.getResultList();
    }

    public List<Reclamos> listarDependenciaTipoReclamosEstadoReclamoRangoFecha(Integer codEstadoReclamo, Date fechaInicio, Date fechaFin) {
        StringBuilder jpql = new StringBuilder();

//        SELECT r.fkCodTipoReclamo.fkCodDependencia.codDependencia, r.fkCodTipoReclamo.fkCodDependencia.nombreDependencia, 
//       r.fkCodTipoReclamo.codTipoReclamo, r.fkCodTipoReclamo.nombreTipoReclamo, r.codReclamo, r.fkCodEstadoReclamo.codEstadoReclamo
//FROM Reclamos r
//WHERE r.fkCodEstadoReclamo.codEstadoReclamo = 1
//AND r.fechaReclamo BETWEEN '2015-07-01' AND '2015-08-10'
//ORDER BY r.fkCodTipoReclamo.fkCodDependencia.codDependencia, r.fkCodTipoReclamo.codTipoReclamo, r.codReclamo
        jpql.append("SELECT r ");
        jpql.append("FROM Reclamos r ");
        jpql.append("WHERE r.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");
        jpql.append("AND r.fechaReclamo BETWEEN :paramFechaInicio AND :paramFechaFin ");
        jpql.append("ORDER BY r.fkCodTipoReclamo.fkCodDependencia.codDependencia, r.fkCodTipoReclamo.codTipoReclamo, r.codReclamo");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        q.setParameter("paramFechaInicio", fechaInicio);
        q.setParameter("paramFechaFin", fechaFin);
        return q.getResultList();
    }

    public List<Reclamos> listarPorEstadoReclamoRangoDeFecha(Integer codEstadoReclamo, Date fechaInicio, Date fechaFin) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodEstadoReclamo.codEstadoReclamo = :paramCodEstadoReclamo ");
        jpql.append("AND e.fechaReclamo BETWEEN :paramFechaInicio AND :paramFechaFin ");
        jpql.append("ORDER BY e.fkCodTipoReclamo.fkCodDependencia.codDependencia ");

        //jpql.append("WHERE e.persona.nombre LIKE '%:paramNombre%'");
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodEstadoReclamo", codEstadoReclamo);
        q.setParameter("paramFechaInicio", fechaInicio);
        q.setParameter("paramFechaFin", fechaFin);
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
