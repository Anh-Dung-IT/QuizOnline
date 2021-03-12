package dungha.question;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class QuestionDAO implements Serializable {

    public List<Question> search(String questionContent, int offSet, int rows) throws SQLException, NamingException {

        List<Question> list = new ArrayList<>();

        String sql = "select questionID, subjectID, questionContent, createDate, status from Question "
                + "where questionContent like ? order by questionID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setInt(2, offSet);
            ps.setInt(3, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setSubjectID(rs.getInt("subjectID"));
                question.setQuestionContent(rs.getString("questionContent"));
                question.setCreateDate(rs.getDate("createDate"));
                question.setStatus(rs.getBoolean("status"));

                list.add(question);
            }
        }

        return list;
    }

    public List<Question> search(String questionContent, boolean status, int subjectID, int offSet, int rows) throws SQLException, NamingException {

        List<Question> list = new ArrayList<>();

        String sql = "select questionID, subjectID, questionContent, createDate, status from Question "
                + "where questionContent like ? and status = ? and subjectID = ? order by questionID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setBoolean(2, status);
            ps.setInt(3, subjectID);
            ps.setInt(4, offSet);
            ps.setInt(5, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setSubjectID(rs.getInt("subjectID"));
                question.setQuestionContent(rs.getString("questionContent"));
                question.setCreateDate(rs.getDate("createDate"));
                question.setStatus(rs.getBoolean("status"));

                list.add(question);
            }
        }

        return list;
    }

    public List<Question> searchByStatus(String questionContent, boolean status, int offSet, int rows) throws SQLException, NamingException {

        List<Question> list = new ArrayList<>();

        String sql = "select questionID, subjectID, questionContent, createDate, status from Question "
                + "where questionContent like ? and status = ? order by questionID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setBoolean(2, status);
            ps.setInt(3, offSet);
            ps.setInt(4, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setSubjectID(rs.getInt("subjectID"));
                question.setQuestionContent(rs.getString("questionContent"));
                question.setCreateDate(rs.getDate("createDate"));
                question.setStatus(rs.getBoolean("status"));

                list.add(question);
            }
        }

        return list;
    }

    public List<Question> searchBySubject(String questionContent, int subjectID, int offSet, int rows) throws SQLException, NamingException {

        List<Question> list = new ArrayList<>();

        String sql = "select questionID, subjectID, questionContent, createDate, status from Question "
                + "where questionContent like ? and subjectID = ? order by questionID offset ? rows fetch next ? rows only";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setInt(2, subjectID);
            ps.setInt(3, offSet);
            ps.setInt(4, rows);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setSubjectID(rs.getInt("subjectID"));
                question.setQuestionContent(rs.getString("questionContent"));
                question.setCreateDate(rs.getDate("createDate"));
                question.setStatus(rs.getBoolean("status"));

                list.add(question);
            }
        }

        return list;
    }

    public int getTotalRecord(String questionContent) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(questionID) as total from Question where questionContent like ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getTotalRecord(String questionContent, boolean status, int subjectID) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(questionID) as total from Question where questionContent like ? and status = ? and subjectID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setBoolean(2, status);
            ps.setInt(3, subjectID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getTotalRecordHasStatus(String questionContent, boolean status) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(questionID) as total from Question where questionContent like ? and status = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setBoolean(2, status);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getTotalRecordHasSubjectID(String questionContent, int subjectID) throws SQLException, NamingException {

        int total = 0;

        String sql = "select COUNT(questionID) as total from Question where questionContent like ? and subjectID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + questionContent + "%");
            ps.setInt(2, subjectID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public boolean changeStatus(int questionID, boolean status) throws SQLException, NamingException {

        String sql = "update Question set status = ? where questionID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, questionID);

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean insert(Question question) throws SQLException, NamingException {

        String sql = "insert Question (subjectID, questionContent, createDate, status) values (?, ?, ?, ?)";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, question.getSubjectID());
            ps.setString(2, question.getQuestionContent());
            ps.setDate(3, question.getCreateDate());
            ps.setBoolean(4, question.isStatus());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }
        return false;
    }

    public int getLastID() throws SQLException, NamingException {

        String sql = "select MAX(questionID) as last_ID from Question";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("last_ID");
            }
        }

        return 0;
    }

    public Question getByID(int questionID) throws SQLException, NamingException {

        Question question = null;

        String sql = "select questionID, subjectID, questionContent from Question where questionID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setSubjectID(rs.getInt("subjectID"));
                question.setQuestionContent(rs.getString("questionContent"));
            }
        }

        return question;
    }

    public boolean update(Question question) throws SQLException, NamingException {

        String sql = "update Question set subjectID = ?, questionContent = ? where questionID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, question.getSubjectID());
            ps.setString(2, question.getQuestionContent());
            ps.setInt(3, question.getQuestionID());

            int rs = ps.executeUpdate();

            if (rs > 0) {
                return true;
            }
        }

        return false;
    }

    public List<Question> getRandom(String rows, int subjectID) throws SQLException, NamingException {

        List<Question> list = new ArrayList<>();

        String sql = "select top " + rows + " questionID, questionContent from Question where subjectID = ? and status = 1 order by NEWID()";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question();

                question.setQuestionID(rs.getInt("questionID"));
                question.setQuestionContent(rs.getString("questionContent"));

                list.add(question);
            }
        }
        return list;
    }
}
