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
@Table(name = "tipos_finalizacion_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposFinalizacionReclamos.findAll", query = "SELECT t FROM TiposFinalizacionReclamos t"),
    @NamedQuery(name = "TiposFinalizacionReclamos.findByCodTipoFinalizacionReclamo", query = "SELECT t FROM TiposFinalizacionReclamos t WHERE t.codTipoFinalizacionReclamo = :codTipoFinalizacionReclamo"),
    @NamedQuery(name = "TiposFinalizacionReclamos.findByNombreTipoFinalizacionReclamo", query = "SELECT t FROM TiposFinalizacionReclamos t WHERE t.nombreTipoFinalizacionReclamo = :nombreTipoFinalizacionReclamo")})
public class TiposFinalizacionReclamos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_finalizacion_reclamo")
    private Integer codTipoFinalizacionReclamo;
    @Size(max = 2147483647)
    @Column(name = "nombre_tipo_finalizacion_reclamo")
    private String nombreTipoFinalizacionReclamo;
    @OneToMany(mappedBy = "fkCodTipoFinalizacionReclamo")
    private List<Reclamos> reclamosList;

    public TiposFinalizacionReclamos() {
    }

    public TiposFinalizacionReclamos(Integer codTipoFinalizacionReclamo) {
        this.codTipoFinalizacionReclamo = codTipoFinalizacionReclamo;
    }

    public Integer getCodTipoFinalizacionReclamo() {
        return codTipoFinalizacionReclamo;
    }

    public void setCodTipoFinalizacionReclamo(Integer codTipoFinalizacionReclamo) {
        this.codTipoFinalizacionReclamo = codTipoFinalizacionReclamo;
    }

    public String getNombreTipoFinalizacionReclamo() {
        return nombreTipoFinalizacionReclamo;
    }

    public void setNombreTipoFinalizacionReclamo(String nombreTipoFinalizacionReclamo) {
        this.nombreTipoFinalizacionReclamo = nombreTipoFinalizacionReclamo;
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
        hash += (codTipoFinalizacionReclamo != null ? codTipoFinalizacionReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposFinalizacionReclamos)) {
            return false;
        }
        TiposFinalizacionReclamos other = (TiposFinalizacionReclamos) object;
        if ((this.codTipoFinalizacionReclamo == null && other.codTipoFinalizacionReclamo != null) || (this.codTipoFinalizacionReclamo != null && !this.codTipoFinalizacionReclamo.equals(other.codTipoFinalizacionReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.TiposFinalizacionReclamos[ codTipoFinalizacionReclamo=" + codTipoFinalizacionReclamo + " ]";
    }
    
}
