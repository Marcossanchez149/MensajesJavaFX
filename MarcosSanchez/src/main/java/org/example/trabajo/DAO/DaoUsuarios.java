package org.example.trabajo.DAO;

import org.example.trabajo.Domain.Modelo.Usuario;

import java.util.List;

public class DaoUsuarios {

    private final DataBase dataBase;

    public DaoUsuarios() throws Exception {
        this.dataBase = new DataBase();
    }

    public boolean añadirUsuario(Usuario usuario) {
        List<Usuario> usuarios = dataBase.loadUsuarios();
        usuarios.add(usuario);
        return dataBase.saveUsuarios(usuarios);
    }

    public Usuario getUsuarioComprobacion(Usuario usuario) {
        return dataBase.loadUsuarios().stream().filter(user -> user.getUserName().
                        equals(usuario.getUserName() ) &&
                        user.getPassword().equals(usuario.getPassword()) &&
                        user.getEmail().equals(usuario.getEmail()))
                .findFirst().orElse(null);
    }

}
