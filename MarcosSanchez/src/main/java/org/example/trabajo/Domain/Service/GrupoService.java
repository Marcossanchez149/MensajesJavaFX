package org.example.trabajo.Domain.Service;


import org.example.trabajo.DAO.DaoGrupos;
import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Mensaje;
import org.example.trabajo.Domain.Modelo.Usuario;

import java.util.List;

public class GrupoService {
    private DaoGrupos daoGrupos;

    public GrupoService() {
        daoGrupos = new DaoGrupos();
    }

    public void crearGrupo(Grupo grupo) {
        daoGrupos.crearGrupo(grupo);
    }

    public void añadirUsuarioaGrupo(String nombreGrupo, String contraseñaGrupo, Usuario usuarioIniciadoSesion) {
        daoGrupos.AñadirUsuarioaGrupo(new Grupo(nombreGrupo,contraseñaGrupo),usuarioIniciadoSesion);
    }

    public Grupo getGrupoEspecifico(String nombrerGrupo) {
        return daoGrupos.getGrupoPorNombre(nombrerGrupo);
    }

    public boolean comprobarEntradaGrupo(String nombrerGrupo, String contraseñaGrupo) {
        return daoGrupos.comprobarEntradGrupo(new Grupo(nombrerGrupo,contraseñaGrupo));
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
