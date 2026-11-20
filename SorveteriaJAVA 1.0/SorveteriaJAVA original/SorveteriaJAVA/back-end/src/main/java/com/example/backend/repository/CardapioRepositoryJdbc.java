package com.example.backend.repository;

import com.example.backend.model.ItemCardapio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CardapioRepositoryJdbc implements CardapioRepository {

    private static final String CAMPOS = "SELECT id, nome, emoji, preco_centavos FROM ItemCardapio ";

    private static final RowMapper<ItemCardapio> MAPPER = (rs, i) -> new ItemCardapio(
            rs.getString("id"),
            rs.getString("nome"),
            rs.getString("emoji"),
            rs.getInt("preco_centavos"));

    private final JdbcTemplate jdbc;

    public CardapioRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public ItemCardapio buscarBase() {
        return jdbc.queryForObject(CAMPOS + "WHERE tipo = 'BASE' LIMIT 1", MAPPER);
    }

    @Override
    public List<ItemCardapio> buscarSabores() {
        return jdbc.query(CAMPOS + "WHERE tipo = 'SABOR' ORDER BY nome", MAPPER);
    }

    @Override
    public Optional<ItemCardapio> buscarSaborPorId(String id) {
        return jdbc.query(CAMPOS + "WHERE tipo = 'SABOR' AND id = ?", MAPPER, id)
                .stream().findFirst();
    }
}