package dungha.account;

import java.io.Serializable;

public class AccountError implements Serializable {

    private String emailNotMatch;
    private String emailExist;
    private String passwordConfirmError;

    public String getEmailNotMatch() {
        return emailNotMatch;
    }

    public void setEmailNotMatch(String emailNotMatch) {
        this.emailNotMatch = emailNotMatch;
    }

    public String getPasswordConfirmError() {
        return passwordConfirmError;
    }

    public void setPasswordConfirmError(String passwordConfirmError) {
        this.passwordConfirmError = passwordConfirmError;
    }

    public String getEmailExist() {
        return emailExist;
    }

    public void setEmailExist(String emailExist) {
        this.emailExist = emailExist;
    }
}
