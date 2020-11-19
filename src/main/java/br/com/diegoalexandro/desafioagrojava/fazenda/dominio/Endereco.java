package br.com.diegoalexandro.desafioagrojava.fazenda.dominio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
class Endereco {

    @Getter
    private String cidade;

    @Getter
    private String uf;

    @Getter
    private String logradouro;

    Endereco(String cidade, String uf, String logradouro) {
        this.cidade = requireNonNull(cidade);
        this.uf = requireNonNull(uf);
        this.logradouro = requireNonNull(logradouro);
    }
}
