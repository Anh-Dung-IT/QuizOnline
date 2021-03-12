package dungha.historyquiz;

import java.io.Serializable;
import java.sql.Date;

public class HistoryQuiz implements Serializable {

    private int historyQuizID;
    private String email;
    private int quizID;
    private int numCorrect;
    private int totalQuestion;
    private Date createDate;

    public int getHistoryQuizID() {
        return historyQuizID;
    }

    public void setHistoryQuizID(int historyQuizID) {
        this.historyQuizID = historyQuizID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "HistoryQuiz{" + "email=" + email + ", quizID=" + quizID + ", numCorrect=" + numCorrect + ", totalQuestion=" + totalQuestion + ", createDate=" + createDate + '}';
    }
}
