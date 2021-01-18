package pl.perlaexport.filmmap.user.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import pl.perlaexport.filmmap.security.config.AuthProperties;
import pl.perlaexport.filmmap.user.model.UserEntity;

@Component
public class MailForwarder {

    private final MailSender mailSender;
    private final AuthProperties authProperties;

    @Autowired
    public MailForwarder(MailSender mailSender, AuthProperties authProperties) {
        this.mailSender = mailSender;
        this.authProperties = authProperties;
    }

    public void sendRegistrationMail(String token, UserEntity user) {
        mailSender.send(constructRegistrationEmail( token, user));
    }

    public void sendPasswordMail(String token, UserEntity user) {
        mailSender.send(constructPasswordEmail( token, user));
    }

    private SimpleMailMessage constructRegistrationEmail(String token, UserEntity user) {
        String message = "Click link to confirm email: ";
        String url = authProperties.getMail().getContext() + "/user/confirmRegistration?userId=" +
                user.getId() + "&token=" + token;
        return constructEmail(message + " \r\n" + url, user, "Confirm registration");
    }

    private SimpleMailMessage constructPasswordEmail(String token, UserEntity user) {
        String message = "Click link to reset password: ";
        String url = authProperties.getMail().getContext() + "/user/resetPassword?userId=" +
                user.getId() + "&token=" + token;
        return constructEmail(message + " \r\n" + url, user, "Reset password");
    }

    private SimpleMailMessage constructEmail(String body,
                                             UserEntity user, String subject) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }
}
