package com.example.marcossanchezspring.domain.service;


import com.example.marcossanchezspring.dao.DaoGrupos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {
    private final DaoGrupos daoGrupos;

    public GrupoService(DaoGrupos dg) {
        daoGrupos = dg;
    }

    public void crearGrupo(Grupo grupo) {
        daoGrupos.crearGrupo(grupo);
    }

    public void anadirUsuarioaGrupo(Grupo grupo, Usuario usuarioIniciadoSesion) {
        daoGrupos.anadirUsuarioaGrupo(grupo,usuarioIniciadoSesion);
    }

    public boolean comprobarEntradaGrupo(Grupo grupo) {
        return daoGrupos.comprobarEntradGrupo(grupo);
    }

    public void enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        daoGrupos.enviarMensaje(grupoSeleccionado,mensaje,usuarioIniciadoSesion);
    }

    public List<Mensaje> obtenerMensajes(Grupo grupoSeleccionado) {
        return daoGrupos.obtenerMensajes(grupoSeleccionado);
    }

    public List<Grupo> obtenerGruposDeUsuario(Usuario usuarioIniciadoSesion) {
        return daoGrupos.getGruposDeUsuario(usuarioIniciadoSesion);
    }
}
