package alghosproject.dao;

import alghosproject.exceptions.UserNotFoundException;
import alghosproject.models.User;
import alghosproject.project.Gender;

import java.sql.*;


public class UserDAO {
    private static Connection connection;

    public void addUser(User user) throws SQLException {
        PreparedStatement prs = connection.prepareStatement("INSERT INTO Account VALUES (?,?,?,?,?,?,?,?)");
        long next_id = getNextId();

        prs.setLong(1, next_id);
        prs.setString(2, user.getLogin());
        prs.setString(3, user.getPassword());
        prs.setString(4, user.getName());
        prs.setInt(5, user.getAge());
        prs.setString(6, user.getEmail());
        prs.setString(7, user.getGender().toString());
        prs.setString(8, user.getPhone_number());

        user.setId(next_id);
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
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setEmail(resultSet.getString("email"));
        user.setGender(Gender.valueOf(resultSet.getString("gender")));
        user.setPhone_number(resultSet.getString("phone_number"));
        return user;
    }

    public long getNextId() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM Account");
        int id = 0;
        while (resultSet.next())
            id = resultSet.getInt(1);
        return id + 1;
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
}
