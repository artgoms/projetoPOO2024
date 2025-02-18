package com.projeto.projetoFabinho.DAO;


import com.projeto.projetoFabinho.Models.ServiceOSModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOSDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_clientes";
    private static final String USER = "projeto";
    private static final String PASSWORD = "projeto";

    // Método para obter a conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para listar todas as Ordens de Serviço
    public List<ServiceOSModel> listarOrdensServico() {
        List<ServiceOSModel> ordens = new ArrayList<>();
        String sql = "SELECT * FROM OrdemServico";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ServiceOSModel os = new ServiceOSModel(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veiculo_id"),
                    rs.getString("tipo_os"),
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

    // Método para buscar uma OS por ID
    public ServiceOSModel buscarPorId(int id) {
        String sql = "SELECT * FROM OrdemServico WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ServiceOSModel(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veiculo_id"),
                    rs.getString("tipo_os"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getString("situacao"),
                    rs.getDate("data_entrada").toLocalDate(),
                    rs.getDate("data_previsao") != null ? rs.getDate("data_previsao").toLocalDate() : null,
                    rs.getDate("data_conclusao") != null ? rs.getDate("data_conclusao").toLocalDate() : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para inserir uma nova OS
    public boolean inserirOS(ServiceOSModel os) {
        String sql = "INSERT INTO OrdemServico (cliente_id, veiculo_id, tipo_os, descricao, valor, situacao, data_entrada, data_previsao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, os.getClienteId());
            stmt.setInt(2, os.getVeiculoId());
            stmt.setString(3, os.getTipoOS());
            stmt.setString(4, os.getDescricao());
            stmt.setBigDecimal(5, os.getValor());
            stmt.setString(6, os.getSituacao());
            stmt.setDate(7, Date.valueOf(os.getDataEntrada()));
            stmt.setDate(8, os.getDataPrevisao() != null ? Date.valueOf(os.getDataPrevisao()) : null);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ServiceOSModel buscarOS(int codigoOS) {
        String query = "SELECT * FROM ordens_servico WHERE codigo_os = ?";
        ServiceOSModel os = null;

        try (Connection conn = getConnection();
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
                    rs.getString("status"),
                    rs.getTimestamp("data_criacao").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("data_previsao") != null ? rs.getTimestamp("data_previsao").toLocalDateTime().toLocalDate() : null,
                    rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime().toLocalDate() : null
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar OS: " + e.getMessage());
        }

        return os;
    }
    
    
}
