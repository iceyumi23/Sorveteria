package com.example.backend;

import com.example.backend.exception.SaborInvalidoException;
import com.example.backend.model.Casquinha;
import com.example.backend.model.ItemCardapio;
import com.example.backend.service.CasquinhaFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Exemplo de dois tipos de cenário de teste para o Decorator da Casquinha:
 *
 *  - CENÁRIO DE SUCESSO: o fluxo "feliz". Entra um dado válido e esperamos o
 *    resultado correto (descrição e preço somados pelos decorators).
 *
 *  - CENÁRIO DE INSUCESSO: o fluxo de erro. Entra um dado inválido e esperamos
 *    que o sistema REJEITE com a exceção certa, em vez de montar um sundae errado.
 *
 * Nenhum dos dois precisa de banco de dados nem de Spring: o Decorator e a
 * Factory são classes Java puras, então o teste fica rápido e isolado.
 */
class CasquinhaCenariosTest {

    // A Factory é quem escolhe o decorator certo para cada sabor.
    private final CasquinhaFactory fabrica = new CasquinhaFactory();

    // ---------------------------------------------------------------------
    // CENÁRIO DE SUCESSO
    // ---------------------------------------------------------------------
    @Test
    void deveEmpilharChocolateEMorangoNaCasquinhaBaseComSucesso() {
        // Arrange (preparar): dados válidos, iguais aos que viriam do banco.
        ItemCardapio itemBase = new ItemCardapio("casquinha", "Casquinha", "\uD83C\uDF66", 500);
        ItemCardapio chocolate = new ItemCardapio("chocolate", "Chocolate", "\uD83C\uDF6B", 200);
        ItemCardapio morango = new ItemCardapio("morango", "Morango", "\uD83C\uDF53", 200);

        // Act (agir): monta o sundae empilhando um decorator sobre o outro.
        Casquinha sundae = fabrica.criarBase(itemBase);
        sundae = fabrica.adicionarSabor(sundae, chocolate); // Casquinha + Chocolate
        sundae = fabrica.adicionarSabor(sundae, morango);   // ... + Morango

        // Assert (verificar): cada decorator somou o seu texto e o seu preço.
        // 500 (base) + 200 (chocolate) + 200 (morango) = 900 centavos = R$ 9,00
        assertEquals("Casquinha + Chocolate + Morango", sundae.getDescricao());
        assertEquals(900, sundae.getPrecoCentavos());
    }

    // ---------------------------------------------------------------------
    // CENÁRIO DE INSUCESSO
    // ---------------------------------------------------------------------
    @Test
    void deveLancarExcecaoAoAdicionarSaborQueNaoExisteNoCardapio() {
        // Arrange: um sabor que NÃO está registrado na Factory (só existem
        // chocolate, morango e baunilha).
        Casquinha base = fabrica.criarBase(new ItemCardapio("casquinha", "Casquinha", "\uD83C\uDF66", 500));
        ItemCardapio saborInexistente = new ItemCardapio("pistache", "Pistache", "\uD83C\uDF30", 200);

        // Act + Assert: assertThrows executa o código e passa SOMENTE se a
        // exceção esperada for lançada. Se nada for lançado, o teste FALHA.
        SaborInvalidoException erro = assertThrows(
                SaborInvalidoException.class,
                () -> fabrica.adicionarSabor(base, saborInexistente)
        );

        // Também conferimos a mensagem, para garantir que o erro aponta o sabor certo.
        assertEquals("Sabor inválido: pistache", erro.getMessage());
    }
}