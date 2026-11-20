package com.example.backend.service;
/** Define como o número de cada pedido é gerado (o service depende só desta interface). */
public interface NumeradorPedido {

    /** Devolve o número do próximo pedido. */
    int proximo();
}
