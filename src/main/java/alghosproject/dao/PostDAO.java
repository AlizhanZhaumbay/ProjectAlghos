package alghosproject.dao;

import alghosproject.models.Post;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostDAO {
    private static Connection connection;


    public void addPost(Post post) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO post(description,date,user_id) VALUES (?,?,?)");
        ps.setString(1,post.getDescription());
        ps.setDate(2,post.getDate());
        ps.setLong(3,post.getUser_id());

        ps.executeUpdate();
    }

    public List<Post> getPostsOfUser(long user_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Post WHERE user_id = ?");
        ps.setLong(1,user_id);

        List<Post> posts = new LinkedList<>();
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            posts.add(setProperties(resultSet));
        }

        return posts;
    }

    public static Post setProperties(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String description = resultSet.getString("description");
        Date created_at = resultSet.getDate("date");
        long user_id = resultSet.getLong("user_id");

        return new Post(id,description,created_at,user_id);
    }

    static {
        connection = DAOLoader.getConnection();
    }
}
