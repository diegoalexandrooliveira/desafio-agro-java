package br.com.diegoalexandro.desafioagrojava.core.validacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
@Component
public class UnicidadeValidador implements ConstraintValidator<Unicidade, Object> {

    private final EntityManager entityManager;
    private final HttpServletRequest servletRequest;

    private String entidade;
    private String campo;
    private String campoId;


    @Override
    public void initialize(Unicidade anotacao) {
        this.entidade = anotacao.entidade();
        this.campo = anotacao.campo();
        this.campoId = anotacao.campoId();
    }

    @Override
    public boolean isValid(Object valor, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(valor)) {
            return true;
        }

        Optional<String> idDaRequisicao = recuperaIdDaRequisicao();

        TypedQuery<Long> query = idDaRequisicao
                .map(id -> {
                    String sql = String.format("select count(*) from %s where %s = ?1 and %s != ?2", entidade, campo, campoId);
                    TypedQuery<Long> longTypedQuery = entityManager.createQuery(sql, Long.class);
                    longTypedQuery.setParameter(2, UUID.fromString(id));
                    return longTypedQuery;
                }).orElse(entityManager.createQuery(String.format("select count(*) from %s where %s = ?1", entidade, campo), Long.class));

        query.setParameter(1, valor);
        return query.getSingleResult() == 0L;
    }

    @SuppressWarnings("unchecked")
    private Optional<String> recuperaIdDaRequisicao() {
        Map<String, String> pathVariables = Optional.ofNullable(servletRequest.getAttribute("org.springframework.web.servlet.View.pathVariables"))
                .map(variables -> (Map<String, String>) variables)
                .orElse(Collections.emptyMap());
        return Optional.ofNullable(pathVariables.get("id"));
    }
}
