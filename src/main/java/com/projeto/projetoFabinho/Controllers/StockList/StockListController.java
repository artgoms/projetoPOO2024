package com.projeto.projetoFabinho.Controllers.StockList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import com.projeto.projetoFabinho.Controllers.CarParts.CarPartsController;
import com.projeto.projetoFabinho.DAO.CarPartsDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockListController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<CarPartsModel> tableView;

    @FXML
    private TableColumn<CarPartsModel, Integer> colId;

    @FXML
    private TableColumn<CarPartsModel, String> colNome;

    @FXML
    private TableColumn<CarPartsModel, String> colMarca;

    @FXML
    private TableColumn<CarPartsModel, Integer> colQuantidade;

    @FXML
    private TableColumn<CarPartsModel, Double> colValorVenda;
    
    @FXML
    private TableColumn<CarPartsModel, LocalDate> colDataEntrada;  // Coluna para a Data de Entrada

    @FXML
    private TableColumn<CarPartsModel, String> colSituacao;  // Coluna para a Situação do Estoque
    
    private List<CarPartsModel> pecasSelecionadas = new ArrayList<>();


    private CarPartsDAO carPartsDAO = new CarPartsDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        colDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
        
        // Adicionar evento de clique duplo na tabela
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Verifica se foi um clique duplo
                adicionarPecaSelecionada(tableView.getSelectionModel().getSelectedItem());
            }
        });

        // Configura a coluna de Situação
        colSituacao.setCellValueFactory(cellData -> {
            CarPartsModel part = cellData.getValue();
            String situacao = calcularSituacao(part.getQuantidade());
            return new javafx.beans.property.ReadOnlyObjectWrapper<>(situacao); // Usa ReadOnlyObjectWrapper em vez de SimpleStringProperty
        });
        
        
        carregarEstoque();

        // Adicionar evento de clique duplo na tabela
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Verifica se foi um clique duplo
                abrirTelaEdicao(tableView.getSelectionModel().getSelectedItem());
            }
        });
    }
    
    private void adicionarPecaSelecionada(CarPartsModel peca) {
        if (peca != null && !pecasSelecionadas.contains(peca)) {
            pecasSelecionadas.add(peca);
        }
    }

    public List<String> getPecasSelecionadas() {
        List<String> nomesPecas = new ArrayList<>();
        for (CarPartsModel peca : pecasSelecionadas) {
            nomesPecas.add(peca.getNome());
        }
        return nomesPecas;
    }

    public double getValorTotalPecas() {
        double total = 0.0;
        for (CarPartsModel peca : pecasSelecionadas) {
            total += peca.getValorVenda();
        }
        return total;
    }
    
    @FXML
    public void onSearch(KeyEvent event) {
        String searchText = searchField.getText().toLowerCase(); // Pega o texto da pesquisa
        // Filtra os dados com base no texto inserido
        ObservableList<CarPartsModel> filteredList = FXCollections.observableArrayList();

        for (CarPartsModel part : carPartsDAO.getAllParts()) {
            if (part.getNome().toLowerCase().contains(searchText) || 
                part.getMarca().toLowerCase().contains(searchText)) {
                filteredList.add(part);
            }
        }

        // Atualiza a TableView com a lista filtrada
        tableView.setItems(filteredList);
    }

    private void carregarEstoque() {
        // Carregar a lista de peças e associá-la ao TableView
        List<CarPartsModel> partsList = carPartsDAO.getAllParts();
        ObservableList<CarPartsModel> observableParts = FXCollections.observableArrayList(partsList);
        tableView.setItems(observableParts);
    }

    private String calcularSituacao(int quantidade) {
        // Lógica para determinar a situação do estoque com base na quantidade
        if (quantidade <= 5) {
            return "Reposição Urgente";  // Quando a quantidade é muito baixa
        } else if (quantidade <= 10) {
            return "Baixa Quantidade";  // Quando a quantidade está abaixo do ideal
        } else {
            return "Em Estoque";  // Quando a quantidade está suficiente
        }
    }

    private void abrirTelaEdicao(CarPartsModel selectedPart) {
        if (selectedPart != null) {
            try {
                // Carregar o FXML da tela de edição
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/CarParts.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);

                // Passar a peça selecionada para a tela de edição
                CarPartsController carPartsController = loader.getController();
                carPartsController.editarPeça(selectedPart);

                // Exibir a janela de edição
                stage.setTitle("Editar Peça");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
