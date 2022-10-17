package alghosproject.project;

import java.time.LocalTime;

public class Post {
    private long id;
    private String description;
    private LocalTime date;
    private long user_id;

    public Post(String description, LocalTime date, long user_id) {
        this.description = description;
        this.date = date;
        this.user_id = user_id;
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

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
