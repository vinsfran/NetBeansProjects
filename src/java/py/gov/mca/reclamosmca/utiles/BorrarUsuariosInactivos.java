package py.gov.mca.reclamosmca.utiles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.util.TimerTask;
import java.util.Timer;
import javax.ejb.EJB;
import py.gov.mca.reclamosmca.entitys.Usuarios;
import py.gov.mca.reclamosmca.sessionbeans.PersonasSB;
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;

/**
 *
 * @author vinsfran
 */
public class BorrarUsuariosInactivos extends TimerTask implements ServletContextListener {

    private Timer timer;
    @EJB
    private UsuariosSB usuariosSB;
    @EJB
    private PersonasSB personasSB;
    @EJB
    private EnviarCorreos enviarCorreos;

    public void contextInitialized(ServletContextEvent evt) {
        // Iniciamos el timer
        timer = new Timer() {
        };
        //timer.schedule(this, 0, 10*60*1000);  // Ejemplo: Cada 10 minutos
        timer.schedule(this, 0, 60 * 60 * 1000);  // Ejemplo: Cada 60 minutos
    }

    public void contextDestroyed(ServletContextEvent evt) {
        timer.cancel();
    }

    public void run() {
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);

        //Se configura para ejecutar a las 3 AM
        if (hora == 3) {
            String mensajeCorreo = "<html>"
                    + "     <head>"
                    + "         <meta charset=\"UTF-8\">"
                    + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "     </head>"
                    + "     <body style='background-color: #ffffff'>"
                    + "       <div style='text-align: center;'>"
                    + "            <h3>REPORTE DE SOBRE BORRAR USUARIOS INACTIVO</h3> "
                    + "       </div> "
                    + "       <div> "
                    + "             <p>FECHA: " + dia + "/" + (mes + 1) + "/" + año + "</p>"
                    + "             <p>HORA: " + hora + ":" + minuto + ":" + segundo + "</p>";
            //System.out.printf("HORA: %02d:%02d:%02d %n", hora, minuto, segundo);
            List<Usuarios> usuariosWebInactivos = usuariosSB.listarUsuariosInactivos(2);
            if (usuariosWebInactivos != null) {
                mensajeCorreo = mensajeCorreo + "<p>HAY USUARIOS INACTIVOS</p>"
                        + "<p>CANTIDAD: " + usuariosWebInactivos.size() + "</p>"
                        + "<p>----------------------------</p>";
                Date fechaMayor = new Date();
                long diferenciaEn_ms;
                for (int i = 0; usuariosWebInactivos.size() > i; i++) {
                    diferenciaEn_ms = fechaMayor.getTime() - usuariosWebInactivos.get(i).getFkCodPersona().getFechaRegistroPersona().getTime();
                    long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
                    String usuario = usuariosWebInactivos.get(i).getLoginUsuario();
                    mensajeCorreo = mensajeCorreo + "<p>USUARIO: " + usuario + "</p>"
                            + "<p>DIAS SIN ACTIVAR CUENTA: " + dias + "</p>";

                    if (dias > 2) {
                        //System.out.println("Eliminado " + usuariosSB.eliminarUsuarios(usuariosWebInactivos.get(i)));
                        String mensaje = personasSB.eliminarPersonas(usuariosWebInactivos.get(i).getFkCodPersona());
                        if (mensaje.equals("OK")) {
                            mensajeCorreo = mensajeCorreo + "<p>USUARIO: " + usuario + " ELIMINADO CORRECTAMENTE.</p>";
                        } else {
                            mensajeCorreo = mensajeCorreo + "<p>ERROR AL ELIMINAR USUARIO: " + usuario + ", " + mensaje + "</p>";
                        }
                    }
                    mensajeCorreo = mensajeCorreo + "<p>----------------------------</p>";
                }
            } else {
                mensajeCorreo = mensajeCorreo + "<p>NO HAY USUARIOS INACTIVOS.</p>";
            }
            mensajeCorreo = mensajeCorreo + "<p>FIN REPORTE</p></div>"
                    + "     </body>"
                    + "</html>";
            enviarCorreos.enviarMail("vinsfran@gmail.com", "Usuarios Inactivos Reporte", mensajeCorreo);
        }
    }
}
