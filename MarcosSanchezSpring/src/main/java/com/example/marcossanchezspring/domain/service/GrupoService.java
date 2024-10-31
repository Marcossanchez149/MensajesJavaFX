package com.example.marcossanchezspring.domain.service;


import com.example.marcossanchezspring.dao.DaoGrupos;
import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppGrupos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.GrupoPrivado;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.example.marcossanchezspring.domain.validators.GrupoValidator;
import io.vavr.control.Either;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {
    private final DaoGrupos daoGrupos;
    private final PasswordEncoder passwordEncoder;
    private final GrupoValidator grupoValidator;

    public GrupoService(DaoGrupos dg, PasswordEncoder passwordEncoder, GrupoValidator grupoValidator) {
        daoGrupos = dg;
        this.passwordEncoder = passwordEncoder;
        this.grupoValidator = grupoValidator;
    }

    public Either<ErrorApp,Boolean> crearGrupo(Grupo grupo) {
        return grupoValidator.validateGrupo(grupo).flatMap(valido -> {
            if (valido) {
                if((grupo.getContrasena() != null)){
                    grupo.setContrasena(passwordEncoder.encode(grupo.getContrasena()));
                }
                return daoGrupos.crearGrupo(grupo).map(result -> true);
            } else {
                    return Either.left(ErrorAppGrupos.NOMBRE_NO_VALIDO);
            }
        });
    }

    public Either<ErrorApp, Boolean> anadirUsuarioaGrupo(Grupo grupo, Usuario usuarioIniciadoSesion) {
        usuarioIniciadoSesion.setPassword(passwordEncoder.encode(usuarioIniciadoSesion.getPassword()));
        return daoGrupos.anadirUsuarioaGrupo(grupo,usuarioIniciadoSesion);
    }

    public Either<ErrorApp, Boolean> comprobarEntradaGrupo(Grupo grupo) {
        return daoGrupos.comprobarEntradGrupo(grupo).flatMap(grupoBd -> {
            if (passwordEncoder.matches(grupo.getContrasena(), grupoBd.getContrasena())) {
                return Either.right(true);
            } else {
                return Either.left(ErrorAppGrupos.GRUPO_CONTRASENA_INCORRECTA);
            }
        });
    }

    public Either<ErrorApp, Boolean> enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        usuarioIniciadoSesion.setPassword(passwordEncoder.encode(usuarioIniciadoSesion.getPassword()));
        return daoGrupos.enviarMensaje(grupoSeleccionado,mensaje,usuarioIniciadoSesion);
    }

    public Either<ErrorApp, List<Mensaje>> obtenerMensajes(Grupo grupoSeleccionado) {
        return daoGrupos.obtenerMensajes(grupoSeleccionado);
    }

    public Either<ErrorApp, List<Grupo>> obtenerGruposDeUsuario(Usuario usuarioIniciadoSesion) {
        usuarioIniciadoSesion.setPassword(passwordEncoder.encode(usuarioIniciadoSesion.getPassword()));
        return daoGrupos.getGruposDeUsuario(usuarioIniciadoSesion);
    }

    public Either<ErrorApp,Boolean> crearGrupoPrivado(GrupoPrivado grupoPrivado) {
    }

    public Either<ErrorApp,Boolean> anadirUsuarioaGrupoPrivado(GrupoPrivado grupoPrivado, Usuario usuarioIniciadoSesion) {
    }
}
