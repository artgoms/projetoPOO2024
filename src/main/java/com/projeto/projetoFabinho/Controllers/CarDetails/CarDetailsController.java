package com.projeto.projetoFabinho.Controllers.CarDetails;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.List;

import com.projeto.projetoFabinho.Models.CarModel;
import com.projeto.projetoFabinho.DAO.OSDAO;

public class CarDetailsController {

    @FXML
    private TextField idCliente, idCarro, placaField, marcaField, modeloField, anoField, situacaoField;

    @FXML
    private TextArea observacoesField, detalhesOS;

    @FXML
    private ComboBox<String> osAssociadas;

    private CarModel veiculo;
    private OSDAO osDAO = new OSDAO(); // DAO para buscar OS no banco

    public void setVeiculo(CarModel veiculo) {
        this.veiculo = veiculo;
        preencherCampos();
        carregarOrdensServico();
    }

    private void preencherCampos() {
        if (veiculo != null) {
            idCliente.setText(String.valueOf(veiculo.getClienteId())); // Agora mostra o ID do cliente
            placaField.setText(veiculo.getPlaca());
            marcaField.setText(veiculo.getMarca());
            modeloField.setText(veiculo.getModelo());
            anoField.setText(String.valueOf(veiculo.getAnoFabricacao()));
            situacaoField.setText(veiculo.getSituacao());
            idCarro.setText(String.valueOf(veiculo.getId()));
            observacoesField.setText(veiculo.getObservacoes());
        }
    }

    // Carregar todas as OS associadas ao veículo na ComboBox, buscando no banco de dados
    private void carregarOrdensServico() {
        if (veiculo != null) {
            List<String> ordensServico = osDAO.getOSPorCarro(veiculo.getId());
            osAssociadas.getItems().addAll(ordensServico);
        }
    }

    // Evento para carregar os detalhes da OS selecionada
    @FXML
    private void carregarDetalhesOS() {
        String osSelecionada = osAssociadas.getValue();
        if (osSelecionada != null) {
            String detalhes = osDAO.getDetalhesOS(osSelecionada);
            detalhesOS.setText(detalhes);
        }
    }
}
