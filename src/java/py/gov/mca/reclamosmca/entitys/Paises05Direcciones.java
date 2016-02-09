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
    @NamedQuery(name = "Paises05Direcciones.findAll", query = "SELECT p FROM Paises05Direcciones p"),
    @NamedQuery(name = "Paises05Direcciones.findByCodDireccion", query = "SELECT p FROM Paises05Direcciones p WHERE p.codDireccion = :codDireccion"),
    @NamedQuery(name = "Paises05Direcciones.findByNombreDireccion", query = "SELECT p FROM Paises05Direcciones p WHERE p.nombreDireccion = :nombreDireccion"),
    @NamedQuery(name = "Paises05Direcciones.findByLatitudDireccion", query = "SELECT p FROM Paises05Direcciones p WHERE p.latitudDireccion = :latitudDireccion"),
    @NamedQuery(name = "Paises05Direcciones.findByLongitudDureccion", query = "SELECT p FROM Paises05Direcciones p WHERE p.longitudDureccion = :longitudDureccion")})
public class Paises05Direcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codDireccion")
    private Integer codDireccion;
    @Size(max = 2147483647)
    @Column(name = "nombreDireccion")
    private String nombreDireccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitudDireccion")
    private Double latitudDireccion;
    @Column(name = "longitudDureccion")
    private Double longitudDureccion;
    @JoinColumn(name = "fkCodBarrio", referencedColumnName = "codBarrio")
    @ManyToOne
    private Paises04Barrios fkCodBarrio;
    @OneToMany(mappedBy = "fkDireccion")
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

    public String getNombreDireccion() {
        return nombreDireccion;
    }

    public void setNombreDireccion(String nombreDireccion) {
        this.nombreDireccion = nombreDireccion;
    }

    public Double getLatitudDireccion() {
        return latitudDireccion;
    }

    public void setLatitudDireccion(Double latitudDireccion) {
        this.latitudDireccion = latitudDireccion;
    }

    public Double getLongitudDureccion() {
        return longitudDureccion;
    }

    public void setLongitudDureccion(Double longitudDureccion) {
        this.longitudDureccion = longitudDureccion;
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
