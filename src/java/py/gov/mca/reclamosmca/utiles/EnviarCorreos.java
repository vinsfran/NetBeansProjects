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
import py.gov.mca.reclamosmca.entitys.ConfCorreo;
import py.gov.mca.reclamosmca.sessionbeans.ConfCorreoSB;

/**
 *
 * @author vinsfran
 */
@Stateless
@SessionScoped
public class EnviarCorreos {
    
    private ConfCorreo confCorreo;

    @EJB
    private ConfCorreoSB confCorreoSB;

    public EnviarCorreos() {
        this.confCorreo = new ConfCorreo();
    }

    public String enviarMail(String destino, String asunto, String htmlMensaje) {
        
        setConfCorreo(confCorreoSB.consultarPorCodCodConfCorreo(1));
        Properties props = new Properties();
        // props.setProperty("mail.smtp.ssl.trust", confCorreo.getMailSmtpSslTrust());
        props.setProperty("mail.debug", getConfCorreo().getMailDebug());
        props.setProperty("mail.smtp.auth", getConfCorreo().getMailSmtpAuth());
        props.setProperty("mail.smtp.host", getConfCorreo().getMailSmtpHost());
        props.setProperty("mail.smtp.port", getConfCorreo().getMailSmtpPort());
        props.setProperty("mail.smtp.starttls.enable", getConfCorreo().getMailSmtpStarttlsEnable());

        Session mailSession = Session.getDefaultInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(asunto);
            try {
                message.setFrom(new InternetAddress(getConfCorreo().getInternetAddress(), "Sistema de Reclamos Online M.C.A."));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EnviarCorreos.class.getName()).log(Level.SEVERE, null, ex);
            }
            message.setContent(htmlMensaje, "text/html; charset=UTF-8");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(getConfCorreo().getMailSmtpHost(), getConfCorreo().getUsuario(), getConfCorreo().getPassword());

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "OK";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }

    /**
     * @return the confCorreo
     */
    public ConfCorreo getConfCorreo() {
        return confCorreo;
    }

    /**
     * @param confCorreo the confCorreo to set
     */
    public void setConfCorreo(ConfCorreo confCorreo) {
        this.confCorreo = confCorreo;
    }

}
