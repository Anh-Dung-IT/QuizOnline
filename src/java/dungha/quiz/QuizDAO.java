package dungha.quiz;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class QuizDAO implements Serializable {

    public List<Quiz> getQuizAvailable() throws SQLException, NamingException {

        List<Quiz> list = new ArrayList<>();

        String sql = "select quizID, subjectID, limited_Min, totalQuestion from Quiz where status = 1";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Quiz quiz = new Quiz();

                quiz.setQuizID(rs.getInt("quizID"));
                quiz.setSubjectID(rs.getInt("subjectID"));
                quiz.setLimited_Min(rs.getInt("limited_Min"));
                quiz.setTotalQuestion(rs.getInt("totalQuestion"));

                list.add(quiz);
            }
        }

        return list;
    }

    public Quiz getByID(int quizID) throws SQLException, NamingException {

        String sql = "select quizID, subjectID from Quiz where quizID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quizID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Quiz quiz = new Quiz();

                quiz.setQuizID(rs.getInt("quizID"));
                quiz.setSubjectID(rs.getInt("subjectID"));

                return quiz;
            }
        }

        return null;
    }
}
