/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "tipos_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposReclamos.findAll", query = "SELECT t FROM TiposReclamos t"),
    @NamedQuery(name = "TiposReclamos.findByCodTipoReclamo", query = "SELECT t FROM TiposReclamos t WHERE t.codTipoReclamo = :codTipoReclamo"),
    @NamedQuery(name = "TiposReclamos.findByNombreTipoReclamo", query = "SELECT t FROM TiposReclamos t WHERE t.nombreTipoReclamo = :nombreTipoReclamo"),
    @NamedQuery(name = "TiposReclamos.findByDiasMaximoPendientes", query = "SELECT t FROM TiposReclamos t WHERE t.diasMaximoPendientes = :diasMaximoPendientes"),
    @NamedQuery(name = "TiposReclamos.findByDiasMaximoFinalizados", query = "SELECT t FROM TiposReclamos t WHERE t.diasMaximoFinalizados = :diasMaximoFinalizados"),
    @NamedQuery(name = "TiposReclamos.findByTopTipoReclamo", query = "SELECT t FROM TiposReclamos t WHERE t.topTipoReclamo = :topTipoReclamo")})
public class TiposReclamos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_reclamo")
    private Integer codTipoReclamo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_tipo_reclamo")
    private String nombreTipoReclamo;
    @Column(name = "dias_maximo_pendientes")
    private Integer diasMaximoPendientes;
    @Column(name = "dias_maximo_finalizados")
    private Integer diasMaximoFinalizados;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "top_tipo_reclamo")
    private Double topTipoReclamo;
    @JoinColumn(name = "fk_cod_dependencia", referencedColumnName = "cod_dependencia")
    @ManyToOne(optional = false)
    private Dependencias fkCodDependencia;

    public TiposReclamos() {
    }

    public TiposReclamos(Integer codTipoReclamo) {
        this.codTipoReclamo = codTipoReclamo;
    }

    public TiposReclamos(Integer codTipoReclamo, String nombreTipoReclamo) {
        this.codTipoReclamo = codTipoReclamo;
        this.nombreTipoReclamo = nombreTipoReclamo;
    }

    public Integer getCodTipoReclamo() {
        return codTipoReclamo;
    }

    public void setCodTipoReclamo(Integer codTipoReclamo) {
        this.codTipoReclamo = codTipoReclamo;
    }

    public String getNombreTipoReclamo() {
        return nombreTipoReclamo;
    }

    public void setNombreTipoReclamo(String nombreTipoReclamo) {
        this.nombreTipoReclamo = nombreTipoReclamo;
    }

    public Integer getDiasMaximoPendientes() {
        return diasMaximoPendientes;
    }

    public void setDiasMaximoPendientes(Integer diasMaximoPendientes) {
        this.diasMaximoPendientes = diasMaximoPendientes;
    }

    public Integer getDiasMaximoFinalizados() {
        return diasMaximoFinalizados;
    }

    public void setDiasMaximoFinalizados(Integer diasMaximoFinalizados) {
        this.diasMaximoFinalizados = diasMaximoFinalizados;
    }

    public Double getTopTipoReclamo() {
        return topTipoReclamo;
    }

    public void setTopTipoReclamo(Double topTipoReclamo) {
        this.topTipoReclamo = topTipoReclamo;
    }

    public Dependencias getFkCodDependencia() {
        return fkCodDependencia;
    }

    public void setFkCodDependencia(Dependencias fkCodDependencia) {
        this.fkCodDependencia = fkCodDependencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoReclamo != null ? codTipoReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposReclamos)) {
            return false;
        }
        TiposReclamos other = (TiposReclamos) object;
        if ((this.codTipoReclamo == null && other.codTipoReclamo != null) || (this.codTipoReclamo != null && !this.codTipoReclamo.equals(other.codTipoReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.TiposReclamos[ codTipoReclamo=" + codTipoReclamo + " ]";
    }
    
}
