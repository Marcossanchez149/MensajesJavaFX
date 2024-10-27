package com.example.marcossanchezspring.domain.modelo;

import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email) &&
                Objects.equals(userName, usuario.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, userName);
    }
}
