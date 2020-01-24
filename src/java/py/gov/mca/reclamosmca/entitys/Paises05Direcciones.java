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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "paises_05_direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises05Direcciones.findAll", query = "SELECT p FROM Paises05Direcciones p")
    , @NamedQuery(name = "Paises05Direcciones.findByCodDireccion", query = "SELECT p FROM Paises05Direcciones p WHERE p.codDireccion = :codDireccion")
    , @NamedQuery(name = "Paises05Direcciones.findByDireccionNombre", query = "SELECT p FROM Paises05Direcciones p WHERE p.direccionNombre = :direccionNombre")
    , @NamedQuery(name = "Paises05Direcciones.findByDireccionLatitud", query = "SELECT p FROM Paises05Direcciones p WHERE p.direccionLatitud = :direccionLatitud")
    , @NamedQuery(name = "Paises05Direcciones.findByDireccionLongitud", query = "SELECT p FROM Paises05Direcciones p WHERE p.direccionLongitud = :direccionLongitud")})
public class Paises05Direcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_direccion")
    private Integer codDireccion;
    @Size(max = 2147483647)
    @Column(name = "direccion_nombre")
    private String direccionNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "direccion_latitud")
    private Double direccionLatitud;
    @Column(name = "direccion_longitud")
    private Double direccionLongitud;
    @JoinColumn(name = "fk_cod_barrio", referencedColumnName = "cod_barrio")
    @ManyToOne
    private Paises04Barrios fkCodBarrio;
    @OneToMany(mappedBy = "fkCodDireccion")
    private List<Reclamos> reclamosList;

    public Paises05Direcciones() {
    }

    public Paises05Direcciones(Integer codDireccion) {
        this.codDireccion = codDireccion;
    }

    public Integer getCodDireccion() {
        return codDireccion;
    }

    public void setCodDireccion(Integer codDireccion) {
        this.codDireccion = codDireccion;
    }

    public String getDireccionNombre() {
        return direccionNombre;
    }

    public void setDireccionNombre(String direccionNombre) {
        this.direccionNombre = direccionNombre;
    }

    public Double getDireccionLatitud() {
        return direccionLatitud;
    }

    public void setDireccionLatitud(Double direccionLatitud) {
        this.direccionLatitud = direccionLatitud;
    }

    public Double getDireccionLongitud() {
        return direccionLongitud;
    }

    public void setDireccionLongitud(Double direccionLongitud) {
        this.direccionLongitud = direccionLongitud;
    }

    public Paises04Barrios getFkCodBarrio() {
        return fkCodBarrio;
    }

    public void setFkCodBarrio(Paises04Barrios fkCodBarrio) {
        this.fkCodBarrio = fkCodBarrio;
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
        hash += (codDireccion != null ? codDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises05Direcciones)) {
            return false;
        }
        Paises05Direcciones other = (Paises05Direcciones) object;
        if ((this.codDireccion == null && other.codDireccion != null) || (this.codDireccion != null && !this.codDireccion.equals(other.codDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Paises05Direcciones[ codDireccion=" + codDireccion + " ]";
    }
    
}
