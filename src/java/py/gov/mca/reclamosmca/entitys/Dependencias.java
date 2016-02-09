/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "dependencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dependencias.findAll", query = "SELECT d FROM Dependencias d"),
    @NamedQuery(name = "Dependencias.findByCodDependencia", query = "SELECT d FROM Dependencias d WHERE d.codDependencia = :codDependencia"),
    @NamedQuery(name = "Dependencias.findByNombreDependencia", query = "SELECT d FROM Dependencias d WHERE d.nombreDependencia = :nombreDependencia"),
    @NamedQuery(name = "Dependencias.findByTelefonoDependencia", query = "SELECT d FROM Dependencias d WHERE d.telefonoDependencia = :telefonoDependencia"),
    @NamedQuery(name = "Dependencias.findByInternoDependencia", query = "SELECT d FROM Dependencias d WHERE d.internoDependencia = :internoDependencia"),
    @NamedQuery(name = "Dependencias.findByMailDependencia", query = "SELECT d FROM Dependencias d WHERE d.mailDependencia = :mailDependencia")})
public class Dependencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_dependencia")
    private Integer codDependencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_dependencia")
    private String nombreDependencia;
    @Size(max = 2147483647)
    @Column(name = "telefono_dependencia")
    private String telefonoDependencia;
    @Size(max = 2147483647)
    @Column(name = "interno_dependencia")
    private String internoDependencia;
    @Size(max = 2147483647)
    @Column(name = "mail_dependencia")
    private String mailDependencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCodDependencia")
    private List<TiposReclamos> tiposReclamosList;
    @OneToMany(mappedBy = "fkCodDependencia")
    private List<Personas> personasList;

    public Dependencias() {
    }

    public Dependencias(Integer codDependencia) {
        this.codDependencia = codDependencia;
    }

    public Dependencias(Integer codDependencia, String nombreDependencia) {
        this.codDependencia = codDependencia;
        this.nombreDependencia = nombreDependencia;
    }

    public Integer getCodDependencia() {
        return codDependencia;
    }

    public void setCodDependencia(Integer codDependencia) {
        this.codDependencia = codDependencia;
    }

    public String getNombreDependencia() {
        return nombreDependencia;
    }

    public void setNombreDependencia(String nombreDependencia) {
        this.nombreDependencia = nombreDependencia;
    }

    public String getTelefonoDependencia() {
        return telefonoDependencia;
    }

    public void setTelefonoDependencia(String telefonoDependencia) {
        this.telefonoDependencia = telefonoDependencia;
    }

    public String getInternoDependencia() {
        return internoDependencia;
    }

    public void setInternoDependencia(String internoDependencia) {
        this.internoDependencia = internoDependencia;
    }

    public String getMailDependencia() {
        return mailDependencia;
    }

    public void setMailDependencia(String mailDependencia) {
        this.mailDependencia = mailDependencia;
    }

    @XmlTransient
    public List<TiposReclamos> getTiposReclamosList() {
        return tiposReclamosList;
    }

    public void setTiposReclamosList(List<TiposReclamos> tiposReclamosList) {
        this.tiposReclamosList = tiposReclamosList;
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
        hash += (codDependencia != null ? codDependencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dependencias)) {
            return false;
        }
        Dependencias other = (Dependencias) object;
        if ((this.codDependencia == null && other.codDependencia != null) || (this.codDependencia != null && !this.codDependencia.equals(other.codDependencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Dependencias[ codDependencia=" + codDependencia + " ]";
    }
    
}
