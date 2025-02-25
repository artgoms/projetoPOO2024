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

    public List<CarModel> buscarVeiculosPorClienteIdECodigo(int clienteId, String filtro) {
        List<CarModel> listaVeiculos = new ArrayList<>();
        String sql = "SELECT * FROM carros WHERE codigo = ? AND (modelo LIKE ? OR placa LIKE ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configurar os parâmetros da consulta
            stmt.setInt(1, clienteId);
            stmt.setString(2, "%" + filtro + "%");  // Pesquisa por modelo
            stmt.setString(3, "%" + filtro + "%");  // Pesquisa por placa

            // Executa a consulta
            ResultSet rs = stmt.executeQuery();

            // Preenche a lista de veículos
            while (rs.next()) {
                int id = rs.getInt("id");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String anoFabricacao = rs.getString("anoFabricacao");
                String placa = rs.getString("placa");
                String situacao = rs.getString("situacao");
                String observacoes = rs.getString("observacoes");

                // Cria um objeto CarModel e adiciona à lista
                CarModel carro = new CarModel(id, clienteId, situacao, marca, modelo, anoFabricacao, placa, observacoes);
                listaVeiculos.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaVeiculos;
    }
    
    public static List<CarModel> buscarCarrosPorCliente(int clienteId) {
        List<CarModel> carros = new ArrayList<>();
        
        String sql = "SELECT id, marca, modelo, placa, anoFabricacao, situacao, observacoes FROM carros WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarModel carro = new CarModel();
                carro.setId(rs.getInt("id"));
                carro.setMarca(rs.getString("marca"));
                carro.setModelo(rs.getString("modelo"));
                carro.setPlaca(rs.getString("placa"));
                carro.setAnoFabricacao(rs.getString("anoFabricacao"));
                carro.setSituacao(rs.getString("situacao"));
                carro.setSituacao(rs.getString("observacoes"));
                
                carros.add(carro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar carros do cliente: " + e.getMessage());
        }

        return carros;
    }
}
