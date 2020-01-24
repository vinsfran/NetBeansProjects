/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "estados_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosUsuarios.findAll", query = "SELECT e FROM EstadosUsuarios e")
    , @NamedQuery(name = "EstadosUsuarios.findByCodEstadoUsuario", query = "SELECT e FROM EstadosUsuarios e WHERE e.codEstadoUsuario = :codEstadoUsuario")
    , @NamedQuery(name = "EstadosUsuarios.findByNombreEstadoUsuario", query = "SELECT e FROM EstadosUsuarios e WHERE e.nombreEstadoUsuario = :nombreEstadoUsuario")})
public class EstadosUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_estado_usuario")
    private Integer codEstadoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_estado_usuario")
    private String nombreEstadoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCodEstadoUsuario")
    private List<Usuarios> usuariosList;

    public EstadosUsuarios() {
    }

    public EstadosUsuarios(Integer codEstadoUsuario) {
        this.codEstadoUsuario = codEstadoUsuario;
    }

    public EstadosUsuarios(Integer codEstadoUsuario, String nombreEstadoUsuario) {
        this.codEstadoUsuario = codEstadoUsuario;
        this.nombreEstadoUsuario = nombreEstadoUsuario;
    }

    public Integer getCodEstadoUsuario() {
        return codEstadoUsuario;
    }

    public void setCodEstadoUsuario(Integer codEstadoUsuario) {
        this.codEstadoUsuario = codEstadoUsuario;
    }

    public String getNombreEstadoUsuario() {
        return nombreEstadoUsuario;
    }

    public void setNombreEstadoUsuario(String nombreEstadoUsuario) {
        this.nombreEstadoUsuario = nombreEstadoUsuario;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstadoUsuario != null ? codEstadoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosUsuarios)) {
            return false;
        }
        EstadosUsuarios other = (EstadosUsuarios) object;
        if ((this.codEstadoUsuario == null && other.codEstadoUsuario != null) || (this.codEstadoUsuario != null && !this.codEstadoUsuario.equals(other.codEstadoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.EstadosUsuarios[ codEstadoUsuario=" + codEstadoUsuario + " ]";
    }
    
}
