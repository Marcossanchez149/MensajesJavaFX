package com.example.marcossanchezspring.domain.modelo;

import lombok.Data;

@Data
public class Usuario {
    private String email;
    private String userName;
    private String password;

    public Usuario(String user, String  email, String password) {
        this.email = email;
        this.userName = user;
        this.password = password;
    }

}
