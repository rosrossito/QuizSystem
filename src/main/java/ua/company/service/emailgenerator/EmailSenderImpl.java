package ua.company.service.emailgenerator;

import org.apache.log4j.Logger;
import ua.company.controller.config.AppManager;
import ua.company.service.logger.MyLogger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * EmailSenderImpl.java -
 *
 * @author Ruslan Omelchenko
 * @version 1.0 14.01.2018
 */
public class EmailSenderImpl implements EmailSender{
    private static final Logger LOGGER = MyLogger.getLOGGER(EmailSenderImpl.class);
    private static final String subject = "JavaQuiz. Notification about test result.";
    private String emailTo;
    private String message;

    public EmailSenderImpl(String emailTo) {
        this.emailTo = emailTo;
    }

    @Override
    public void sendEmail(String name, double score) throws MessagingException {
        LOGGER.info("Sending email to " + name +"...");

        Authenticator auth = new MyAuthenticator(AppManager.getInstance().getProperty(AppManager.getEmailLogin()),
                AppManager.getInstance().getProperty(AppManager.getEmailPassword()));

        Properties props = System.getProperties();
        props.put("mail.smtp.host", AppManager.getInstance().getProperty(AppManager.getSmtpHost()));
        props.put("mail.smtp.port", AppManager.getInstance().getProperty(AppManager.getSmtpPort()));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", AppManager.getInstance().getProperty(AppManager.getENCODING()));
        Session session = Session.getDefaultInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(AppManager.getInstance().getProperty(AppManager.getEmailAdress())));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        msg.setSubject(subject);

        message = "<i>������� ���, " + name + "!</i><br><br>" +
                "<i>�� ������� ���� �� ���� JavaQuiz!</i><br><br>" +
                "<i>���, �� �� �������: <b>" + score + "</b>.</i><br><br>" +
                "<i>������ �� ��, �� ������������ ����� �������.</i><br><br>" +
                "<b><font color=blue>� ���������� �����������, ������� JavaQuiz </font></b>";

        msg.setContent(message, "text/html; charset=UTF-8");
        Transport.send(msg);

    }

    class MyAuthenticator extends Authenticator {
        private String user;
        private String password;

        MyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            String user = this.user;
            String password = this.password;
            return new PasswordAuthentication(user, password);
        }
    }
}
