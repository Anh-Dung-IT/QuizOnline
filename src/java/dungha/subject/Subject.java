package dungha.subject;

import java.io.Serializable;

public class Subject implements Serializable {

    private int subjectID;
    private String subjectCode;
    private String name;
    private boolean status;

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectID=" + subjectID + ", subjectCode=" + subjectCode + ", name=" + name + ", status=" + status + '}';
    }
}
