package com.projeto.projetoFabinho.DAO;

import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.Models.ClientModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecasDAO extends BaseDAO<ClientModel> {
	public List<CarPartsModel> listarPecas() {
		List<CarPartsModel> pecas = new ArrayList<>();
		String sql = "SELECT id, nome, marca, valorVenda FROM pecas";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				CarPartsModel peca = new CarPartsModel(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"),
						rs.getDouble("valorVenda"));
				pecas.add(peca);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pecas;
	}

	public Integer buscarIdPorNome(String nome) {
		String sql = "SELECT id FROM pecas WHERE nome = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Retorna null se não encontrar a peça
	}

	public BigDecimal buscarValorPecaPorId(int pecaId) {
		String sql = "SELECT valorVenda FROM pecas WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, pecaId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getBigDecimal("valorVenda");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BigDecimal.ZERO; // Retorna 0 caso a peça não seja encontrada
	}

}
