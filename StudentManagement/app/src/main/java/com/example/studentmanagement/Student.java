package com.example.studentmanagement;

public class Student {
    private String id;
    private String name;
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    public Student() {
    }

    // Constructor with parameters
    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tên: " + name + ", " +
                "Email: " + email;
    }
}
