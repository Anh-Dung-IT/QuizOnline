package dungha.question;

import java.io.Serializable;
import java.sql.Date;

public class Question implements Serializable {

    private int questionID;
    private int subjectID;
    private String questionContent;
    private Date createDate;
    private boolean status;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Question{" + "questionID=" + questionID + ", subjectID=" + subjectID + ", questionContent=" + questionContent + ", createDate=" + createDate + ", status=" + status + '}';
    }
}
