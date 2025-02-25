package com.projeto.projetoFabinho.Controllers.ClientList;

import com.projeto.projetoFabinho.Controllers.Client.ClientController;
import com.projeto.projetoFabinho.DAO.ClienteDAO;
import com.projeto.projetoFabinho.Models.ClientModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.util.List;

public class ClientListController {

	
	@FXML
	private TextField txtPesquisa;
	
    @FXML
    private TableView<ClientModel> tabelaClientes;
    @FXML
    private TableColumn<ClientModel, String> colNome, colInscricao, colSituacao, colTelefone, colResponsavel;
   
    @FXML
    private TableColumn<ClientModel, Integer> colCodigo;
    
    private ClientSelectionListener selectionListener;
    
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ClientController clientController; // Referência para a tela anterior

    @FXML
    private void initialize() {
        // Adiciona o listener para pesquisa de clientes
        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            pesquisarClientes();
        });

        // Configurar as colunas da tabela
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colInscricao.setCellValueFactory(new PropertyValueFactory<>("inscricaoNumero"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone1"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));

        // Carregar todos os clientes na tabela
        carregarClientes();

        // Evento para capturar o clique duplo na tabela
        tabelaClientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Verifica se foi um clique duplo
                handleSelection(); // Chama o método que trata a seleção
            }
        });
    }
    
    

    public interface ClientSelectionListener {
        void onClientSelected(String codigoCliente);
    }
    
    public void setSelectionListener(ClientSelectionListener listener) {
        this.selectionListener = listener;
    }

    @FXML
    private void handleSelection() {
        ClientModel clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null && selectionListener != null) {
            // Passa o código do cliente para o listener
            selectionListener.onClientSelected(String.valueOf(clienteSelecionado.getCodigo()));

            // Fechar a janela após a seleção
            Stage stage = (Stage) tabelaClientes.getScene().getWindow();
            stage.close();
        }
    }
    
       
	public void setClientController(ClientController clientController) {
	    this.clientController = clientController;
	}

    @FXML
	private void pesquisarClientes() {
	    String filtro = txtPesquisa.getText().trim();
	    ClienteDAO clienteDAO = new ClienteDAO();
	    List<ClientModel> clientesFiltrados = clienteDAO.buscarClientesPorNomeOuCPF(filtro);

	    ObservableList<ClientModel> clientesObservable = FXCollections.observableArrayList(clientesFiltrados);
	    tabelaClientes.setItems(clientesObservable);
	}
	
    private void carregarClientes() {
        List<ClientModel> clientes = clienteDAO.listarTodos();
        ObservableList<ClientModel> clientesLista = FXCollections.observableArrayList(clientes);
        tabelaClientes.setItems(clientesLista);
    }
}
