package com.projeto.projetoFabinho.DAO;


import com.projeto.projetoFabinho.DatabaseConnection;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecasDAO {
    public List<CarPartsModel> listarPecas() {
        List<CarPartsModel> pecas = new ArrayList<>();
        String sql = "SELECT id, nome, marca, valorVenda FROM pecas";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CarPartsModel peca = new CarPartsModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("marca"),
                        rs.getDouble("valorVenda")
                    );
                pecas.add(peca);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }
}
