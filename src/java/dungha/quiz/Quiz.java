package dungha.quiz;

import java.io.Serializable;

public class Quiz implements Serializable {

    private int quizID;
    private int subjectID;
    private int limited_Min;
    private int totalQuestion;
    private boolean status;

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getLimited_Min() {
        return limited_Min;
    }

    public void setLimited_Min(int limited_Min) {
        this.limited_Min = limited_Min;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Quiz{" + "quizID=" + quizID + ", subjectID=" + subjectID + ", limited_Min=" + limited_Min + ", totalQuestion=" + totalQuestion + ", status=" + status + '}';
    }
}
