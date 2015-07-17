/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByCodPersona", query = "SELECT p FROM Personas p WHERE p.codPersona = :codPersona"),
    @NamedQuery(name = "Personas.findByCedulaPersona", query = "SELECT p FROM Personas p WHERE p.cedulaPersona = :cedulaPersona"),
    @NamedQuery(name = "Personas.findByNombrePersona", query = "SELECT p FROM Personas p WHERE p.nombrePersona = :nombrePersona"),
    @NamedQuery(name = "Personas.findByApellidoPersona", query = "SELECT p FROM Personas p WHERE p.apellidoPersona = :apellidoPersona"),
    @NamedQuery(name = "Personas.findByFechaIngresoPersona", query = "SELECT p FROM Personas p WHERE p.fechaIngresoPersona = :fechaIngresoPersona"),
    @NamedQuery(name = "Personas.findByFechaNacimientoPersona", query = "SELECT p FROM Personas p WHERE p.fechaNacimientoPersona = :fechaNacimientoPersona"),
    @NamedQuery(name = "Personas.findByFechaRegistroPersona", query = "SELECT p FROM Personas p WHERE p.fechaRegistroPersona = :fechaRegistroPersona"),
    @NamedQuery(name = "Personas.findByDireccionPersona", query = "SELECT p FROM Personas p WHERE p.direccionPersona = :direccionPersona"),
    @NamedQuery(name = "Personas.findByTelefonoPersona", query = "SELECT p FROM Personas p WHERE p.telefonoPersona = :telefonoPersona"),
    @NamedQuery(name = "Personas.findByCelularPersona", query = "SELECT p FROM Personas p WHERE p.celularPersona = :celularPersona"),
    @NamedQuery(name = "Personas.findByCtaCtePersona", query = "SELECT p FROM Personas p WHERE p.ctaCtePersona = :ctaCtePersona"),
    @NamedQuery(name = "Personas.findByOrigenRegistro", query = "SELECT p FROM Personas p WHERE p.origenRegistro = :origenRegistro")})
public class Personas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_persona")
    private Integer codPersona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cedula_persona")
    private String cedulaPersona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_persona")
    private String nombrePersona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellido_persona")
    private String apellidoPersona;
    @Column(name = "fecha_ingreso_persona")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoPersona;
    @Column(name = "fecha_nacimiento_persona")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoPersona;
    @Column(name = "fecha_registro_persona")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistroPersona;
    @Size(max = 2147483647)
    @Column(name = "direccion_persona")
    private String direccionPersona;
    @Size(max = 2147483647)
    @Column(name = "telefono_persona")
    private String telefonoPersona;
    @Size(max = 2147483647)
    @Column(name = "celular_persona")
    private String celularPersona;
    @Size(max = 2147483647)
    @Column(name = "cta_cte_persona")
    private String ctaCtePersona;
    @Size(max = 2147483647)
    @Column(name = "origen_registro")
    private String origenRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCodPersona")
    private List<Usuarios> usuariosList;
    @JoinColumn(name = "fk_cod_dependencia", referencedColumnName = "cod_dependencia")
    @ManyToOne
    private Dependencias fkCodDependencia;
    @JoinColumn(name = "fk_cod_laboral", referencedColumnName = "cod_laboral")
    @ManyToOne
    private Laborales fkCodLaboral;
    @JoinColumn(name = "fk_cod_sexo", referencedColumnName = "cod_sexo")
    @ManyToOne
    private Sexos fkCodSexo;

    public Personas() {
    }

    public Personas(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public Personas(Integer codPersona, String cedulaPersona, String nombrePersona, String apellidoPersona) {
        this.codPersona = codPersona;
        this.cedulaPersona = cedulaPersona;
        this.nombrePersona = nombrePersona;
        this.apellidoPersona = apellidoPersona;
    }

    public Integer getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public String getCedulaPersona() {
        return cedulaPersona;
    }

    public void setCedulaPersona(String cedulaPersona) {
        this.cedulaPersona = cedulaPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }

    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public Date getFechaIngresoPersona() {
        return fechaIngresoPersona;
    }

    public void setFechaIngresoPersona(Date fechaIngresoPersona) {
        this.fechaIngresoPersona = fechaIngresoPersona;
    }

    public Date getFechaNacimientoPersona() {
        return fechaNacimientoPersona;
    }

    public void setFechaNacimientoPersona(Date fechaNacimientoPersona) {
        this.fechaNacimientoPersona = fechaNacimientoPersona;
    }

    public Date getFechaRegistroPersona() {
        return fechaRegistroPersona;
    }

    public void setFechaRegistroPersona(Date fechaRegistroPersona) {
        this.fechaRegistroPersona = fechaRegistroPersona;
    }

    public String getDireccionPersona() {
        return direccionPersona;
    }

    public void setDireccionPersona(String direccionPersona) {
        this.direccionPersona = direccionPersona;
    }

    public String getTelefonoPersona() {
        return telefonoPersona;
    }

    public void setTelefonoPersona(String telefonoPersona) {
        this.telefonoPersona = telefonoPersona;
    }

    public String getCelularPersona() {
        return celularPersona;
    }

    public void setCelularPersona(String celularPersona) {
        this.celularPersona = celularPersona;
    }

    public String getCtaCtePersona() {
        return ctaCtePersona;
    }

    public void setCtaCtePersona(String ctaCtePersona) {
        this.ctaCtePersona = ctaCtePersona;
    }

    public String getOrigenRegistro() {
        return origenRegistro;
    }

    public void setOrigenRegistro(String origenRegistro) {
        this.origenRegistro = origenRegistro;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public Dependencias getFkCodDependencia() {
        return fkCodDependencia;
    }

    public void setFkCodDependencia(Dependencias fkCodDependencia) {
        this.fkCodDependencia = fkCodDependencia;
    }

    public Laborales getFkCodLaboral() {
        return fkCodLaboral;
    }

    public void setFkCodLaboral(Laborales fkCodLaboral) {
        this.fkCodLaboral = fkCodLaboral;
    }

    public Sexos getFkCodSexo() {
        return fkCodSexo;
    }

    public void setFkCodSexo(Sexos fkCodSexo) {
        this.fkCodSexo = fkCodSexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPersona != null ? codPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.codPersona == null && other.codPersona != null) || (this.codPersona != null && !this.codPersona.equals(other.codPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Personas[ codPersona=" + codPersona + " ]";
    }
    
}
