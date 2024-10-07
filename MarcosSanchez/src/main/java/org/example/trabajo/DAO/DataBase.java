package org.example.trabajo.DAO;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;



import lombok.extern.log4j.Log4j2;
import org.example.trabajo.Common.Configuracion;
import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Mensaje;
import org.example.trabajo.Domain.Modelo.Usuario;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class DataBase {


    private final Gson gson;

    private final Configuracion configuracion;

    public DataBase() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))

                .create();


        this.configuracion = Configuracion.getInstance();
    }




    public List<Usuario> loadUsuarios() {
        Type userListType = new TypeToken<ArrayList<Usuario>>() {
        }.getType();

        List<Usuario> usuarios = null;
        try {
            usuarios = gson.fromJson(
                    new FileReader(configuracion.getPathJsonUsuarios()),
                    userListType);
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return usuarios;
    }

    public boolean saveUsuarios(List<Usuario> usuarios) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonUsuarios())) {
            gson.toJson(usuarios, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public List<Grupo> loadGrupos() {
        Type grupoListType = new TypeToken<ArrayList<Grupo>>() {
        }.getType();

        List<Grupo> grupos = null;
        try {
            grupos = gson.fromJson(
                    new FileReader(configuracion.getPathJsonGrupos()),
                    grupoListType);
            if (grupos == null) {
                grupos = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return grupos;
    }

    public boolean saveGrupos(List<Grupo> grupos) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonGrupos())) {
            gson.toJson(grupos, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public List<Mensaje> loadMensajes() {
        Type mensajeListType = new TypeToken<ArrayList<Mensaje>>() {
        }.getType();

        List<Mensaje> mensajes = null;
        try {
            mensajes = gson.fromJson(
                    new FileReader(configuracion.getPathJsonMensajes()),
                    mensajeListType);
            if (mensajes == null) {
                mensajes = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return mensajes;
    }

    public boolean saveMensajes(List<Mensaje> mensajes) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonMensajes())) {
            gson.toJson(mensajes, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

}