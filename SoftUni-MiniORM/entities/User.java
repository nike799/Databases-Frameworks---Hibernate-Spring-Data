package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Id;

import java.util.Date;

@Entity(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;
    @Column(name ="grade")
    private int grade;

    @Column(name = "registration_date")
    private Date registrationDate;

    public User() {

    }

    public User(String username, int age, Date registrationDate) {
        setUsername(username);
        setAge(age);
        // parse date to sql format
        setRegistrationDate(new java.sql.Date(registrationDate.getTime()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
