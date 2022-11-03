package alghosproject.dao;

import alghosproject.models.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubscriptionSubscriberDAO {
    private static Connection connection;
    private static UserDAO userDAO;

    public List<User> getSubscriptions(long id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM resident_subscriber WHERE subscriber_id = ?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> subscriptions = new LinkedList<>();
        while (resultSet.next()){
            User user = userDAO.getUser(resultSet.getInt("resident_id"));
            subscriptions.add(user);
        }
        return subscriptions;
    }

    public List<User> getSubscribers(long id) throws SQLException{
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM resident_subscriber WHERE resident_id = ?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> subscribers = new LinkedList<>();
        while (resultSet.next()){
            User user = userDAO.getUser(resultSet.getInt("subscriber_id"));
            subscribers.add(user);
        }
        return subscribers;
    }

    public void follow(long subscriber_id, long respondent_id) throws SQLException{
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO resident_subscriber VALUES (?, ?)");
        preparedStatement.setLong(1, respondent_id);
        preparedStatement.setLong(2,subscriber_id);
        preparedStatement.executeUpdate();
    }

    public void unfollow(long subscriber_id, long respondent_id) throws SQLException{
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE FROM resident_subscriber WHERE resident_id = ? AND subscriber_id = ?");
        preparedStatement.setLong(1,respondent_id);
        preparedStatement.setLong(2,subscriber_id);
        preparedStatement.executeUpdate();
    }

    public void blockUser(long block_by,long blocked_to) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO BlockUsers VALUES(?,?);");
        preparedStatement.setLong(1,block_by);
        preparedStatement.setLong(2,blocked_to);
        preparedStatement.executeUpdate();

        preparedStatement =
                connection.prepareStatement("DELETE FROM resident_subscriber WHERE resident_id IN(?,?) AND subscriber_id IN (?,?)");
        preparedStatement.setLong(1,block_by);
        preparedStatement.setLong(2,blocked_to);
        preparedStatement.setLong(3,blocked_to);
        preparedStatement.setLong(4,block_by);
        preparedStatement.executeUpdate();
    }

    public boolean checkForSubscribe(long subscriber_id, long respondent_id) throws  SQLException{
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM resident_subscriber WHERE resident_id = ? AND subscriber_id = ?");
        preparedStatement.setLong(1,respondent_id);
        preparedStatement.setLong(2,subscriber_id);
        return preparedStatement.executeQuery().next();

    }

    public boolean checkForBlocking(long block_by, long blocked_to) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM blockusers WHERE block_by = ? AND blocked_to = ?");
        preparedStatement.setLong(1,block_by);
        preparedStatement.setLong(2,blocked_to);
        return preparedStatement.executeQuery().next();
    }

    static {
        connection = DAOLoader.getConnection();
        userDAO = new UserDAO();
    }
}
