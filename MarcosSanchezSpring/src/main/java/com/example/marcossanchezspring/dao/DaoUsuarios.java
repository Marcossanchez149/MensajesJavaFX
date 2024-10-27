package com.example.marcossanchezspring.dao;


import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppUsuarios;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

@Repository
public class DaoUsuarios {

    private final DataBase dataBase;

    public DaoUsuarios(DataBase db) {
        this.dataBase = db;
    }

    public Either<ErrorApp,Boolean> anadirUsuario(Usuario usuario) {
        return dataBase.loadUsuarios().flatMap(usuarios -> {
            if (usuarios.stream().anyMatch(u -> u.getUserName().equals(usuario.getUserName()))) {
                return Either.left(ErrorAppUsuarios.USUARIO_YA_EXISTE);
            }
            usuarios.add(usuario);
            return dataBase.saveUsuarios(usuarios);
        });
    }

    public Either<ErrorApp,Usuario> getUsuarioComprobacion(Usuario usuario) {
        return dataBase.loadUsuarios().flatMap(usuarios -> {
            Usuario usuarioDB = usuarios.stream()
                    .filter(u -> u.getUserName().equals(usuario.getUserName()))
                    .findFirst()
                    .orElse(null);
            if (usuarioDB == null) {
                return Either.left(ErrorAppUsuarios.USUARIO_NO_EXISTE);
            }
            return Either.right(usuarioDB);
        });
    }

    public Either< ErrorApp,Usuario> getUsuarioByUsername(String s) {
        return dataBase.loadUsuarios().flatMap(usuarios -> {
            Usuario usuarioDB = usuarios.stream()
                    .filter(u -> u.getUserName().equals(s))
                    .findFirst()
                    .orElse(null);
            if (usuarioDB == null) {
                return Either.left(ErrorAppUsuarios.USUARIO_NO_EXISTE);
            }
            return Either.right(usuarioDB);
        });
    }
}
