package py.gov.mca.reclamosmca.utiles;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import py.gov.mca.reclamosmca.beansession.MbSUsuarios;
import py.gov.mca.reclamosmca.entitys.Usuarios;

/**
 *
 * @author vinsfran
 */
public class DataUtil {

    public static Usuarios recuperarUsuarioSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        MbSUsuarios usuario = (MbSUsuarios) session.getAttribute("mbSUsuarios");
        return usuario.getUsuario();
    }

    public static String dateToString(Date fecha, String patron) {
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        if (fecha == null) {
            return "";
        } else {
            return formato.format(fecha);
        }
    }

    public static String armarNombreDeArchivo(String nombreOriginal) {
        String nombreArchivo = nombreOriginal;
        nombreArchivo = nombreArchivo.trim();
        nombreArchivo = eliminarCarateresEspeciales(nombreArchivo);
        nombreArchivo = reemplazarEspacios(nombreArchivo, "_");
        return nombreArchivo;
    }

    public static String reemplazarEspacios(String nombre, String reemplazo) {
        return nombre.replaceAll("\\s", "_");
    }

    /**
     * Función que elimina acentos y caracteres especiales de una cadena de
     * texto.
     *
     * @param input
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    public static String eliminarCarateresEspeciales(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }

}
