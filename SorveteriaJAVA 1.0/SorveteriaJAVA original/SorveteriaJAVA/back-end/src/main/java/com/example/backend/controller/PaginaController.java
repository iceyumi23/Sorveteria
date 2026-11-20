package com.example.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Entrega a página do front-end no endereço /sorveteria.
 * Usa @Controller (e não @RestController) porque devolve uma página, não JSON.

 * O navegador pede GET /sorveteria. O PaginaController responde com o index.html, que é a View.
 */

@Controller
public class PaginaController {

    @GetMapping("/sorveteria")
    public String abrirPagina() {
        // "forward:" repassa a requisição internamente para o index.html estático
        return "forward:/index.html";
    }
}