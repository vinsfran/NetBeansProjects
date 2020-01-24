package py.gov.mca.reclamosmca.reportes;

/**
 *
 * @author vinsfran
 */
public class TiposReclamosCantidad {
    private String nombreTipoReclamo;
    private Integer cantidadTipoReclamo;
    
    public TiposReclamosCantidad(){
        
    }
    
    public TiposReclamosCantidad(String nombreTipoReclamo, Integer cantidadTipoReclamo){
        this.nombreTipoReclamo = nombreTipoReclamo;
        this.cantidadTipoReclamo = cantidadTipoReclamo;        
    }

    /**
     * @return the nombreTipoReclamo
     */
    public String getNombreTipoReclamo() {
        return nombreTipoReclamo;
    }

    /**
     * @param nombreTipoReclamo the nombreTipoReclamo to set
     */
    public void setNombreTipoReclamo(String nombreTipoReclamo) {
        this.nombreTipoReclamo = nombreTipoReclamo;
    }

    /**
     * @return the cantidadTipoReclamo
     */
    public Integer getCantidadTipoReclamo() {
        return cantidadTipoReclamo;
    }

    /**
     * @param cantidadTipoReclamo the cantidadTipoReclamo to set
     */
    public void setCantidadTipoReclamo(Integer cantidadTipoReclamo) {
        this.cantidadTipoReclamo = cantidadTipoReclamo;
    }
}
