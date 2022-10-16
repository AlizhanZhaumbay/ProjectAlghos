package alghosproject.dao;

import alghosproject.exceptions.UserNotFoundException;
import alghosproject.models.User;
import alghosproject.project.Gender;

import java.sql.*;


public class UserDAO {
    private static Connection connection;

    public void addUser(User user) throws SQLException {
        PreparedStatement prs = connection.prepareStatement(
                "INSERT INTO Account(login,password,name,age,email,gender,phone_number) " +
                        "VALUES (?,?,?,?,?,?,?)");

        prs.setString(1, user.getLogin());
        prs.setString(2, user.getPassword());
        prs.setString(3, user.getName());
        prs.setInt(4, user.getAge());
        prs.setString(5, user.getEmail());
        prs.setString(6, user.getGender().toString());
        prs.setString(7, user.getPhone_number());

        prs.executeUpdate();
    }

    public User getUser(long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return setProperties(resultSet);
    }

    public User getUser(String login) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account WHERE login = ?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return setProperties(resultSet);
    }

    public static User setProperties(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setEmail(resultSet.getString("email"));
        user.setGender(Gender.valueOf(resultSet.getString("gender")));
        user.setPhone_number(resultSet.getString("phone_number"));
        return user;
    }

    public User getUser(String login, String password) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM Account WHERE login = ? AND password = ?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) return null;

        //  If user exists
        return setProperties(resultSet);
    }

    public static long getUserIdByLogin(String login) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT id FROM Account WHERE login = ?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) throw new UserNotFoundException();

        //  If user exists
        return resultSet.getInt("id");
    }

    static {
        connection = DAOLoader.getConnection();
    }

    public void changePassword(String login, String password) throws SQLException{
        PreparedStatement prs = connection.prepareStatement("UPDATE ACCOUNT SET password = ? WHERE login = ?");
        prs.setString(1,password);
        prs.setString(2,login);
        prs.executeUpdate();
    }
}
