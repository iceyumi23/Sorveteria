package com.example.backend.model.decorator;

import com.example.backend.model.Casquinha;
import com.example.backend.model.CasquinhaBase;
import com.example.backend.model.ItemCardapio;

import java.util.Objects;

/**
 * Decorator abstrato.
 *
 * - É uma CasquinhaBase (herança): guarda o nome e o preço do PRÓPRIO sabor.
 * - Tem uma Casquinha (composição): o item que está sendo "decorado".
 *
 * A descrição e o preço final = o que já existia + a contribuição deste sabor.
 */
public abstract class CasquinhaDecorator extends CasquinhaBase {

    private final Casquinha casquinha;

    protected CasquinhaDecorator(Casquinha casquinha, ItemCardapio sabor) {
        super(sabor.nome(), sabor.precoCentavos());
        this.casquinha = Objects.requireNonNull(casquinha, "A casquinha decorada não pode ser nula.");
    }

    /** Ex.: "Casquinha + Chocolate". super.getDescricao() é o nome do próprio sabor. */
    @Override
    public String getDescricao() {
        return casquinha.getDescricao() + " + " + super.getDescricao();
    }

    /** Ex.: 500 + 200 = 700. super.getPrecoCentavos() é o preço do próprio sabor. */
    @Override
    public int getPrecoCentavos() {
        return casquinha.getPrecoCentavos() + super.getPrecoCentavos();
    }
}
