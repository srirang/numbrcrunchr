package au.com.numbrcrunchr.web;

import java.io.Serializable;

public class VersionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String versionNumber;
    private String contactEmail;

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }
}
