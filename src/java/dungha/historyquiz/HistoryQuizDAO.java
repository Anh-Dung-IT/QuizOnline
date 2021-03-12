package dungha.historyquiz;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class HistoryQuizDAO implements Serializable {

    public boolean insert(HistoryQuiz historyQuiz) throws SQLException, NamingException {

        String sql = "insert History_Quiz (email, quizID, numCorrect, totalQuestion, createDate) values (?, ?, ?, ?, ?)";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, historyQuiz.getEmail());
            ps.setInt(2, historyQuiz.getQuizID());
            ps.setInt(3, historyQuiz.getNumCorrect());
            ps.setInt(4, historyQuiz.getTotalQuestion());
            ps.setDate(5, historyQuiz.getCreateDate());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }

        return false;
    }

    public List<HistoryQuiz> search(String email, int offSet, int rows) throws SQLException, NamingException {

        List<HistoryQuiz> list = new ArrayList<>();

        String sql = "select quizID, numCorrect, totalQuestion, createDate from History_Quiz "
                + "where email = ? order by quizID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setInt(2, offSet);
            ps.setInt(3, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HistoryQuiz historyQuiz = new HistoryQuiz();

                historyQuiz.setQuizID(rs.getInt("quizID"));
                historyQuiz.setNumCorrect(rs.getInt("numCorrect"));
                historyQuiz.setTotalQuestion(rs.getInt("totalQuestion"));
                historyQuiz.setCreateDate(rs.getDate("createDate"));

                list.add(historyQuiz);
            }
        }

        return list;
    }

    public List<HistoryQuiz> search(String email, int subjectID, int offSet, int rows) throws SQLException, NamingException {

        List<HistoryQuiz> list = new ArrayList<>();

        String sql = "select h.quizID, numCorrect, totalQuestion, createDate "
                + "from History_Quiz as h join (select quizID from Quiz where subjectID = ?) as q on h.quizID = q.quizID "
                + "where email = ? order by quizID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectID);
            ps.setString(2, email);
            ps.setInt(3, offSet);
            ps.setInt(4, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HistoryQuiz historyQuiz = new HistoryQuiz();

                historyQuiz.setQuizID(rs.getInt("quizID"));
                historyQuiz.setNumCorrect(rs.getInt("numCorrect"));
                historyQuiz.setTotalQuestion(rs.getInt("totalQuestion"));
                historyQuiz.setCreateDate(rs.getDate("createDate"));

                list.add(historyQuiz);
            }
        }

        return list;
    }

    public int getTotal(String email) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(email) as total from History_Quiz where email = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getTotal(String email, int subjectID) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(email) as total "
                + "from History_Quiz as h join (select quizID from Quiz where subjectID = ?) as q on h.quizID = q.quizID "
                + "where email = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectID);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }
}
