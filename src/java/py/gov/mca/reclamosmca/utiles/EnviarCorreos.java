package py.gov.mca.reclamosmca.utiles;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import py.gov.mca.reclamosmca.entitys.Configuraciones;
import py.gov.mca.reclamosmca.sessionbeans.ConfiguracionesSB;

/**
 *
 * @author vinsfran
 */
@Stateless
@SessionScoped
public class EnviarCorreos {
    
    private Configuraciones configuraciones;

    @EJB
    private ConfiguracionesSB configuracionesSB;

    public EnviarCorreos() {
        this.configuraciones = new Configuraciones();
    }

    public String enviarMail(String destino, String asunto, String htmlMensaje) {
        
        setConfiguraciones(configuracionesSB.consultarPorCodConfiguracion(1));
        Properties props = new Properties();
        // props.setProperty("mail.smtp.ssl.trust", confCorreo.getMailSmtpSslTrust());
        props.setProperty("mail.debug", getConfiguraciones().getMailDebug());
        props.setProperty("mail.smtp.auth", getConfiguraciones().getMailSmtpAuth());
        props.setProperty("mail.smtp.host", getConfiguraciones().getMailSmtpHost());
        props.setProperty("mail.smtp.port", getConfiguraciones().getMailSmtpPort());
        props.setProperty("mail.smtp.starttls.enable", getConfiguraciones().getMailSmtpStarttlsEnable());

        Session mailSession = Session.getDefaultInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(asunto);
            try {
                message.setFrom(new InternetAddress(getConfiguraciones().getInternetAddress(), "Sistema de Reclamos Online M.C.A."));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EnviarCorreos.class.getName()).log(Level.SEVERE, null, ex);
            }
            message.setContent(htmlMensaje, "text/html; charset=UTF-8");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(getConfiguraciones().getMailSmtpHost(), getConfiguraciones().getUsuario(), getConfiguraciones().getPassword());

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "OK";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }

    /**
     * @return the configuraciones
     */
    public Configuraciones getConfiguraciones() {
        return configuraciones;
    }

    /**
     * @param configuraciones the configuraciones to set
     */
    public void setConfiguraciones(Configuraciones configuraciones) {
        this.configuraciones = configuraciones;
    }

    

}
