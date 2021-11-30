package br.com.zup.zupsale.config;

import br.com.zup.zupsale.exceptions.CalcadoNaoLocalizadoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CalcadoNaoLocalizadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro ExcecaoDeCalcadoNaoLocalizado(CalcadoNaoLocalizadoException exception) {
        return new MensagemDeErro(exception.getMessage());
    }

}
