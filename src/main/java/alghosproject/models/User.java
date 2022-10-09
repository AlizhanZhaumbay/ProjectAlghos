package alghosproject.models;


import alghosproject.project.Gender;
import alghosproject.project.Post;

import java.util.List;



public class User {
    private long id;
    private String login;
    private String password;
    private String name;
    private int age;
    private String email;
    private Gender gender;
    private String phone_number;

    private List<Post> posts;

    public User(String login, String password, String name, int age, String email, Gender gender, String phone_number) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.phone_number = phone_number;
    }

    public User(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


