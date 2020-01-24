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
import javax.persistence.Lob;
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
@Table(name = "denuncia_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DenunciaDocumento.findAll", query = "SELECT d FROM DenunciaDocumento d")
    , @NamedQuery(name = "DenunciaDocumento.findById", query = "SELECT d FROM DenunciaDocumento d WHERE d.id = :id")
    , @NamedQuery(name = "DenunciaDocumento.findByNombre", query = "SELECT d FROM DenunciaDocumento d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DenunciaDocumento.findByContentType", query = "SELECT d FROM DenunciaDocumento d WHERE d.contentType = :contentType")
    , @NamedQuery(name = "DenunciaDocumento.findByTitulo", query = "SELECT d FROM DenunciaDocumento d WHERE d.titulo = :titulo")
    , @NamedQuery(name = "DenunciaDocumento.findByFecha", query = "SELECT d FROM DenunciaDocumento d WHERE d.fecha = :fecha")})
public class DenunciaDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "content_type")
    private String contentType;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_denuncia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Denuncia idDenuncia;
    @JoinColumn(name = "id_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;

    public DenunciaDocumento() {
    }

    public DenunciaDocumento(Integer id) {
        this.id = id;
    }

    public DenunciaDocumento(Integer id, String nombre, String contentType, byte[] archivo, String titulo, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.contentType = contentType;
        this.archivo = archivo;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Denuncia getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(Denuncia idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DenunciaDocumento)) {
            return false;
        }
        DenunciaDocumento other = (DenunciaDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.DenunciaDocumento[ id=" + id + " ]";
    }
    
}
