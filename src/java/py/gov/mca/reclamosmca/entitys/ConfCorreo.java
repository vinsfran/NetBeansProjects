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
@Table(name = "conf_correo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfCorreo.findAll", query = "SELECT c FROM ConfCorreo c"),
    @NamedQuery(name = "ConfCorreo.findByNombreProveedor", query = "SELECT c FROM ConfCorreo c WHERE c.nombreProveedor = :nombreProveedor"),
    @NamedQuery(name = "ConfCorreo.findByUsuario", query = "SELECT c FROM ConfCorreo c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "ConfCorreo.findByPassword", query = "SELECT c FROM ConfCorreo c WHERE c.password = :password"),
    @NamedQuery(name = "ConfCorreo.findByMailDebug", query = "SELECT c FROM ConfCorreo c WHERE c.mailDebug = :mailDebug"),
    @NamedQuery(name = "ConfCorreo.findByMailSmtpAuth", query = "SELECT c FROM ConfCorreo c WHERE c.mailSmtpAuth = :mailSmtpAuth"),
    @NamedQuery(name = "ConfCorreo.findByMailSmtpHost", query = "SELECT c FROM ConfCorreo c WHERE c.mailSmtpHost = :mailSmtpHost"),
    @NamedQuery(name = "ConfCorreo.findByMailSmtpPort", query = "SELECT c FROM ConfCorreo c WHERE c.mailSmtpPort = :mailSmtpPort"),
    @NamedQuery(name = "ConfCorreo.findByMailSmtpStarttlsEnable", query = "SELECT c FROM ConfCorreo c WHERE c.mailSmtpStarttlsEnable = :mailSmtpStarttlsEnable"),
    @NamedQuery(name = "ConfCorreo.findByInternetAddress", query = "SELECT c FROM ConfCorreo c WHERE c.internetAddress = :internetAddress"),
    @NamedQuery(name = "ConfCorreo.findByCodConfCorreo", query = "SELECT c FROM ConfCorreo c WHERE c.codConfCorreo = :codConfCorreo"),
    @NamedQuery(name = "ConfCorreo.findByMailSmtpSslTrust", query = "SELECT c FROM ConfCorreo c WHERE c.mailSmtpSslTrust = :mailSmtpSslTrust")})
public class ConfCorreo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_proveedor")
    private String nombreProveedor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mail_debug")
    private String mailDebug;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mail_smtp_auth")
    private String mailSmtpAuth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mail_smtp_host")
    private String mailSmtpHost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mail_smtp_port")
    private String mailSmtpPort;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mail_smtp_starttls_enable")
    private String mailSmtpStarttlsEnable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "internet_address")
    private String internetAddress;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_conf_correo")
    private Integer codConfCorreo;
    @Size(max = 2147483647)
    @Column(name = "mail_smtp_ssl_trust")
    private String mailSmtpSslTrust;

    public ConfCorreo() {
    }

    public ConfCorreo(Integer codConfCorreo) {
        this.codConfCorreo = codConfCorreo;
    }

    public ConfCorreo(Integer codConfCorreo, String nombreProveedor, String usuario, String password, String mailDebug, String mailSmtpAuth, String mailSmtpHost, String mailSmtpPort, String mailSmtpStarttlsEnable, String internetAddress) {
        this.codConfCorreo = codConfCorreo;
        this.nombreProveedor = nombreProveedor;
        this.usuario = usuario;
        this.password = password;
        this.mailDebug = mailDebug;
        this.mailSmtpAuth = mailSmtpAuth;
        this.mailSmtpHost = mailSmtpHost;
        this.mailSmtpPort = mailSmtpPort;
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
        this.internetAddress = internetAddress;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public void setMailSmtpStarttlsEnable(String mailSmtpStarttlsEnable) {
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
    }

    public String getInternetAddress() {
        return internetAddress;
    }

    public void setInternetAddress(String internetAddress) {
        this.internetAddress = internetAddress;
    }

    public Integer getCodConfCorreo() {
        return codConfCorreo;
    }

    public void setCodConfCorreo(Integer codConfCorreo) {
        this.codConfCorreo = codConfCorreo;
    }

    public String getMailSmtpSslTrust() {
        return mailSmtpSslTrust;
    }

    public void setMailSmtpSslTrust(String mailSmtpSslTrust) {
        this.mailSmtpSslTrust = mailSmtpSslTrust;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codConfCorreo != null ? codConfCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfCorreo)) {
            return false;
        }
        ConfCorreo other = (ConfCorreo) object;
        if ((this.codConfCorreo == null && other.codConfCorreo != null) || (this.codConfCorreo != null && !this.codConfCorreo.equals(other.codConfCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.ConfCorreo[ codConfCorreo=" + codConfCorreo + " ]";
    }
    
}
