package py.gov.mca.reclamosmca.managedbeans;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.gov.mca.reclamosmca.entitys.EstadosUsuarios;
import py.gov.mca.reclamosmca.entitys.Personas;
import py.gov.mca.reclamosmca.entitys.Roles;
import py.gov.mca.reclamosmca.utiles.Converciones;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "registroUsuarioMB")
@SessionScoped
public class RegistroUsuarioMB implements Serializable {

    private Usuarios usuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String telefono;
    private String contrasena1;
    private String contrasena2;
    private String cuentaCorriente;
    private String cedula;
    private Date fechaRegistro;
    private String redireccion;
    @EJB
    private UsuariosSB usuariosSB;

    public RegistroUsuarioMB() {
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.direccion = "";
        this.telefono = "";
        this.contrasena1 = "";
        this.contrasena2 = "";
        this.cuentaCorriente = "";
        this.cedula = "";
        this.redireccion = "?faces-redirect=true";
    }

    public String btnConfirmar() {
        String pagina;
        String mensaje1 = "Validación de registro: ";
        FacesMessage message = new FacesMessage();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
       
        if (nombre.equals("") || apellido.equals("") || correo.equals("") || contrasena1.equals("") || contrasena2.equals("")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(mensaje1);
            message.setDetail("Los campos con (*) no pueden estar vacios.");
            pagina = "registroUsuario";
        } else {
            if (contrasena1.equals(contrasena2)) {
                Converciones c = new Converciones();
                String contrasenaMD5 = c.getMD5(contrasena1);
                if (contrasenaMD5 == null) {
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    message.setSummary("ERROR de Registro: ");
                    message.setDetail("No se pudo crear. (MD5)");
                    pagina = "registroUsuario" + redireccion;
                } else {
                    usuario = new Usuarios();
                    usuario.setLoginUsuario(correo);
                    usuario.setClaveUsuario(contrasenaMD5);
                    usuario.setFkCodPersona(new Personas());
                    usuario.getFkCodPersona().setCedulaPersona(cedula);
                    usuario.getFkCodPersona().setNombrePersona(nombre);
                    usuario.getFkCodPersona().setApellidoPersona(apellido);
                    usuario.getFkCodPersona().setFechaRegistroPersona(new Date());
                    usuario.getFkCodPersona().setDireccionPersona(direccion);
                    usuario.getFkCodPersona().setTelefonoPersona(telefono);
                    usuario.getFkCodPersona().setCtaCtePersona(cuentaCorriente);
                    usuario.setFkCodEstadoUsuario(new EstadosUsuarios());
                    usuario.getFkCodEstadoUsuario().setCodEstadoUsuario(2);
                    usuario.setFkCodRol(new Roles());
                    usuario.getFkCodRol().setCodRol(6);
                    String resultado = usuariosSB.crearUsuariosWeb(usuario);
                    System.out.println("RESU " + resultado);
                    if (resultado.equals("OK")) {
                         System.out.println("ENTRO" + nombre);
                        pagina = "mensajeRegistro" + redireccion;
                    } else {
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        message.setSummary("ERROR de Registro: ");
                        message.setDetail(resultado);
                        pagina = "registroUsuario" + redireccion;
                    }
                }
            } else {
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setSummary(mensaje1);
                message.setDetail("Las contraseñas no son iguales.");
                pagina = "registroUsuario" + redireccion;
            }

        }
        context.addMessage(null, message);
        return pagina;
    }

    public void limpiarCampos() {
        System.out.println("LIMPIA!!!");
        this.nombre = "";
        this.apellido = "";
        this.correo = "";
        this.direccion = "";
        this.telefono = "";
        this.contrasena1 = "";
        this.contrasena2 = "";
        this.cuentaCorriente = "";
        this.cedula = "";
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuarioWeb the usuario to set
     */
    public void setUsuarioWeb(Usuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena2
     */
    public String getContrasena2() {
        return contrasena2;
    }

    /**
     * @param contrasena2 the contrasena2 to set
     */
    public void setContrasena2(String contrasena2) {
        this.contrasena2 = contrasena2;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the contrasena1
     */
    public String getContrasena1() {
        return contrasena1;
    }

    /**
     * @param contrasena1 the contrasena1 to set
     */
    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    /**
     * @return the cuentaCorriente
     */
    public String getCuentaCorriente() {
        return cuentaCorriente;
    }

    /**
     * @param cuentaCorriente the cuentaCorriente to set
     */
    public void setCuentaCorriente(String cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
