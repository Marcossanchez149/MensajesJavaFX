package com.example.marcossanchezspring.dao;


import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppDatos;
import com.example.marcossanchezspring.domain.errors.ErrorAppGrupos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
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
                      return Either.left(ErrorAppDatos.NO_GROUPS_FOUND);
                  } else {
                      return Either.right(grupoList);
                  }
              });
    }



    public Either<ErrorApp, Void> anadirUsuarioaGrupo(Grupo grupo, Usuario user) {
       return dataBase.loadGrupos().flatMap(grupos -> {
              Either<ErrorApp,Grupo> grupoEither = grupos.stream()
                     .filter(g -> g.getNombre().equals(grupo.getNombre()))
                     .findFirst()
                     .map(Either::<ErrorApp,Grupo>right)
                     .orElseGet(() -> Either.left(ErrorAppDatos.NO_GROUPS_FOUND));
              return grupoEither.flatMap(grupoDB -> {
                grupoDB.getParticipantes().add(user);
                return dataBase.saveGrupos(grupos);
              });
       });
    }

    public Either<ErrorApp,Void> crearGrupo(Grupo grupoNuevo) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            if (grupos.stream().anyMatch(g -> g.getNombre().equals(grupoNuevo.getNombre()))) {
                return Either.left(ErrorAppGrupos.GRUPO_YA_EXISTE);
            }
            grupos.add(grupoNuevo);
            return dataBase.saveGrupos(grupos);
        });
    }

    public Either<ErrorApp, Boolean> comprobarEntradGrupo(Grupo grupo) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            if (grupos.stream().anyMatch(g -> g.getNombre().equals(grupo.getNombre()))) {
                return Either.right(true);
            }
            return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
        });
    }

    public Either<ErrorApp,Void> enviarMensaje(Grupo grupoSeleccionado, String mensaje, Usuario usuarioIniciadoSesion) {
        return dataBase.loadGrupos().flatMap(grupos -> {
            Grupo grupo = grupos.stream().filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre())).findFirst().orElse(null);
            if (grupo == null) {
                return Either.left(ErrorAppGrupos.NO_GROUPS_FOUND);
            }
            grupo.getMensajes().add(new Mensaje(mensaje,usuarioIniciadoSesion));
            return dataBase.saveGrupos(grupos);
        });
    }

    public List<Mensaje> obtenerMensajes(Grupo grupoSeleccionado) {
        return  Objects.requireNonNull(dataBase.loadGrupos().stream().filter(g -> g.getNombre().equals(grupoSeleccionado.getNombre()))
                .findFirst().orElse(null)).getMensajes();
    }
}
