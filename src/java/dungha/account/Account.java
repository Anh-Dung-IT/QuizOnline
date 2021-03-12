package dungha.account;

import java.io.Serializable;

public class Account implements Serializable {

    private String email;
    private String password;
    private String name;
    private boolean isAdmin;
    private boolean status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" + "email=" + email + ", password=" + password + ", name=" + name + ", isAdmin=" + isAdmin + ", status=" + status + '}';
    }
}
