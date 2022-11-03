package alghosproject.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private long id;
    private String description;
    private Date date;
    private long user_id;
    private String name;
    private List<Comment> comments;
    private long likes;


    public Post(long id, String description, Date date, long user_id, String name) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.user_id = user_id;
        this.name = name;
        this.likes = likes;
    }

    public Post(String description, Date date, long user_id, String name) {
        this.description = description;
        this.date = date;
        this.user_id = user_id;
        this.name = name;
        this.likes = likes;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        if(comments == null) comments = new ArrayList<>();
        comments.add(comment);
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return description + "\n" + "Created at: " + date.toString() + "\n" + likes + " - likes";
    }
}
