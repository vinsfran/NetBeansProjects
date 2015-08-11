package py.gov.mca.reclamosmca.reportes;

import java.util.List;
import py.gov.mca.reclamosmca.entitys.Reclamos;

/**
 *
 * @author vinsfran
 */
public class TiposReclamosReporte {
    
    private Integer codTipoReclamo;
    private String nombreTipoReclamo;
    private List<Reclamos> reclamos;

    /**
     * @return the codTipoReclamo
     */
    public Integer getCodTipoReclamo() {
        return codTipoReclamo;
    }

    /**
     * @param codTipoReclamo the codTipoReclamo to set
     */
    public void setCodTipoReclamo(Integer codTipoReclamo) {
        this.codTipoReclamo = codTipoReclamo;
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
     * @return the reclamos
     */
    public List<Reclamos> getReclamos() {
        return reclamos;
    }

    /**
     * @param reclamos the reclamos to set
     */
    public void setReclamos(List<Reclamos> reclamos) {
        this.reclamos = reclamos;
    }
    
}
