package com.example.backend.model.decorator;

import com.example.backend.model.Casquinha;
import com.example.backend.model.ItemCardapio;

/**
 * Decorator concreto: adiciona o sabor Baunilha à casquinha.
 * Nome e preço vêm do banco (ItemCardapio); a classe só identifica o sabor.
 */
public class Baunilha extends CasquinhaDecorator {

    /** Identificador usado pelo front e pelo banco. */
    public static final String ID = "baunilha";

    public Baunilha(Casquinha casquinha, ItemCardapio sabor) {
        super(casquinha, sabor);
    }
}
