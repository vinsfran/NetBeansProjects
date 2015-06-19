package py.gov.mca.reclamosmca.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.entitys.Usuarios;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "elementosWebMB")
@SessionScoped
public class ElementosWebMB {

    private String btnCerrarSesionVisible;
    private String btnCerrarSesionDesactivado;
    private String btnCambiarContrasenaVisible;
    private String btnCambiarContrasenaDesactivado;
    private String btnAjustesVisible;
    private String btnAjustesDesactivado;
    private String btnReportesVisible;
    private String btnReportesDesactivado;
    private String btnReclamosVisible;
    private String btnReclamosDesactivado;
    private String btnBuscarVisible;
    private String btnBuscarDesactivado;
    private String btnCancelarVisible;
    private String btnCancelarDesactivado;
    private String btnIngresarVisible;
    private String btnIngresarDesactivado;
    private String btnNuevoVisible;
    private String btnNuevoDesactivado;
    private String btnPendienesVisible;
    private String btnPendienesDesactivado;
    private String btnAtendidosVisible;
    private String btnAtendidosDesactivado;
    private String btnFinalizadosVisible;
    private String btnFinalizadosDesactivado;
    private String btnGenerarVisible;
    private String btnGenerarDesactivado;
    private String btnEnviarVisible;
    private String btnEnviarDesactivado;
    private String btnProcesarVisible;
    private String btnProcesarDesactivado;
    private String btnNuevoReclamoVisible;
    private String btnNuevoReclamoDesactivado;
    private String btnEnProcesoVisible;
    private String btnEnProcesoDesactivado;
    private String btnDescargarVisible;
    private String btnDescargarDesactivado;
    private String btnDescargarPdfVisible;
    private String btnDescargarPdfDesactivado;
    private String btnAgregarVisible;
    private String btnAgregarDesactivado;
    private String btnAceptarVisible;
    private String btnAceptarDesactivado;
    private String btnFinalizarVisible;
    private String btnFinalizarDesactivado;
    private String btnActualizarVisible;
    private String btnActualizarDesactivado;
    private String btnCambiarVisible;
    private String btnCambiarDesactivado;
    private String btnImprimirVisible;
    private String btnImprimirDesactivado;
    private String btnConfirmarVisible;
    private String btnConfirmarDesactivado;
    private String btnRecuperarVisible;
    private String btnRecuperarDesactivado;

    public ElementosWebMB() {
        Usuarios usu = recuperarSessionUsuario();
        
        btnCerrarSesionVisible = "false";

    }

    public Usuarios recuperarSessionUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UsuariosMB login = (UsuariosMB) session.getAttribute("usuariosMB");
        return login.getUsuario();
    }

    /**
     * @return the btnCerrarSesionVisible
     */
    public String getBtnCerrarSesionVisible() {
        return btnCerrarSesionVisible;
    }

    /**
     * @param btnCerrarSesionVisible the btnCerrarSesionVisible to set
     */
    public void setBtnCerrarSesionVisible(String btnCerrarSesionVisible) {
        this.btnCerrarSesionVisible = btnCerrarSesionVisible;
    }

    /**
     * @return the btnCerrarSesionDesactivado
     */
    public String getBtnCerrarSesionDesactivado() {
        return btnCerrarSesionDesactivado;
    }

    /**
     * @param btnCerrarSesionDesactivado the btnCerrarSesionDesactivado to set
     */
    public void setBtnCerrarSesionDesactivado(String btnCerrarSesionDesactivado) {
        this.btnCerrarSesionDesactivado = btnCerrarSesionDesactivado;
    }

    /**
     * @return the btnCambiarContrasenaVisible
     */
    public String getBtnCambiarContrasenaVisible() {
        return btnCambiarContrasenaVisible;
    }

    /**
     * @param btnCambiarContrasenaVisible the btnCambiarContrasenaVisible to set
     */
    public void setBtnCambiarContrasenaVisible(String btnCambiarContrasenaVisible) {
        this.btnCambiarContrasenaVisible = btnCambiarContrasenaVisible;
    }

    /**
     * @return the btnCambiarContrasenaDesactivado
     */
    public String getBtnCambiarContrasenaDesactivado() {
        return btnCambiarContrasenaDesactivado;
    }

    /**
     * @param btnCambiarContrasenaDesactivado the
     * btnCambiarContrasenaDesactivado to set
     */
    public void setBtnCambiarContrasenaDesactivado(String btnCambiarContrasenaDesactivado) {
        this.btnCambiarContrasenaDesactivado = btnCambiarContrasenaDesactivado;
    }

    /**
     * @return the btnAjustesVisible
     */
    public String getBtnAjustesVisible() {
        return btnAjustesVisible;
    }

    /**
     * @param btnAjustesVisible the btnAjustesVisible to set
     */
    public void setBtnAjustesVisible(String btnAjustesVisible) {
        this.btnAjustesVisible = btnAjustesVisible;
    }

    /**
     * @return the btnAjustesDesactivado
     */
    public String getBtnAjustesDesactivado() {
        return btnAjustesDesactivado;
    }

    /**
     * @param btnAjustesDesactivado the btnAjustesDesactivado to set
     */
    public void setBtnAjustesDesactivado(String btnAjustesDesactivado) {
        this.btnAjustesDesactivado = btnAjustesDesactivado;
    }

    /**
     * @return the btnReportesVisible
     */
    public String getBtnReportesVisible() {
        return btnReportesVisible;
    }

    /**
     * @param btnReportesVisible the btnReportesVisible to set
     */
    public void setBtnReportesVisible(String btnReportesVisible) {
        this.btnReportesVisible = btnReportesVisible;
    }

    /**
     * @return the btnReportesDesactivado
     */
    public String getBtnReportesDesactivado() {
        return btnReportesDesactivado;
    }

    /**
     * @param btnReportesDesactivado the btnReportesDesactivado to set
     */
    public void setBtnReportesDesactivado(String btnReportesDesactivado) {
        this.btnReportesDesactivado = btnReportesDesactivado;
    }

    /**
     * @return the btnReclamosVisible
     */
    public String getBtnReclamosVisible() {
        return btnReclamosVisible;
    }

    /**
     * @param btnReclamosVisible the btnReclamosVisible to set
     */
    public void setBtnReclamosVisible(String btnReclamosVisible) {
        this.btnReclamosVisible = btnReclamosVisible;
    }

    /**
     * @return the btnReclamosDesactivado
     */
    public String getBtnReclamosDesactivado() {
        return btnReclamosDesactivado;
    }

    /**
     * @param btnReclamosDesactivado the btnReclamosDesactivado to set
     */
    public void setBtnReclamosDesactivado(String btnReclamosDesactivado) {
        this.btnReclamosDesactivado = btnReclamosDesactivado;
    }

    /**
     * @return the btnBuscarVisible
     */
    public String getBtnBuscarVisible() {
        return btnBuscarVisible;
    }

    /**
     * @param btnBuscarVisible the btnBuscarVisible to set
     */
    public void setBtnBuscarVisible(String btnBuscarVisible) {
        this.btnBuscarVisible = btnBuscarVisible;
    }

    /**
     * @return the btnBuscarDesactivado
     */
    public String getBtnBuscarDesactivado() {
        return btnBuscarDesactivado;
    }

    /**
     * @param btnBuscarDesactivado the btnBuscarDesactivado to set
     */
    public void setBtnBuscarDesactivado(String btnBuscarDesactivado) {
        this.btnBuscarDesactivado = btnBuscarDesactivado;
    }

    /**
     * @return the btnCancelarVisible
     */
    public String getBtnCancelarVisible() {
        return btnCancelarVisible;
    }

    /**
     * @param btnCancelarVisible the btnCancelarVisible to set
     */
    public void setBtnCancelarVisible(String btnCancelarVisible) {
        this.btnCancelarVisible = btnCancelarVisible;
    }

    /**
     * @return the btnCancelarDesactivado
     */
    public String getBtnCancelarDesactivado() {
        return btnCancelarDesactivado;
    }

    /**
     * @param btnCancelarDesactivado the btnCancelarDesactivado to set
     */
    public void setBtnCancelarDesactivado(String btnCancelarDesactivado) {
        this.btnCancelarDesactivado = btnCancelarDesactivado;
    }

    /**
     * @return the btnIngresarVisible
     */
    public String getBtnIngresarVisible() {
        return btnIngresarVisible;
    }

    /**
     * @param btnIngresarVisible the btnIngresarVisible to set
     */
    public void setBtnIngresarVisible(String btnIngresarVisible) {
        this.btnIngresarVisible = btnIngresarVisible;
    }

    /**
     * @return the btnIngresarDesactivado
     */
    public String getBtnIngresarDesactivado() {
        return btnIngresarDesactivado;
    }

    /**
     * @param btnIngresarDesactivado the btnIngresarDesactivado to set
     */
    public void setBtnIngresarDesactivado(String btnIngresarDesactivado) {
        this.btnIngresarDesactivado = btnIngresarDesactivado;
    }

    /**
     * @return the btnNuevoVisible
     */
    public String getBtnNuevoVisible() {
        return btnNuevoVisible;
    }

    /**
     * @param btnNuevoVisible the btnNuevoVisible to set
     */
    public void setBtnNuevoVisible(String btnNuevoVisible) {
        this.btnNuevoVisible = btnNuevoVisible;
    }

    /**
     * @return the btnNuevoDesactivado
     */
    public String getBtnNuevoDesactivado() {
        return btnNuevoDesactivado;
    }

    /**
     * @param btnNuevoDesactivado the btnNuevoDesactivado to set
     */
    public void setBtnNuevoDesactivado(String btnNuevoDesactivado) {
        this.btnNuevoDesactivado = btnNuevoDesactivado;
    }

    /**
     * @return the btnPendienesVisible
     */
    public String getBtnPendienesVisible() {
        return btnPendienesVisible;
    }

    /**
     * @param btnPendienesVisible the btnPendienesVisible to set
     */
    public void setBtnPendienesVisible(String btnPendienesVisible) {
        this.btnPendienesVisible = btnPendienesVisible;
    }

    /**
     * @return the btnPendienesDesactivado
     */
    public String getBtnPendienesDesactivado() {
        return btnPendienesDesactivado;
    }

    /**
     * @param btnPendienesDesactivado the btnPendienesDesactivado to set
     */
    public void setBtnPendienesDesactivado(String btnPendienesDesactivado) {
        this.btnPendienesDesactivado = btnPendienesDesactivado;
    }

    /**
     * @return the btnAtendidosVisible
     */
    public String getBtnAtendidosVisible() {
        return btnAtendidosVisible;
    }

    /**
     * @param btnAtendidosVisible the btnAtendidosVisible to set
     */
    public void setBtnAtendidosVisible(String btnAtendidosVisible) {
        this.btnAtendidosVisible = btnAtendidosVisible;
    }

    /**
     * @return the btnAtendidosDesactivado
     */
    public String getBtnAtendidosDesactivado() {
        return btnAtendidosDesactivado;
    }

    /**
     * @param btnAtendidosDesactivado the btnAtendidosDesactivado to set
     */
    public void setBtnAtendidosDesactivado(String btnAtendidosDesactivado) {
        this.btnAtendidosDesactivado = btnAtendidosDesactivado;
    }

    /**
     * @return the btnFinalizadosVisible
     */
    public String getBtnFinalizadosVisible() {
        return btnFinalizadosVisible;
    }

    /**
     * @param btnFinalizadosVisible the btnFinalizadosVisible to set
     */
    public void setBtnFinalizadosVisible(String btnFinalizadosVisible) {
        this.btnFinalizadosVisible = btnFinalizadosVisible;
    }

    /**
     * @return the btnFinalizadosDesactivado
     */
    public String getBtnFinalizadosDesactivado() {
        return btnFinalizadosDesactivado;
    }

    /**
     * @param btnFinalizadosDesactivado the btnFinalizadosDesactivado to set
     */
    public void setBtnFinalizadosDesactivado(String btnFinalizadosDesactivado) {
        this.btnFinalizadosDesactivado = btnFinalizadosDesactivado;
    }

    /**
     * @return the btnGenerarVisible
     */
    public String getBtnGenerarVisible() {
        return btnGenerarVisible;
    }

    /**
     * @param btnGenerarVisible the btnGenerarVisible to set
     */
    public void setBtnGenerarVisible(String btnGenerarVisible) {
        this.btnGenerarVisible = btnGenerarVisible;
    }

    /**
     * @return the btnGenerarDesactivado
     */
    public String getBtnGenerarDesactivado() {
        return btnGenerarDesactivado;
    }

    /**
     * @param btnGenerarDesactivado the btnGenerarDesactivado to set
     */
    public void setBtnGenerarDesactivado(String btnGenerarDesactivado) {
        this.btnGenerarDesactivado = btnGenerarDesactivado;
    }

    /**
     * @return the btnEnviarVisible
     */
    public String getBtnEnviarVisible() {
        return btnEnviarVisible;
    }

    /**
     * @param btnEnviarVisible the btnEnviarVisible to set
     */
    public void setBtnEnviarVisible(String btnEnviarVisible) {
        this.btnEnviarVisible = btnEnviarVisible;
    }

    /**
     * @return the btnEnviarDesactivado
     */
    public String getBtnEnviarDesactivado() {
        return btnEnviarDesactivado;
    }

    /**
     * @param btnEnviarDesactivado the btnEnviarDesactivado to set
     */
    public void setBtnEnviarDesactivado(String btnEnviarDesactivado) {
        this.btnEnviarDesactivado = btnEnviarDesactivado;
    }

    /**
     * @return the btnProcesarVisible
     */
    public String getBtnProcesarVisible() {
        return btnProcesarVisible;
    }

    /**
     * @param btnProcesarVisible the btnProcesarVisible to set
     */
    public void setBtnProcesarVisible(String btnProcesarVisible) {
        this.btnProcesarVisible = btnProcesarVisible;
    }

    /**
     * @return the btnProcesarDesactivado
     */
    public String getBtnProcesarDesactivado() {
        return btnProcesarDesactivado;
    }

    /**
     * @param btnProcesarDesactivado the btnProcesarDesactivado to set
     */
    public void setBtnProcesarDesactivado(String btnProcesarDesactivado) {
        this.btnProcesarDesactivado = btnProcesarDesactivado;
    }

    /**
     * @return the btnNuevoReclamoVisible
     */
    public String getBtnNuevoReclamoVisible() {
        return btnNuevoReclamoVisible;
    }

    /**
     * @param btnNuevoReclamoVisible the btnNuevoReclamoVisible to set
     */
    public void setBtnNuevoReclamoVisible(String btnNuevoReclamoVisible) {
        this.btnNuevoReclamoVisible = btnNuevoReclamoVisible;
    }

    /**
     * @return the btnNuevoReclamoDesactivado
     */
    public String getBtnNuevoReclamoDesactivado() {
        return btnNuevoReclamoDesactivado;
    }

    /**
     * @param btnNuevoReclamoDesactivado the btnNuevoReclamoDesactivado to set
     */
    public void setBtnNuevoReclamoDesactivado(String btnNuevoReclamoDesactivado) {
        this.btnNuevoReclamoDesactivado = btnNuevoReclamoDesactivado;
    }

    /**
     * @return the btnEnProcesoVisible
     */
    public String getBtnEnProcesoVisible() {
        return btnEnProcesoVisible;
    }

    /**
     * @param btnEnProcesoVisible the btnEnProcesoVisible to set
     */
    public void setBtnEnProcesoVisible(String btnEnProcesoVisible) {
        this.btnEnProcesoVisible = btnEnProcesoVisible;
    }

    /**
     * @return the btnEnProcesoDesactivado
     */
    public String getBtnEnProcesoDesactivado() {
        return btnEnProcesoDesactivado;
    }

    /**
     * @param btnEnProcesoDesactivado the btnEnProcesoDesactivado to set
     */
    public void setBtnEnProcesoDesactivado(String btnEnProcesoDesactivado) {
        this.btnEnProcesoDesactivado = btnEnProcesoDesactivado;
    }

    /**
     * @return the btnDescargarVisible
     */
    public String getBtnDescargarVisible() {
        return btnDescargarVisible;
    }

    /**
     * @param btnDescargarVisible the btnDescargarVisible to set
     */
    public void setBtnDescargarVisible(String btnDescargarVisible) {
        this.btnDescargarVisible = btnDescargarVisible;
    }

    /**
     * @return the btnDescargarDesactivado
     */
    public String getBtnDescargarDesactivado() {
        return btnDescargarDesactivado;
    }

    /**
     * @param btnDescargarDesactivado the btnDescargarDesactivado to set
     */
    public void setBtnDescargarDesactivado(String btnDescargarDesactivado) {
        this.btnDescargarDesactivado = btnDescargarDesactivado;
    }

    /**
     * @return the btnDescargarPdfVisible
     */
    public String getBtnDescargarPdfVisible() {
        return btnDescargarPdfVisible;
    }

    /**
     * @param btnDescargarPdfVisible the btnDescargarPdfVisible to set
     */
    public void setBtnDescargarPdfVisible(String btnDescargarPdfVisible) {
        this.btnDescargarPdfVisible = btnDescargarPdfVisible;
    }

    /**
     * @return the btnDescargarPdfDesactivado
     */
    public String getBtnDescargarPdfDesactivado() {
        return btnDescargarPdfDesactivado;
    }

    /**
     * @param btnDescargarPdfDesactivado the btnDescargarPdfDesactivado to set
     */
    public void setBtnDescargarPdfDesactivado(String btnDescargarPdfDesactivado) {
        this.btnDescargarPdfDesactivado = btnDescargarPdfDesactivado;
    }

    /**
     * @return the btnAgregarVisible
     */
    public String getBtnAgregarVisible() {
        return btnAgregarVisible;
    }

    /**
     * @param btnAgregarVisible the btnAgregarVisible to set
     */
    public void setBtnAgregarVisible(String btnAgregarVisible) {
        this.btnAgregarVisible = btnAgregarVisible;
    }

    /**
     * @return the btnAgregarDesactivado
     */
    public String getBtnAgregarDesactivado() {
        return btnAgregarDesactivado;
    }

    /**
     * @param btnAgregarDesactivado the btnAgregarDesactivado to set
     */
    public void setBtnAgregarDesactivado(String btnAgregarDesactivado) {
        this.btnAgregarDesactivado = btnAgregarDesactivado;
    }

    /**
     * @return the btnAceptarVisible
     */
    public String getBtnAceptarVisible() {
        return btnAceptarVisible;
    }

    /**
     * @param btnAceptarVisible the btnAceptarVisible to set
     */
    public void setBtnAceptarVisible(String btnAceptarVisible) {
        this.btnAceptarVisible = btnAceptarVisible;
    }

    /**
     * @return the btnAceptarDesactivado
     */
    public String getBtnAceptarDesactivado() {
        return btnAceptarDesactivado;
    }

    /**
     * @param btnAceptarDesactivado the btnAceptarDesactivado to set
     */
    public void setBtnAceptarDesactivado(String btnAceptarDesactivado) {
        this.btnAceptarDesactivado = btnAceptarDesactivado;
    }

    /**
     * @return the btnFinalizarVisible
     */
    public String getBtnFinalizarVisible() {
        return btnFinalizarVisible;
    }

    /**
     * @param btnFinalizarVisible the btnFinalizarVisible to set
     */
    public void setBtnFinalizarVisible(String btnFinalizarVisible) {
        this.btnFinalizarVisible = btnFinalizarVisible;
    }

    /**
     * @return the btnFinalizarDesactivado
     */
    public String getBtnFinalizarDesactivado() {
        return btnFinalizarDesactivado;
    }

    /**
     * @param btnFinalizarDesactivado the btnFinalizarDesactivado to set
     */
    public void setBtnFinalizarDesactivado(String btnFinalizarDesactivado) {
        this.btnFinalizarDesactivado = btnFinalizarDesactivado;
    }

    /**
     * @return the btnActualizarVisible
     */
    public String getBtnActualizarVisible() {
        return btnActualizarVisible;
    }

    /**
     * @param btnActualizarVisible the btnActualizarVisible to set
     */
    public void setBtnActualizarVisible(String btnActualizarVisible) {
        this.btnActualizarVisible = btnActualizarVisible;
    }

    /**
     * @return the btnActualizarDesactivado
     */
    public String getBtnActualizarDesactivado() {
        return btnActualizarDesactivado;
    }

    /**
     * @param btnActualizarDesactivado the btnActualizarDesactivado to set
     */
    public void setBtnActualizarDesactivado(String btnActualizarDesactivado) {
        this.btnActualizarDesactivado = btnActualizarDesactivado;
    }

    /**
     * @return the btnCambiarVisible
     */
    public String getBtnCambiarVisible() {
        return btnCambiarVisible;
    }

    /**
     * @param btnCambiarVisible the btnCambiarVisible to set
     */
    public void setBtnCambiarVisible(String btnCambiarVisible) {
        this.btnCambiarVisible = btnCambiarVisible;
    }

    /**
     * @return the btnCambiarDesactivado
     */
    public String getBtnCambiarDesactivado() {
        return btnCambiarDesactivado;
    }

    /**
     * @param btnCambiarDesactivado the btnCambiarDesactivado to set
     */
    public void setBtnCambiarDesactivado(String btnCambiarDesactivado) {
        this.btnCambiarDesactivado = btnCambiarDesactivado;
    }

    /**
     * @return the btnImprimirVisible
     */
    public String getBtnImprimirVisible() {
        return btnImprimirVisible;
    }

    /**
     * @param btnImprimirVisible the btnImprimirVisible to set
     */
    public void setBtnImprimirVisible(String btnImprimirVisible) {
        this.btnImprimirVisible = btnImprimirVisible;
    }

    /**
     * @return the btnImprimirDesactivado
     */
    public String getBtnImprimirDesactivado() {
        return btnImprimirDesactivado;
    }

    /**
     * @param btnImprimirDesactivado the btnImprimirDesactivado to set
     */
    public void setBtnImprimirDesactivado(String btnImprimirDesactivado) {
        this.btnImprimirDesactivado = btnImprimirDesactivado;
    }

    /**
     * @return the btnConfirmarVisible
     */
    public String getBtnConfirmarVisible() {
        return btnConfirmarVisible;
    }

    /**
     * @param btnConfirmarVisible the btnConfirmarVisible to set
     */
    public void setBtnConfirmarVisible(String btnConfirmarVisible) {
        this.btnConfirmarVisible = btnConfirmarVisible;
    }

    /**
     * @return the btnConfirmarDesactivado
     */
    public String getBtnConfirmarDesactivado() {
        return btnConfirmarDesactivado;
    }

    /**
     * @param btnConfirmarDesactivado the btnConfirmarDesactivado to set
     */
    public void setBtnConfirmarDesactivado(String btnConfirmarDesactivado) {
        this.btnConfirmarDesactivado = btnConfirmarDesactivado;
    }

    /**
     * @return the btnRecuperarVisible
     */
    public String getBtnRecuperarVisible() {
        return btnRecuperarVisible;
    }

    /**
     * @param btnRecuperarVisible the btnRecuperarVisible to set
     */
    public void setBtnRecuperarVisible(String btnRecuperarVisible) {
        this.btnRecuperarVisible = btnRecuperarVisible;
    }

    /**
     * @return the btnRecuperarDesactivado
     */
    public String getBtnRecuperarDesactivado() {
        return btnRecuperarDesactivado;
    }

    /**
     * @param btnRecuperarDesactivado the btnRecuperarDesactivado to set
     */
    public void setBtnRecuperarDesactivado(String btnRecuperarDesactivado) {
        this.btnRecuperarDesactivado = btnRecuperarDesactivado;
    }

}
