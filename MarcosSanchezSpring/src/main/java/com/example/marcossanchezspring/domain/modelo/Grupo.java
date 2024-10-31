package com.example.marcossanchezspring.domain.modelo;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Grupo {

    private List<Usuario> participantes;
    private String nombre;
    private String contrasena;
    private List<Mensaje> mensajes;

    public Grupo(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.participantes = new ArrayList<>();
        this.mensajes = new ArrayList<>();
    }

}
