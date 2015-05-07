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
import py.gov.mca.reclamosmca.sessionbeans.UsuariosSB;

/**
 *
 * @author vinsfran
 */
public class BorrarUsuariosInactivos extends TimerTask implements ServletContextListener {

    private Timer timer;
    @EJB
    private UsuariosSB usuariosSB;

    public void contextInitialized(ServletContextEvent evt) {
        // Iniciamos el timer
        timer = new Timer() {
        };
        // timer.schedule(this, 0, 10*60*1000);  // Ejemplo: Cada 10 minutos
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
        System.out.println("Fecha Actual: "
                + dia + "/" + (mes + 1) + "/" + año);
        System.out.printf("Hora Actual: %02d:%02d:%02d %n",
                hora, minuto, segundo);
        if (hora == 03) {
            List<Usuarios> usuariosWebInactivos = usuariosSB.listarUsuariosInactivos(2);
            if (usuariosWebInactivos != null) {
                Date fechaMayor = new Date();
                long diferenciaEn_ms;
                for (int i = 0; usuariosWebInactivos.size() > i; i++) {
                    System.out.println("Hay INACTIVOS");
                    System.out.println("Cantdad INACTIVOS: " + usuariosWebInactivos.size());
                    diferenciaEn_ms = fechaMayor.getTime() - usuariosWebInactivos.get(i).getFkCodPersona().getFechaRegistroPersona().getTime();
                    long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
                    System.out.println("DIAS: " + dias);
                    if (dias == 2) {
                        System.out.println("Eliminado");
                        usuariosSB.eliminarUsuarios(usuariosWebInactivos.get(i));
                    }
                }
            } else {
                System.out.println("No hay INACTIVOS");
            }
        }
    }
}
