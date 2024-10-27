package com.example.marcossanchezspring.domain.validators;

import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppGrupos;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class GrupoValidator {

    public Either<ErrorApp,Boolean> validateGrupo(Grupo user){
        if (user.getNombre().isBlank() || user.getNombre().isEmpty()) {
            return Either.left(ErrorAppGrupos.NOMBRE_NO_VALIDO);
        }
        return Either.right(true);
    }

}
