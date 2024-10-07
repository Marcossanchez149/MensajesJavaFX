package org.example.trabajo.Domain.Modelo;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Mensaje {
    private String contenido;
    private Usuario usuario;

    public Mensaje(String contenido,Usuario usuario) {
        this.contenido = contenido;
        this.usuario = usuario;
    }

}
