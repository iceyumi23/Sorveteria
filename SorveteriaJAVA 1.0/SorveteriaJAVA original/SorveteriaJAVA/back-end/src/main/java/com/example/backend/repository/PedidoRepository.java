package com.example.backend.repository;

import java.util.List;

/** Porta de acesso para gravar pedidos (o service depende desta interface, não do banco). */
public interface PedidoRepository {

    /** Grava o pedido e seus sabores. Devolve o id gerado pelo banco. */
    long salvar(int totalCentavos, List<String> idsSabores, int numero);
}