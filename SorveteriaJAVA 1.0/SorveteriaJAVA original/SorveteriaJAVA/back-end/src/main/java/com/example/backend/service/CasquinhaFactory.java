package com.example.backend.service;

import com.example.backend.exception.SaborInvalidoException;
import com.example.backend.model.Casquinha;
import com.example.backend.model.CasquinhaBase;
import com.example.backend.model.ItemCardapio;
import com.example.backend.model.decorator.Baunilha;
import com.example.backend.model.decorator.Chocolate;
import com.example.backend.model.decorator.Morango;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Cria a casquinha e aplica o decorator certo para cada sabor.
 * Para adicionar um sabor novo: crie a classe decorator e registre aqui (Open/Closed).
 */
@Component
public class CasquinhaFactory {

    private static final Map<String, BiFunction<Casquinha, ItemCardapio, Casquinha>> SABORES = Map.of(
            Chocolate.ID, Chocolate::new,
            Morango.ID, Morango::new,
            Baunilha.ID, Baunilha::new
    );

    public Casquinha criarBase(ItemCardapio base) {
        return new CasquinhaBase(base.nome(), base.precoCentavos());
    }

    /** Envolve a casquinha atual com o decorator do sabor informado. */
    public Casquinha adicionarSabor(Casquinha atual, ItemCardapio sabor) {
        BiFunction<Casquinha, ItemCardapio, Casquinha> criador = SABORES.get(sabor.id());
        if (criador == null) {
            throw new SaborInvalidoException(sabor.id());
        }
        return criador.apply(atual, sabor);
    }
}
