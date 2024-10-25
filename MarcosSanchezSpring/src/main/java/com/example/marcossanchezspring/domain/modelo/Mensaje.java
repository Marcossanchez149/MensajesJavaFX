package com.example.marcossanchezspring.domain.modelo;

import lombok.Data;

@Data
public class Mensaje {
    private String contenido;
    private Usuario usuario;

    public Mensaje(String contenido,Usuario usuario) {
        this.contenido = contenido;
        this.usuario = usuario;
    }

}
