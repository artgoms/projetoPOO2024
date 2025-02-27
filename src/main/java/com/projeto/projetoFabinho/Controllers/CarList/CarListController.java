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
    private TableColumn<CarModel, String> colMarca, colModelo, colPlaca, colAnoFabricacao, colSituacao ;

    @FXML
    private Button selecionarButton;

    private CarSelectionListener selectionListener;
    private int clienteId = -1; // Inicializar como -1 para evitar NullPointerException


    @FXML
    private void initialize() {
        // Colunas da tabela para exibir as informações de cada veículo no registro de OS
        colMarca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
        colModelo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModelo()));
        colPlaca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPlaca()));
        colAnoFabricacao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAnoFabricacao()));
        colSituacao.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSituacao()));
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> pesquisarVeiculos());

        if (clienteId > 0) {
            carregarCarrosPorCliente();
        }
        
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleSelection();
            }
        });

    }

    public interface CarSelectionListener {
    	void onCarSelected(String carroId);
    }
    
    public void setSelectionListener(CarSelectionListener listener) {
    	this.selectionListener = listener;
    }
    
    public void setClienteId(int clienteId) {
    	this.clienteId = clienteId;
    	if (tableView != null) { // Evita erro se tableView ainda não estiver carregado
    		carregarCarrosPorCliente();
    	}
    	System.out.println("Cliente ID recebido: " + clienteId);
    }
    
    private void pesquisarVeiculos() {
        String filtro = searchField.getText().trim();
        CarDAO carrosDAO = new CarDAO();
        List<CarModel> carrosFiltrados = carrosDAO.buscarCarrosPorClienteComFiltro(clienteId, filtro);

        ObservableList<CarModel> carrosObservable = FXCollections.observableArrayList(carrosFiltrados);
        tableView.setItems(carrosObservable);
    }

    public void carregarCarrosPorCliente() {
        if (clienteId > 0) {
            CarDAO carDAO = new CarDAO();
            List<CarModel> carros = carDAO.buscarCarrosPorCliente(clienteId);

            ObservableList<CarModel> observableCarros = FXCollections.observableArrayList(carros);
            tableView.setItems(observableCarros);
        }
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
