package com.example.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record CardapioResponse(String nomeBase, BigDecimal precoBase, List<SaborResponse> sabores) {
}
