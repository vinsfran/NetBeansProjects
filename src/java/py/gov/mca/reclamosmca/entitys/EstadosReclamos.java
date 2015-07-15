/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "estados_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosReclamos.findAll", query = "SELECT e FROM EstadosReclamos e"),
    @NamedQuery(name = "EstadosReclamos.findByCodEstadoReclamo", query = "SELECT e FROM EstadosReclamos e WHERE e.codEstadoReclamo = :codEstadoReclamo"),
    @NamedQuery(name = "EstadosReclamos.findByNombreEstadoReclamo", query = "SELECT e FROM EstadosReclamos e WHERE e.nombreEstadoReclamo = :nombreEstadoReclamo")})
public class EstadosReclamos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_estado_reclamo")
    private Integer codEstadoReclamo;
    @Size(max = 255)
    @Column(name = "nombre_estado_reclamo")
    private String nombreEstadoReclamo;
    @OneToMany(mappedBy = "fkCodEstadoReclamo")
    private List<Reclamos> reclamosList;

    public EstadosReclamos() {
    }

    public EstadosReclamos(Integer codEstadoReclamo) {
        this.codEstadoReclamo = codEstadoReclamo;
    }

    public Integer getCodEstadoReclamo() {
        return codEstadoReclamo;
    }

    public void setCodEstadoReclamo(Integer codEstadoReclamo) {
        this.codEstadoReclamo = codEstadoReclamo;
    }

    public String getNombreEstadoReclamo() {
        return nombreEstadoReclamo;
    }

    public void setNombreEstadoReclamo(String nombreEstadoReclamo) {
        this.nombreEstadoReclamo = nombreEstadoReclamo;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList() {
        return reclamosList;
    }

    public void setReclamosList(List<Reclamos> reclamosList) {
        this.reclamosList = reclamosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstadoReclamo != null ? codEstadoReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosReclamos)) {
            return false;
        }
        EstadosReclamos other = (EstadosReclamos) object;
        if ((this.codEstadoReclamo == null && other.codEstadoReclamo != null) || (this.codEstadoReclamo != null && !this.codEstadoReclamo.equals(other.codEstadoReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.EstadosReclamos[ codEstadoReclamo=" + codEstadoReclamo + " ]";
    }
    
}
