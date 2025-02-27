package com.projeto.projetoFabinho.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projeto.projetoFabinho.Models.ClientModel;

public class OSDAO extends BaseDAO<ClientModel> {

    // Buscar todas as OS associadas ao veículo
    public List<String> getOSPorCarro(int carroId) {
        List<String> osLista = new ArrayList<>();
        String sql = "SELECT id, data_entrada FROM ordens_servico WHERE carro_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, carroId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int osId = rs.getInt("id");
                String dataEntrada = rs.getString("data_entrada");
                osLista.add("OS " + osId + " - " + dataEntrada);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return osLista;
    }

    // Buscar os detalhes da OS selecionada no banco de dados
    public String getDetalhesOS(String osSelecionada) {
        String detalhes = "Nenhuma informação encontrada.";
        String sql = "SELECT descricao FROM ordens_servico WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            int osId = Integer.parseInt(osSelecionada.split(" ")[1]); // Extrai o ID da OS
            stmt.setInt(1, osId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                detalhes = rs.getString("descricao");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalhes;
    }
}
