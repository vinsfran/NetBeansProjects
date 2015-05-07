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
@Table(name = "sexos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sexos.findAll", query = "SELECT s FROM Sexos s"),
    @NamedQuery(name = "Sexos.findByCodSexo", query = "SELECT s FROM Sexos s WHERE s.codSexo = :codSexo"),
    @NamedQuery(name = "Sexos.findByNombreSexo", query = "SELECT s FROM Sexos s WHERE s.nombreSexo = :nombreSexo")})
public class Sexos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_sexo")
    private Integer codSexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_sexo")
    private String nombreSexo;
    @OneToMany(mappedBy = "fkCodSexo")
    private List<Personas> personasList;

    public Sexos() {
    }

    public Sexos(Integer codSexo) {
        this.codSexo = codSexo;
    }

    public Sexos(Integer codSexo, String nombreSexo) {
        this.codSexo = codSexo;
        this.nombreSexo = nombreSexo;
    }

    public Integer getCodSexo() {
        return codSexo;
    }

    public void setCodSexo(Integer codSexo) {
        this.codSexo = codSexo;
    }

    public String getNombreSexo() {
        return nombreSexo;
    }

    public void setNombreSexo(String nombreSexo) {
        this.nombreSexo = nombreSexo;
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
        hash += (codSexo != null ? codSexo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sexos)) {
            return false;
        }
        Sexos other = (Sexos) object;
        if ((this.codSexo == null && other.codSexo != null) || (this.codSexo != null && !this.codSexo.equals(other.codSexo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Sexos[ codSexo=" + codSexo + " ]";
    }
    
}
