package fersa;

import java.time.LocalDate;
import java.time.LocalTime;

public class MailMaintenanceThread implements Runnable{
    private String emailSender;
    private String emailReciever;
    private String usernameSender;
    private int idApartment;
    private LocalDate requestDate;
    private LocalTime requestTime;
    private boolean isAccepted;
    private Mailer mailer = Mailer.getInstance();

    public MailMaintenanceThread(String emailSender, String emailReciever, String usernameSender, int idApartment,
                                 LocalDate requestDate, LocalTime requestTime, boolean isAccepted) {
        this.emailSender = emailSender;
        this.emailReciever = emailReciever;
        this.usernameSender = usernameSender;
        this.idApartment = idApartment;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.isAccepted = isAccepted;
    }

    @Override
    public void run() {
        mailer.sendMaintenanceReqRespondEmail(emailSender, emailReciever, usernameSender, idApartment, requestDate,
                requestTime, isAccepted);
    }
}
