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
            System.out.println("----------------------- INICIO DE BORRAR USUARIOS SE ACTIVO ---------------------------");
            System.out.println("FECHA: " + dia + "/" + (mes + 1) + "/" + año);
            System.out.printf("HORA: %02d:%02d:%02d %n", hora, minuto, segundo);
            List<Usuarios> usuariosWebInactivos = usuariosSB.listarUsuariosInactivos(2);
            if (usuariosWebInactivos != null) {
                System.out.println("HAY USUARIOS INACTIVOS");
                System.out.println("CANTIDAD: " + usuariosWebInactivos.size());
                System.out.println("---------------------------------------------------------------------------------------");
                Date fechaMayor = new Date();
                long diferenciaEn_ms;
                for (int i = 0; usuariosWebInactivos.size() > i; i++) {
                    diferenciaEn_ms = fechaMayor.getTime() - usuariosWebInactivos.get(i).getFkCodPersona().getFechaRegistroPersona().getTime();
                    long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
                    String usuario = usuariosWebInactivos.get(i).getLoginUsuario();
                    System.out.println("USUARIO: " + usuario);
                    System.out.println("DIAS SIN ACTIVAR CUENTA: " + dias);
                    if (dias > 2) {
                        //System.out.println("Eliminado " + usuariosSB.eliminarUsuarios(usuariosWebInactivos.get(i)));
                        String mensaje = personasSB.eliminarPersonas(usuariosWebInactivos.get(i).getFkCodPersona());
                        if (mensaje.equals("OK")) {
                            System.out.println("USUARIO: " + usuario + " ELIMINADO CORRECTAMENTE.");
                        } else {
                            System.out.println("ERROR AL ELIMINAR: " + mensaje);
                        }
                    }
                    System.out.println("---------------------------------------------------------------------------------------");
                }
            } else {
                System.out.println("NO HAY USUARIOS INACTIVOS.");
            }
            System.out.println("----------------------- FIN DE BORRAR USUARIOS SE ACTIVO ------------------------------");
        }

    }
}
