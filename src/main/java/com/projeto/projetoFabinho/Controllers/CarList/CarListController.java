package com.projeto.projetoFabinho.Controllers.CarList;

import com.projeto.projetoFabinho.DAO.CarDAO;
import com.projeto.projetoFabinho.Models.CarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CarListController {

    @FXML private TableView<CarModel> carTableView;
    @FXML private TableColumn<CarModel, Integer> idColumn;
    @FXML private TableColumn<CarModel, String> placaColumn;
    @FXML private TableColumn<CarModel, String> marcaColumn;
    @FXML private TableColumn<CarModel, String> modeloColumn;
    @FXML private TableColumn<CarModel, String> situacaoColumn;
    @FXML private TableColumn<CarModel, Integer> codigoClienteColumn;
    @FXML private TableColumn<CarModel, String> nomeClienteColumn;

    private final CarDAO carDAO = new CarDAO();

    @FXML
    public void initialize() {
        // Configurar as colunas da TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("placa"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        situacaoColumn.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        codigoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        nomeClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));

        // Carregar os dados na TableView
        carregarDados();
    }

    private void carregarDados() {
        ObservableList<CarModel> listaCarros = FXCollections.observableArrayList(carDAO.getAllCars());
        carTableView.setItems(listaCarros);
    }
}
