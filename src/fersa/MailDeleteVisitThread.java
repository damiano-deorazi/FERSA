package fersa;

import java.time.LocalDate;
import java.time.LocalTime;

public class MailDeleteVisitThread implements Runnable {
    private String emailSender;
    private String emailReceiver;
    private String usernameSender;
    private int idApartment;
    private LocalDate visitDate;
    private LocalTime visitTime;
    private boolean isLessor;
    private Mailer mailer = Mailer.getInstance();

    public MailDeleteVisitThread(String emailSender, String emailReceiver, String usernameSender, int idApartment,
                                 LocalDate visitDate, LocalTime visitTime, boolean isLessor) {
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
        this.usernameSender = usernameSender;
        this.idApartment = idApartment;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.isLessor = isLessor;
    }

    @Override
    public void run() {
        mailer.sendDeleteVisitEmail(emailSender, emailReceiver, usernameSender, idApartment,
                visitDate, visitTime, isLessor);
    }
}
