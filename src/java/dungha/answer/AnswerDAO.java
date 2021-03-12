package dungha.answer;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class AnswerDAO implements Serializable {

    public List<Answer> getByQuestionID(int questionID) throws SQLException, NamingException {

        List<Answer> list = new ArrayList<>();

        String sql = "select answerID, questionID, answerContent, isCorrect from Answer where questionID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Answer answer = new Answer();

                answer.setAnswerID(rs.getInt("answerID"));
                answer.setQuestionID(rs.getInt("questionID"));
                answer.setAnswerContent(rs.getString("answerContent"));
                answer.setIsCorrect(rs.getBoolean("isCorrect"));

                list.add(answer);
            }
        }

        return list;
    }

    public boolean insert(Answer answer) throws SQLException, NamingException {

        String sql = "insert Answer (questionID, answerContent, isCorrect, status) values (?, ?, ?, ?)";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, answer.getQuestionID());
            ps.setString(2, answer.getAnswerContent());
            ps.setBoolean(3, answer.isIsCorrect());
            ps.setBoolean(4, answer.isStatus());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean update(Answer answer) throws SQLException, NamingException {

        String sql = "update Answer set answerContent = ?, isCorrect = ? where answerID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, answer.getAnswerContent());
            ps.setBoolean(2, answer.isIsCorrect());
            ps.setInt(3, answer.getAnswerID());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean checkAnswerCorrect(int answerID) throws SQLException, NamingException {

        String sql = "select isCorrect from Answer where answerID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, answerID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("isCorrect");
            }
        }

        return false;
    }
}
