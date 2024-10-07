package org.example.trabajo.UI;

import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Mensaje;
import org.example.trabajo.Domain.Modelo.Usuario;
import org.example.trabajo.Domain.Service.GrupoService;

import java.util.List;


public class AdministracionGrupo {
    GrupoService grupoService;
    public AdministracionGrupo() {
        try {
            grupoService = new GrupoService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearGrupo(Grupo grupo) {
        grupoService.crearGrupo(grupo);
    }

    public void añadirUsuarioaGrupo(String nombreGrupo, String contraseñaGrupo, Usuario usuarioIniciadoSesion) {
        grupoService.añadirUsuarioaGrupo(nombreGrupo,contraseñaGrupo,usuarioIniciadoSesion);

    }

    public Grupo getGrupoEspecifico(String nombrerGrupo) {
        return grupoService.getGrupoEspecifico(nombrerGrupo);
    }

    public boolean comprobarEntradaGrupo(String nombrerGrupo, String contraseñaGrupo) {
        return grupoService.comprobarEntradaGrupo(nombrerGrupo,contraseñaGrupo);
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
