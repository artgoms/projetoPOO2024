package com.projeto.projetoFabinho.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.DatabaseConnection;

public class CarPartsDAO {

	public List<CarPartsModel> getAllParts() {
		List<CarPartsModel> partsList = new ArrayList<>();
		String sql = "SELECT id, nome, marca, quantidade, custo,margemLucro, valorVenda,  dataEntrada FROM pecas";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				partsList.add(new CarPartsModel(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"),
						rs.getInt("quantidade"), rs.getDouble("custo"),rs.getDouble("margemLucro"), rs.getDouble("valorVenda"),
						 rs.getDate("dataEntrada").toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partsList;
	}

	public List<CarPartsModel> getPartsByName(String searchTerm) {
		List<CarPartsModel> partsList = new ArrayList<>();
		String sql = "SELECT id, nome, marca,  quantidade, custo,margemLucro, valorVenda, dataEntrada FROM pecas WHERE nome LIKE ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + searchTerm + "%");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					partsList.add(new CarPartsModel(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"),
							rs.getInt("quantidade"), rs.getDouble("custo"), 
							rs.getDouble("margemLucro"), rs.getDouble("valorVenda"),rs.getDate("dataEntrada").toLocalDate()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partsList;
	}

	public boolean inserirPeca(CarPartsModel peca) {
		String sql = "INSERT INTO pecas (nome, marca, quantidade, custo, margemLucro, valorVenda, dataEntrada) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, peca.getNome());
			stmt.setString(2, peca.getMarca());
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

	public int obterMaiorId() {
		int maiorId = 0;
		String query = "SELECT MAX(id) FROM pecas"; // Ajuste o nome da tabela conforme necessário

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				maiorId = rs.getInt(1); // Pega o maior ID
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maiorId;
	}

	// Método para atualizar a peça de carro no banco de dados
	public boolean update(CarPartsModel peca) {
		String sql = "UPDATE pecas SET nome = ?, marca = ?, quantidade = ?, custo = ?, margemLucro = ?, valorVenda = ?, dataEntrada = ? WHERE id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, peca.getNome());
			stmt.setString(2, peca.getMarca());
			stmt.setInt(3, peca.getQuantidade());
			stmt.setDouble(4, peca.getCusto());
			stmt.setDouble(5, peca.getMargemLucro());
			stmt.setDouble(6, peca.getValorVenda());
			stmt.setDate(7, java.sql.Date.valueOf(peca.getDataEntrada()));
			stmt.setInt(8, peca.getId());

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0; // Se a atualização foi bem-sucedida, retorna true
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Se houve erro, retorna false
		}
	}
}
