/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.entitys;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "permisos_elementos_web")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PermisosElementosWeb.findAll", query = "SELECT p FROM PermisosElementosWeb p"),
    @NamedQuery(name = "PermisosElementosWeb.findByCodPermisoElementoWeb", query = "SELECT p FROM PermisosElementosWeb p WHERE p.codPermisoElementoWeb = :codPermisoElementoWeb"),
    @NamedQuery(name = "PermisosElementosWeb.findByDetalleDelPermiso", query = "SELECT p FROM PermisosElementosWeb p WHERE p.detalleDelPermiso = :detalleDelPermiso"),
    @NamedQuery(name = "PermisosElementosWeb.findByValorDesactivado", query = "SELECT p FROM PermisosElementosWeb p WHERE p.valorDesactivado = :valorDesactivado"),
    @NamedQuery(name = "PermisosElementosWeb.findByValorVisible", query = "SELECT p FROM PermisosElementosWeb p WHERE p.valorVisible = :valorVisible")})
public class PermisosElementosWeb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_permiso_elemento_web")
    private Integer codPermisoElementoWeb;
    @Size(max = 255)
    @Column(name = "detalle_del_permiso")
    private String detalleDelPermiso;
    @Size(max = 255)
    @Column(name = "valor_desactivado")
    private String valorDesactivado;
    @Size(max = 255)
    @Column(name = "valor_visible")
    private String valorVisible;
    @JoinColumn(name = "fk_cod_elemento_web", referencedColumnName = "cod_elemento_web")
    @ManyToOne
    private ElementosWeb fkCodElementoWeb;
    @JoinColumn(name = "fk_cod_rol", referencedColumnName = "cod_rol")
    @ManyToOne
    private Roles fkCodRol;

    public PermisosElementosWeb() {
    }

    public PermisosElementosWeb(Integer codPermisoElementoWeb) {
        this.codPermisoElementoWeb = codPermisoElementoWeb;
    }

    public Integer getCodPermisoElementoWeb() {
        return codPermisoElementoWeb;
    }

    public void setCodPermisoElementoWeb(Integer codPermisoElementoWeb) {
        this.codPermisoElementoWeb = codPermisoElementoWeb;
    }

    public String getDetalleDelPermiso() {
        return detalleDelPermiso;
    }

    public void setDetalleDelPermiso(String detalleDelPermiso) {
        this.detalleDelPermiso = detalleDelPermiso;
    }

    public String getValorDesactivado() {
        return valorDesactivado;
    }

    public void setValorDesactivado(String valorDesactivado) {
        this.valorDesactivado = valorDesactivado;
    }

    public String getValorVisible() {
        return valorVisible;
    }

    public void setValorVisible(String valorVisible) {
        this.valorVisible = valorVisible;
    }

    public ElementosWeb getFkCodElementoWeb() {
        return fkCodElementoWeb;
    }

    public void setFkCodElementoWeb(ElementosWeb fkCodElementoWeb) {
        this.fkCodElementoWeb = fkCodElementoWeb;
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
        hash += (codPermisoElementoWeb != null ? codPermisoElementoWeb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisosElementosWeb)) {
            return false;
        }
        PermisosElementosWeb other = (PermisosElementosWeb) object;
        if ((this.codPermisoElementoWeb == null && other.codPermisoElementoWeb != null) || (this.codPermisoElementoWeb != null && !this.codPermisoElementoWeb.equals(other.codPermisoElementoWeb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.PermisosElementosWeb[ codPermisoElementoWeb=" + codPermisoElementoWeb + " ]";
    }
    
}
