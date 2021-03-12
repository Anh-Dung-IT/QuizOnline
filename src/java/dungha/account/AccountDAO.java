package dungha.account;

import dungha.utils.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

public class AccountDAO implements Serializable {

    private static final int SUCCESS = 1;

    public boolean checkEmailExist(String email) throws SQLException, NamingException {

        String sql = "select name from Account where email = ?";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public boolean insert(Account account) throws SQLException, NamingException {

        String sql = "insert Account (email, password, name, isAdmin, status) values (?, ?, ?, ?, ?)";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getName());
            ps.setBoolean(4, account.isIsAdmin());
            ps.setBoolean(5, account.isStatus());

            int rs = ps.executeUpdate();

            if (rs == SUCCESS) {
                return true;
            }
        }

        return false;
    }

    public Account checkLogin(String email, String password) throws SQLException, NamingException {

        Account account = null;

        String sql = "select email, name, isAdmin from Account where email = ? and password = ? and status = 1";

        try (Connection con = DBAccess.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();

                account.setEmail(rs.getString("email"));
                account.setName(rs.getString("name"));
                account.setIsAdmin(rs.getBoolean("isAdmin"));
            }
        }

        return account;
    }
}
