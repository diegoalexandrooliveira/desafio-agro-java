package br.com.diegoalexandro.desafioagrojava.config;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BeanValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> handle(MethodArgumentNotValidException erro) {
        Erro retorno = new Erro();
        erro.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(objectError -> String.format("Campo %s %s", objectError.getField(), objectError.getDefaultMessage()))
                .forEach(retorno::adicionaErro);
        return ResponseEntity.badRequest().body(retorno);
    }

    private final class Erro {
        @Getter
        private List<String> erros = new ArrayList<>();

        void adicionaErro(String erro) {
            erros.add(erro);
        }
    }
}
