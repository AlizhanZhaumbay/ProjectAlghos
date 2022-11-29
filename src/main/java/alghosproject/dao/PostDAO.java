package alghosproject.dao;

import alghosproject.collections.MyArrayList;
import alghosproject.models.Comment;
import alghosproject.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostDAO {
    private static Connection connection;


    public void addPost(Post post) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                        ("INSERT INTO post(description,date,user_id,name) VALUES (?,?,?,?)");
        ps.setString(1,post.getDescription());
        ps.setDate(2,post.getDate());
        ps.setLong(3,post.getUser_id());
        ps.setString(4,post.getName());

        ps.executeUpdate();
    }

    public Post getPost(long post_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Post WHERE id = ?");
        ps.setLong(1,post_id);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        return setProperties(resultSet);

    }

    public MyArrayList<Post> getPostsOfUser(long user_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Post WHERE user_id = ?");
        ps.setLong(1,user_id);

        MyArrayList<Post> posts = new MyArrayList<>();
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            posts.add(setProperties(resultSet));
        }

        return posts;
    }

    public MyArrayList<Comment> getComments(long post_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Comment WHERE post_id = ?");
        ps.setLong(1,post_id);

        MyArrayList<Comment> comments = new MyArrayList<>();
        ResultSet resultSet = ps.executeQuery();


        while(resultSet.next()){
            long comment_id = resultSet.getLong("id");
            String message = resultSet.getString("message");
            Date created_at = resultSet.getDate("date");
            long user_id = resultSet.getLong("user_id");
            comments.add(new Comment(message,created_at,user_id,post_id));
        }

        return comments;
    }

    public void addComment(Comment comment) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Comment(message,date,user_id,post_id) VALUES (?,?,?,?)");
        ps.setString(1,comment.getMessage());
        ps.setDate(2,comment.created_at());
        ps.setLong(3,comment.getUser_id());
        ps.setLong(4,comment.getPost_id());

        ps.executeUpdate();
    }

    public long getLikesOfPost(long id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT likes from post where id = ?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("likes");
    }

    public void addLike(long id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE POST SET likes = likes + 1 WHERE id = ?");
        preparedStatement.setLong(1,id);
        preparedStatement.executeUpdate();
    }




    public static Post setProperties(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String description = resultSet.getString("description");
        Date created_at = resultSet.getDate("date");
        long user_id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");

        return new Post(id,description,created_at,user_id,name);
    }

    static {
        connection = DAOLoader.getConnection();
    }
}
