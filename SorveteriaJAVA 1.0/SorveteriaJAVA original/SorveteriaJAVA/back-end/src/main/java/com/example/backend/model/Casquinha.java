package com.example.backend.model;

/**
 * Contrato (Component do Decorator) de tudo que pode compor um sundae.
 * Tanto a casquinha base quanto cada sabor implementam esta interface,
 * por isso podem ser empilhados uns sobre os outros.
 */
public interface Casquinha {

    /** Texto do item (String que virá do banco). Ex.: "Casquinha + Chocolate". */
    String getDescricao();

    /** Preço em centavos (Int que virá do banco). Ex.: 700 = R$ 7,00. */
    int getPrecoCentavos();
}
