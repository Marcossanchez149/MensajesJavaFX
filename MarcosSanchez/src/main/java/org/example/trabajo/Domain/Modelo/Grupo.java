package org.example.trabajo.Domain.Modelo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Grupo {

    private List<Usuario> participantes;
    private String nombre;
    private String contraseña;
    private List<Mensaje> mensajes;

    public Grupo(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.participantes = new ArrayList<>();
        this.mensajes = new ArrayList<>();
    }
}
