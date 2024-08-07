package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private Integer id;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
