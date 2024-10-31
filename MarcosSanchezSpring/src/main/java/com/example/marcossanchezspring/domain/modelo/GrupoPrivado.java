package com.example.marcossanchezspring.domain.modelo;

import java.util.ArrayList;
import java.util.List;

public class GrupoPrivado {

    private final String nombre;
    private final List<Usuario> participantes;
    private final List<Mensaje> mensajes;

    public GrupoPrivado(String nombre, List<Usuario> participantes) {
        this.nombre = nombre;
        this.participantes = participantes;
        this.mensajes = new ArrayList<>();
    }
}
