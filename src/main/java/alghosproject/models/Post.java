package alghosproject.models;

import java.sql.Date;
import java.time.LocalDate;

public class Post {
    private long id;
    private String description;
    private Date date;
    private long user_id;


    public Post(long id, String description, Date date, long user_id){
        this.id = id;
        this.description = description;
        this.date = date;
        this.user_id = user_id;
    }

    public Post(String description, Date date, long user_id) {
        this.description = description;
        this.date = date;
        this.user_id = user_id;
    }

    public Post(){}

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

    @Override
    public String toString() {
        return description + "\n" + "Created at: " + date.toString() + "\n";
    }
}
