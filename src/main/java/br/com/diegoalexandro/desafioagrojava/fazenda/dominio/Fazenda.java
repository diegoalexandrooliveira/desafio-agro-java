package br.com.diegoalexandro.desafioagrojava.fazenda.dominio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

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

    @Embedded
    @NotNull
    private Endereco endereco;

    public String getCidade() {
        return endereco.getCidade();
    }

    public String getUf() {
        return endereco.getUf();
    }

    public String getLogradouro() {
        return endereco.getLogradouro();
    }


    public static FazendaBuilder builder() {
        return new FazendaBuilder();
    }

    public void alterarNome(String nome) {
        this.nome = requireNonNull(nome);
    }

    public static final class FazendaBuilder {
        private String nome;
        private String cnpj;
        private String cidade;
        private String uf;
        private String logradouro;

        private FazendaBuilder() {
        }

        public FazendaBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public FazendaBuilder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        public FazendaBuilder cidade(String cidade) {
            this.cidade = cidade;
            return this;
        }

        public FazendaBuilder uf(String uf) {
            this.uf = uf;
            return this;
        }

        public FazendaBuilder logradouro(String logradouro) {
            this.logradouro = logradouro;
            return this;
        }

        public Fazenda build() {
            Fazenda fazenda = new Fazenda();
            fazenda.cnpj = requireNonNull(this.cnpj);
            fazenda.nome = requireNonNull(this.nome);
            fazenda.endereco = new Endereco(cidade, uf, logradouro);
            return fazenda;
        }
    }
}
