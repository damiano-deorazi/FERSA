package fersa;

import java.time.LocalDate;
import java.time.LocalTime;

public class MailModifyVisitThread implements Runnable {
    private String emailSender;
    private String emailReceiver;
    private String usernameSender;
    private int idApartment;
    private LocalDate visitDate, modDate;
    private LocalTime visitTime, modTime;
    private boolean isLessor;
    private Mailer mailer = Mailer.getInstance();

    public MailModifyVisitThread(String emailSender, String emailReceiver, String usernameSender, int idApartment,
                                 LocalDate visitDate, LocalTime visitTime, LocalDate modDate, LocalTime modTime,
                                 boolean isLessor) {
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
        this.usernameSender = usernameSender;
        this.idApartment = idApartment;
        this.visitDate = visitDate;
        this.modDate = modDate;
        this.visitTime = visitTime;
        this.modTime = modTime;
        this.isLessor = isLessor;
    }

    @Override
    public void run() {
        mailer.sendModifyVisitEmail(emailSender, emailReceiver, usernameSender, idApartment,
        visitDate, visitTime, modDate, modTime, isLessor);
    }
}
