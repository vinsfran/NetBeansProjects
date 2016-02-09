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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "imagenes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imagenes.findAll", query = "SELECT i FROM Imagenes i"),
    @NamedQuery(name = "Imagenes.findByCodImagen", query = "SELECT i FROM Imagenes i WHERE i.codImagen = :codImagen"),
    @NamedQuery(name = "Imagenes.findByNombreImagen", query = "SELECT i FROM Imagenes i WHERE i.nombreImagen = :nombreImagen"),
    @NamedQuery(name = "Imagenes.findByTipoImagen", query = "SELECT i FROM Imagenes i WHERE i.tipoImagen = :tipoImagen")})
public class Imagenes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_imagen")
    private Integer codImagen;
    @Size(max = 2147483647)
    @Column(name = "nombre_imagen")
    private String nombreImagen;
    @Lob
    @Column(name = "archivo_imagen")
    private byte[] archivoImagen;
    @Size(max = 45)
    @Column(name = "tipo_imagen")
    private String tipoImagen;
    @OneToMany(mappedBy = "fkImagen")
    private List<Reclamos> reclamosList;

    public Imagenes() {
    }

    public Imagenes(Integer codImagen) {
        this.codImagen = codImagen;
    }

    public Integer getCodImagen() {
        return codImagen;
    }

    public void setCodImagen(Integer codImagen) {
        this.codImagen = codImagen;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public byte[] getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(byte[] archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    @XmlTransient
    public List<Reclamos> getReclamosList() {
        return reclamosList;
    }

    public void setReclamosList(List<Reclamos> reclamosList) {
        this.reclamosList = reclamosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codImagen != null ? codImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagenes)) {
            return false;
        }
        Imagenes other = (Imagenes) object;
        if ((this.codImagen == null && other.codImagen != null) || (this.codImagen != null && !this.codImagen.equals(other.codImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Imagenes[ codImagen=" + codImagen + " ]";
    }
    
}
