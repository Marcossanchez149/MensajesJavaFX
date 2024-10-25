package com.example.marcossanchezspring.common.config;



import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


@Getter
@Log4j2
@Component
public class Configuracion {

    private String pathJsonUsuarios;
    private String pathJsonGrupos;
    private String pathJsonMensajes;


    private Configuracion() {

        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader()
                    .getResourceAsStream("config.properties"));
            this.pathJsonUsuarios = p.getProperty("pathJsonUsuarios");
            this.pathJsonGrupos = p.getProperty("pathJsonGrupos");
            this.pathJsonMensajes = p.getProperty("pathJsonMensajes");

        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }


}
