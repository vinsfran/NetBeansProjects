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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "laborales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laborales.findAll", query = "SELECT l FROM Laborales l"),
    @NamedQuery(name = "Laborales.findByCodLaboral", query = "SELECT l FROM Laborales l WHERE l.codLaboral = :codLaboral"),
    @NamedQuery(name = "Laborales.findByNombreLaboral", query = "SELECT l FROM Laborales l WHERE l.nombreLaboral = :nombreLaboral")})
public class Laborales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_laboral")
    private Integer codLaboral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_laboral")
    private String nombreLaboral;
    @OneToMany(mappedBy = "fkCodLaboral")
    private List<Personas> personasList;

    public Laborales() {
    }

    public Laborales(Integer codLaboral) {
        this.codLaboral = codLaboral;
    }

    public Laborales(Integer codLaboral, String nombreLaboral) {
        this.codLaboral = codLaboral;
        this.nombreLaboral = nombreLaboral;
    }

    public Integer getCodLaboral() {
        return codLaboral;
    }

    public void setCodLaboral(Integer codLaboral) {
        this.codLaboral = codLaboral;
    }

    public String getNombreLaboral() {
        return nombreLaboral;
    }

    public void setNombreLaboral(String nombreLaboral) {
        this.nombreLaboral = nombreLaboral;
    }

    @XmlTransient
    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codLaboral != null ? codLaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laborales)) {
            return false;
        }
        Laborales other = (Laborales) object;
        if ((this.codLaboral == null && other.codLaboral != null) || (this.codLaboral != null && !this.codLaboral.equals(other.codLaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Laborales[ codLaboral=" + codLaboral + " ]";
    }
    
}
