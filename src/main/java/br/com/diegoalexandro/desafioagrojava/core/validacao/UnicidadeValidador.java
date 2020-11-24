package br.com.diegoalexandro.desafioagrojava.core.validacao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class UnicidadeValidador implements ConstraintValidator<Unicidade, Object> {

    private final EntityManager entityManager;

    private String entidade;
    private String campo;

    @Override
    public void initialize(Unicidade anotacao) {
        this.entidade = anotacao.entidade();
        this.campo = anotacao.campo();
    }

    @Override
    public boolean isValid(Object valor, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(valor)) {
            return true;
        }
        Query query = entityManager.createQuery(String.format("select count(*) from %s where %s = ?1", entidade, campo), Long.class);
        query.setParameter(1, valor);
        return (Long) query.getSingleResult() == 0L;
    }
}
