package pl.arturzaczek.demo.user;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class UserDAO {

    private List<User> userList = new ArrayList<>(); // poradzic sobie z NPE

    private static DataSource datasource;

    public boolean checkIfUserExist(String eMail) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "SELECT eMail FROM users WHERE eMail = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, eMail);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (SQLException e) {

            }
        }
    }


    public void saveUser(User user) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "INSERT INTO users (firstName, lastName, eMail, passwordHash, birthDate, pesel, phone, preferEmails) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEMail());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getBirthDate());
            ps.setString(6, user.getPesel());
            ps.setString(7, user.getPhone());
            ps.setBoolean(8, user.isPreferEmails());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newUserId = generatedKeys.getInt(1);
                saveUserAddresses(user.getUserAddress(), newUserId, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
            }
        }
    }

    private void saveUserAddresses(UserAddress userAddress, int newUserId, Connection connection) {
//Connection connection1 = null;
        try {
            connection = getConnection();
            String sql = "INSERT INTO useraddresses (userId, city, country, zipCode, street) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, newUserId);
            ps.setString(2, userAddress.getCity());
            ps.setString(3, userAddress.getCountry());
            ps.setString(4, userAddress.getZipCode());
            ps.setString(5, userAddress.getStreet());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Żeby pobrać id wstawionego rekordu, wystarczy dodać drugi parametr (za SQL'em w Stringu)
//           do metody connection.prepareStatement - Statement.RETURN_GENERATED_KEYS.
//        Wtedy po wykonaniu executeUpdate można pobrać ResultSet z metody ps.getGeneratedKeys i dalej
//            normalnie odczytać wartośc id z tego rs'a, tak samo jak ze zwykłego select'a.
//                Na przykładzie aplikacji, którą robiliśmy na zajęciach wyglądałoby to tak:
//        String sqlInsert = "INSERT INTO payout (empno, amount, added) VALUES (?, ?, now())";
//            PreparedStatement psi = connection.prepareStatement(sqlInsert);
//        PreparedStatement psi = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
//        psi.setInt(1, employeeId);
//        psi.setBigDecimal(2, amount);
//        psi.executeUpdate();
//        ResultSet generatedKeys = psi.getGeneratedKeys();
//        if (generatedKeys.next()){
//            System.out.println("Payout id: " + generatedKeys.getInt(1));
//        }
//        Jedną linię wykomentowałem, poniżej zmodyfikowana z drugim paramterem.
//        I 4 ostatnie linie doszły, do wyciągnięcia id.
    }

    private static Connection getConnection() throws SQLException {
        if (datasource == null) {
            String connectionString = "jdbc:mysql://127.0.0.1:3306/cisniemykodzik?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String password = "1234";
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(connectionString);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            basicDataSource.setMaxTotal(5);
            basicDataSource.setInitialSize(3);
            basicDataSource.setMaxWaitMillis(5000);

            datasource = basicDataSource;
        }
        return datasource.getConnection();
    }
}
