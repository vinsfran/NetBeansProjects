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
@Table(name = "paises_02_departamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises02Departamentos.findAll", query = "SELECT p FROM Paises02Departamentos p"),
    @NamedQuery(name = "Paises02Departamentos.findByCodDepartamento", query = "SELECT p FROM Paises02Departamentos p WHERE p.codDepartamento = :codDepartamento"),
    @NamedQuery(name = "Paises02Departamentos.findByNombreDepartamento", query = "SELECT p FROM Paises02Departamentos p WHERE p.nombreDepartamento = :nombreDepartamento")})
public class Paises02Departamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codDepartamento")
    private Integer codDepartamento;
    @Size(max = 2147483647)
    @Column(name = "nombreDepartamento")
    private String nombreDepartamento;
    @JoinColumn(name = "fk_pais", referencedColumnName = "codPais")
    @ManyToOne
    private Paises01 fkPais;
    @OneToMany(mappedBy = "fkDepartamento")
    private List<Paises03Ciudades> paises03CiudadesList;

    public Paises02Departamentos() {
    }

    public Paises02Departamentos(Integer codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public Integer getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(Integer codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Paises01 getFkPais() {
        return fkPais;
    }

    public void setFkPais(Paises01 fkPais) {
        this.fkPais = fkPais;
    }

    @XmlTransient
    public List<Paises03Ciudades> getPaises03CiudadesList() {
        return paises03CiudadesList;
    }

    public void setPaises03CiudadesList(List<Paises03Ciudades> paises03CiudadesList) {
        this.paises03CiudadesList = paises03CiudadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDepartamento != null ? codDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises02Departamentos)) {
            return false;
        }
        Paises02Departamentos other = (Paises02Departamentos) object;
        if ((this.codDepartamento == null && other.codDepartamento != null) || (this.codDepartamento != null && !this.codDepartamento.equals(other.codDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Paises02Departamentos[ codDepartamento=" + codDepartamento + " ]";
    }
    
}
