package com.example.backend.service;

import com.example.backend.dto.CardapioResponse;
import com.example.backend.dto.PedidoResponse;
import com.example.backend.dto.SaborResponse;
import com.example.backend.exception.SaborInvalidoException;
import com.example.backend.model.Casquinha;
import com.example.backend.model.ItemCardapio;
import com.example.backend.repository.CardapioRepository;
import com.example.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/** Regras de negócio: monta o sundae com os decorators e calcula o total. */
@Service
public class SundaeService {

    private final CardapioRepository repositorio;
    private final PedidoRepository pedidos;
    private final CasquinhaFactory fabrica;
    private final NumeradorPedido numerador;

    public SundaeService(CardapioRepository repositorio, PedidoRepository pedidos, CasquinhaFactory fabrica, NumeradorPedido numerador) {
        this.repositorio = repositorio;
        this.pedidos = pedidos;
        this.fabrica = fabrica;
        this.numerador = numerador;
    }

    /** Dados para o front montar os botões: nome/preço da base e lista de sabores. */
    public CardapioResponse listarCardapio() {
        ItemCardapio base = repositorio.buscarBase();
        List<SaborResponse> sabores = repositorio.buscarSabores().stream()
                .map(s -> new SaborResponse(s.id(), s.nome(), s.emoji(), emReais(s.precoCentavos())))
                .toList();
        return new CardapioResponse(base.nome(), emReais(base.precoCentavos()), sabores);
    }

    /** Calcula o total sem registrar pedido. */
    public PedidoResponse calcular(List<String> ids) {
        List<String> sabores = normalizar(ids);
        Casquinha sundae = montar(sabores);
        return new PedidoResponse(null, sabores, emReais(sundae.getPrecoCentavos()));
    }

    /** Fecha o pedido e grava no banco (Pedido + ItemPedido na mesma transação). */
    @Transactional
    public PedidoResponse finalizar(List<String> ids) {
        List<String> sabores = normalizar(ids);
        Casquinha sundae = montar(sabores);
        int total = sundae.getPrecoCentavos();

        int numero = numerador.proximo();
        pedidos.salvar(total,sabores,numero);

        return new PedidoResponse((long) numero, sabores, emReais(total));
    }

    /** Empilha um decorator por sabor sobre a casquinha base. */
    private Casquinha montar(List<String> ids) {
        Casquinha sundae = fabrica.criarBase(repositorio.buscarBase());
        for (String id : ids) {
            ItemCardapio sabor = repositorio.buscarSaborPorId(id)
                    .orElseThrow(() -> new SaborInvalidoException(id));
            sundae = fabrica.adicionarSabor(sundae, sabor);
        }
        return sundae;
    }

    /** Lista nula vira vazia e repetidos são removidos (cada sabor vale uma vez). */
    private List<String> normalizar(List<String> ids) {
        return ids == null ? List.of() : ids.stream().distinct().toList();
    }

    /** Converte centavos (int) em reais (BigDecimal): 700 -> 7.00. */
    private BigDecimal emReais(int centavos) {
        return BigDecimal.valueOf(centavos, 2);
    }
}

