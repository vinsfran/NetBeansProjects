/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reclamos.findAll", query = "SELECT r FROM Reclamos r"),
    @NamedQuery(name = "Reclamos.findByCodReclamo", query = "SELECT r FROM Reclamos r WHERE r.codReclamo = :codReclamo"),
    @NamedQuery(name = "Reclamos.findByDescripcionReclamoContribuyente", query = "SELECT r FROM Reclamos r WHERE r.descripcionReclamoContribuyente = :descripcionReclamoContribuyente"),
    @NamedQuery(name = "Reclamos.findByDireccionReclamo", query = "SELECT r FROM Reclamos r WHERE r.direccionReclamo = :direccionReclamo"),
    @NamedQuery(name = "Reclamos.findByOrigenReclamo", query = "SELECT r FROM Reclamos r WHERE r.origenReclamo = :origenReclamo"),
    @NamedQuery(name = "Reclamos.findByFechaReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaReclamo = :fechaReclamo"),
    @NamedQuery(name = "Reclamos.findByDescripcionAtencionReclamo", query = "SELECT r FROM Reclamos r WHERE r.descripcionAtencionReclamo = :descripcionAtencionReclamo"),
    @NamedQuery(name = "Reclamos.findByFechaAtencionReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaAtencionReclamo = :fechaAtencionReclamo"),
    @NamedQuery(name = "Reclamos.findByDescripcionCulminacionReclamo", query = "SELECT r FROM Reclamos r WHERE r.descripcionCulminacionReclamo = :descripcionCulminacionReclamo"),
    @NamedQuery(name = "Reclamos.findByFechaCulminacionReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaCulminacionReclamo = :fechaCulminacionReclamo"),
    @NamedQuery(name = "Reclamos.findByCantidadDiasProceso", query = "SELECT r FROM Reclamos r WHERE r.cantidadDiasProceso = :cantidadDiasProceso"),
    @NamedQuery(name = "Reclamos.findByCtaCteContribuyente", query = "SELECT r FROM Reclamos r WHERE r.ctaCteContribuyente = :ctaCteContribuyente"),
    @NamedQuery(name = "Reclamos.findByLatitud", query = "SELECT r FROM Reclamos r WHERE r.latitud = :latitud"),
    @NamedQuery(name = "Reclamos.findByLongitud", query = "SELECT r FROM Reclamos r WHERE r.longitud = :longitud")})
public class Reclamos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_reclamo")
    private Integer codReclamo;
    @Size(max = 500)
    @Column(name = "descripcion_reclamo_contribuyente")
    private String descripcionReclamoContribuyente;
    @Size(max = 2147483647)
    @Column(name = "direccion_reclamo")
    private String direccionReclamo;
    @Size(max = 2147483647)
    @Column(name = "origen_reclamo")
    private String origenReclamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_reclamo")
    @Temporal(TemporalType.DATE)
    private Date fechaReclamo;
    @Size(max = 500)
    @Column(name = "descripcion_atencion_reclamo")
    private String descripcionAtencionReclamo;
    @Column(name = "fecha_atencion_reclamo")
    @Temporal(TemporalType.DATE)
    private Date fechaAtencionReclamo;
    @Size(max = 500)
    @Column(name = "descripcion_culminacion_reclamo")
    private String descripcionCulminacionReclamo;
    @Column(name = "fecha_culminacion_reclamo")
    @Temporal(TemporalType.DATE)
    private Date fechaCulminacionReclamo;
    @Column(name = "cantidad_dias_proceso")
    private Integer cantidadDiasProceso;
    @Size(max = 2147483647)
    @Column(name = "cta_cte_contribuyente")
    private String ctaCteContribuyente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitud")
    private Double latitud;
    @Column(name = "longitud")
    private Double longitud;
    @JoinColumn(name = "fk_cod_estado_reclamo", referencedColumnName = "cod_estado_reclamo")
    @ManyToOne(optional = false)
    private EstadosReclamos fkCodEstadoReclamo;
    @JoinColumn(name = "fk_imagen", referencedColumnName = "cod_imagen")
    @ManyToOne
    private Imagenes fkImagen;
    @JoinColumn(name = "fkDireccion", referencedColumnName = "codDireccion")
    @ManyToOne
    private Paises05Direcciones fkDireccion;
    @JoinColumn(name = "fk_cod_tipo_finalizacion_reclamo", referencedColumnName = "cod_tipo_finalizacion_reclamo")
    @ManyToOne
    private TiposFinalizacionReclamos fkCodTipoFinalizacionReclamo;
    @JoinColumn(name = "fk_cod_tipo_reclamo", referencedColumnName = "cod_tipo_reclamo")
    @ManyToOne(optional = false)
    private TiposReclamos fkCodTipoReclamo;
    @JoinColumn(name = "fk_cod_usuario_culminacion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioCulminacion;
    @JoinColumn(name = "fk_cod_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuario;
    @JoinColumn(name = "fk_cod_usuario_atencion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioAtencion;
    @JoinColumn(name = "fk_cod_usuario_derivacion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioDerivacion;

    public Reclamos() {
    }

    public Reclamos(Integer codReclamo) {
        this.codReclamo = codReclamo;
    }

    public Reclamos(Integer codReclamo, Date fechaReclamo) {
        this.codReclamo = codReclamo;
        this.fechaReclamo = fechaReclamo;
    }

    public Integer getCodReclamo() {
        return codReclamo;
    }

    public void setCodReclamo(Integer codReclamo) {
        this.codReclamo = codReclamo;
    }

    public String getDescripcionReclamoContribuyente() {
        return descripcionReclamoContribuyente;
    }

    public void setDescripcionReclamoContribuyente(String descripcionReclamoContribuyente) {
        this.descripcionReclamoContribuyente = descripcionReclamoContribuyente;
    }

    public String getDireccionReclamo() {
        return direccionReclamo;
    }

    public void setDireccionReclamo(String direccionReclamo) {
        this.direccionReclamo = direccionReclamo;
    }

    public String getOrigenReclamo() {
        return origenReclamo;
    }

    public void setOrigenReclamo(String origenReclamo) {
        this.origenReclamo = origenReclamo;
    }

    public Date getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(Date fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getDescripcionAtencionReclamo() {
        return descripcionAtencionReclamo;
    }

    public void setDescripcionAtencionReclamo(String descripcionAtencionReclamo) {
        this.descripcionAtencionReclamo = descripcionAtencionReclamo;
    }

    public Date getFechaAtencionReclamo() {
        return fechaAtencionReclamo;
    }

    public void setFechaAtencionReclamo(Date fechaAtencionReclamo) {
        this.fechaAtencionReclamo = fechaAtencionReclamo;
    }

    public String getDescripcionCulminacionReclamo() {
        return descripcionCulminacionReclamo;
    }

    public void setDescripcionCulminacionReclamo(String descripcionCulminacionReclamo) {
        this.descripcionCulminacionReclamo = descripcionCulminacionReclamo;
    }

    public Date getFechaCulminacionReclamo() {
        return fechaCulminacionReclamo;
    }

    public void setFechaCulminacionReclamo(Date fechaCulminacionReclamo) {
        this.fechaCulminacionReclamo = fechaCulminacionReclamo;
    }

    public Integer getCantidadDiasProceso() {
        return cantidadDiasProceso;
    }

    public void setCantidadDiasProceso(Integer cantidadDiasProceso) {
        this.cantidadDiasProceso = cantidadDiasProceso;
    }

    public String getCtaCteContribuyente() {
        return ctaCteContribuyente;
    }

    public void setCtaCteContribuyente(String ctaCteContribuyente) {
        this.ctaCteContribuyente = ctaCteContribuyente;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public EstadosReclamos getFkCodEstadoReclamo() {
        return fkCodEstadoReclamo;
    }

    public void setFkCodEstadoReclamo(EstadosReclamos fkCodEstadoReclamo) {
        this.fkCodEstadoReclamo = fkCodEstadoReclamo;
    }

    public Imagenes getFkImagen() {
        return fkImagen;
    }

    public void setFkImagen(Imagenes fkImagen) {
        this.fkImagen = fkImagen;
    }

    public Paises05Direcciones getFkDireccion() {
        return fkDireccion;
    }

    public void setFkDireccion(Paises05Direcciones fkDireccion) {
        this.fkDireccion = fkDireccion;
    }

    public TiposFinalizacionReclamos getFkCodTipoFinalizacionReclamo() {
        return fkCodTipoFinalizacionReclamo;
    }

    public void setFkCodTipoFinalizacionReclamo(TiposFinalizacionReclamos fkCodTipoFinalizacionReclamo) {
        this.fkCodTipoFinalizacionReclamo = fkCodTipoFinalizacionReclamo;
    }

    public TiposReclamos getFkCodTipoReclamo() {
        return fkCodTipoReclamo;
    }

    public void setFkCodTipoReclamo(TiposReclamos fkCodTipoReclamo) {
        this.fkCodTipoReclamo = fkCodTipoReclamo;
    }

    public Usuarios getFkCodUsuarioCulminacion() {
        return fkCodUsuarioCulminacion;
    }

    public void setFkCodUsuarioCulminacion(Usuarios fkCodUsuarioCulminacion) {
        this.fkCodUsuarioCulminacion = fkCodUsuarioCulminacion;
    }

    public Usuarios getFkCodUsuario() {
        return fkCodUsuario;
    }

    public void setFkCodUsuario(Usuarios fkCodUsuario) {
        this.fkCodUsuario = fkCodUsuario;
    }

    public Usuarios getFkCodUsuarioAtencion() {
        return fkCodUsuarioAtencion;
    }

    public void setFkCodUsuarioAtencion(Usuarios fkCodUsuarioAtencion) {
        this.fkCodUsuarioAtencion = fkCodUsuarioAtencion;
    }

    public Usuarios getFkCodUsuarioDerivacion() {
        return fkCodUsuarioDerivacion;
    }

    public void setFkCodUsuarioDerivacion(Usuarios fkCodUsuarioDerivacion) {
        this.fkCodUsuarioDerivacion = fkCodUsuarioDerivacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codReclamo != null ? codReclamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reclamos)) {
            return false;
        }
        Reclamos other = (Reclamos) object;
        if ((this.codReclamo == null && other.codReclamo != null) || (this.codReclamo != null && !this.codReclamo.equals(other.codReclamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Reclamos[ codReclamo=" + codReclamo + " ]";
    }
    
}
