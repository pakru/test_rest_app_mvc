package com.example.bcs.client.demo.csv;

public class CsvData {

    private String name;

    private String email;

    public CsvData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CsvData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
