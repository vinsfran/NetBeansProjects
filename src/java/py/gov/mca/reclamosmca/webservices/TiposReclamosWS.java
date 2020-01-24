package py.gov.mca.reclamosmca.webservices;

import java.text.ParseException;
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
import py.gov.mca.reclamosmca.entitys.Dependencias;
import py.gov.mca.reclamosmca.entitys.TiposReclamos;
import py.gov.mca.reclamosmca.sessionbeans.TiposReclamosSB;

/**
 *
 * @author vinsfran
 */
@Stateless
@Path("/tiposreclamos")
public class TiposReclamosWS {

    @EJB
    private TiposReclamosSB tiposReclamosSB;

    @POST
    @Path("/crearTiposReclamos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String crearTiposReclamos(String json) throws JSONException, ParseException {
        //System.out.println("JSON: " + json);
        JSONObject jsonObject = new JSONObject(json);
        TiposReclamos tiposReclamos = new TiposReclamos();
        tiposReclamos.setFkCodDependencia(new Dependencias());
        tiposReclamos.setNombreTipoReclamo(jsonObject.get("nombreTipoReclamo").toString());
        tiposReclamos.setDiasMaximoPendientes(jsonObject.getInt("diasMaximoPendientes"));
        tiposReclamos.setDiasMaximoFinalizados(jsonObject.getInt("diasMaximoFinalizados"));
        tiposReclamos.getFkCodDependencia().setCodDependencia(jsonObject.getInt("codDependencia"));
        return tiposReclamosSB.crearTiposReclamos(tiposReclamos);
    }

    @POST
    @Path("/actualizarTiposReclamos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String actualizarTiposReclamos(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        TiposReclamos tiposReclamos = new TiposReclamos();
        tiposReclamos.setFkCodDependencia(new Dependencias());
        tiposReclamos.setCodTipoReclamo(jsonObject.getInt("codTipoReclamo"));
        tiposReclamos.setNombreTipoReclamo(jsonObject.get("nombreTipoReclamo").toString());
        tiposReclamos.setDiasMaximoPendientes(jsonObject.getInt("diasMaximoPendientes"));
        tiposReclamos.setDiasMaximoFinalizados(jsonObject.getInt("diasMaximoFinalizados"));
        tiposReclamos.getFkCodDependencia().setCodDependencia(jsonObject.getInt("codDependencia"));
        return tiposReclamosSB.actualizarTiposReclamos(tiposReclamos);
    }

    @POST
    @Path("/consultarTiposReclamos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public TiposReclamos consultarTiposReclamos(String json) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(json);
        TiposReclamos tiposReclamos = tiposReclamosSB.consultarTipoReclamo(jsonObject.getInt("codTipoReclamo"));
        return tiposReclamos;
    }

    @GET
    @Path("/listarTiposReclamos")
    @Produces({MediaType.APPLICATION_JSON})
    public List<TiposReclamos> listarTiposReclamos() {
        return tiposReclamosSB.listarTiposReclamos();
    }
}
