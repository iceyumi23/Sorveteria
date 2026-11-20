package com.example.backend.model;

/**
 * Um registro do cardápio, exatamente como virá do banco de dados.
 * Usado tanto para a base (casquinha) quanto para cada sabor.
 *
 * @param id            identificador usado pelo front (ex.: "chocolate")
 * @param nome          texto exibido (ex.: "Chocolate")
 * @param emoji         enfeite visual do botão (opcional)
 * @param precoCentavos preço em centavos, para evitar erro de arredondamento
 */
public record ItemCardapio(String id, String nome, String emoji, int precoCentavos) {
}
