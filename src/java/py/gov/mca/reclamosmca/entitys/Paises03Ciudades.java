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
@Table(name = "paises_03_ciudades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises03Ciudades.findAll", query = "SELECT p FROM Paises03Ciudades p"),
    @NamedQuery(name = "Paises03Ciudades.findByCodCuidad", query = "SELECT p FROM Paises03Ciudades p WHERE p.codCuidad = :codCuidad"),
    @NamedQuery(name = "Paises03Ciudades.findByNombreCiudad", query = "SELECT p FROM Paises03Ciudades p WHERE p.nombreCiudad = :nombreCiudad")})
public class Paises03Ciudades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codCuidad")
    private Integer codCuidad;
    @Size(max = 2147483647)
    @Column(name = "nombreCiudad")
    private String nombreCiudad;
    @JoinColumn(name = "fk_departamento", referencedColumnName = "codDepartamento")
    @ManyToOne
    private Paises02Departamentos fkDepartamento;
    @OneToMany(mappedBy = "fkCiudad")
    private List<Paises04Barrios> paises04BarriosList;

    public Paises03Ciudades() {
    }

    public Paises03Ciudades(Integer codCuidad) {
        this.codCuidad = codCuidad;
    }

    public Integer getCodCuidad() {
        return codCuidad;
    }

    public void setCodCuidad(Integer codCuidad) {
        this.codCuidad = codCuidad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public Paises02Departamentos getFkDepartamento() {
        return fkDepartamento;
    }

    public void setFkDepartamento(Paises02Departamentos fkDepartamento) {
        this.fkDepartamento = fkDepartamento;
    }

    @XmlTransient
    public List<Paises04Barrios> getPaises04BarriosList() {
        return paises04BarriosList;
    }

    public void setPaises04BarriosList(List<Paises04Barrios> paises04BarriosList) {
        this.paises04BarriosList = paises04BarriosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCuidad != null ? codCuidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises03Ciudades)) {
            return false;
        }
        Paises03Ciudades other = (Paises03Ciudades) object;
        if ((this.codCuidad == null && other.codCuidad != null) || (this.codCuidad != null && !this.codCuidad.equals(other.codCuidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Paises03Ciudades[ codCuidad=" + codCuidad + " ]";
    }
    
}
