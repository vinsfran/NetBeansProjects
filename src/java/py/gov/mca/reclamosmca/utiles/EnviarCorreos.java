package py.gov.mca.reclamosmca.utiles;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author vinsfran
 */
public class EnviarCorreos {

    public String enviarMail(String destino, String asunto, String htmlMensaje) {

        // MCA
        String servidorSMTP = "126.10.10.180";
        String puerto = "25";
        String usuario = "reclamosmca";
        String password = "dos01";

        //GMAIL
//        String servidorSMTP = "smtp.gmail.com";
//        String puerto = "587";
//        String usuario = "reclamosmca@gmail.com";
//        String password = "dos123456";
        
        
        Properties props = new Properties();

        //props.setProperty("mail.smtp.ssl.trust", "smtpserver");
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.host", servidorSMTP);
        props.setProperty("mail.smtp.port", puerto);
        //PARA MCA COMENTAR LA LINEA DE ABAJO
        //props.setProperty("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getDefaultInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(asunto);
            try {
                message.setFrom(new InternetAddress("no_responder@mca.gov.py", "Sistema de Reclamos Online M.C.A."));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EnviarCorreos.class.getName()).log(Level.SEVERE, null, ex);
            }
            message.setContent(htmlMensaje, "text/html; charset=UTF-8");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(servidorSMTP, usuario, password);

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "OK";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}