package fersa;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class Mailer {
    private static Mailer mailer_instance;
    private Session session;
    private Popup popup;

    private Mailer() {
        popup = new Popup();
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("e8db4aa7cd2dc8", "9f42156852dda7");
            }
        });
    }

    public static Mailer getInstance() {
        if (mailer_instance == null) {
            mailer_instance = new Mailer();
        }
        return mailer_instance;
    }

    private void sendMessage(Message message, String msg) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public void sendDeleteVisitEmail(String emailSender, String emailReceiver, String usernameSender, int idApartment,
                                     LocalDate date, LocalTime time, boolean senderIsLessor) {
        String msg;

        if (senderIsLessor) {
            String mailText = "Il locatore %s (email: %s) ha cancellato la visita prenotata per l'appartamento " +
                    "con codice id #%s (data/ora visita: %s %s)";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(idApartment), date.toString(),
                    time.toString());
        }
        else {
            String mailText = "L'utente %s (email: %s) ha cancellato la visita prenotata per l'appartamento " +
                    "con codice id #%s (data/ora visita: %s %s)";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(idApartment), date.toString(),
                    time.toString());
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@fersa.it"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
            message.setSubject("Avviso cancellazione visita");

            sendMessage(message, msg);
        } catch (MessagingException e){
            e.printStackTrace();
            popup.showEmailErrorPopup();
            sendDeleteVisitEmail(emailSender, emailReceiver, usernameSender, idApartment, date, time, senderIsLessor);
        }
    }

    public void sendModifyVisitEmail(String emailSender, String emailReceiver, String usernameSender, int idApartment,
                                     LocalDate dateVisit, LocalTime timeVisit, LocalDate modDate, LocalTime modTime,
                                     boolean senderIsLessor) {
        String msg;
        if (senderIsLessor) {
            String mailText = "Il locatore %s (email: %s) ha modificato la visita prenotata per l'appartamento " +
                    "con codice id #%s\nData/ora visita di prenotazione: %s %s\nData/ora visita modificata: %s %s";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(idApartment),
                    dateVisit.toString(), timeVisit.toString(), modDate.toString(), modTime.toString());
        }
        else {
            String mailText = "L'utente %s (email: %s) ha modificato la visita prenotata per l'appartamento " +
                    "con codice id #%s\nData/ora visita di prenotazione: %s %s\nData/ora visita modificata: %s %s";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(idApartment),
                    dateVisit.toString(), timeVisit.toString(), modDate.toString(), modTime.toString());
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@fersa.it"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
            message.setSubject("Avviso modifica visita");

            sendMessage(message, msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            popup.showEmailErrorPopup();
            sendModifyVisitEmail(emailSender, emailReceiver, usernameSender, idApartment, dateVisit, timeVisit, modDate,
                    modTime, senderIsLessor);
        }
    }

    public void sendMaintenanceReqRespondEmail(String emailSender, String emailReceiver, String usernameSender,
                                               int apartmentId, LocalDate dateRequest, LocalTime timeRequest,
                                               boolean isAccepted) {
        String msg;
        if (isAccepted) {
            String mailText = "Il locatore %s (email: %s) ha accettato la richiesta di manutenzione prenotata per " +
                    "l'appartamento con codice id #%s richiesta in data %s alle ore %s";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(apartmentId),
                    dateRequest.toString(), timeRequest.toString());
        }
        else{
            String mailText = "Il locatore %s (email: %s) ha rifiutato la richiesta di manutenzione prenotata per " +
                    "l'appartamento con codice id #%s richiesta in data %s alle ore %s";
            msg = String.format(mailText, usernameSender, emailSender, String.valueOf(apartmentId),
                    dateRequest.toString(), timeRequest.toString());
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@fersa.it"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
            message.setSubject("Avviso richiesta di manutenzione");
            sendMessage(message, msg);
        }  catch (MessagingException e) {
            e.printStackTrace();
            popup.showEmailErrorPopup();
            sendMaintenanceReqRespondEmail(emailSender, emailReceiver, usernameSender, apartmentId, dateRequest,
                    timeRequest, isAccepted);
        }
    }
}
