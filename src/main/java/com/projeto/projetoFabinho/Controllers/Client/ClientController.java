package com.projeto.projetoFabinho.Controllers.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.projeto.projetoFabinho.Controllers.ClientList.ClientListController;
import com.projeto.projetoFabinho.DAO.ClienteDAO;
import com.projeto.projetoFabinho.Models.ClientModel;
import com.projeto.projetoFabinho.Models.ClientModel.TipoEndereco;
import com.projeto.projetoFabinho.Utils.InputValidator;
import com.projeto.projetoFabinho.Utils.MascaraInscricao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientController {


	@FXML
	private Button anteriorButton;
	@FXML
	private Button proximoButton;
	@FXML
	private TextField codigoField;
	@FXML
	private ChoiceBox<String> inscricaoChoice;
	@FXML
	private TextField inscricaoNumeroField;
	@FXML
	private TextField nomeField;
	@FXML
	private ChoiceBox<String> situacaoChoice;
	@FXML
	private ChoiceBox<TipoEndereco> tipoEnderecoChoice;
	@FXML
	private TextField logradouroField;
	@FXML
	private TextField numeroField;
	@FXML
	private TextField bairroField;
	@FXML
	private TextField municipioField;
	@FXML
	private ChoiceBox<String> ufChoice;
	@FXML
	private TextField cepField;
	@FXML
	private TextField complementoField;
	@FXML
	private TextField responsavelField;
	@FXML
	private TextField telefone1Field;
	@FXML
	private TextField telefone2Field;

	@FXML
	private Button gravarButton;
	@FXML
	private Button editarButton;
	@FXML
	private Button novoButton;
	


	private ClienteDAO clienteDAO = new ClienteDAO();
	private ClientModel cliente;

	private List<ClientModel> listaClientes = new ArrayList<>();
	private int clienteAtualIndex = -1;

	private ClientModel clienteAntesEdicao; // Armazena o cliente antes de clicar em "Novo" ou "Editar"

	@FXML
	private void initialize() {

		codigoField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case F2:
                    abrirJanelaClientes();
                    break;
                default:
                    break;
            }
        });
		MascaraInscricao.aplicarMascaraTelefone(telefone1Field);
		MascaraInscricao.aplicarMascaraTelefone(telefone2Field);
		MascaraInscricao.aplicarMascaraCEP(cepField);
		MascaraInscricao.aplicarMascaraInscricao(inscricaoNumeroField);
		preencherChoiceBoxes(); // Garante que os ChoiceBox tenham valores antes de usar
		listaClientes = clienteDAO.listarTodos(); // Carrega todos os clientes
		if (!listaClientes.isEmpty()) {
			clienteAtualIndex = 0; // Começa pelo primeiro cliente
			preencherCampos(listaClientes.get(clienteAtualIndex));

		}
		atualizarEstadoBotoes();
		carregarUltimoCliente(); // Busca e preenche os campos com o último cliente do banco
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
			alert.setContentText(
					"Preencha todos os campos obrigatórios antes de salvar!");
			alert.showAndWait();
			return;
		}

		if (cliente == null) {
			cliente = new ClientModel();
		}

		atualizarCliente(); // Atualiza os dados do objeto cliente com o formulário

		if (codigoField.isDisable()) {
			// Se códigoField está desabilitado, significa que é um cliente existente →
			// atualizar
			clienteDAO.atualizar(cliente);
		} else {
			// Se códigoField está habilitado, significa que é um novo cliente → inserir
			clienteDAO.inserir(cliente);
			listaClientes.add(cliente); // Adiciona o novo cliente à lista
			clienteAtualIndex = listaClientes.size() - 1; // Define o índice do último cliente
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

		// Sempre permitir a edição do campo de situação
		situacaoChoice.setDisable(false);

		// Habilitar os campos caso a situação já seja "Ativo"
		habilitarCampos("Ativo".equalsIgnoreCase(cliente.getSituacao()));

		// Ativar botões necessários para edição
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

	    clienteAntesEdicao = cliente; // Salva o cliente atual antes de iniciar um novo
	    cliente = new ClientModel(); // Criando novo cliente
	    limparCampos();
	    preencherChoiceBoxes(); // Garante que os valores reapareçam
	    habilitarCampos(true); // No modo "Novo", códigoField pode ser editado
	    atualizarCodigo(); // Define o novo ID automaticamente
		situacaoChoice.setDisable(false); // Sempre permitir a edição do campo de situação

	    gravarButton.setDisable(false);
	    editarButton.setDisable(true);

	    novoButton.setText("Cancelar"); // Altera o texto do botão

	    anteriorButton.setDisable(true);
	    proximoButton.setDisable(true);
	}
	

	@FXML
	private void handleCancelar() {

		if (cliente == null)
			return;
		
		// Reativar o botão de edição e desativar os botões de salvar e cancelar
		editarButton.setDisable(false);
		gravarButton.setDisable(true);

		atualizarEstadoBotoes(); // Atualiza os botões "Anterior" e "Próximo"

		// Bloquear todos os campos ao cancelar a edição
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

			// Obter o controlador da listagem
			ClientListController clientListController = loader.getController();
			clientListController.setClientController(this); // Passar referência desta tela

			// Criar a nova janela
			Stage stage = new Stage();
			stage.setTitle("Listagem de Clientes");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao carregar a tela de listagem de clientes.");
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
	
	
	
	
	private boolean validarCamposObrigatorios() {
		boolean camposValidos = true;

		camposValidos &= validarCampoObrigatorio(inscricaoChoice);
		camposValidos &= validarCampoObrigatorio(inscricaoNumeroField);
		camposValidos &= validarCampoObrigatorio(situacaoChoice);
		camposValidos &= validarCampoObrigatorio(nomeField);
		camposValidos &= validarCampoObrigatorio(tipoEnderecoChoice);
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
