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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "configuraciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuraciones.findAll", query = "SELECT c FROM Configuraciones c"),
    @NamedQuery(name = "Configuraciones.findByCodConfiguracion", query = "SELECT c FROM Configuraciones c WHERE c.codConfiguracion = :codConfiguracion"),
    @NamedQuery(name = "Configuraciones.findByInternetAddress", query = "SELECT c FROM Configuraciones c WHERE c.internetAddress = :internetAddress"),
    @NamedQuery(name = "Configuraciones.findByMailDebug", query = "SELECT c FROM Configuraciones c WHERE c.mailDebug = :mailDebug"),
    @NamedQuery(name = "Configuraciones.findByMailSmtpAuth", query = "SELECT c FROM Configuraciones c WHERE c.mailSmtpAuth = :mailSmtpAuth"),
    @NamedQuery(name = "Configuraciones.findByMailSmtpHost", query = "SELECT c FROM Configuraciones c WHERE c.mailSmtpHost = :mailSmtpHost"),
    @NamedQuery(name = "Configuraciones.findByMailSmtpPort", query = "SELECT c FROM Configuraciones c WHERE c.mailSmtpPort = :mailSmtpPort"),
    @NamedQuery(name = "Configuraciones.findByMailSmtpSslTrust", query = "SELECT c FROM Configuraciones c WHERE c.mailSmtpSslTrust = :mailSmtpSslTrust"),
    @NamedQuery(name = "Configuraciones.findByMailSmtpStarttlsEnable", query = "SELECT c FROM Configuraciones c WHERE c.mailSmtpStarttlsEnable = :mailSmtpStarttlsEnable"),
    @NamedQuery(name = "Configuraciones.findByNombreProveedor", query = "SELECT c FROM Configuraciones c WHERE c.nombreProveedor = :nombreProveedor"),
    @NamedQuery(name = "Configuraciones.findByPassword", query = "SELECT c FROM Configuraciones c WHERE c.password = :password"),
    @NamedQuery(name = "Configuraciones.findByUsuario", query = "SELECT c FROM Configuraciones c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Configuraciones.findByDetalleDeConfiguracion", query = "SELECT c FROM Configuraciones c WHERE c.detalleDeConfiguracion = :detalleDeConfiguracion")})
public class Configuraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_configuracion")
    private Integer codConfiguracion;
    @Size(max = 255)
    @Column(name = "internet_address")
    private String internetAddress;
    @Size(max = 255)
    @Column(name = "mail_debug")
    private String mailDebug;
    @Size(max = 255)
    @Column(name = "mail_smtp_auth")
    private String mailSmtpAuth;
    @Size(max = 255)
    @Column(name = "mail_smtp_host")
    private String mailSmtpHost;
    @Size(max = 255)
    @Column(name = "mail_smtp_port")
    private String mailSmtpPort;
    @Size(max = 255)
    @Column(name = "mail_smtp_ssl_trust")
    private String mailSmtpSslTrust;
    @Size(max = 255)
    @Column(name = "mail_smtp_starttls_enable")
    private String mailSmtpStarttlsEnable;
    @Size(max = 255)
    @Column(name = "nombre_proveedor")
    private String nombreProveedor;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 2147483647)
    @Column(name = "detalle_de_configuracion")
    private String detalleDeConfiguracion;

    public Configuraciones() {
    }

    public Configuraciones(Integer codConfiguracion) {
        this.codConfiguracion = codConfiguracion;
    }

    public Integer getCodConfiguracion() {
        return codConfiguracion;
    }

    public void setCodConfiguracion(Integer codConfiguracion) {
        this.codConfiguracion = codConfiguracion;
    }

    public String getInternetAddress() {
        return internetAddress;
    }

    public void setInternetAddress(String internetAddress) {
        this.internetAddress = internetAddress;
    }

    public String getMailDebug() {
        return mailDebug;
    }

    public void setMailDebug(String mailDebug) {
        this.mailDebug = mailDebug;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(String mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

    public String getMailSmtpSslTrust() {
        return mailSmtpSslTrust;
    }

    public void setMailSmtpSslTrust(String mailSmtpSslTrust) {
        this.mailSmtpSslTrust = mailSmtpSslTrust;
    }

    public String getMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public void setMailSmtpStarttlsEnable(String mailSmtpStarttlsEnable) {
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDetalleDeConfiguracion() {
        return detalleDeConfiguracion;
    }

    public void setDetalleDeConfiguracion(String detalleDeConfiguracion) {
        this.detalleDeConfiguracion = detalleDeConfiguracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codConfiguracion != null ? codConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuraciones)) {
            return false;
        }
        Configuraciones other = (Configuraciones) object;
        if ((this.codConfiguracion == null && other.codConfiguracion != null) || (this.codConfiguracion != null && !this.codConfiguracion.equals(other.codConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.Configuraciones[ codConfiguracion=" + codConfiguracion + " ]";
    }
    
}
