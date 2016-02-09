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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByCodUsuario", query = "SELECT u FROM Usuarios u WHERE u.codUsuario = :codUsuario"),
    @NamedQuery(name = "Usuarios.findByLoginUsuario", query = "SELECT u FROM Usuarios u WHERE u.loginUsuario = :loginUsuario"),
    @NamedQuery(name = "Usuarios.findByClaveUsuario", query = "SELECT u FROM Usuarios u WHERE u.claveUsuario = :claveUsuario")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_usuario")
    private Integer codUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "login_usuario")
    private String loginUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "clave_usuario")
    private String claveUsuario;
    @OneToMany(mappedBy = "fkCodUsuarioCulminacion")
    private List<Reclamos> reclamosList;
    @OneToMany(mappedBy = "fkCodUsuario")
    private List<Reclamos> reclamosList1;
    @OneToMany(mappedBy = "fkCodUsuarioAtencion")
    private List<Reclamos> reclamosList2;
    @OneToMany(mappedBy = "fkCodUsuarioDerivacion")
    private List<Reclamos> reclamosList3;
    @JoinColumn(name = "fk_cod_estado_usuario", referencedColumnName = "cod_estado_usuario")
    @ManyToOne(optional = false)
    private EstadosUsuarios fkCodEstadoUsuario;
    @JoinColumn(name = "fk_cod_persona", referencedColumnName = "cod_persona")
    @ManyToOne(optional = false)
    private Personas fkCodPersona;
    @JoinColumn(name = "fk_cod_rol", referencedColumnName = "cod_rol")
    @ManyToOne(optional = false)
    private Roles fkCodRol;

    public Usuarios() {
    }

    public Usuarios(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Usuarios(Integer codUsuario, String loginUsuario, String claveUsuario) {
        this.codUsuario = codUsuario;
        this.loginUsuario = loginUsuario;
        this.claveUsuario = claveUsuario;
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList() {
        return reclamosList;
    }

    public void setReclamosList(List<Reclamos> reclamosList) {
        this.reclamosList = reclamosList;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList1() {
        return reclamosList1;
    }

    public void setReclamosList1(List<Reclamos> reclamosList1) {
        this.reclamosList1 = reclamosList1;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList2() {
        return reclamosList2;
    }

    public void setReclamosList2(List<Reclamos> reclamosList2) {
        this.reclamosList2 = reclamosList2;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList3() {
        return reclamosList3;
    }

    public void setReclamosList3(List<Reclamos> reclamosList3) {
        this.reclamosList3 = reclamosList3;
    }

    public EstadosUsuarios getFkCodEstadoUsuario() {
        return fkCodEstadoUsuario;
    }

    public void setFkCodEstadoUsuario(EstadosUsuarios fkCodEstadoUsuario) {
        this.fkCodEstadoUsuario = fkCodEstadoUsuario;
    }

    public Personas getFkCodPersona() {
        return fkCodPersona;
    }

    public void setFkCodPersona(Personas fkCodPersona) {
        this.fkCodPersona = fkCodPersona;
    }

    public Roles getFkCodRol() {
        return fkCodRol;
    }

    public void setFkCodRol(Roles fkCodRol) {
        this.fkCodRol = fkCodRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Usuarios[ codUsuario=" + codUsuario + " ]";
    }
    
}
