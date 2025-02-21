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

    
    public boolean atualizarOrdemServico(ServiceOSModel os) {
        String sql = "UPDATE ordens_servico SET codigo = ?, veiculo_id = ?, tipoOS = ?, valor = ?, data_entrada = ?, data_previsao = ?, descricao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, os.getClienteId());
            stmt.setInt(2, os.getVeiculoId());
            stmt.setString(3, os.getTipoOS());
            stmt.setDouble(5, os.getValor().doubleValue());
            stmt.setDate(5, Date.valueOf(os.getDataEntrada()));
            stmt.setDate(6, Date.valueOf(os.getDataPrevisao()));
            stmt.setString(7, os.getDescricao());
            stmt.setInt(8, os.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // Método para inserir uma nova OS
    public int salvar(ServiceOSModel os) {
        String sql = "INSERT INTO ordens_servico (cliente_id, carro_id, tipoOS, valor, situacao, descricao, data_entrada, data_previsao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;  // ID gerado será retornado
        
        try (Connection conn = DatabaseConnection.getConnection();
        		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, os.getClienteId());
            stmt.setInt(2, os.getVeiculoId());
            stmt.setString(3, os.getTipoOS());
            stmt.setBigDecimal(4, os.getValor());
            stmt.setString(5, os.getSituacao());
            stmt.setString(6, os.getDescricao());
            stmt.setObject(7, os.getDataEntrada());
            stmt.setObject(8, os.getDataPrevisao());

            stmt.executeUpdate();
            
            // Obtendo o ID gerado pelo banco de dados
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);  // O ID gerado é o primeiro campo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar Ordem de Serviço: " + e.getMessage());
        }
        return generatedId;  // Retorna o ID gerado
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
