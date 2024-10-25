package com.example.marcossanchezspring.ui;


import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.example.marcossanchezspring.domain.service.UsuarioService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministracionUsuarios {
    private UsuarioService usuarioService;

    public AdministracionUsuarios(UsuarioService us) {
        try {
            usuarioService = us;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean comprobarUsuario(Usuario usuario) {
        return usuarioService.comprobarUsuario(usuario);
    }

    public boolean crearUsuario(Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    public List<Usuario> buscarUsuariosPorNombres(String nombresUsuariosStr) {
        return usuarioService.buscarUsuariosPorNombres(nombresUsuariosStr);
    }
}
