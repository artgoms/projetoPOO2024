package com.projeto.projetoFabinho.Controllers.SelecionarPecas;

import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.DAO.PecasDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SelecionarPecasController {

    @FXML
    private TextField pesquisaField;

    @FXML
    private TableView<CarPartsModel> tabelaPecas;

    @FXML
    private TableColumn<CarPartsModel, String> colunaId, colunaNome, colunaMarca;

    @FXML
    private TableColumn<CarPartsModel, Double> colunaValor;

    @FXML
    private Button selecionarBtn;

    private ObservableList<CarPartsModel> listaPecas = FXCollections.observableArrayList();
    private CarPartsModel pecaSelecionada = null;
    
    // Inicializa o DAO corretamente
    private PecasDAO pecasDAO;

    @FXML
    public void initialize() {
    	pecasDAO = new PecasDAO();
        colunaId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colunaNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colunaMarca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMarca()));
        colunaValor.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getValorVenda()));

        carregarPecas();

        pesquisaField.setOnKeyReleased(event -> pesquisarPecas());
    }

    private void carregarPecas() {
        listaPecas.setAll(pecasDAO.listarPecas());
        tabelaPecas.setItems(listaPecas);
    }

    @FXML
    private void pesquisarPecas() {
        String filtro = pesquisaField.getText().toLowerCase();
        ObservableList<CarPartsModel> listaFiltrada = FXCollections.observableArrayList();

        for (CarPartsModel peca : listaPecas) {
            if (peca.getNome().toLowerCase().contains(filtro) ||
                peca.getMarca().toLowerCase().contains(filtro)) {
                listaFiltrada.add(peca);
            }
        }
        tabelaPecas.setItems(listaFiltrada);
    }

    @FXML
    private void selecionarPeca() {
        pecaSelecionada = tabelaPecas.getSelectionModel().getSelectedItem();
        if (pecaSelecionada != null) {
            fecharJanela();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma peça antes de continuar!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void fecharJanela() {
        Stage stage = (Stage) selecionarBtn.getScene().getWindow();
        stage.close();
    }

    public CarPartsModel getPecaSelecionada() {
        return pecaSelecionada;
    }
}
