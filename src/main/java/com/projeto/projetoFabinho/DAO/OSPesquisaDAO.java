package com.projeto.projetoFabinho.DAO;

import com.projeto.projetoFabinho.DatabaseConnection;
import com.projeto.projetoFabinho.Models.OSModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OSPesquisaDAO {

    public ObservableList<OSModel> buscarOS(String filtro) {
        ObservableList<OSModel> listaOS = FXCollections.observableArrayList();
        String query = "SELECT os.codigo_os, c.nome AS cliente, v.id AS veiculoID, v.modelo, os.status " +
                       "FROM ordens_servico os " +
                       "JOIN clientes c ON os.cliente_id = c.codigo " +
                       "JOIN carros v ON os.carro_id = v.id " +
                       "WHERE c.nome LIKE ? OR v.modelo LIKE ? OR os.codigo_os LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");
            stmt.setString(3, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaOS.add(new OSModel(
                    rs.getString("codigo_os"),
                    rs.getString("cliente"),
                    rs.getString("veiculoID"),
                    rs.getString("modelo"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar OS: " + e.getMessage());
        }

        return listaOS;
    }
}
