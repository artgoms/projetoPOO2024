package com.projeto.projetoFabinho.DAO;

import com.projeto.projetoFabinho.DatabaseConnection;
import com.projeto.projetoFabinho.Models.CarModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO extends BaseDAO<CarModel> {

    // 🔹 Inserir um novo carro
    public void insertCar(CarModel car) {
        String sql = "INSERT INTO carros (cliente_id, situacao, marca, modelo, anoFabricacao, placa, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getClienteId());  // Agora usa "codigo" no lugar de "idCliente"
            stmt.setString(2, car.getSituacao());
            stmt.setString(3, car.getMarca());
            stmt.setString(4, car.getModelo());
            stmt.setString(5, car.getAnoFabricacao());
            stmt.setString(6, car.getPlaca());
            stmt.setString(7, car.getObservacoes());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Atualizar um carro existente
    public void updateCar(CarModel car) {
        String sql = "UPDATE carros SET cliente_id = ?, situacao = ?, marca = ?, modelo = ?, anoFabricacao = ?, placa = ?, observacoes = ? WHERE id = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getClienteId());
            stmt.setString(2, car.getSituacao());
            stmt.setString(3, car.getMarca());
            stmt.setString(4, car.getModelo());
            stmt.setString(5, car.getAnoFabricacao());
            stmt.setString(6, car.getPlaca());
            stmt.setString(7, car.getObservacoes());
            stmt.setInt(8, car.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getNextCarId() {
        String sql = "SELECT MAX(id) FROM carros";
        int nextId = 1; // Começa em 1 se não houver carros no banco

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int maxId = rs.getInt(1);
                nextId = maxId + 1; // O próximo ID é o maior ID + 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }

    public CarModel getCarByPlaca(String placa) {
        String sql = "SELECT id, cliente_id, situacao, marca, modelo, anoFabricacao, placa, observacoes, codigoCliente, nomeCliente FROM carros WHERE placa = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CarModel(
                    rs.getInt("id"),
                    rs.getInt("clienteId"),
                    rs.getString("situacao"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("anoFabricacao"),
                    rs.getString("placa"),
                    rs.getString("observacoes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CarModel> buscarCarrosPorClienteComFiltro(int clienteId, String filtro) {
        List<CarModel> veiculos = new ArrayList<>();
        
        String sql = "SELECT c.id, c.marca, c.modelo, c.anoFabricacao, c.placa, " +
                     "(SELECT situacao FROM ordens_servico WHERE carro_id = c.id ORDER BY id DESC LIMIT 1) AS situacao, " +
                     "(SELECT id FROM ordens_servico WHERE carro_id = c.id ORDER BY id DESC LIMIT 1) AS ultima_os " +
                     "FROM carros c " +
                     "WHERE c.cliente_id = ? AND (c.modelo LIKE ? OR c.placa LIKE ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setString(2, "%" + filtro + "%");
            stmt.setString(3, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarModel carro = new CarModel();
                carro.setId(rs.getInt("id"));
                carro.setMarca(rs.getString("marca"));
                carro.setModelo(rs.getString("modelo"));
                carro.setAnoFabricacao(rs.getString("anoFabricacao"));
                carro.setPlaca(rs.getString("placa"));
                carro.setSituacao(rs.getString("situacao"));
                carro.setUltimaOS(rs.getInt("ultima_os"));

                veiculos.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    
    public List<CarModel> buscarCarrosPorCliente(int clienteId) {
        List<CarModel> veiculos = new ArrayList<>();
        String sql = "SELECT c.id, c.marca, c.modelo, c.anoFabricacao, " +
                     "(SELECT situacao FROM ordens_servico WHERE carro_id = c.id ORDER BY id DESC LIMIT 1) AS situacao, " +
                     "(SELECT id FROM ordens_servico WHERE carro_id = c.id ORDER BY id DESC LIMIT 1) AS ultima_os " +
                     "FROM carros c WHERE c.cliente_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarModel carro = new CarModel();
                carro.setId(rs.getInt("id"));
                carro.setMarca(rs.getString("marca"));
                carro.setModelo(rs.getString("modelo"));
                carro.setAnoFabricacao(rs.getString("anoFabricacao"));
                carro.setSituacao(rs.getString("situacao"));
                carro.setUltimaOS(rs.getInt("ultima_os")); // Adicionando a última OS

                veiculos.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public List<CarModel> getVeiculosPorCliente(int clienteId) {
        List<CarModel> veiculos = new ArrayList<>();
        String sql = "SELECT id, marca, modelo, anoFabricacao, situacao FROM carros WHERE cliente_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarModel carro = new CarModel();
                carro.setId(rs.getInt("id"));
                carro.setMarca(rs.getString("marca"));
                carro.setModelo(rs.getString("modelo"));
                carro.setAnoFabricacao(rs.getString("anoFabricacao"));
                carro.setSituacao(rs.getString("situacao"));
                veiculos.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

}
