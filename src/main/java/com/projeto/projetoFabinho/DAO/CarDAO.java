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
        String sql = "INSERT INTO carros (codigo, situacao, marca, modelo, anoFabricacao, placa, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getCodigo());  // Agora usa "codigo" no lugar de "idCliente"
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
        String sql = "UPDATE carros SET codigo = ?, situacao = ?, marca = ?, modelo = ?, anoFabricacao = ?, placa = ?, observacoes = ? WHERE id = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getCodigo());
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

    public List<CarModel> getAllCars() {
        List<CarModel> carros = new ArrayList<>();
        String sql = "SELECT c.id, c.placa, c.marca, c.modelo, c.situacao, cl.codigo AS codigoCliente, cl.nome AS nomeCliente " +
                     "FROM carros c " +
                     "JOIN clientes cl ON c.codigo = cl.codigo";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CarModel car = new CarModel(
                        rs.getInt("id"),
                        rs.getInt("codigo"),
                        rs.getString("situacao"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("anoFabricacao"),
                        rs.getString("placa"),
                        rs.getString("observacoes"),
                        rs.getInt("codigoCliente"),
                        rs.getString("nomeCliente")
                );
                carros.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return carros;
    }

    public CarModel getCarByPlaca(String placa) {
        String sql = "SELECT id, codigo, situacao, marca, modelo, anoFabricacao, placa, observacoes, codigoCliente, nomeCliente FROM carros WHERE placa = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CarModel(
                    rs.getInt("id"),
                    rs.getInt("codigo"),
                    rs.getString("situacao"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("anoFabricacao"),
                    rs.getString("placa"),
                    rs.getString("observacoes"),
                    rs.getInt("codigoCliente"),
                    rs.getString("nomeCliente")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
