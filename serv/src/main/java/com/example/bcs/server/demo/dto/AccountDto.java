package com.example.bcs.server.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AccountDto {

    private Long id;

    @NotEmpty(message = "Account name is not provided")
    private String name;

    @JsonProperty("created_at")
    private String createdAt;

    @NotEmpty(message = "Account email is not provided")
    @Email
    private String email;

    public AccountDto() {
    }

    public AccountDto(long id, String name, String createdAt, String email) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
