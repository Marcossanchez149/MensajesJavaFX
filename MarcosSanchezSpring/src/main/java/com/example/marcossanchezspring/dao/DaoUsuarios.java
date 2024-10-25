package com.example.marcossanchezspring.dao;


import com.example.marcossanchezspring.domain.modelo.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoUsuarios {

    private final DataBase dataBase;

    public DaoUsuarios(DataBase db) {
        this.dataBase = db;
    }

    public boolean anadirUsuario(Usuario usuario) {
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

    public Usuario getUsuarioByUsername(String s) {
     return dataBase.loadUsuarios().stream().filter(usuario -> usuario.getUserName().equals(s)).findAny().orElse(null);
    }
}
