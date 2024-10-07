package org.example.trabajo.DAO;

import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Mensaje;
import org.example.trabajo.Domain.Modelo.Usuario;

import java.util.List;

public class DaoGrupos {

    private final DataBase dataBase;

    public DaoGrupos() {
        this.dataBase = new DataBase();
    }


    public Grupo getGrupoPorNombre(String grupoName) {
        return dataBase.loadGrupos().stream().filter(grupo -> grupo.getNombre().equals(grupoName))
                .findFirst().orElse(null);
    }
    public List<Grupo> getGruposDeUsuario(Usuario user) {
        return dataBase.loadGrupos().stream().filter(grupo -> grupo.getParticipantes().contains(user))
                .toList();
    }


    public boolean AñadirUsuarioaGrupo(Grupo grupo, Usuario user) {
        List<Grupo> grupos = dataBase.loadGrupos();
        Grupo grupoDB = grupos.stream().filter(g -> g.getNombre().equals(grupo.getNombre()))
                .findFirst().orElse(null);
        if (grupoDB != null) {
            grupoDB.getParticipantes().add(user);
            dataBase.saveGrupos(grupos);
            return true;
        }
        return false;
    }

    public boolean crearGrupo(Grupo grupoNuevo) {

        List<Grupo> grupos = dataBase.loadGrupos();
        grupos.add(grupoNuevo);
        return dataBase.saveGrupos(grupos);

    }

    public boolean comprobarEntradGrupo(Grupo grupo) {
        return dataBase.loadGrupos().stream().anyMatch
                (g -> g.getNombre().equals(grupo.getNombre()) && g.getContraseña().equals(grupo.getContraseña()));
    }

    public void enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        List<Grupo> grupos = dataBase.loadGrupos();
        Grupo grupoDB = grupos.stream().filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre()))
                .findFirst().orElse(null);
        if (grupoDB != null) {
            grupoDB.getMensajes().add(new Mensaje(mensaje,usuarioIniciadoSesion));
            dataBase.saveGrupos(grupos);
        }
    }

    public List<Mensaje> obtenerMensajes(Grupo grupoSeleccionado) {
        return dataBase.loadGrupos().stream().filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre()))
                .findFirst().orElse(null).getMensajes();
    }
}
