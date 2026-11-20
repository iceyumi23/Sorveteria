package com.example.backend;

import com.example.backend.model.Casquinha;
import com.example.backend.model.CasquinhaBase;
import com.example.backend.model.ItemCardapio;
import com.example.backend.model.decorator.Chocolate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChocolateTest {

    @Test
    void deveAdicionarChocolateNaCasquinhaBaseComSucesso() {
        // Arrange: casquinha base a R$ 5,00 e sabor chocolate a R$ 2,00
        Casquinha base = new CasquinhaBase("Casquinha", 500);
        ItemCardapio saborChocolate = new ItemCardapio("chocolate", "Chocolate", "🍫", 200);

        // Act: decora a base com o chocolate
        Casquinha sundae = new Chocolate(base, saborChocolate);

        // Assert: descrição e preço somados pelo decorator
        assertEquals("Casquinha + Chocolate", sundae.getDescricao());
        assertEquals(700, sundae.getPrecoCentavos());
    }
}