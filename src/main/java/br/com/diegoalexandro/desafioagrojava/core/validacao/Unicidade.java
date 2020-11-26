package br.com.diegoalexandro.desafioagrojava.core.validacao;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER
})
@Constraint(validatedBy = UnicidadeValidador.class)
public @interface Unicidade {
    String entidade();

    String campo();

    String campoId() default "id";

    String message() default "duplicado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
