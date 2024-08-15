package csc180.perez.diego.stockappjavafx.Model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String userName;
    private int age;

    public Person(String firstName, String lastName, String phoneNumber, int age, String email, String password, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
