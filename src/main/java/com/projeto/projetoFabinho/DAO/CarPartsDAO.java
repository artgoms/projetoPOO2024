package com.projeto.projetoFabinho.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.DatabaseConnection;

public class CarPartsDAO {

    public List<CarPartsModel> getAllParts() {
        List<CarPartsModel> partsList = new ArrayList<>();
        String sql = "SELECT id, nome, modelo, quantidade, valorVenda FROM pecas";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                partsList.add(new CarPartsModel(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("modelo"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valorVenda")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partsList;
    }

    public List<CarPartsModel> getPartsByNameOrModel(String searchTerm, String modelFilter) {
        List<CarPartsModel> partsList = new ArrayList<>();
        String sql = "SELECT id, nome, modelo, quantidade, valorVenda FROM pecas WHERE nome LIKE ?";

        if (modelFilter != null && !modelFilter.isEmpty()) {
            sql += " AND modelo = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchTerm + "%");

            if (modelFilter != null && !modelFilter.isEmpty()) {
                stmt.setString(2, modelFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    partsList.add(new CarPartsModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("modelo"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valorVenda")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partsList;
    }

    public boolean inserirPeca(CarPartsModel peca) {
        String sql = "INSERT INTO pecas (nome, modelo, quantidade, custo, margemLucro, valorVenda, dataEntrada) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getModelo());
            stmt.setInt(3, peca.getQuantidade());
            stmt.setDouble(4, peca.getCusto());
            stmt.setDouble(5, peca.getMargemLucro());
            stmt.setDouble(6, peca.getValorVenda());
            stmt.setDate(7, java.sql.Date.valueOf(peca.getDataEntrada()));

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
