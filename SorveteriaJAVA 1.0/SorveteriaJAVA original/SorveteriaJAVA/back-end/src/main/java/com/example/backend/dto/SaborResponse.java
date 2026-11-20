package com.example.backend.dto;

import java.math.BigDecimal;

public record SaborResponse(String id, String nome, String emoji, BigDecimal preco) {
}
