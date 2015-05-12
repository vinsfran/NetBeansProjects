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
    @NamedQuery(name = "Configuraciones.findByPar01", query = "SELECT c FROM Configuraciones c WHERE c.par01 = :par01"),
    @NamedQuery(name = "Configuraciones.findByPar02", query = "SELECT c FROM Configuraciones c WHERE c.par02 = :par02"),
    @NamedQuery(name = "Configuraciones.findByPar03", query = "SELECT c FROM Configuraciones c WHERE c.par03 = :par03"),
    @NamedQuery(name = "Configuraciones.findByPar04", query = "SELECT c FROM Configuraciones c WHERE c.par04 = :par04"),
    @NamedQuery(name = "Configuraciones.findByPar05", query = "SELECT c FROM Configuraciones c WHERE c.par05 = :par05"),
    @NamedQuery(name = "Configuraciones.findByPar06", query = "SELECT c FROM Configuraciones c WHERE c.par06 = :par06"),
    @NamedQuery(name = "Configuraciones.findByPar07", query = "SELECT c FROM Configuraciones c WHERE c.par07 = :par07"),
    @NamedQuery(name = "Configuraciones.findByPar08", query = "SELECT c FROM Configuraciones c WHERE c.par08 = :par08"),
    @NamedQuery(name = "Configuraciones.findByPar09", query = "SELECT c FROM Configuraciones c WHERE c.par09 = :par09"),
    @NamedQuery(name = "Configuraciones.findByPar10", query = "SELECT c FROM Configuraciones c WHERE c.par10 = :par10"),
    @NamedQuery(name = "Configuraciones.findByDetalleDeConfiguracion", query = "SELECT c FROM Configuraciones c WHERE c.detalleDeConfiguracion = :detalleDeConfiguracion"),
    @NamedQuery(name = "Configuraciones.findByPar11", query = "SELECT c FROM Configuraciones c WHERE c.par11 = :par11"),
    @NamedQuery(name = "Configuraciones.findByPar12", query = "SELECT c FROM Configuraciones c WHERE c.par12 = :par12"),
    @NamedQuery(name = "Configuraciones.findByPar13", query = "SELECT c FROM Configuraciones c WHERE c.par13 = :par13"),
    @NamedQuery(name = "Configuraciones.findByPar14", query = "SELECT c FROM Configuraciones c WHERE c.par14 = :par14"),
    @NamedQuery(name = "Configuraciones.findByPar15", query = "SELECT c FROM Configuraciones c WHERE c.par15 = :par15"),
    @NamedQuery(name = "Configuraciones.findByPar16", query = "SELECT c FROM Configuraciones c WHERE c.par16 = :par16"),
    @NamedQuery(name = "Configuraciones.findByPar17", query = "SELECT c FROM Configuraciones c WHERE c.par17 = :par17"),
    @NamedQuery(name = "Configuraciones.findByPar18", query = "SELECT c FROM Configuraciones c WHERE c.par18 = :par18"),
    @NamedQuery(name = "Configuraciones.findByPar19", query = "SELECT c FROM Configuraciones c WHERE c.par19 = :par19"),
    @NamedQuery(name = "Configuraciones.findByPar20", query = "SELECT c FROM Configuraciones c WHERE c.par20 = :par20"),
    @NamedQuery(name = "Configuraciones.findByPar21", query = "SELECT c FROM Configuraciones c WHERE c.par21 = :par21"),
    @NamedQuery(name = "Configuraciones.findByPar22", query = "SELECT c FROM Configuraciones c WHERE c.par22 = :par22"),
    @NamedQuery(name = "Configuraciones.findByPar23", query = "SELECT c FROM Configuraciones c WHERE c.par23 = :par23"),
    @NamedQuery(name = "Configuraciones.findByPar24", query = "SELECT c FROM Configuraciones c WHERE c.par24 = :par24"),
    @NamedQuery(name = "Configuraciones.findByPar25", query = "SELECT c FROM Configuraciones c WHERE c.par25 = :par25"),
    @NamedQuery(name = "Configuraciones.findByPar26", query = "SELECT c FROM Configuraciones c WHERE c.par26 = :par26"),
    @NamedQuery(name = "Configuraciones.findByPar27", query = "SELECT c FROM Configuraciones c WHERE c.par27 = :par27"),
    @NamedQuery(name = "Configuraciones.findByPar28", query = "SELECT c FROM Configuraciones c WHERE c.par28 = :par28"),
    @NamedQuery(name = "Configuraciones.findByPar29", query = "SELECT c FROM Configuraciones c WHERE c.par29 = :par29"),
    @NamedQuery(name = "Configuraciones.findByPar30", query = "SELECT c FROM Configuraciones c WHERE c.par30 = :par30"),
    @NamedQuery(name = "Configuraciones.findByPar31", query = "SELECT c FROM Configuraciones c WHERE c.par31 = :par31"),
    @NamedQuery(name = "Configuraciones.findByPar32", query = "SELECT c FROM Configuraciones c WHERE c.par32 = :par32"),
    @NamedQuery(name = "Configuraciones.findByPar33", query = "SELECT c FROM Configuraciones c WHERE c.par33 = :par33"),
    @NamedQuery(name = "Configuraciones.findByPar34", query = "SELECT c FROM Configuraciones c WHERE c.par34 = :par34"),
    @NamedQuery(name = "Configuraciones.findByPar35", query = "SELECT c FROM Configuraciones c WHERE c.par35 = :par35"),
    @NamedQuery(name = "Configuraciones.findByPar36", query = "SELECT c FROM Configuraciones c WHERE c.par36 = :par36"),
    @NamedQuery(name = "Configuraciones.findByPar37", query = "SELECT c FROM Configuraciones c WHERE c.par37 = :par37"),
    @NamedQuery(name = "Configuraciones.findByPar38", query = "SELECT c FROM Configuraciones c WHERE c.par38 = :par38"),
    @NamedQuery(name = "Configuraciones.findByPar39", query = "SELECT c FROM Configuraciones c WHERE c.par39 = :par39"),
    @NamedQuery(name = "Configuraciones.findByPar40", query = "SELECT c FROM Configuraciones c WHERE c.par40 = :par40")})
public class Configuraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_configuracion")
    private Integer codConfiguracion;
    @Size(max = 255)
    @Column(name = "par01")
    private String par01;
    @Size(max = 255)
    @Column(name = "par02")
    private String par02;
    @Size(max = 255)
    @Column(name = "par03")
    private String par03;
    @Size(max = 255)
    @Column(name = "par04")
    private String par04;
    @Size(max = 255)
    @Column(name = "par05")
    private String par05;
    @Size(max = 255)
    @Column(name = "par06")
    private String par06;
    @Size(max = 255)
    @Column(name = "par07")
    private String par07;
    @Size(max = 255)
    @Column(name = "par08")
    private String par08;
    @Size(max = 255)
    @Column(name = "par09")
    private String par09;
    @Size(max = 255)
    @Column(name = "par10")
    private String par10;
    @Size(max = 2000)
    @Column(name = "detalle_de_configuracion")
    private String detalleDeConfiguracion;
    @Size(max = 2147483647)
    @Column(name = "par11")
    private String par11;
    @Size(max = 2147483647)
    @Column(name = "par12")
    private String par12;
    @Size(max = 2147483647)
    @Column(name = "par13")
    private String par13;
    @Size(max = 2147483647)
    @Column(name = "par14")
    private String par14;
    @Size(max = 2147483647)
    @Column(name = "par15")
    private String par15;
    @Size(max = 2147483647)
    @Column(name = "par16")
    private String par16;
    @Size(max = 2147483647)
    @Column(name = "par17")
    private String par17;
    @Size(max = 2147483647)
    @Column(name = "par18")
    private String par18;
    @Size(max = 2147483647)
    @Column(name = "par19")
    private String par19;
    @Size(max = 2147483647)
    @Column(name = "par20")
    private String par20;
    @Size(max = 2147483647)
    @Column(name = "par21")
    private String par21;
    @Size(max = 2147483647)
    @Column(name = "par22")
    private String par22;
    @Size(max = 2147483647)
    @Column(name = "par23")
    private String par23;
    @Size(max = 2147483647)
    @Column(name = "par24")
    private String par24;
    @Size(max = 2147483647)
    @Column(name = "par25")
    private String par25;
    @Size(max = 2147483647)
    @Column(name = "par26")
    private String par26;
    @Size(max = 2147483647)
    @Column(name = "par27")
    private String par27;
    @Size(max = 2147483647)
    @Column(name = "par28")
    private String par28;
    @Size(max = 2147483647)
    @Column(name = "par29")
    private String par29;
    @Size(max = 2147483647)
    @Column(name = "par30")
    private String par30;
    @Size(max = 2147483647)
    @Column(name = "par31")
    private String par31;
    @Size(max = 2147483647)
    @Column(name = "par32")
    private String par32;
    @Size(max = 2147483647)
    @Column(name = "par33")
    private String par33;
    @Size(max = 2147483647)
    @Column(name = "par34")
    private String par34;
    @Size(max = 2147483647)
    @Column(name = "par35")
    private String par35;
    @Size(max = 2147483647)
    @Column(name = "par36")
    private String par36;
    @Size(max = 2147483647)
    @Column(name = "par37")
    private String par37;
    @Size(max = 2147483647)
    @Column(name = "par38")
    private String par38;
    @Size(max = 2147483647)
    @Column(name = "par39")
    private String par39;
    @Size(max = 2147483647)
    @Column(name = "par40")
    private String par40;

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

    public String getPar01() {
        return par01;
    }

    public void setPar01(String par01) {
        this.par01 = par01;
    }

    public String getPar02() {
        return par02;
    }

    public void setPar02(String par02) {
        this.par02 = par02;
    }

    public String getPar03() {
        return par03;
    }

    public void setPar03(String par03) {
        this.par03 = par03;
    }

    public String getPar04() {
        return par04;
    }

    public void setPar04(String par04) {
        this.par04 = par04;
    }

    public String getPar05() {
        return par05;
    }

    public void setPar05(String par05) {
        this.par05 = par05;
    }

    public String getPar06() {
        return par06;
    }

    public void setPar06(String par06) {
        this.par06 = par06;
    }

    public String getPar07() {
        return par07;
    }

    public void setPar07(String par07) {
        this.par07 = par07;
    }

    public String getPar08() {
        return par08;
    }

    public void setPar08(String par08) {
        this.par08 = par08;
    }

    public String getPar09() {
        return par09;
    }

    public void setPar09(String par09) {
        this.par09 = par09;
    }

    public String getPar10() {
        return par10;
    }

    public void setPar10(String par10) {
        this.par10 = par10;
    }

    public String getDetalleDeConfiguracion() {
        return detalleDeConfiguracion;
    }

    public void setDetalleDeConfiguracion(String detalleDeConfiguracion) {
        this.detalleDeConfiguracion = detalleDeConfiguracion;
    }

    public String getPar11() {
        return par11;
    }

    public void setPar11(String par11) {
        this.par11 = par11;
    }

    public String getPar12() {
        return par12;
    }

    public void setPar12(String par12) {
        this.par12 = par12;
    }

    public String getPar13() {
        return par13;
    }

    public void setPar13(String par13) {
        this.par13 = par13;
    }

    public String getPar14() {
        return par14;
    }

    public void setPar14(String par14) {
        this.par14 = par14;
    }

    public String getPar15() {
        return par15;
    }

    public void setPar15(String par15) {
        this.par15 = par15;
    }

    public String getPar16() {
        return par16;
    }

    public void setPar16(String par16) {
        this.par16 = par16;
    }

    public String getPar17() {
        return par17;
    }

    public void setPar17(String par17) {
        this.par17 = par17;
    }

    public String getPar18() {
        return par18;
    }

    public void setPar18(String par18) {
        this.par18 = par18;
    }

    public String getPar19() {
        return par19;
    }

    public void setPar19(String par19) {
        this.par19 = par19;
    }

    public String getPar20() {
        return par20;
    }

    public void setPar20(String par20) {
        this.par20 = par20;
    }

    public String getPar21() {
        return par21;
    }

    public void setPar21(String par21) {
        this.par21 = par21;
    }

    public String getPar22() {
        return par22;
    }

    public void setPar22(String par22) {
        this.par22 = par22;
    }

    public String getPar23() {
        return par23;
    }

    public void setPar23(String par23) {
        this.par23 = par23;
    }

    public String getPar24() {
        return par24;
    }

    public void setPar24(String par24) {
        this.par24 = par24;
    }

    public String getPar25() {
        return par25;
    }

    public void setPar25(String par25) {
        this.par25 = par25;
    }

    public String getPar26() {
        return par26;
    }

    public void setPar26(String par26) {
        this.par26 = par26;
    }

    public String getPar27() {
        return par27;
    }

    public void setPar27(String par27) {
        this.par27 = par27;
    }

    public String getPar28() {
        return par28;
    }

    public void setPar28(String par28) {
        this.par28 = par28;
    }

    public String getPar29() {
        return par29;
    }

    public void setPar29(String par29) {
        this.par29 = par29;
    }

    public String getPar30() {
        return par30;
    }

    public void setPar30(String par30) {
        this.par30 = par30;
    }

    public String getPar31() {
        return par31;
    }

    public void setPar31(String par31) {
        this.par31 = par31;
    }

    public String getPar32() {
        return par32;
    }

    public void setPar32(String par32) {
        this.par32 = par32;
    }

    public String getPar33() {
        return par33;
    }

    public void setPar33(String par33) {
        this.par33 = par33;
    }

    public String getPar34() {
        return par34;
    }

    public void setPar34(String par34) {
        this.par34 = par34;
    }

    public String getPar35() {
        return par35;
    }

    public void setPar35(String par35) {
        this.par35 = par35;
    }

    public String getPar36() {
        return par36;
    }

    public void setPar36(String par36) {
        this.par36 = par36;
    }

    public String getPar37() {
        return par37;
    }

    public void setPar37(String par37) {
        this.par37 = par37;
    }

    public String getPar38() {
        return par38;
    }

    public void setPar38(String par38) {
        this.par38 = par38;
    }

    public String getPar39() {
        return par39;
    }

    public void setPar39(String par39) {
        this.par39 = par39;
    }

    public String getPar40() {
        return par40;
    }

    public void setPar40(String par40) {
        this.par40 = par40;
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
