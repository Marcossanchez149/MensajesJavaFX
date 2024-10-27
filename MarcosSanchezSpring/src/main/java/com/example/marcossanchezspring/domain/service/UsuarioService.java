package com.example.marcossanchezspring.domain.service;


import com.example.marcossanchezspring.dao.DaoUsuarios;
import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppUsuarios;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import io.vavr.control.Either;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioService {
    private final DaoUsuarios controlUsuarios;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(DaoUsuarios controlUsuarios, PasswordEncoder passwordEncoder) {
        this.controlUsuarios = controlUsuarios;
        this.passwordEncoder = passwordEncoder;
    }

    public Either<ErrorApp,Boolean>  comprobarUsuario(Usuario usuario) {
        return controlUsuarios.getUsuarioComprobacion(usuario).flatMap(usuarioDB -> {
            if (passwordEncoder.matches(usuario.getPassword(), usuarioDB.getPassword())) {
                return Either.right(true);
            } else {
                return Either.left(ErrorAppUsuarios.PASSWORD_INCORRECTA);
            }
        });
    }

    public Either<ErrorApp,Boolean> crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return controlUsuarios.anadirUsuario(usuario);
    }

    public Either<ErrorApp,List<Usuario>>  buscarUsuariosPorNombres(String nombresUsuariosStr) {
        List<String> nobresUsuariossep = Arrays.stream(nombresUsuariosStr.split(",")).toList();
        List<Usuario> usuariosToReturn = new ArrayList<>();
        for (String s : nobresUsuariossep) {
            usuariosToReturn.add(controlUsuarios.getUsuarioByUsername(s).get());
        }
        return usuariosToReturn.isEmpty() ? Either.left(ErrorAppUsuarios.USUARIO_NO_ENCONTRADO) : Either.right(usuariosToReturn);
    }
}
