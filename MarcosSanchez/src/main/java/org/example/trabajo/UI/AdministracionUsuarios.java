package org.example.trabajo.UI;


import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Usuario;
import org.example.trabajo.Domain.Service.UsuarioService;

import java.util.List;

public class AdministracionUsuarios {
    private UsuarioService usuarioService;

    public AdministracionUsuarios() {
        try {
            usuarioService = new UsuarioService();
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
}
