package com.example.marcossanchezspring.ui;


import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.example.marcossanchezspring.domain.service.UsuarioService;
import io.vavr.control.Either;
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

    public Either<ErrorApp,Boolean> comprobarUsuario(Usuario usuario) {
        return usuarioService.comprobarUsuario(usuario);
    }

    public Either<ErrorApp, Boolean> crearUsuario(Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    public Either<ErrorApp, List<Usuario>> buscarUsuariosPorNombres(String nombresUsuariosStr) {
        return usuarioService.buscarUsuariosPorNombres(nombresUsuariosStr);
    }
}
