package com.example.demo.observer_reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.demo.service.EmailSettingsService;
import com.example.demo.service.ReservationService;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


@Component
public class NotifyViaEmail implements Observer {

    private final ReservationService reservationService;
    private final EmailSettingsService emailSettingsService;

    @Autowired
    private JavaMailSender mailSender;

    public NotifyViaEmail(ReservationService reservationService, EmailSettingsService emailSettingsService) {
        this.reservationService = reservationService;
        this.emailSettingsService = emailSettingsService;
    }

    @Override
    public void update(ReservationState obs) {
        int id = ((ReservationStateImpl) obs).getId();
        String status = ((ReservationStateImpl) obs).getState();

        String to = reservationService.getReservation(id).getClient().getEmail();
        String name = reservationService.getReservation(id).getClient().getName();
        String subject = "Reservation";
        String color = status.equals("accepted") ? "#28a745;" : "#dc3545;";

        String htmlTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"fr\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Nunito+Sans:ital,opsz,wght@0,6..12,200..1000;1,6..12,200..1000&display=swap\" rel=\"stylesheet\">\n"
                +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body style='font-family: \"Nunito Sans\", sans-serif; padding:10px 5px 30px 5px; background:#cfff04'>\n"
                +
                "    <div style=\" font-size: 30px;\n" +
                "    text-align: center;\n" +
                "    color: #ffffff;\n" +
                "    margin-bottom: 20px;\n" +
                "    margin: 10px 0\">SaFOX!</div>\n" +
                "\n" +
                "    <div style=\"font-family: Arial, sans-serif; max-width: 550px; padding:10px 20px 20px 20px;background: #F6F8FC;\n"
                +
                "    box-shadow: 0 0 1px #ddd;\n" +
                "\n" +
                "    border-radius: 5px;margin:auto\">\n" +
                "        <h2>Hello, {{ $name }}</h2>\n" +
                "        <p>We hope this email finds you in good health.</p>\n" +
                "        <p>{{$message_}}</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        String text = htmlTemplate.replace("{{ $name }}", name).replace("{{$message_}}",
                "Your reservation status is: <span style='color : "+color+"'>" + status + "</span>");

        String from = "SaFOX" + "<" + emailSettingsService.getEmailSettings().getUsername() + ">";
        sendEmail(from, to, subject, text);
    }

    public void sendEmail(String from, String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            // set content
            message.setContent(text, "text/html");
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
