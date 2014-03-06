package au.com.numbrcrunchr.web;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class FeedbackController {
    private String feedbackComment;
    private String name;

    @ManagedProperty(value = "#{versionDetails}")
    private VersionDetails versionDetails;

    public void sendFeedback(ActionEvent event) throws IOException {
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .redirect(
                        "mailto:" + versionDetails.getContactEmail()
                                + "?subject=Feedback on NumbrCrunchr from "
                                + String.valueOf(getName()) + "&body="
                                + String.valueOf(getFeedbackComment()));
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

    public void setVersionDetails(VersionDetails versionDetails) {
        this.versionDetails = versionDetails;
    }

    public VersionDetails getVersionDetails() {
        return versionDetails;
    }
}
