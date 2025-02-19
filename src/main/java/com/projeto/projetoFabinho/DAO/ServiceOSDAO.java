package com.projeto.projetoFabinho.DAO;


import com.projeto.projetoFabinho.DatabaseConnection;
import com.projeto.projetoFabinho.Models.ServiceOSModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOSDAO {

    // Método para listar todas as Ordens de Serviço
    public List<ServiceOSModel> listarOrdensServico() {
        List<ServiceOSModel> ordens = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ServiceOSModel os = new ServiceOSModel(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veiculo_id"),
                    rs.getString("tipoOS"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getString("situacao"),
                    rs.getDate("data_entrada").toLocalDate(),
                    rs.getDate("data_previsao") != null ? rs.getDate("data_previsao").toLocalDate() : null,
                    rs.getDate("data_conclusao") != null ? rs.getDate("data_conclusao").toLocalDate() : null
                );
                ordens.add(os);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordens;
    }

    // Método para inserir uma nova OS
    public int inserirOS(ServiceOSModel os) {
        int generatedId = -1;
        String sql = "INSERT INTO ordens_servico (cliente_id, carro_id, tipoOS, descricao, valor, situacao, data_entrada, data_previsao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, os.getClienteId());
            stmt.setInt(2, os.getVeiculoId());
            stmt.setString(3, os.getTipoOS());
            stmt.setString(4, os.getDescricao());
            stmt.setDouble(5, os.getValor().doubleValue());
            stmt.setString(6, os.getSituacao());
            stmt.setDate(7, java.sql.Date.valueOf(os.getDataEntrada()));
            stmt.setDate(8, java.sql.Date.valueOf(os.getDataPrevisao()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    
    public ServiceOSModel buscarOS(int codigoOS) {
        String query = "SELECT * FROM ordens_servico WHERE id = ?";
        ServiceOSModel os = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, codigoOS);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                os = new ServiceOSModel(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("carro_id"),
                    rs.getString("tipoOS"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getString("situacao"),
                    rs.getTimestamp("data_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("data_previsao") != null ? rs.getTimestamp("data_previsao").toLocalDateTime().toLocalDate() : null
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar OS: " + e.getMessage());
        }

        return os;
    }
    
    
}
