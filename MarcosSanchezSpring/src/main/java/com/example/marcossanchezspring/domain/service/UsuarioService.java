package com.example.marcossanchezspring.domain.service;


import com.example.marcossanchezspring.dao.DaoUsuarios;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioService {
    private final DaoUsuarios controlUsuarios;

    public UsuarioService(DaoUsuarios controlUsuarios) {
        this.controlUsuarios = controlUsuarios;
    }

    public boolean comprobarUsuario(Usuario usuario) {
        return controlUsuarios.getUsuarioComprobacion(usuario) != null;
    }

    public boolean crearUsuario(Usuario usuario) {
        return controlUsuarios.anadirUsuario(usuario);
    }

    public List<Usuario> buscarUsuariosPorNombres(String nombresUsuariosStr) {
        List<String> nobresUsuariossep = Arrays.stream(nombresUsuariosStr.split(",")).toList();
        List<Usuario> usuariosToReturn = new ArrayList<>();
        for (String s : nobresUsuariossep) {
            usuariosToReturn.add(controlUsuarios.getUsuarioByUsername(s));
        }
        return usuariosToReturn;
    }
}
