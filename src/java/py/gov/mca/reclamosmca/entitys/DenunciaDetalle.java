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
@Table(name = "denuncia_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DenunciaDetalle.findAll", query = "SELECT d FROM DenunciaDetalle d")
    , @NamedQuery(name = "DenunciaDetalle.findById", query = "SELECT d FROM DenunciaDetalle d WHERE d.id = :id")
    , @NamedQuery(name = "DenunciaDetalle.findByFecha", query = "SELECT d FROM DenunciaDetalle d WHERE d.fecha = :fecha")
    , @NamedQuery(name = "DenunciaDetalle.findByDescripcion", query = "SELECT d FROM DenunciaDetalle d WHERE d.descripcion = :descripcion")})
public class DenunciaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_denuncia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Denuncia idDenuncia;
    @JoinColumn(name = "id_usuario", referencedColumnName = "cod_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;

    public DenunciaDetalle() {
    }

    public DenunciaDetalle(Integer id) {
        this.id = id;
    }

    public DenunciaDetalle(Integer id, Date fecha, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof DenunciaDetalle)) {
            return false;
        }
        DenunciaDetalle other = (DenunciaDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.DenunciaDetalle[ id=" + id + " ]";
    }
    
}
