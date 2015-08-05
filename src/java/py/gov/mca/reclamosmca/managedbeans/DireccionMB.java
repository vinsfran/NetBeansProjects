/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "direccionMB")
@SessionScoped
public class DireccionMB implements Serializable {

    private String inicio;
    private String registro;
    private String sesion;
    private String contacto;

    public DireccionMB() {
        this.inicio = "active";
    }

    public void asignarEstilo(String asignar) {
        limpiar();
        if (asignar.equals("Inicio")) {
            this.inicio = "active";
        } else if (asignar.equals("Registro")) {
            this.registro = "active";
        } else if (asignar.equals("Sesion")) {
            this.setSesion("active");
        } else {
            this.contacto = "active";
        }
    }

    private void limpiar() {
        this.inicio = "#";
        this.registro = "#";
        this.setSesion("#");
        this.contacto = "#";
    }

    /**
     * @return the inicio
     */
    public String getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the registro
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * @return the sesion
     */
    public String getSesion() {
        return sesion;
    }

    /**
     * @param sesion the sesion to set
     */
    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    /**
     * @return the contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContaco(String contacto) {
        this.contacto = contacto;
    }

}
