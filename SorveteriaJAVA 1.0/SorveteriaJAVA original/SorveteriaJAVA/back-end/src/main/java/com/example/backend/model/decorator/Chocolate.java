package com.example.backend.model.decorator;

import com.example.backend.model.Casquinha;
import com.example.backend.model.ItemCardapio;

/**
 * Decorator concreto: adiciona o sabor Chocolate à casquinha.
 * Nome e preço vêm do banco (ItemCardapio); a classe só identifica o sabor.
 */
public class Chocolate extends CasquinhaDecorator {

    /** Identificador usado pelo front e pelo banco. */
    public static final String ID = "chocolate";

    public Chocolate(Casquinha casquinha, ItemCardapio sabor) {
        super(casquinha, sabor);
    }
}
