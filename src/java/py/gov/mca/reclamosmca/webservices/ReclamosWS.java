package py.gov.mca.reclamosmca.webservices;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import py.gov.mca.reclamosmca.entitys.EstadosReclamos;
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
    public String crearReclamosWeb(String json) throws JSONException, ParseException {
        //System.out.println("JSON: " + json);
        JSONObject jsonObjectReclamo = new JSONObject(json);
        Reclamos reclamos = new Reclamos();

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
        for (int i=0; lista.size()> i; i++) {
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
