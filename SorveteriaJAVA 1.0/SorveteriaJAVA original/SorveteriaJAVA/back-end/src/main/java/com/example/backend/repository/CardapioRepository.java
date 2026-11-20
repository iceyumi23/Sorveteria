package com.example.backend.repository;

import com.example.backend.model.ItemCardapio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardapioRepository {

    ItemCardapio buscarBase();

    List<ItemCardapio> buscarSabores();

    Optional<ItemCardapio> buscarSaborPorId(String ID);
}
