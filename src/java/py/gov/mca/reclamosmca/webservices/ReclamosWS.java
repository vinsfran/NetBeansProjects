package py.gov.mca.reclamosmca.webservices;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.util.Base64;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
import py.gov.mca.reclamosmca.entitys.Imagenes;
import py.gov.mca.reclamosmca.entitys.Reclamos;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.ReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.RolesSB;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;

/**
 *
 * @author vinsfran
 */
@Stateless
@Path("/reclamos")
public class ReclamosWS {

    @EJB
    private ReclamosSB reclamosSB;

    @EJB
    private UsuariosSB usuariosSB;

    @EJB
    private TiposReclamosSB tiposReclamosSB;

    @EJB
    private RolesSB rolesSB;

    @POST
    @Path("/crearReclamosWeb")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String crearReclamosWeb(String json) throws JSONException, ParseException, Exception {
        //System.out.println("JSON: " + json);
        JSONObject jsonObjectReclamo = new JSONObject(json);
        Reclamos reclamos = new Reclamos();

        if (!jsonObjectReclamo.getString("nombreImagen").equals("NULL")) {
            reclamos.setFkImagen(new Imagenes());
            reclamos.getFkImagen().setTipoImagen(jsonObjectReclamo.getString("tipoImagen"));
            reclamos.getFkImagen().setNombreImagen(jsonObjectReclamo.getString("nombreImagen"));

//            String s = new BASE64Encoder().encode(jsonObjectReclamo.getString("imagenCodificadaBase64").getBytes("UTF-8"));
//            byte[] imagenDecodificadaBase64 = new BASE64Decoder().decodeBuffer(s);
            byte[] imagenDecodificadaBase64 = Base64.decode(jsonObjectReclamo.getString("imagenCodificadaBase64").getBytes("UTF-8"));

            reclamos.getFkImagen().setArchivoImagen(imagenDecodificadaBase64);

        } else {
            reclamos.setFkImagen(null);
        }

        reclamos.setDescripcionReclamoContribuyente(jsonObjectReclamo.getString("descripcionReclamoContribuyente"));
        reclamos.setDireccionReclamo(jsonObjectReclamo.getString("direccionReclamo"));
        reclamos.setOrigenReclamo(jsonObjectReclamo.getString("origenReclamo"));
        reclamos.setFechaReclamo(new Date());
        reclamos.setLatitud(jsonObjectReclamo.getDouble("latitud"));
        reclamos.setLongitud(jsonObjectReclamo.getDouble("longitud"));

        reclamos.setFkCodEstadoReclamo(new EstadosReclamos());
        reclamos.getFkCodEstadoReclamo().setCodEstadoReclamo(1);

        reclamos.setFkCodTipoReclamo(new TiposReclamos());
        JSONObject jsonObjectTipoReclamo = new JSONObject(jsonObjectReclamo.getString("fkCodTipoReclamo"));
        reclamos.setFkCodTipoReclamo(tiposReclamosSB.consultarTipoReclamo(jsonObjectTipoReclamo.getInt("codTipoReclamo")));

        reclamos.setFkCodUsuario(new Usuarios());
        JSONObject jsonObjectUsuario = new JSONObject(jsonObjectReclamo.getString("fkCodUsuario"));
        reclamos.setFkCodUsuario(usuariosSB.consultarUsuarios(jsonObjectUsuario.getString("loginUsuario")));

        String respuesta = reclamosSB.crearReclamos(reclamos);
        if (respuesta.equals("OK")) {
            return "{\"status\":\"OK\", \"mensaje\":\"Reclamo realizado.\"}";
        } else {
            return "{\"status\":\"ERROR\", \"mensaje\":\"" + respuesta + "\"}";
        }
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

    @POST
    @Path("/actualizarReclamos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String actualizarReclamos(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        Reclamos reclamos = new Reclamos();
        reclamos.setFkCodUsuario(new Usuarios());
        reclamos.getFkCodUsuario().setCodUsuario(jsonObject.getInt("codUsuario"));
        reclamos.setDescripcionReclamoContribuyente(jsonObject.getString("descripcionReclamoContribuyente"));
        reclamos.setDireccionReclamo(jsonObject.getString("direccionReclamo"));
        reclamos.setFechaReclamo(new Date());
        reclamos.setLatitud(jsonObject.getDouble("latitud"));
        reclamos.setLongitud(jsonObject.getDouble("longitud"));

        reclamos.setFkCodTipoReclamo(new TiposReclamos());
        reclamos.getFkCodTipoReclamo().setCodTipoReclamo(jsonObject.getInt("codTipoReclamo"));
        reclamos.getFkCodTipoReclamo().setNombreTipoReclamo(jsonObject.getString("nombreTipoReclamo"));

        reclamos.setFkCodEstadoReclamo(new EstadosReclamos());
        reclamos.getFkCodEstadoReclamo().setCodEstadoReclamo(jsonObject.getInt("codEstadoReclamo"));
        return reclamosSB.crearReclamos(reclamos);
    }

    @POST
    @Path("/consultarReclamos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Reclamos consultarReclamos(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        Reclamos reclamos = reclamosSB.consultarReclamo(jsonObject.getInt("codReclamo"));
        return reclamos;
    }

    @POST
    @Path("/consultarReclamosPorUsuarioEstado")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<Reclamos> consultarReclamosPorUsuarioEstado(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        return reclamosSB.listarPorUsuarioEstado(jsonObject.getInt("codUsuario"), jsonObject.getInt("codEstadoReclamo"));
    }

    @POST
    @Path("/consultarReclamosLoginUsuarioEstadoReclamo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<Reclamos> consultarReclamosLoginUsuarioEstadoReclamo(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        List<Reclamos> lista = reclamosSB.listarPorLoginUsuarioEstadoReclamo(jsonObject.getString("loginUsuario"), jsonObject.getInt("codEstadoReclamo"));
        //Verificar para el momento en que se necesite enviar la imagen
        for (int i = 0; lista.size() > i; i++) {
            lista.get(i).setFkImagen(null);
        }

        return lista;
    }

    @GET
    @Path("/listarReclamos")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Reclamos> listaReclamos() {
        return reclamosSB.listarReclamos();
    }
}
