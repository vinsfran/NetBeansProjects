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
@Table(name = "elementos_web")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementosWeb.findAll", query = "SELECT e FROM ElementosWeb e"),
    @NamedQuery(name = "ElementosWeb.findByCodElementoWeb", query = "SELECT e FROM ElementosWeb e WHERE e.codElementoWeb = :codElementoWeb"),
    @NamedQuery(name = "ElementosWeb.findByNombreElementoWeb", query = "SELECT e FROM ElementosWeb e WHERE e.nombreElementoWeb = :nombreElementoWeb"),
    @NamedQuery(name = "ElementosWeb.findByDescripcionDelElementoWeb", query = "SELECT e FROM ElementosWeb e WHERE e.descripcionDelElementoWeb = :descripcionDelElementoWeb")})
public class ElementosWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_elemento_web")
    private Integer codElementoWeb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_elemento_web")
    private String nombreElementoWeb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion_del_elemento_web")
    private String descripcionDelElementoWeb;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCodElementoWeb")
    private List<PermisosElementosWeb> permisosElementosWebList;

    public ElementosWeb() {
    }

    public ElementosWeb(Integer codElementoWeb) {
        this.codElementoWeb = codElementoWeb;
    }

    public ElementosWeb(Integer codElementoWeb, String nombreElementoWeb, String descripcionDelElementoWeb) {
        this.codElementoWeb = codElementoWeb;
        this.nombreElementoWeb = nombreElementoWeb;
        this.descripcionDelElementoWeb = descripcionDelElementoWeb;
    }

    public Integer getCodElementoWeb() {
        return codElementoWeb;
    }

    public void setCodElementoWeb(Integer codElementoWeb) {
        this.codElementoWeb = codElementoWeb;
    }

    public String getNombreElementoWeb() {
        return nombreElementoWeb;
    }

    public void setNombreElementoWeb(String nombreElementoWeb) {
        this.nombreElementoWeb = nombreElementoWeb;
    }

    public String getDescripcionDelElementoWeb() {
        return descripcionDelElementoWeb;
    }

    public void setDescripcionDelElementoWeb(String descripcionDelElementoWeb) {
        this.descripcionDelElementoWeb = descripcionDelElementoWeb;
    }

    @XmlTransient
    public List<PermisosElementosWeb> getPermisosElementosWebList() {
        return permisosElementosWebList;
    }

    public void setPermisosElementosWebList(List<PermisosElementosWeb> permisosElementosWebList) {
        this.permisosElementosWebList = permisosElementosWebList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codElementoWeb != null ? codElementoWeb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementosWeb)) {
            return false;
        }
        ElementosWeb other = (ElementosWeb) object;
        if ((this.codElementoWeb == null && other.codElementoWeb != null) || (this.codElementoWeb != null && !this.codElementoWeb.equals(other.codElementoWeb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.ElementosWeb[ codElementoWeb=" + codElementoWeb + " ]";
    }
    
}
