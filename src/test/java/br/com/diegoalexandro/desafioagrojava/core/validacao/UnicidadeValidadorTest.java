package br.com.diegoalexandro.desafioagrojava.core.validacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UnicidadeValidadorTest {


    private Unicidade unicidadeMock;
    private EntityManager entityManagerMock;
    private TypedQuery queryMock;

    @BeforeEach
    void setup() {
        this.unicidadeMock = Mockito.mock(Unicidade.class);
        this.entityManagerMock = Mockito.mock(EntityManager.class);
        this.queryMock = Mockito.mock(TypedQuery.class);
    }

    @Test
    @DisplayName("Deve passar na validação")
    void teste() {
        when(unicidadeMock.entidade()).thenReturn("Entidade");
        when(unicidadeMock.campo()).thenReturn("id");

        UnicidadeValidador unicidadeValidador = new UnicidadeValidador(entityManagerMock, null);

        unicidadeValidador.initialize(unicidadeMock);

        when(queryMock.getSingleResult()).thenReturn(0L);

        String selectUnicidade = "select count(*) from Entidade where id = ?1";

        when(entityManagerMock.createQuery(selectUnicidade, Long.class)).thenReturn(queryMock);

        assertTrue(unicidadeValidador.isValid("ID", null));

        verify(entityManagerMock).createQuery(selectUnicidade, Long.class);
        verify(queryMock).setParameter(1, "ID");
    }

    @Test
    @DisplayName("Não deve passar na validação por já existir a entidade")
    void teste2() {
        when(unicidadeMock.entidade()).thenReturn("Entidade");
        when(unicidadeMock.campo()).thenReturn("id");

        UnicidadeValidador unicidadeValidador = new UnicidadeValidador(entityManagerMock, null);

        unicidadeValidador.initialize(unicidadeMock);

        when(queryMock.getSingleResult()).thenReturn(1L);

        String selectUnicidade = "select count(*) from Entidade where id = ?1";

        when(entityManagerMock.createQuery(selectUnicidade, Long.class)).thenReturn(queryMock);

        assertFalse(unicidadeValidador.isValid("ID", null));

        verify(entityManagerMock).createQuery(selectUnicidade, Long.class);
        verify(queryMock).setParameter(1, "ID");
    }

    @Test
    @DisplayName("Não deve realizar o select porque o atributo é nulo")
    void teste3() {
        when(unicidadeMock.entidade()).thenReturn("Entidade");
        when(unicidadeMock.campo()).thenReturn("id");

        UnicidadeValidador unicidadeValidador = new UnicidadeValidador(entityManagerMock, null);

        unicidadeValidador.initialize(unicidadeMock);

        verify(entityManagerMock, never()).createQuery(Mockito.anyString(), Mockito.any());
        verify(queryMock, never()).setParameter(anyInt(), any());

        assertTrue(unicidadeValidador.isValid(null, null));
    }


}
