package com.example.backend.controller;

import com.example.backend.dto.CardapioResponse;
import com.example.backend.dto.PedidoRequest;
import com.example.backend.dto.PedidoResponse;
import com.example.backend.service.SundaeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller (C do MVC): recebe as requisições do front e delega ao service.
 * Não tem regra de negócio. As rotas seguem o contrato do api.js.

 * O JavaScript da página pede GET /sorveteria/sabores. O SundaeController responde com JSON, os dados.
 *
 */
@RestController
@RequestMapping("/sorveteria")
public class SundaeController {

    private final SundaeService service;

    public SundaeController(SundaeService service) {
        this.service = service;
    }

    /** GET /api/sabores: cardápio para montar a tela. */
    @GetMapping("/sabores")
    public CardapioResponse listarSabores() {
        return service.listarCardapio();
    }

    /** POST /api/sundae/calcular: total dos sabores escolhidos. */
    @PostMapping("/sundae/calcular")
    public PedidoResponse calcular(@RequestBody PedidoRequest pedido) {
        return service.calcular(pedido.sabores());
    }

    /** POST /api/pedidos: finaliza o pedido. */
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse finalizar(@RequestBody PedidoRequest pedido) {
        return service.finalizar(pedido.sabores());
    }
}
