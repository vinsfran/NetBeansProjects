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
import javax.persistence.Id;
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
@Table(name = "reande_audit_99")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReandeAudit99.findAll", query = "SELECT r FROM ReandeAudit99 r")
    , @NamedQuery(name = "ReandeAudit99.findByCrutaAnde", query = "SELECT r FROM ReandeAudit99 r WHERE r.crutaAnde = :crutaAnde")
    , @NamedQuery(name = "ReandeAudit99.findByItinerario", query = "SELECT r FROM ReandeAudit99 r WHERE r.itinerario = :itinerario")
    , @NamedQuery(name = "ReandeAudit99.findByAsecue", query = "SELECT r FROM ReandeAudit99 r WHERE r.asecue = :asecue")
    , @NamedQuery(name = "ReandeAudit99.findByCbarrio", query = "SELECT r FROM ReandeAudit99 r WHERE r.cbarrio = :cbarrio")
    , @NamedQuery(name = "ReandeAudit99.findByAfactu", query = "SELECT r FROM ReandeAudit99 r WHERE r.afactu = :afactu")
    , @NamedQuery(name = "ReandeAudit99.findByXapellRazon", query = "SELECT r FROM ReandeAudit99 r WHERE r.xapellRazon = :xapellRazon")
    , @NamedQuery(name = "ReandeAudit99.findByXnombre", query = "SELECT r FROM ReandeAudit99 r WHERE r.xnombre = :xnombre")
    , @NamedQuery(name = "ReandeAudit99.findByCrmc", query = "SELECT r FROM ReandeAudit99 r WHERE r.crmc = :crmc")
    , @NamedQuery(name = "ReandeAudit99.findByCtipooi", query = "SELECT r FROM ReandeAudit99 r WHERE r.ctipooi = :ctipooi")
    , @NamedQuery(name = "ReandeAudit99.findByCobjimp", query = "SELECT r FROM ReandeAudit99 r WHERE r.cobjimp = :cobjimp")
    , @NamedQuery(name = "ReandeAudit99.findByIpagar", query = "SELECT r FROM ReandeAudit99 r WHERE r.ipagar = :ipagar")
    , @NamedQuery(name = "ReandeAudit99.findByCtrib", query = "SELECT r FROM ReandeAudit99 r WHERE r.ctrib = :ctrib")
    , @NamedQuery(name = "ReandeAudit99.findByEnteCob", query = "SELECT r FROM ReandeAudit99 r WHERE r.enteCob = :enteCob")
    , @NamedQuery(name = "ReandeAudit99.findByCtermi", query = "SELECT r FROM ReandeAudit99 r WHERE r.ctermi = :ctermi")
    , @NamedQuery(name = "ReandeAudit99.findByCusua", query = "SELECT r FROM ReandeAudit99 r WHERE r.cusua = :cusua")
    , @NamedQuery(name = "ReandeAudit99.findByFultAct", query = "SELECT r FROM ReandeAudit99 r WHERE r.fultAct = :fultAct")
    , @NamedQuery(name = "ReandeAudit99.findByMmulta", query = "SELECT r FROM ReandeAudit99 r WHERE r.mmulta = :mmulta")
    , @NamedQuery(name = "ReandeAudit99.findByXruc", query = "SELECT r FROM ReandeAudit99 r WHERE r.xruc = :xruc")
    , @NamedQuery(name = "ReandeAudit99.findByNzona", query = "SELECT r FROM ReandeAudit99 r WHERE r.nzona = :nzona")
    , @NamedQuery(name = "ReandeAudit99.findByNmanzana", query = "SELECT r FROM ReandeAudit99 r WHERE r.nmanzana = :nmanzana")
    , @NamedQuery(name = "ReandeAudit99.findByNlote", query = "SELECT r FROM ReandeAudit99 r WHERE r.nlote = :nlote")
    , @NamedQuery(name = "ReandeAudit99.findByNpisoCatas", query = "SELECT r FROM ReandeAudit99 r WHERE r.npisoCatas = :npisoCatas")
    , @NamedQuery(name = "ReandeAudit99.findByNdpto", query = "SELECT r FROM ReandeAudit99 r WHERE r.ndpto = :ndpto")
    , @NamedQuery(name = "ReandeAudit99.findByCtipoConst", query = "SELECT r FROM ReandeAudit99 r WHERE r.ctipoConst = :ctipoConst")
    , @NamedQuery(name = "ReandeAudit99.findByNsuperEdit", query = "SELECT r FROM ReandeAudit99 r WHERE r.nsuperEdit = :nsuperEdit")
    , @NamedQuery(name = "ReandeAudit99.findByFonstRefor", query = "SELECT r FROM ReandeAudit99 r WHERE r.fonstRefor = :fonstRefor")})
public class ReandeAudit99 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "cruta_ande")
    private Short crutaAnde;
    @Column(name = "itinerario")
    private Short itinerario;
    @Column(name = "asecue")
    private Integer asecue;
    @Column(name = "cbarrio")
    private Short cbarrio;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "afactu")
    private Integer afactu;
    @Size(max = 50)
    @Column(name = "xapell_razon")
    private String xapellRazon;
    @Size(max = 30)
    @Column(name = "xnombre")
    private String xnombre;
    @Column(name = "crmc")
    private Integer crmc;
    @Size(max = 3)
    @Column(name = "ctipooi")
    private String ctipooi;
    @Size(max = 14)
    @Column(name = "cobjimp")
    private String cobjimp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ipagar")
    private Double ipagar;
    @Column(name = "ctrib")
    private Short ctrib;
    @Column(name = "ente_cob")
    private Integer enteCob;
    @Size(max = 6)
    @Column(name = "ctermi")
    private String ctermi;
    @Size(max = 15)
    @Column(name = "cusua")
    private String cusua;
    @Column(name = "fult_act")
    @Temporal(TemporalType.DATE)
    private Date fultAct;
    @Column(name = "mmulta")
    private Character mmulta;
    @Size(max = 20)
    @Column(name = "xruc")
    private String xruc;
    @Column(name = "nzona")
    private Integer nzona;
    @Column(name = "nmanzana")
    private Short nmanzana;
    @Column(name = "nlote")
    private Integer nlote;
    @Size(max = 2)
    @Column(name = "npiso_catas")
    private String npisoCatas;
    @Column(name = "ndpto")
    private Short ndpto;
    @Size(max = 2)
    @Column(name = "ctipo_const")
    private String ctipoConst;
    @Column(name = "nsuper_edit")
    private Double nsuperEdit;
    @Column(name = "fonst_refor")
    @Temporal(TemporalType.DATE)
    private Date fonstRefor;

    public ReandeAudit99() {
    }

    public ReandeAudit99(Integer afactu) {
        this.afactu = afactu;
    }

    public Short getCrutaAnde() {
        return crutaAnde;
    }

    public void setCrutaAnde(Short crutaAnde) {
        this.crutaAnde = crutaAnde;
    }

    public Short getItinerario() {
        return itinerario;
    }

    public void setItinerario(Short itinerario) {
        this.itinerario = itinerario;
    }

    public Integer getAsecue() {
        return asecue;
    }

    public void setAsecue(Integer asecue) {
        this.asecue = asecue;
    }

    public Short getCbarrio() {
        return cbarrio;
    }

    public void setCbarrio(Short cbarrio) {
        this.cbarrio = cbarrio;
    }

    public Integer getAfactu() {
        return afactu;
    }

    public void setAfactu(Integer afactu) {
        this.afactu = afactu;
    }

    public String getXapellRazon() {
        return xapellRazon;
    }

    public void setXapellRazon(String xapellRazon) {
        this.xapellRazon = xapellRazon;
    }

    public String getXnombre() {
        return xnombre;
    }

    public void setXnombre(String xnombre) {
        this.xnombre = xnombre;
    }

    public Integer getCrmc() {
        return crmc;
    }

    public void setCrmc(Integer crmc) {
        this.crmc = crmc;
    }

    public String getCtipooi() {
        return ctipooi;
    }

    public void setCtipooi(String ctipooi) {
        this.ctipooi = ctipooi;
    }

    public String getCobjimp() {
        return cobjimp;
    }

    public void setCobjimp(String cobjimp) {
        this.cobjimp = cobjimp;
    }

    public Double getIpagar() {
        return ipagar;
    }

    public void setIpagar(Double ipagar) {
        this.ipagar = ipagar;
    }

    public Short getCtrib() {
        return ctrib;
    }

    public void setCtrib(Short ctrib) {
        this.ctrib = ctrib;
    }

    public Integer getEnteCob() {
        return enteCob;
    }

    public void setEnteCob(Integer enteCob) {
        this.enteCob = enteCob;
    }

    public String getCtermi() {
        return ctermi;
    }

    public void setCtermi(String ctermi) {
        this.ctermi = ctermi;
    }

    public String getCusua() {
        return cusua;
    }

    public void setCusua(String cusua) {
        this.cusua = cusua;
    }

    public Date getFultAct() {
        return fultAct;
    }

    public void setFultAct(Date fultAct) {
        this.fultAct = fultAct;
    }

    public Character getMmulta() {
        return mmulta;
    }

    public void setMmulta(Character mmulta) {
        this.mmulta = mmulta;
    }

    public String getXruc() {
        return xruc;
    }

    public void setXruc(String xruc) {
        this.xruc = xruc;
    }

    public Integer getNzona() {
        return nzona;
    }

    public void setNzona(Integer nzona) {
        this.nzona = nzona;
    }

    public Short getNmanzana() {
        return nmanzana;
    }

    public void setNmanzana(Short nmanzana) {
        this.nmanzana = nmanzana;
    }

    public Integer getNlote() {
        return nlote;
    }

    public void setNlote(Integer nlote) {
        this.nlote = nlote;
    }

    public String getNpisoCatas() {
        return npisoCatas;
    }

    public void setNpisoCatas(String npisoCatas) {
        this.npisoCatas = npisoCatas;
    }

    public Short getNdpto() {
        return ndpto;
    }

    public void setNdpto(Short ndpto) {
        this.ndpto = ndpto;
    }

    public String getCtipoConst() {
        return ctipoConst;
    }

    public void setCtipoConst(String ctipoConst) {
        this.ctipoConst = ctipoConst;
    }

    public Double getNsuperEdit() {
        return nsuperEdit;
    }

    public void setNsuperEdit(Double nsuperEdit) {
        this.nsuperEdit = nsuperEdit;
    }

    public Date getFonstRefor() {
        return fonstRefor;
    }

    public void setFonstRefor(Date fonstRefor) {
        this.fonstRefor = fonstRefor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (afactu != null ? afactu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReandeAudit99)) {
            return false;
        }
        ReandeAudit99 other = (ReandeAudit99) object;
        if ((this.afactu == null && other.afactu != null) || (this.afactu != null && !this.afactu.equals(other.afactu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.mca.reclamosmca.entitys.ReandeAudit99[ afactu=" + afactu + " ]";
    }
    
}
