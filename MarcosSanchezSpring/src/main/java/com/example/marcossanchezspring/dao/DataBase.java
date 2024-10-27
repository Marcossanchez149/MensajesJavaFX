package com.example.marcossanchezspring.dao;

import com.example.marcossanchezspring.common.config.Configuracion;
import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppDatos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DataBase {

    private final Gson gson;

    private final Configuracion configuracion;

    public DataBase(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }


    public Either<ErrorApp,List<Usuario>> loadUsuarios() {
        Type userListType = new TypeToken<ArrayList<Usuario>>() {
        }.getType();
        List<Usuario> usuarios ;
        try {
            usuarios = gson.fromJson(
                    new FileReader(configuracion.getPathJsonUsuarios()),
                    userListType);
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }
            return Either.right(usuarios);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDatos.NO_CONNECTION);
        }
    }

    public Either< ErrorApp,Boolean> saveUsuarios(List<Usuario> usuarios) {
        try (FileWriter w = new FileWriter(configuracion.getPathJsonUsuarios())) {
            gson.toJson(usuarios, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDatos.NO_CONNECTION);
        }
        return Either.right(true);
    }

    public Either<ErrorApp,List<Grupo>> loadGrupos() {
        Type grupoListType = new TypeToken<ArrayList<Grupo>>() {
        }.getType();

        List<Grupo> grupos;
        try {
            grupos = gson.fromJson(
                    new FileReader(configuracion.getPathJsonGrupos()),
                    grupoListType);
            if (grupos == null) {
                grupos = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDatos.NO_CONNECTION);
        }
        return Either.right(grupos);
    }

    public Either<ErrorApp,Boolean> saveGrupos(List<Grupo> grupos) {
        try (FileWriter w = new FileWriter(configuracion.getPathJsonGrupos())) {
            gson.toJson(grupos, w);
            return Either.right(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDatos.NO_CONNECTION);
        }
    }
}