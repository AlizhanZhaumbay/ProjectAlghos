package alghosproject.models;

import java.sql.Date;

public class Comment {
    private long id;
    private String message;
    private Date created_at;
    private long user_id;
    private long post_id;

    public Comment(String message, Date created_at, long user_id, long post_id) {
        this.message = message;
        this.created_at = created_at;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date created_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }
}
