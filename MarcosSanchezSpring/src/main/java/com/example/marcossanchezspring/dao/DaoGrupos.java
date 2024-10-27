package com.example.marcossanchezspring.dao;


import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppGrupos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DaoGrupos {

    private final DataBase dataBase;

    public DaoGrupos(DataBase db) {
        this.dataBase = db;
    }


    public Either<ErrorApp,List<Grupo>> getGruposDeUsuario(Usuario user) {
      return dataBase.loadGrupos().map(grupos -> grupos.stream()
              .filter(grupo -> grupo.getParticipantes().contains(user))
              .collect(Collectors.toList()))
              .flatMap(grupoList -> {
                  if (grupoList.isEmpty()) {
                      return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
                  } else {
                      return Either.right(grupoList);
                  }
              });
    }



    public Either<ErrorApp, Boolean> anadirUsuarioaGrupo(Grupo grupo, Usuario user) {
       return dataBase.loadGrupos().flatMap(grupos -> {
           Grupo grupoEncontrado = grupos.stream().filter(g -> g.getNombre().equals(grupo.getNombre())).findFirst().orElse(null);
           if (grupoEncontrado == null) {
               return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
           }
           grupoEncontrado.getParticipantes().add(user);
           return dataBase.saveGrupos(grupos);
       });
    }

    public Either<ErrorApp,Boolean> crearGrupo(Grupo grupoNuevo) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            if (grupos.stream().anyMatch(g -> g.getNombre().equals(grupoNuevo.getNombre()))) {
                return Either.left(ErrorAppGrupos.GRUPO_YA_EXISTE);
            }else{
                grupos.add(grupoNuevo);
                return dataBase.saveGrupos(grupos);
            }
        });
    }

    public Either<ErrorApp, Grupo> comprobarEntradGrupo(Grupo grupo) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            if (grupos.stream().anyMatch(g -> g.getNombre().equals(grupo.getNombre()))) {
                return Either.right(grupos.stream().filter(g -> g.getNombre().equals(grupo.getNombre()))
                        .findFirst().get());
            }
            return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
        });
    }

    public Either<ErrorApp, Boolean> enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            Grupo grupo = grupos.stream().filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre())).findFirst().orElse(null);
            if (grupo == null) {
                return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
            }
            grupo.getMensajes().add(new Mensaje(mensaje,usuarioIniciadoSesion));
            return dataBase.saveGrupos(grupos);
        });
    }

    public Either< ErrorApp,List<Mensaje>> obtenerMensajes(Grupo grupoSeleccionado) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            Grupo grupo = grupos.stream()
                    .filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre()))
                    .findFirst()
                    .orElse(null);
            if (grupo == null) {
                return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
            }
            List<Mensaje> mensajes = grupo.getMensajes();
            if (mensajes == null || mensajes.isEmpty()) {
                return Either.left(ErrorAppGrupos.NO_MESSAGES_FOUND);
            }
            return Either.right(mensajes);
        });
    }
}
