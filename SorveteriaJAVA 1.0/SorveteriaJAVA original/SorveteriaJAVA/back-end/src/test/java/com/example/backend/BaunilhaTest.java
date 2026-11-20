package com.example.backend;

import com.example.backend.model.Casquinha;
import com.example.backend.model.CasquinhaBase;
import com.example.backend.model.ItemCardapio;
import com.example.backend.model.decorator.Baunilha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaunilhaTest {

    @Test
    void deveAdicionarBaunilhaNaCasquinhaBaseComSucesso() {
        // Arrange: casquinha base a R$ 5,00 e sabor baunilha a R$ 2,00
        Casquinha base = new CasquinhaBase("Casquinha", 500);
        ItemCardapio saborBaunilha = new ItemCardapio("baunilha", "Baunilha", "🍨", 200);

        // Act: decora a base com o baunilha
        Casquinha sundae = new Baunilha(base, saborBaunilha);

        // Assert: descrição e preço somados pelo decorator
        assertEquals("Casquinha + Baunilha", sundae.getDescricao());
        assertEquals(700, sundae.getPrecoCentavos());
    }
}