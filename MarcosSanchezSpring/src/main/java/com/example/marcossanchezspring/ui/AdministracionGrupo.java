package com.example.marcossanchezspring.ui;


import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.GrupoPrivado;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.example.marcossanchezspring.domain.service.GrupoService;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;

@Component
public class AdministracionGrupo {
    GrupoService grupoService;
    public AdministracionGrupo(GrupoService gr) {
        grupoService = gr;
    }

    public Either<ErrorApp, Boolean> crearGrupo(Grupo grupo) {
       return grupoService.crearGrupo(grupo);
    }

    public Either<ErrorApp, Boolean> anadirUsuarioaGrupo(Grupo grupo, Usuario usuarioIniciadoSesion) {
       return grupoService.anadirUsuarioaGrupo(grupo,usuarioIniciadoSesion);

    }

    public Either<ErrorApp, Boolean> comprobarEntradaGrupo(Grupo grupo) {
        return grupoService.comprobarEntradaGrupo(grupo);
    }

    public Either<ErrorApp, Boolean> enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        return grupoService.enviarMensaje(grupoSeleccionado,mensaje,usuarioIniciadoSesion);
    }

    public Either<ErrorApp, List<Mensaje>> obtenerMensajes(Grupo grupoSeleccionado) {
        return grupoService.obtenerMensajes(grupoSeleccionado);
    }

    public Either<ErrorApp, List<Grupo>> obtenerGruposDeUsuario(Usuario usuarioIniciadoSesion) {
        return grupoService.obtenerGruposDeUsuario(usuarioIniciadoSesion);
    }

    public Either<ErrorApp,Boolean> crearGrupoPrivado(GrupoPrivado grupoPrivado) {
        return grupoService.crearGrupoPrivado(grupoPrivado);
    }

    public Either<ErrorApp,Boolean> anadirUsuarioaGrupoPrivado(GrupoPrivado grupoPrivado, Usuario usuarioIniciadoSesion) {
       return grupoService.anadirUsuarioaGrupoPrivado(grupoPrivado,usuarioIniciadoSesion);
    }
}
