package org.example.trabajo.Domain.Service;

import org.example.trabajo.DAO.DaoUsuarios;
import org.example.trabajo.Domain.Modelo.Usuario;

public class UsuarioService {
    private DaoUsuarios controlUsuarios;

    public UsuarioService() throws Exception {
        this.controlUsuarios = new DaoUsuarios();
    }

    public boolean comprobarUsuario(Usuario usuario) {
        return controlUsuarios.getUsuarioComprobacion(usuario) != null;
    }

    public boolean crearUsuario(Usuario usuario) {
        return controlUsuarios.añadirUsuario(usuario);
    }
}
