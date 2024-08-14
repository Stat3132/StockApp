package csc180.perez.diego.stockappjavafx.Model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int age;

    public Person(String firstName, String lastName, String phoneNumber, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
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
}
