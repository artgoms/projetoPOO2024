package com.projeto.projetoFabinho.Controllers.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.projeto.projetoFabinho.Controllers.CarList.CarListController;
import com.projeto.projetoFabinho.Controllers.ClientList.ClientListController;
import com.projeto.projetoFabinho.DAO.CarDAO;
import com.projeto.projetoFabinho.DAO.ClienteDAO;
import com.projeto.projetoFabinho.Models.CarModel;
import com.projeto.projetoFabinho.Models.ClientModel;
import com.projeto.projetoFabinho.Models.ClientModel.TipoEndereco;
import com.projeto.projetoFabinho.Utils.InputValidator;
import com.projeto.projetoFabinho.Utils.MascaraInscricao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientController {

	@FXML
	private ChoiceBox<String> inscricaoChoice, situacaoChoice, ufChoice;

	@FXML
	private ChoiceBox<TipoEndereco> tipoEnderecoChoice;

	@FXML
	private TextField codigoField, inscricaoNumeroField, nomeField, logradouroField, numeroField, bairroField,
			municipioField, cepField, complementoField, responsavelField, telefone1Field, telefone2Field;

	@FXML
	private Button anteriorButton, proximoButton, gravarButton, editarButton, novoButton;

	@FXML
	private TableView<CarModel> tabelaVeiculos;

	@FXML
	private TableColumn<CarModel, Integer> colCodigo, colUltimoOS;

	@FXML
	private TableColumn<CarModel, String> colModelo, colMarca, colAno, colSituacao;

	@FXML
	private CarListController carListController; // Referência ao controlador da aba de veículos

	private ClienteDAO clienteDAO = new ClienteDAO();
	private ClientModel cliente;

	private List<ClientModel> listaClientes = new ArrayList<>();
	private int clienteAtualIndex = -1;

	private ClientModel clienteAntesEdicao; // gravar o cliente antes de clicar em novo ou editar

	@FXML
	private void initialize() {
		colCodigo.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		colMarca.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
		colModelo.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getModelo()));
		colAno.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAnoFabricacao()));
		colSituacao.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSituacao()));
		colUltimoOS.setCellValueFactory(
				cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getUltimaOS())
						.asObject());

		codigoField.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case F2:
				abrirJanelaClientes();
				break;
			default:
				break;
			}
		});
		
		
		// MASCARA em UTILS
		MascaraInscricao.aplicarMascaraTelefone(telefone1Field);
		MascaraInscricao.aplicarMascaraTelefone(telefone2Field);
		MascaraInscricao.aplicarMascaraCEP(cepField);
		MascaraInscricao.aplicarMascaraInscricao(inscricaoNumeroField);
				
		preencherChoiceBoxes();
		
		listaClientes = clienteDAO.listarTodos(); // carrega todos os clientes
		if (!listaClientes.isEmpty()) {
			clienteAtualIndex = 0 ; // começa pelo primeiro cliente
			preencherCampos(listaClientes.get(clienteAtualIndex));

		}
		atualizarEstadoBotoes();
		carregarUltimoCliente(); // 
		
		InputValidator.aplicarValidacoes(codigoField, inscricaoNumeroField, nomeField, telefone1Field, telefone2Field,
				cepField, numeroField);

		desabilitarCampos();
		gravarButton.setDisable(true);
		editarButton.setDisable(false);
		novoButton.setDisable(false);

	}

	private void carregarUltimoCliente() {
		ClienteDAO clienteDAO = new ClienteDAO();
		List<ClientModel> clientes = clienteDAO.listarTodos();

		if (!clientes.isEmpty()) {
			ClientModel ultimoCliente = clientes.get(clientes.size() - 1); // Pega o último cliente cadastrado
			preencherCampos(ultimoCliente);
		}
	}

	private void atualizarCodigo() {
		int maiorId = clienteDAO.obterMaiorId() + 1; // Obtém o maior ID e soma 1 para o próximo cliente
		codigoField.setText(String.valueOf(maiorId));
	}

	private void preencherChoiceBoxes() {
		// Preenchendo os valores permitidos para inscrição (CPF, CNPJ, RG)
		inscricaoChoice.getItems().setAll("CPF", "CNPJ");

		// Preenchendo os status disponíveis para um cliente
		situacaoChoice.getItems().setAll("Ativo", "Inativo");

		// Preenchendo os tipos de endereço disponíveis no ENUM
		tipoEnderecoChoice.getItems().setAll(ClientModel.TipoEndereco.values());

		// Preenchendo a lista de estados brasileiros
		ufChoice.getItems().setAll("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
				"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
	}

	@FXML
	private void handleGravar() {
		if (!validarCamposObrigatorios()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Campos Obrigatórios");
			alert.setHeaderText(null);
			alert.setContentText("Preencha todos os campos obrigatórios antes de salvar!");
			alert.showAndWait();
			return;
		}

		if (cliente == null) {
			cliente = new ClientModel();
		}

		atualizarCliente(); 

		if (codigoField.isDisable()) {
			clienteDAO.atualizar(cliente);
		} else {
			
			clienteDAO.inserir(cliente); // coigoField habilitado, novo cliente -> inserir
			listaClientes.add(cliente); 
			clienteAtualIndex = listaClientes.size() - 1; 
		}

		habilitarCampos(false);
		gravarButton.setDisable(true);
		editarButton.setDisable(false);
		novoButton.setDisable(false);

		atualizarEstadoBotoes(); // Atualiza os botões Anterior e Próximo corretamente
	}

	@FXML
	private void handleAnterior() {
		if (clienteAtualIndex > 0) {
			clienteAtualIndex--;
			preencherCampos(listaClientes.get(clienteAtualIndex));
		}
		atualizarEstadoBotoes();
	}

	@FXML
	private void handleProximo() {
		if (clienteAtualIndex < listaClientes.size() - 1) {
			clienteAtualIndex++;
			preencherCampos(listaClientes.get(clienteAtualIndex));
		}
		atualizarEstadoBotoes();
	}

	@FXML
	private void handleEditar() {
		if (cliente == null)
			return;

		situacaoChoice.setDisable(false);
		habilitarCampos("Ativo".equalsIgnoreCase(cliente.getSituacao()));
		editarButton.setDisable(true);
		gravarButton.setDisable(false);
		novoButton.setText("Cancelar"); // Altera o texto do botão

		atualizarEstadoBotoes(); // Atualiza os botões "Anterior" e "Próximo"
	}

	@FXML
	private void handleNovo() {
		if ("Cancelar".equals(novoButton.getText())) {
			handleCancelar();
			novoButton.setText("Novo");
			return;
		}

		clienteAntesEdicao = cliente; // salva o cliente atual antes de iniciar um novo
		cliente = new ClientModel(); 
		limparCampos();
		preencherChoiceBoxes(); 
		habilitarCampos(true);
		atualizarCodigo(); 
		situacaoChoice.setDisable(false); 

		gravarButton.setDisable(false);
		editarButton.setDisable(true);

		novoButton.setText("Cancelar");

		anteriorButton.setDisable(true);
		proximoButton.setDisable(true);
	}

	@FXML
	private void handleCancelar() {

		if (cliente == null)
			return;

		editarButton.setDisable(false);
		gravarButton.setDisable(true);
		atualizarEstadoBotoes();
		habilitarCampos(false);
		situacaoChoice.setDisable(true);

		if (clienteAntesEdicao != null) {
			preencherCampos(clienteAntesEdicao); // Restaura os dados antigos
		}
	}

	@FXML
	private void handleListarClientes() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ClientList.fxml"));
			Parent root = loader.load();

			ClientListController clientListController = loader.getController();
			clientListController.setClientController(this); // Passar referência dessa tela

			clientListController.setSelectionListener(codigoCliente -> {
				ClientModel clienteSelecionado = clienteDAO.buscarPorCodigo(Integer.parseInt(codigoCliente));
				preencherCampos(clienteSelecionado);
			});

			Stage stage = new Stage();
			stage.setTitle("Listagem de Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro no carregamento: tela de listagem de clientes");
		}
	}

	private void abrirJanelaClientes() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ClientList.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Lista de Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void atualizarVeiculosCliente() {
		if (clienteAtualIndex >= 0 && clienteAtualIndex < listaClientes.size()) {
			int clienteId = listaClientes.get(clienteAtualIndex).getCodigo();
			System.out.println("Cliente ID para buscar veículos: " + clienteId);

			CarDAO carDAO = new CarDAO();
			List<CarModel> veiculos = carDAO.buscarCarrosPorCliente(clienteId);

			if (veiculos != null) {
				ObservableList<CarModel> observableVeiculos = FXCollections.observableArrayList(veiculos);
				tabelaVeiculos.setItems(observableVeiculos);
			} else {
				System.out.println("sem veículo para o cliente ID: " + clienteId);
			}
		}
	}

	private boolean validarCamposObrigatorios() {
		boolean camposValidos = true;

		camposValidos &= validarCampoObrigatorio(inscricaoChoice);
		camposValidos &= validarCampoObrigatorio(inscricaoNumeroField);
		camposValidos &= validarCampoObrigatorio(situacaoChoice);
		camposValidos &= validarCampoObrigatorio(nomeField);
		camposValidos &= validarCampoObrigatorio(municipioField);
		camposValidos &= validarCampoObrigatorio(ufChoice);
		camposValidos &= validarCampoObrigatorio(logradouroField);
		camposValidos &= validarCampoObrigatorio(numeroField);
		camposValidos &= validarCampoObrigatorio(bairroField);
		camposValidos &= validarCampoObrigatorio(cepField);
		camposValidos &= validarCampoObrigatorio(telefone1Field);

		return camposValidos;
	}

	private boolean validarCampoObrigatorio(Control campo) {
		boolean preenchido;

		if (campo instanceof TextField) {
			preenchido = ((TextField) campo).getText() != null && !((TextField) campo).getText().trim().isEmpty();
		} else if (campo instanceof ChoiceBox) {
			preenchido = ((ChoiceBox<?>) campo).getValue() != null;
		} else {
			preenchido = true;
		}

		if (!preenchido) {
			campo.setStyle("-fx-border-color: #FFCCCC; -fx-border-width: 1px; -fx-background-color: #f6e1dd;");
		} else {
			campo.setStyle(""); // Remove a borda vermelha se estiver preenchido
		}

		return preenchido;
	}

	private void atualizarEstadoBotoes() {
		anteriorButton.setDisable(clienteAtualIndex <= 0);
		proximoButton.setDisable(clienteAtualIndex >= listaClientes.size() - 1);
	}

	private void habilitarCampos(boolean habilitar) {
		inscricaoChoice.setDisable(!habilitar);
		inscricaoNumeroField.setDisable(!habilitar);
		nomeField.setDisable(!habilitar);
		tipoEnderecoChoice.setDisable(!habilitar);
		logradouroField.setDisable(!habilitar);
		numeroField.setDisable(!habilitar);
		bairroField.setDisable(!habilitar);
		complementoField.setDisable(!habilitar);
		municipioField.setDisable(!habilitar);
		ufChoice.setDisable(!habilitar);
		cepField.setDisable(!habilitar);
		telefone1Field.setDisable(!habilitar);
		telefone2Field.setDisable(!habilitar);
		responsavelField.setDisable(!habilitar);
	}

	private void atualizarCliente() {
		cliente.setCodigo(Integer.parseInt(codigoField.getText()));
		cliente.setTipoInscricao(inscricaoChoice.getValue());
		cliente.setInscricaoNumero(inscricaoNumeroField.getText());
		cliente.setNome(nomeField.getText());
		cliente.setSituacao(situacaoChoice.getValue());
		cliente.setTipoEndereco(tipoEnderecoChoice.getValue());
		cliente.setLogradouro(logradouroField.getText());
		cliente.setNumero(numeroField.getText());
		cliente.setBairro(bairroField.getText());
		cliente.setMunicipio(municipioField.getText());
		cliente.setUf(ufChoice.getValue());
		cliente.setCep(cepField.getText());
		cliente.setComplemento(complementoField.getText());
		cliente.setResponsavel(responsavelField.getText());
		cliente.setTelefone1(telefone1Field.getText());
		cliente.setTelefone2(telefone2Field.getText());
	}

	private void limparCampos() {
		codigoField.clear();
		inscricaoChoice.getSelectionModel().clearSelection();
		inscricaoNumeroField.clear();
		nomeField.clear();
		situacaoChoice.getSelectionModel().clearSelection();
		tipoEnderecoChoice.getSelectionModel().clearSelection();
		logradouroField.clear();
		numeroField.clear();
		bairroField.clear();
		municipioField.clear();
		ufChoice.getSelectionModel().clearSelection();
		cepField.clear();
		complementoField.clear();
		responsavelField.clear();
		telefone1Field.clear();
		telefone2Field.clear();
	}

	private void desabilitarCampos() {
		codigoField.setDisable(true);
		inscricaoChoice.setDisable(true);
		inscricaoNumeroField.setDisable(true);
		nomeField.setDisable(true);
		situacaoChoice.setDisable(true);
		tipoEnderecoChoice.setDisable(true);
		logradouroField.setDisable(true);
		numeroField.setDisable(true);
		bairroField.setDisable(true);
		municipioField.setDisable(true);
		ufChoice.setDisable(true);
		cepField.setDisable(true);
		complementoField.setDisable(true);
		responsavelField.setDisable(true);
		telefone1Field.setDisable(true);
		telefone2Field.setDisable(true);
	}

	public void preencherCampos(ClientModel cliente) {
		this.cliente = cliente;
		clienteAtualIndex = listaClientes.indexOf(cliente); // Atualiza o índice baseado no códigoField selecionado

		nomeField.setText(cliente.getNome());
		codigoField.setText(String.valueOf(cliente.getCodigo()));

		atualizarVeiculosCliente(); // Atualiza a aba de veículos com os carros do cliente selecionado

		codigoField.setText(String.valueOf(cliente.getCodigo()));
		inscricaoChoice.setValue(cliente.getTipoInscricao());
		inscricaoNumeroField.setText(cliente.getInscricaoNumero());
		nomeField.setText(cliente.getNome());
		situacaoChoice.setValue(cliente.getSituacao());
		tipoEnderecoChoice.setValue(cliente.getTipoEndereco());
		logradouroField.setText(cliente.getLogradouro());
		numeroField.setText(cliente.getNumero());
		bairroField.setText(cliente.getBairro());
		municipioField.setText(cliente.getMunicipio());
		ufChoice.setValue(cliente.getUf());
		cepField.setText(cliente.getCep());
		complementoField.setText(cliente.getComplemento());
		responsavelField.setText(cliente.getResponsavel());
		telefone1Field.setText(cliente.getTelefone1());
		telefone2Field.setText(cliente.getTelefone2());

		atualizarEstadoBotoes(); // Atualiza os botões de navegação
	}
}
