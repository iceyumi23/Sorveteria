package com.example.backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PedidoRepositoryJdbc implements PedidoRepository {

    private final JdbcTemplate jdbc;

    public PedidoRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public long salvar(int totalCentavos, List<String> idsSabores, int numero) {
        KeyHolder chave = new GeneratedKeyHolder();

        // 1) insere o pedido e recupera o id gerado (AUTO_INCREMENT)
        jdbc.update(conexao -> {
            PreparedStatement ps = conexao.prepareStatement(
                    "INSERT INTO Pedido(numero, total_centavos) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, numero);
            ps.setInt(2, totalCentavos);
            return ps;
        }, chave);

        long idPedido = chave.getKey().longValue();

        // 2)  um registro em ItemPedido para cada sabor escolhido
        for (String idSabor : idsSabores) {
            jdbc.update("INSERT INTO ItemPedido(id_pedido, id_item) VALUES (?, ?)", idPedido, idSabor);
        }

        return idPedido;
    }
}