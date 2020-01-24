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
@Table(name = "denuncia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Denuncia.findAll", query = "SELECT d FROM Denuncia d")
    , @NamedQuery(name = "Denuncia.findById", query = "SELECT d FROM Denuncia d WHERE d.id = :id")
    , @NamedQuery(name = "Denuncia.findByTitulo", query = "SELECT d FROM Denuncia d WHERE d.titulo = :titulo")
    , @NamedQuery(name = "Denuncia.findByFechaInicio", query = "SELECT d FROM Denuncia d WHERE d.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Denuncia.findByFechaFin", query = "SELECT d FROM Denuncia d WHERE d.fechaFin = :fechaFin")})
public class Denuncia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "id_denuncia_estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DenunciaEstado idDenunciaEstado;
    @JoinColumn(name = "id_denuncia_tipo", referencedColumnName = "id")
    @ManyToOne
    private DenunciaTipo idDenunciaTipo;
    @JoinColumn(name = "id_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDenuncia")
    private List<DenunciaDocumento> denunciaDocumentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDenuncia")
    private List<DenunciaDetalle> denunciaDetalleList;

    public Denuncia() {
    }

    public Denuncia(Integer id) {
        this.id = id;
    }

    public Denuncia(Integer id, String titulo, Date fechaInicio) {
        this.id = id;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public DenunciaEstado getIdDenunciaEstado() {
        return idDenunciaEstado;
    }

    public void setIdDenunciaEstado(DenunciaEstado idDenunciaEstado) {
        this.idDenunciaEstado = idDenunciaEstado;
    }

    public DenunciaTipo getIdDenunciaTipo() {
        return idDenunciaTipo;
    }

    public void setIdDenunciaTipo(DenunciaTipo idDenunciaTipo) {
        this.idDenunciaTipo = idDenunciaTipo;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<DenunciaDocumento> getDenunciaDocumentoList() {
        return denunciaDocumentoList;
    }

    public void setDenunciaDocumentoList(List<DenunciaDocumento> denunciaDocumentoList) {
        this.denunciaDocumentoList = denunciaDocumentoList;
    }

    @XmlTransient
    public List<DenunciaDetalle> getDenunciaDetalleList() {
        return denunciaDetalleList;
    }

    public void setDenunciaDetalleList(List<DenunciaDetalle> denunciaDetalleList) {
        this.denunciaDetalleList = denunciaDetalleList;
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
        if (!(object instanceof Denuncia)) {
            return false;
        }
        Denuncia other = (Denuncia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Denuncia[ id=" + id + " ]";
    }
    
}
