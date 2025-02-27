package com.projeto.projetoFabinho.DAO;

import com.projeto.projetoFabinho.Models.ClientModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends BaseDAO<ClientModel> {

    public boolean inserir(ClientModel cliente) {
        if (codigoExiste(cliente.getCodigo())) {
            System.out.println("Já existe um cliente com esse código!");
            return false; // Impede a inserção e retorna falso
        }

        String sql = "INSERT INTO clientes (codigo, tipo_inscricao, inscricao_numero, nome, situacao, tipo_endereco, logradouro, numero, bairro, municipio, uf, cep, complemento, responsavel, telefone1, telefone2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getCodigo());
            stmt.setString(2, cliente.getTipoInscricao());
            stmt.setString(3, cliente.getInscricaoNumero());
            stmt.setString(4, cliente.getNome());
            stmt.setString(5, cliente.getSituacao());
            stmt.setString(6, cliente.getTipoEndereco().toString());
            stmt.setString(7, cliente.getLogradouro());
            stmt.setString(8, cliente.getNumero());
            stmt.setString(9, cliente.getBairro());
            stmt.setString(10, cliente.getMunicipio());
            stmt.setString(11, cliente.getUf());
            stmt.setString(12, cliente.getCep());
            stmt.setString(13, cliente.getComplemento());
            stmt.setString(14, cliente.getResponsavel());
            stmt.setString(15, cliente.getTelefone1());
            stmt.setString(16, cliente.getTelefone2());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obterMaiorId() {
        String sql = "SELECT MAX(codigo) FROM clientes";
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

    public boolean codigoExiste(int codigo) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE codigo = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se já existir o código
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void atualizar(ClientModel cliente) {
        String sql = "UPDATE clientes SET tipo_inscricao = ?, inscricao_numero = ?, nome = ?, situacao = ?, tipo_endereco = ?, logradouro = ?, numero = ?, bairro = ?, municipio = ?, uf = ?, cep = ?, complemento = ?, responsavel = ?, telefone1 = ?, telefone2 = ? WHERE codigo = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getTipoInscricao());
            stmt.setString(2, cliente.getInscricaoNumero());
            stmt.setString(3, cliente.getNome());
            stmt.setString(4, cliente.getSituacao());
            stmt.setString(5, cliente.getTipoEndereco().toString());
            stmt.setString(6, cliente.getLogradouro());
            stmt.setString(7, cliente.getNumero());
            stmt.setString(8, cliente.getBairro());
            stmt.setString(9, cliente.getMunicipio());
            stmt.setString(10, cliente.getUf());
            stmt.setString(11, cliente.getCep());
            stmt.setString(12, cliente.getComplemento());
            stmt.setString(13, cliente.getResponsavel());
            stmt.setString(14, cliente.getTelefone1());
            stmt.setString(15, cliente.getTelefone2());
            stmt.setInt(16, cliente.getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ClientModel> listarTodos() {
        List<ClientModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY codigo ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClientModel cliente = new ClientModel();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setTipoInscricao(rs.getString("tipo_inscricao"));
                cliente.setInscricaoNumero(rs.getString("inscricao_numero"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSituacao(rs.getString("situacao"));
                cliente.setTipoEndereco(ClientModel.TipoEndereco.valueOf(rs.getString("tipo_endereco")));
                cliente.setLogradouro(rs.getString("logradouro"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setMunicipio(rs.getString("municipio"));
                cliente.setUf(rs.getString("uf"));
                cliente.setCep(rs.getString("cep"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setResponsavel(rs.getString("responsavel"));
                cliente.setTelefone1(rs.getString("telefone1"));
                cliente.setTelefone2(rs.getString("telefone2"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public ClientModel buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM clientes WHERE codigo = ?";
        ClientModel cliente = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new ClientModel();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setTipoInscricao(rs.getString("tipo_inscricao"));
                cliente.setInscricaoNumero(rs.getString("inscricao_numero"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSituacao(rs.getString("situacao"));
                cliente.setTipoEndereco(ClientModel.TipoEndereco.valueOf(rs.getString("tipo_endereco")));
                cliente.setLogradouro(rs.getString("logradouro"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setMunicipio(rs.getString("municipio"));
                cliente.setUf(rs.getString("uf"));
                cliente.setCep(rs.getString("cep"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setResponsavel(rs.getString("responsavel"));
                cliente.setTelefone1(rs.getString("telefone1"));
                cliente.setTelefone2(rs.getString("telefone2"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
    
    public List<ClientModel> buscarClientesPorNomeOuCPF(String filtro) {
        List<ClientModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR inscricao_numero LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClientModel cliente = new ClientModel();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setNome(rs.getString("nome"));
                cliente.setInscricaoNumero(rs.getString("inscricao_numero"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    
}
