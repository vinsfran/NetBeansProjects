package py.gov.mca.reclamosmca.reportes;

import java.util.List;

/**
 *
 * @author vinsfran
 */
public class DependenciasReporte {

    private Integer codDependencia;
    private String nombreDependencia;
    private Integer cantidadReclamosDependencia;
    private List<TiposReclamosReporte> tiposReclamosReporte;

    /**
     * @return the codDependencia
     */
    public Integer getCodDependencia() {
        return codDependencia;
    }

    /**
     * @param codDependencia the codDependencia to set
     */
    public void setCodDependencia(Integer codDependencia) {
        this.codDependencia = codDependencia;
    }

    /**
     * @return the nombreDependencia
     */
    public String getNombreDependencia() {
        return nombreDependencia;
    }

    /**
     * @param nombreDependencia the nombreDependencia to set
     */
    public void setNombreDependencia(String nombreDependencia) {
        this.nombreDependencia = nombreDependencia;
    }

    /**
     * @return the cantidadReclamosDependencia
     */
    public Integer getCantidadReclamosDependencia() {
        return cantidadReclamosDependencia;
    }

    /**
     * @param cantidadReclamosDependencia the cantidadReclamosDependencia to set
     */
    public void setCantidadReclamosDependencia(Integer cantidadReclamosDependencia) {
        this.cantidadReclamosDependencia = cantidadReclamosDependencia;
    }

    /**
     * @return the tiposReclamosReporte
     */
    public List<TiposReclamosReporte> getTiposReclamosReporte() {
        return tiposReclamosReporte;
    }

    /**
     * @param tiposReclamosReporte the tiposReclamosReporte to set
     */
    public void setTiposReclamosReporte(List<TiposReclamosReporte> tiposReclamosReporte) {
        this.tiposReclamosReporte = tiposReclamosReporte;
    }

    

}
