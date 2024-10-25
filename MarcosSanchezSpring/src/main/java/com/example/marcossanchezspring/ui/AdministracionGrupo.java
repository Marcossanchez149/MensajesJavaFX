package com.example.marcossanchezspring.ui;


import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.example.marcossanchezspring.domain.service.GrupoService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministracionGrupo {
    GrupoService grupoService;
    public AdministracionGrupo(GrupoService gr) {
        grupoService = gr;
    }

    public void crearGrupo(Grupo grupo) {
        grupoService.crearGrupo(grupo);
    }

    public void anadirUsuarioaGrupo(Grupo grupo, Usuario usuarioIniciadoSesion) {
        grupoService.anadirUsuarioaGrupo(grupo,usuarioIniciadoSesion);

    }

    public boolean comprobarEntradaGrupo(Grupo grupo) {
        return grupoService.comprobarEntradaGrupo(grupo);
    }

    public void enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        grupoService.enviarMensaje(grupoSeleccionado,mensaje,usuarioIniciadoSesion);
    }

    public List<Mensaje> obtenerMensajes(Grupo grupoSeleccionado) {
        return grupoService.obtenerMensajes(grupoSeleccionado);
    }

    public List<Grupo> obtenerGruposDeUsuario(Usuario usuarioIniciadoSesion) {
        return grupoService.obtenerGruposDeUsuario(usuarioIniciadoSesion);
    }
}
