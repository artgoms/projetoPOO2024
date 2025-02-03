package com.projeto.projetoFabinho.Controllers.ClientList;

import com.projeto.projetoFabinho.Controllers.Client.ClientController;
import com.projeto.projetoFabinho.DAO.ClienteDAO;
import com.projeto.projetoFabinho.Models.ClientModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.util.List;

public class ClientListController {

    @FXML
    private TableView<ClientModel> tabelaClientes;
    @FXML
    private TableColumn<ClientModel, String> colNome;
    @FXML
    private TableColumn<ClientModel, String> colInscricao;
    @FXML
    private TableColumn<ClientModel, String> colSituacao;
    @FXML
    private TableColumn<ClientModel, String> colTelefone;
    @FXML
    private TableColumn<ClientModel, String> colResponsavel;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ClientController clientController; // Referência para a tela anterior

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @FXML
    private void initialize() {
        // Configurar colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colInscricao.setCellValueFactory(new PropertyValueFactory<>("inscricaoNumero"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone1"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));

        // Carregar clientes na tabela
        carregarClientes();

        // Adicionar evento de clique para enviar o cliente de volta
        tabelaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Duplo clique
                ClientModel clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
                if (clienteSelecionado != null && clientController != null) {
                    clientController.preencherCampos(clienteSelecionado); // Preenche a tela anterior

                    // Fecha a janela atual
                    Stage stage = (Stage) tabelaClientes.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }

    private void carregarClientes() {
        List<ClientModel> clientes = clienteDAO.listarTodos();
        ObservableList<ClientModel> clientesLista = FXCollections.observableArrayList(clientes);
        tabelaClientes.setItems(clientesLista);
    }
}
