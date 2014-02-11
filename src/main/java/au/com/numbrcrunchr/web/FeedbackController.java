package au.com.numbrcrunchr.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@ManagedBean
public class FeedbackController {
    private String feedbackComment;
    private String name;
    private String email;

    public void sendFeedback(ActionEvent event) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("NumbrCrunchr feedback from " + name);
        mailMessage.setText(feedbackComment);
        mailMessage.setFrom(email);
        mailMessage.setTo("yourpropertyevaluator@gmail.com");

        mailSender.send(mailMessage);
    }

    @ManagedProperty(value = "#{mailSender}")
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setFeedbackComment(String feedback) {
        this.feedbackComment = feedback;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
