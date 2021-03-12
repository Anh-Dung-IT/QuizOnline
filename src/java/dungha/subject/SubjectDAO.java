package dungha.subject;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

public class SubjectDAO implements Serializable {

    public Map<Integer, Subject> getAll() throws SQLException, NamingException {

        Map<Integer, Subject> list = new HashMap<>();

        String sql = "select subjectID, subjectCode, name from Subject where status = 1";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();

                subject.setSubjectID(rs.getInt("subjectID"));
                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setName(rs.getString("name"));

                list.put(subject.getSubjectID(), subject);
            }
        }

        return list;
    }

    public Subject getByID(int subjectID) throws SQLException, NamingException {

        Subject subject = null;

        String sql = "select subjectID, subjectCode, name from Subject where subjectID = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, subjectID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subject = new Subject();

                subject.setSubjectID(rs.getInt("subjectID"));
                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setName(rs.getString("name"));
            }
        }

        return subject;
    }
}
