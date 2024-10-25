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
    private boolean privado;

    public Grupo(String nombre, String contrasena, boolean privado) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.participantes = new ArrayList<>();
        this.mensajes = new ArrayList<>();
        this.privado = privado;
    }
    public Grupo(String nombre, List<Usuario> participantes, boolean privado) {
        this.nombre = nombre;
        this.contrasena = null;
        this.participantes = participantes;
        this.mensajes = new ArrayList<>();
        this.privado = privado;
    }
}
