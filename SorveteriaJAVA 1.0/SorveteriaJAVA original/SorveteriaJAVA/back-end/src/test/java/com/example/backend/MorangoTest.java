package com.example.backend;

import com.example.backend.model.Casquinha;
import com.example.backend.model.CasquinhaBase;
import com.example.backend.model.ItemCardapio;
import com.example.backend.model.decorator.Morango;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MorangoTest {

    @Test
    void deveAdicionarMorangoNaCasquinhaBaseComSucesso() {
        // Arrange: casquinha base a R$ 5,00 e sabor morango a R$ 2,00
        Casquinha base = new CasquinhaBase("Casquinha", 500);
        ItemCardapio saborMorango = new ItemCardapio("morango", "Morango", "🍓", 200);

        // Act: decora a base com o morango
        Casquinha sundae = new Morango(base, saborMorango);

        // Assert: descrição e preço somados pelo decorator
        assertEquals("Casquinha + Morango", sundae.getDescricao());
        assertEquals(700, sundae.getPrecoCentavos());
    }
}