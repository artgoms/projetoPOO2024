package com.projeto.projetoFabinho.Controllers.StockList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.projeto.projetoFabinho.DAO.CarPartsDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StockListController {

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> modelFilter;

    @FXML
    private TableView<CarPartsModel> tableView;

    @FXML
    private TableColumn<CarPartsModel, Integer> colId;

    @FXML
    private TableColumn<CarPartsModel, String> colNome;

    @FXML
    private TableColumn<CarPartsModel, String> colModelo;

    @FXML
    private TableColumn<CarPartsModel, Integer> colQuantidade;

    @FXML
    private TableColumn<CarPartsModel, Double> colValorVenda;

    private CarPartsDAO carPartsDAO = new CarPartsDAO();

    @FXML
    public void initialize() {
        // Configurar as colunas para usar os métodos get do modelo sem Property
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));

        // Carregar os dados na tabela
        loadParts("");
    }

    // 🔹 Método para carregar as peças na tabela com filtro
    private void loadParts(String searchTerm) {
        String selectedModel = modelFilter.getValue();
        List<CarPartsModel> parts = carPartsDAO.getPartsByNameOrModel(searchTerm, selectedModel);
        ObservableList<CarPartsModel> observableParts = FXCollections.observableArrayList(parts);
        tableView.setItems(observableParts);
    }

    // 🔹 Método chamado quando o usuário digita no campo de busca
    @FXML
    private void onSearch() {
        loadParts(searchField.getText());
    }
}
