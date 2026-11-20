package com.example.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(Long id, List<String> sabores, BigDecimal total) {
}
