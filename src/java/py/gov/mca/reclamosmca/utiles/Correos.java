package py.gov.mca.reclamosmca.utiles;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URL;
import javax.activation.URLDataSource;

/**
 *
 * @author vinsfran
 */
public class Correos {

    private String servidorSMTP;
    private String puerto;
    private String usuario;
    private String password;
    private String destino;
    private String asunto;
    private String mensaje;
    private Properties props;

    public Correos() {
        this.props = new Properties();

        // MCA
        this.usuario = "reclamosmca";
        this.password = "dos01";
        this.puerto = "25";
        this.servidorSMTP = "126.10.10.180";
        //GMAIL
//        this.usuario = "reclamosmca@gmail.com";
//        this.password = "dos123456";
//        this.puerto = "587";
//        this.servidorSMTP = "smtp.gmail.com";

    }

    public String enviarMail(String destino, String asunto, String htmlMensaje) {

//        :mail.smtp.host=mail.optonline.net
//   3:mail.smtp.subject=Testing email
//   4:mail.smtp.from=xxx@yahoo.com
//   5:mail.smtp.to=xxxx@yahoo.com
//   6:mail.smtp.content=This is a test. Erase!
//   7:mail.account.host=mail.yahoo.com
        //props.setProperty("mail.smtp.ssl.trust", "smtpserver");
        getProps().put("mail.debug", "true");
        getProps().put("mail.smtp.auth", true);

        //PARA MCA COMENTAR LA LINEA DE ABAJO
     //   getProps().put("mail.smtp.starttls.enable", true);
        getProps().put("mail.smtp.host", getServidorSMTP());
        getProps().put("mail.smtp.port", getPuerto());

//         Session mailSession = Session.getDefaultInstance(props, null);
//        mailSession.setDebug(true);
//        Transport transport = mailSession.getTransport();
//        MimeMessage message = new MimeMessage(mailSession);
//        message.setSubject("HTML  mail with images");
//        message.setFrom(new InternetAddress("me@sender.com"));
//        message.setContent("<h1>Hello world</h1>", "text/html");
//        message.addRecipient(Message.RecipientType.TO,
//             new InternetAddress("you@receiver.com"));
        
        Session session = Session.getInstance(getProps(), null);

        try {
            MimeMessage message = new MimeMessage(session);

            try {
//                message.setContent(this, mensaje);
                message.setFrom(new InternetAddress("no_responder@mca.gov.py", "Sistema de Reclamos Online M.C.A."));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Correos.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("CORREO!1: ");
            message.setSubject(asunto);
            message.setSentDate(new Date());
           // message.setText(mensaje);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(htmlMensaje, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);
 
            // second part (the image)
            //   ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            //  String urlImagen = ((ServletContext) ctx.getContext()).getRealPath("/resources/images/logo_3.jpg");

         //   System.out.println("URLIMA: " + urlImagen);
            messageBodyPart = new MimeBodyPart();

            DataSource fds;
            fds = new URLDataSource(new URL("http://appserver.mca.gov.py/reclamosmca/faces/resources/images/logo_3.jpg"));
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            message.saveChanges();

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            // Send message
            Transport tr = session.getTransport("smtp");
            tr.connect(getServidorSMTP(), getUsuario(), getPassword());

            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

//            Store store = session.getStore("imap");
//            store.connect(getServidorSMTP(), getUsuario(), getPassword());
//            Folder folder = store.getFolder("Enviados");
//            folder.open(Folder.READ_WRITE);
//            message.setFlag(Flag.SEEN, true);
//            folder.appendMessages(new Message[]{message});
//            store.close();
            return "OK";
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Correos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the servidorSMTP
     */
    public String getServidorSMTP() {
        return servidorSMTP;
    }

    /**
     * @param servidorSMTP the servidorSMTP to set
     */
    public void setServidorSMTP(String servidorSMTP) {
        this.servidorSMTP = servidorSMTP;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the props
     */
    public Properties getProps() {
        return props;
    }

    /**
     * @param props the props to set
     */
    public void setProps(Properties props) {
        this.props = props;
    }
}
