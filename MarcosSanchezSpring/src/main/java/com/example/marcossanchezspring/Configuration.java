package com.example.marcossanchezspring;


import com.google.gson.*;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;



@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public FXMLLoader createLoader(ApplicationContext context) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        return loader;
    }


    @Bean
    public Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                        LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()));
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                        new JsonPrimitive(localDateTime.toString()));
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                        LocalDate.parse(json.getAsJsonPrimitive().getAsString()));
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) ->
                        new JsonPrimitive(localDate.toString()));
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}