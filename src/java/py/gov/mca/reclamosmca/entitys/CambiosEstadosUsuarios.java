/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "cambios_estados_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CambiosEstadosUsuarios.findAll", query = "SELECT c FROM CambiosEstadosUsuarios c")
    , @NamedQuery(name = "CambiosEstadosUsuarios.findByCodCambioEstadoUsuario", query = "SELECT c FROM CambiosEstadosUsuarios c WHERE c.codCambioEstadoUsuario = :codCambioEstadoUsuario")
    , @NamedQuery(name = "CambiosEstadosUsuarios.findByLoginUsuario", query = "SELECT c FROM CambiosEstadosUsuarios c WHERE c.loginUsuario = :loginUsuario")
    , @NamedQuery(name = "CambiosEstadosUsuarios.findByNombreEstadoUsuario", query = "SELECT c FROM CambiosEstadosUsuarios c WHERE c.nombreEstadoUsuario = :nombreEstadoUsuario")
    , @NamedQuery(name = "CambiosEstadosUsuarios.findByDetalleCambioEstadoUsuario", query = "SELECT c FROM CambiosEstadosUsuarios c WHERE c.detalleCambioEstadoUsuario = :detalleCambioEstadoUsuario")
    , @NamedQuery(name = "CambiosEstadosUsuarios.findByFechaCambioEstado", query = "SELECT c FROM CambiosEstadosUsuarios c WHERE c.fechaCambioEstado = :fechaCambioEstado")})
public class CambiosEstadosUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_cambio_estado_usuario")
    private Integer codCambioEstadoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "login_usuario")
    private String loginUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_estado_usuario")
    private String nombreEstadoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "detalle_cambio_estado_usuario")
    private String detalleCambioEstadoUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_cambio_estado")
    @Temporal(TemporalType.DATE)
    private Date fechaCambioEstado;

    public CambiosEstadosUsuarios() {
    }

    public CambiosEstadosUsuarios(Integer codCambioEstadoUsuario) {
        this.codCambioEstadoUsuario = codCambioEstadoUsuario;
    }

    public CambiosEstadosUsuarios(Integer codCambioEstadoUsuario, String loginUsuario, String nombreEstadoUsuario, String detalleCambioEstadoUsuario, Date fechaCambioEstado) {
        this.codCambioEstadoUsuario = codCambioEstadoUsuario;
        this.loginUsuario = loginUsuario;
        this.nombreEstadoUsuario = nombreEstadoUsuario;
        this.detalleCambioEstadoUsuario = detalleCambioEstadoUsuario;
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public Integer getCodCambioEstadoUsuario() {
        return codCambioEstadoUsuario;
    }

    public void setCodCambioEstadoUsuario(Integer codCambioEstadoUsuario) {
        this.codCambioEstadoUsuario = codCambioEstadoUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getNombreEstadoUsuario() {
        return nombreEstadoUsuario;
    }

    public void setNombreEstadoUsuario(String nombreEstadoUsuario) {
        this.nombreEstadoUsuario = nombreEstadoUsuario;
    }

    public String getDetalleCambioEstadoUsuario() {
        return detalleCambioEstadoUsuario;
    }

    public void setDetalleCambioEstadoUsuario(String detalleCambioEstadoUsuario) {
        this.detalleCambioEstadoUsuario = detalleCambioEstadoUsuario;
    }

    public Date getFechaCambioEstado() {
        return fechaCambioEstado;
    }

    public void setFechaCambioEstado(Date fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCambioEstadoUsuario != null ? codCambioEstadoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CambiosEstadosUsuarios)) {
            return false;
        }
        CambiosEstadosUsuarios other = (CambiosEstadosUsuarios) object;
        if ((this.codCambioEstadoUsuario == null && other.codCambioEstadoUsuario != null) || (this.codCambioEstadoUsuario != null && !this.codCambioEstadoUsuario.equals(other.codCambioEstadoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.CambiosEstadosUsuarios[ codCambioEstadoUsuario=" + codCambioEstadoUsuario + " ]";
    }
    
}
