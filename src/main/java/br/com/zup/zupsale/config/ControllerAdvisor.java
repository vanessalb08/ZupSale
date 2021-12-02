package br.com.zup.zupsale.config;

import br.com.zup.zupsale.exceptions.CalcadoNaoLocalizadoException;
import br.com.zup.zupsale.exceptions.EstoqueInsuficienteException;
import br.com.zup.zupsale.exceptions.IdNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErroDeValidacao> ErrosDeValidacao(MethodArgumentNotValidException exception) {
        List<ErroDeValidacao> erros = new ArrayList<>();

        for (FieldError fieldError : exception.getFieldErrors()) {
            ErroDeValidacao erroDeValidacao = new ErroDeValidacao(
                    fieldError.getDefaultMessage());
            erros.add(erroDeValidacao);
        }

        return erros;
    }

    @ExceptionHandler(CalcadoNaoLocalizadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro ExcecaoDeCalcadoNaoLocalizado(CalcadoNaoLocalizadoException exception) {
        return new MensagemDeErro(exception.getMessage());
    }

    @ExceptionHandler(IdNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro ExcecaoDeIdNaoLocalizado(IdNaoEncontradoException exception) {
        return new MensagemDeErro(exception.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public MensagemDeErro ExcecaoDeEstoqueInsuficiente(EstoqueInsuficienteException exception) {
        return new MensagemDeErro(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro ExcecaoDeEnumInvalido(HttpMessageNotReadableException exception) {
        return new MensagemDeErro("Opção inválida");
    }

}
