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
import lombok.Data;

/**
 *
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reclamos.findAll", query = "SELECT r FROM Reclamos r")
    , @NamedQuery(name = "Reclamos.findByCodReclamo", query = "SELECT r FROM Reclamos r WHERE r.codReclamo = :codReclamo")
    , @NamedQuery(name = "Reclamos.findByDescripcionReclamoContribuyente", query = "SELECT r FROM Reclamos r WHERE r.descripcionReclamoContribuyente = :descripcionReclamoContribuyente")
    , @NamedQuery(name = "Reclamos.findByDireccionReclamo", query = "SELECT r FROM Reclamos r WHERE r.direccionReclamo = :direccionReclamo")
    , @NamedQuery(name = "Reclamos.findByOrigenReclamo", query = "SELECT r FROM Reclamos r WHERE r.origenReclamo = :origenReclamo")
    , @NamedQuery(name = "Reclamos.findByFechaReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaReclamo = :fechaReclamo")
    , @NamedQuery(name = "Reclamos.findByDescripcionAtencionReclamo", query = "SELECT r FROM Reclamos r WHERE r.descripcionAtencionReclamo = :descripcionAtencionReclamo")
    , @NamedQuery(name = "Reclamos.findByFechaAtencionReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaAtencionReclamo = :fechaAtencionReclamo")
    , @NamedQuery(name = "Reclamos.findByDescripcionCulminacionReclamo", query = "SELECT r FROM Reclamos r WHERE r.descripcionCulminacionReclamo = :descripcionCulminacionReclamo")
    , @NamedQuery(name = "Reclamos.findByFechaCulminacionReclamo", query = "SELECT r FROM Reclamos r WHERE r.fechaCulminacionReclamo = :fechaCulminacionReclamo")
    , @NamedQuery(name = "Reclamos.findByCantidadDiasProceso", query = "SELECT r FROM Reclamos r WHERE r.cantidadDiasProceso = :cantidadDiasProceso")
    , @NamedQuery(name = "Reclamos.findByCtaCteContribuyente", query = "SELECT r FROM Reclamos r WHERE r.ctaCteContribuyente = :ctaCteContribuyente")
    , @NamedQuery(name = "Reclamos.findByLatitud", query = "SELECT r FROM Reclamos r WHERE r.latitud = :latitud")
    , @NamedQuery(name = "Reclamos.findByLongitud", query = "SELECT r FROM Reclamos r WHERE r.longitud = :longitud")})
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

    @JoinColumn(name = "fk_cod_direccion", referencedColumnName = "cod_direccion")
    @ManyToOne
    private Paises05Direcciones fkCodDireccion;

    @JoinColumn(name = "fk_cod_tipo_finalizacion_reclamo", referencedColumnName = "cod_tipo_finalizacion_reclamo")
    @ManyToOne
    private TiposFinalizacionReclamos fkCodTipoFinalizacionReclamo;

    @JoinColumn(name = "fk_cod_tipo_reclamo", referencedColumnName = "cod_tipo_reclamo")
    @ManyToOne(optional = false)
    private TiposReclamos fkCodTipoReclamo;

    @JoinColumn(name = "fk_cod_usuario_atencion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioAtencion;

    @JoinColumn(name = "fk_cod_usuario_culminacion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioCulminacion;

    @JoinColumn(name = "fk_cod_usuario_derivacion", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuarioDerivacion;

    @JoinColumn(name = "fk_cod_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne
    private Usuarios fkCodUsuario;

    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;

//    @Column(name = "nombre_ciudadano")
//    private String nombreCiudadano;
//
//    @Column(name = "nombre_ciudadano")
//    private String apellidoCiudadano;
//
//    @Column(name = "nombre_ciudadano")
//    private String telefonoCiudadano;

}
