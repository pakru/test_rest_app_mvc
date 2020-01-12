package com.example.bcs.server.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AccountDto {

    @NotEmpty(message = "Account name is not provided")
    private String name;

    @NotEmpty(message = "Account email is not provided")
    @Email(message = "Invalid email")
    private String email;

    @JsonProperty("phone_number")
    @NotEmpty(message = "Phone number is not provided")
    @Digits(message = "Phone number should contain digits only", integer = 11, fraction = 0)
    private String phoneNumber;

    private String address;


    public AccountDto(@NotEmpty(message = "Account name is not provided") String name,
                      @NotEmpty(message = "Account email is not provided") @Email(message = "Invalid email") String email,
                      @NotEmpty(message = "Phone number is not provided") @Digits(message = "Phone number should contain digits only", integer = 11, fraction = 0) String phoneNumber,
                      String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
