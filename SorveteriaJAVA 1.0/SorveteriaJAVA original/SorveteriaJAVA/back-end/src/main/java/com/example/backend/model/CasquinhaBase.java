package com.example.backend.model;

/**
 * Componente concreto do Decorator: a casquinha simples, sem sabor.
 * O nome e o preço são recebidos de fora (vêm do banco), nada fica fixo aqui.
 *
 * Os decorators (Chocolate, Morango, Baunilha) HERDAM desta classe.
 */
public class CasquinhaBase implements Casquinha {

    private final String nome;
    private final int precoCentavos;

    public CasquinhaBase(String nome, int precoCentavos) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do item não pode ser vazio.");
        }
        if (precoCentavos < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.nome = nome;
        this.precoCentavos = precoCentavos;
    }

    /** Na base, a descrição é apenas o próprio nome. */
    @Override
    public String getDescricao() {
        return nome;
    }

    /** Na base, o preço é apenas o próprio preço. */
    @Override
    public int getPrecoCentavos() {
        return precoCentavos;
    }
}
