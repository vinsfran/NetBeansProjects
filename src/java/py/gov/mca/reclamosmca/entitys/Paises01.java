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
@Table(name = "paises_01")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises01.findAll", query = "SELECT p FROM Paises01 p"),
    @NamedQuery(name = "Paises01.findByCodPais", query = "SELECT p FROM Paises01 p WHERE p.codPais = :codPais"),
    @NamedQuery(name = "Paises01.findByNombrePais", query = "SELECT p FROM Paises01 p WHERE p.nombrePais = :nombrePais")})
public class Paises01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_pais")
    private Integer codPais;
    @Size(max = 2147483647)
    @Column(name = "nombre_pais")
    private String nombrePais;
    @OneToMany(mappedBy = "fkCodPais")
    private List<Paises02Departamentos> paises02DepartamentosList;

    public Paises01() {
    }

    public Paises01(Integer codPais) {
        this.codPais = codPais;
    }

    public Integer getCodPais() {
        return codPais;
    }

    public void setCodPais(Integer codPais) {
        this.codPais = codPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    @XmlTransient
    public List<Paises02Departamentos> getPaises02DepartamentosList() {
        return paises02DepartamentosList;
    }

    public void setPaises02DepartamentosList(List<Paises02Departamentos> paises02DepartamentosList) {
        this.paises02DepartamentosList = paises02DepartamentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPais != null ? codPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises01)) {
            return false;
        }
        Paises01 other = (Paises01) object;
        if ((this.codPais == null && other.codPais != null) || (this.codPais != null && !this.codPais.equals(other.codPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Paises01[ codPais=" + codPais + " ]";
    }
    
}
