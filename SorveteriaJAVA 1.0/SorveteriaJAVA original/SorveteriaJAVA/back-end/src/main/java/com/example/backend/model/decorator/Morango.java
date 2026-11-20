package com.example.backend.model.decorator;

import com.example.backend.model.Casquinha;
import com.example.backend.model.ItemCardapio;

/**
 * Decorator concreto: adiciona o sabor Morango à casquinha.
 * Nome e preço vêm do banco (ItemCardapio); a classe só identifica o sabor.
 */
public class Morango extends CasquinhaDecorator {

    /** Identificador usado pelo front e pelo banco. */
    public static final String ID = "morango";

    public Morango(Casquinha casquinha, ItemCardapio sabor) {
        super(casquinha, sabor);
    }
}
