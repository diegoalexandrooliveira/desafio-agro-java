package br.com.diegoalexandro.desafioagrojava.fazenda.dominio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fazenda {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @NotNull
    @Getter
    private String nome;

    @NotNull
    @Getter
    private String cnpj;

    public Fazenda(String nome, String cnpj) {
        this.nome = Objects.requireNonNull(nome);
        this.cnpj = Objects.requireNonNull(cnpj);
    }
}
