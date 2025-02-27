package com.projeto.projetoFabinho.DAO;

import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.Models.ClientModel;
import com.projeto.projetoFabinho.Models.ServiceOSModel;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceOSDAO extends BaseDAO<ClientModel> {

	// Método para listar todas as Ordens de Serviço
	public List<ServiceOSModel> listarOrdensServico() {
		List<ServiceOSModel> ordens = new ArrayList<>();
		String sql = "SELECT * FROM ordens_servico";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				ServiceOSModel os = new ServiceOSModel(rs.getInt("id"), rs.getInt("cliente_id"),
						rs.getInt("veiculo_id"), rs.getString("tipoOS"), rs.getString("descricao"),
						rs.getBigDecimal("valor"), rs.getString("situacao"), rs.getDate("data_entrada").toLocalDate(),
						rs.getDate("data_previsao") != null ? rs.getDate("data_previsao").toLocalDate() : null);
				ordens.add(os);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordens;
	}

	public boolean atualizarOrdemServico(ServiceOSModel os) {
		String sql = "UPDATE ordens_servico SET cliente_id = ?, carro_id = ?, tipoOS = ?, descricao = ?, valor = ?, situacao = ?, data_entrada = ?, data_previsao = ? WHERE id = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, os.getClienteId());
			stmt.setInt(2, os.getVeiculoId());
			stmt.setString(3, os.getTipoOS());
			stmt.setString(4, os.getDescricao());
			stmt.setBigDecimal(5, os.getValor());
			stmt.setString(6, os.getSituacao());
			stmt.setDate(7, java.sql.Date.valueOf(os.getDataEntrada()));
			stmt.setDate(8, os.getDataPrevisao() != null ? java.sql.Date.valueOf(os.getDataPrevisao()) : null);
			stmt.setInt(9, os.getId());

			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Método para inserir uma nova OS
	public int salvar(ServiceOSModel os) {
		String sql = "INSERT INTO ordens_servico (cliente_id, carro_id, tipoOS, valor, situacao, descricao, data_entrada, data_previsao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		int generatedId = -1; // ID gerado será retornado

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, os.getClienteId());
			stmt.setInt(2, os.getVeiculoId());
			stmt.setString(3, os.getTipoOS());
			stmt.setBigDecimal(4, os.getValor());
			stmt.setString(5, os.getSituacao());
			stmt.setString(6, os.getDescricao());
			stmt.setDate(7, Date.valueOf(os.getDataEntrada()));
			stmt.setDate(8, os.getDataPrevisao() != null ? Date.valueOf(os.getDataPrevisao()) : null);

			stmt.executeUpdate();

			// Obtendo o ID gerado pelo banco de dados
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1); // O ID gerado é o primeiro campo
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar Ordem de Serviço: " + e.getMessage());
		}
		return generatedId; // Retorna o ID gerado
	}

	public ServiceOSModel buscarOSPorId(int id) {

		String sql = "SELECT id, cliente_id, carro_id, descricao, valor, situacao, data_entrada, tipoOS, data_previsao FROM ordens_servico WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				ServiceOSModel os = new ServiceOSModel(rs.getInt("id"), rs.getInt("cliente_id"), rs.getInt("carro_id"),
						rs.getString("tipoOS") != null && !rs.getString("tipoOS").isEmpty() ? rs.getString("tipoOS")
								: "Indefinido",
						rs.getString("descricao") != null ? rs.getString("descricao") : "Sem descrição",
						rs.getBigDecimal("valor") != null ? rs.getBigDecimal("valor") : BigDecimal.ZERO,
						rs.getString("situacao") != null && !rs.getString("situacao").isEmpty()
								? rs.getString("situacao")
								: "Aberta",
						rs.getTimestamp("data_entrada") != null
								? rs.getTimestamp("data_entrada").toLocalDateTime().toLocalDate()
								: LocalDate.now(),
						rs.getDate("data_previsao") != null ? rs.getDate("data_previsao").toLocalDate() : null);

				System.out.println("📌 Detalhes da OS carregados:");
				System.out.println("Cliente ID: " + os.getClienteId());
				System.out.println("Carro ID: " + os.getVeiculoId());
				System.out.println("Tipo OS: " + os.getTipoOS());
				System.out.println("Descrição: " + os.getDescricao());
				System.out.println("Valor: " + os.getValor());
				System.out.println("Situação: " + os.getSituacao());
				System.out.println("Entrada: " + os.getDataEntrada());
				System.out.println("Previsão: " + os.getDataPrevisao());

				return os;
			} else {
				System.out.println("ERRO!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int obterMaiorId() {
		String sql = "SELECT MAX(id) FROM ordens_servico";
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; // Retorna 0 caso não haja clientes cadastrados
	}

	public List<CarPartsModel> buscarPecasPorOS(int osId) {
		List<CarPartsModel> pecas = new ArrayList<>();
		String sql = "SELECT p.id, p.nome, p.marca, p.valorVenda " + "FROM ordens_servico_pecas op "
				+ "JOIN pecas p ON op.peca_id = p.id " + "WHERE op.os_id = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, osId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				CarPartsModel peca = new CarPartsModel(rs.getInt("id"), rs.getString("nome"), rs.getString("marca"),
						rs.getBigDecimal("valorVenda"));
				pecas.add(peca);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pecas;
	}

	public int salvarOrdemServico(ServiceOSModel os) {
		String sql = "INSERT INTO ordens_servico (cliente_id, carro_id, descricao, valor, situacao, data_entrada, tipoOS, data_previsao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, os.getClienteId());
			stmt.setInt(2, os.getVeiculoId());
			stmt.setString(3, os.getDescricao());
			stmt.setBigDecimal(4, os.getValor());
			stmt.setString(5, os.getSituacao());
			stmt.setDate(6, java.sql.Date.valueOf(os.getDataEntrada()));
			stmt.setString(7, os.getTipoOS());
			stmt.setDate(8, os.getDataPrevisao() != null ? java.sql.Date.valueOf(os.getDataPrevisao()) : null);

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("ERRO: salvarOrdemServico -> ServiceOSDAO.java");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("ERRO: salvarOrdemServico -> ServiceOSDAO.java -> ID");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1; // Retorna -1 se houver erro
	}

	public int salvarOrdemServicoComPecas(ServiceOSModel os) {
		int osId = salvarOrdemServico(os); // ORDEM: salva a OS e obtém o ID gerado

		if (osId > 0) {
			salvarPecasOrdemServico(osId, os.getPecas()); // Passa a lista original de peças
		} else {
			System.err.println("ERRO: A OS não foi salvo corretamente -> nenhum ID gerado");
		}

		return osId;
	}

	public void salvarPecasOrdemServico(int osId, List<CarPartsModel> pecas) {
		if (osId <= 0) {
			System.err.println("ERRO: id invalido (menor que zero)");
			return;
		}

		String deleteSQL = "DELETE FROM ordens_servico_pecas WHERE os_id = ?";
		String insertSQL = "INSERT INTO ordens_servico_pecas (os_id, peca_id, valor_unitario) VALUES (?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
				PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

			deleteStmt.setInt(1, osId);
			deleteStmt.executeUpdate();

			for (CarPartsModel peca : pecas) {
				insertStmt.setInt(1, osId);
				insertStmt.setInt(2, peca.getId());
				insertStmt.setBigDecimal(3, BigDecimal.valueOf(peca.getValorVenda()));
				insertStmt.addBatch();
			}

			insertStmt.executeBatch();
			System.out.println("Deu bom");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
