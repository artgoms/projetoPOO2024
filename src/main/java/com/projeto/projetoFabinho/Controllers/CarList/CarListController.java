package com.projeto.projetoFabinho.Controllers.CarList;

import com.projeto.projetoFabinho.DAO.CarDAO;
import com.projeto.projetoFabinho.Models.CarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class CarListController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<CarModel> tableView;

    @FXML
    private TableColumn<CarModel, String> colMarca;

    @FXML
    private TableColumn<CarModel, String> colModelo;

    @FXML
    private TableColumn<CarModel, String> colPlaca;

    @FXML
    private TableColumn<CarModel, String> colAnoFabricacao;

    @FXML
    private TableColumn<CarModel, String> colSituacao;

    @FXML
    private Button selecionarButton;

    private CarSelectionListener selectionListener;
    private int clienteId;

    public interface CarSelectionListener {
        void onCarSelected(String carroId);
    }

    public void setSelectionListener(CarSelectionListener listener) {
        this.selectionListener = listener;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
        System.out.println("Cliente ID recebido: " + clienteId); // Verificando o valor do clienteId
    }
    

    @FXML
    private void initialize() {
        // Configuração das colunas da tabela para exibir as informações de cada veículo
        colMarca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
        colModelo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModelo()));
        colPlaca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPlaca()));
        colAnoFabricacao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAnoFabricacao()));
        colSituacao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSituacao()));

        carregarVeiculos(); // Chama a função para carregar os veículos e exibir no terminal

        // Filtro de pesquisa para buscar veículos pelo modelo
        searchField.textProperty().addListener((observable, oldValue, newValue) -> pesquisarVeiculos());

        // Evento de clique para selecionar um veículo
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Se o clique for duplo, seleciona o veículo
                handleSelection();
            }
        });
    }

    private void pesquisarVeiculos() {
        String filtro = searchField.getText().trim();
        CarDAO carrosDAO = new CarDAO();
        List<CarModel> carrosFiltrados = carrosDAO.buscarVeiculosPorClienteIdECodigo(clienteId, filtro);

        ObservableList<CarModel> carrosObservable = FXCollections.observableArrayList(carrosFiltrados);
        tableView.setItems(carrosObservable);
    }

    private void carregarVeiculos() {
        CarDAO carrosDAO = new CarDAO();
        List<CarModel> carros = carrosDAO.buscarVeiculosPorClienteId(clienteId);

        // Exibe no terminal os carros encontrados
        System.out.println("Veículos encontrados para o cliente " + clienteId + ":");
        for (CarModel car : carros) {
            System.out.println("ID: " + car.getId() + ", Marca: " + car.getMarca() + ", Modelo: " + car.getModelo() +
                    ", Placa: " + car.getPlaca() + ", Ano: " + car.getAnoFabricacao() + ", Situação: " + car.getSituacao());
        }

        ObservableList<CarModel> carrosObservable = FXCollections.observableArrayList(carros);
        tableView.setItems(carrosObservable); // Atualiza a tabela com os veículos do cliente
    }

    @FXML
    private void handleSelection() {
        CarModel selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null && selectionListener != null) {
            selectionListener.onCarSelected(String.valueOf(selectedCar.getId()));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
        }
    }
}
