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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "expediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expediente.findAll", query = "SELECT e FROM Expediente e"),
    @NamedQuery(name = "Expediente.findByNroExpediente", query = "SELECT e FROM Expediente e WHERE e.nroExpediente = :nroExpediente"),
    @NamedQuery(name = "Expediente.findByNroFactura", query = "SELECT e FROM Expediente e WHERE e.nroFactura = :nroFactura")})
public class Expediente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_expediente")
    private Integer nroExpediente;
    @Column(name = "nro_factura")
    private Integer nroFactura;

    public Expediente() {
    }

    public Expediente(Integer nroExpediente) {
        this.nroExpediente = nroExpediente;
    }

    public Integer getNroExpediente() {
        return nroExpediente;
    }

    public void setNroExpediente(Integer nroExpediente) {
        this.nroExpediente = nroExpediente;
    }

    public Integer getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(Integer nroFactura) {
        this.nroFactura = nroFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroExpediente != null ? nroExpediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expediente)) {
            return false;
        }
        Expediente other = (Expediente) object;
        if ((this.nroExpediente == null && other.nroExpediente != null) || (this.nroExpediente != null && !this.nroExpediente.equals(other.nroExpediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Expediente[ nroExpediente=" + nroExpediente + " ]";
    }
    
}
