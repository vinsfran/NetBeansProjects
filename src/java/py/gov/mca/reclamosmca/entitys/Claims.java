package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author vinsfran
 */
@Data
@Entity
@Table(name = "claims")
public class Claims implements Serializable {

    private static long serialVersionUID = 1L;

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

    @Size(max = 2147483647)
    @Column(name = "usuario_atencion")
    private String usuarioAtencion;

    @Size(max = 2147483647)
    @Column(name = "usuario_culminacion")
    private String usuarioCulminacion;

    @Size(max = 2147483647)
    @Column(name = "usuario_derivacion")
    private String usuarioDerivacion;

    @Size(max = 2147483647)
    @Column(name = "login")
    private String login;

    @Column(name = "enviar_mail")
    private Boolean enviarMail;

   

}
