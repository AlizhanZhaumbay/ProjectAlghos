package alghosproject.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Post {
    private long id;
    private String description;
    private Date date;
    private long user_id;
    private String name;
    private List<Comment> comments;
    private long likes;


    public Post(long id, String description, Date date, long user_id, String name, long likes) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.user_id = user_id;
        this.name = name;
        this.likes = likes;
    }

    public Post(String description, Date date, long user_id, String name, long likes) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (user_id != post.user_id) return false;
        if (likes != post.likes) return false;
        if (!Objects.equals(description, post.description)) return false;
        if (!Objects.equals(date, post.date)) return false;
        if (!Objects.equals(name, post.name)) return false;
        return Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (user_id ^ (user_id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (int) (likes ^ (likes >>> 32));
        return result;
    }

    @Override
    public String toString() {
        if(date == null) date = new Date(2022,11,30);
        return description + "\n" + "Created at: " + date.toString() + " likes: " + likes + "\n";
    }
}
