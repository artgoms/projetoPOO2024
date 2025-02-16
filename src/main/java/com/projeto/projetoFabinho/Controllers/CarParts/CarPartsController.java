package com.projeto.projetoFabinho.Controllers.CarParts;

import com.projeto.projetoFabinho.DAO.CarPartsDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;

public class CarPartsController {

	@FXML
	private TextField nomeField;

	@FXML
	private TextField marcaField;

	@FXML
	private TextField quantidadeField;

	@FXML
	private TextField custoField;

	@FXML
	private TextField margemField;

	@FXML
	private TextField valorVendaField;

	@FXML
	private TextField idField;

	@FXML
	private DatePicker dataEntradaField;

	@FXML
	private Button salvarButton;

	@FXML
	private Button editarButton;

	@FXML
	private Button novoButton;

	private CarPartsDAO carPartsDAO = new CarPartsDAO();

	private CarPartsModel currentPart;

	private boolean isEditing = false;

	@FXML
	private void initialize() {
		// Inicialmente desabilita o botão "Salvar"
		salvarButton.setDisable(true);
		// Desabilita o botão "Editar", pois estamos no estado inicial (não estamos
		// editando)
		editarButton.setDisable(false);
		// O botão "Novo" deve estar ativo
		novoButton.setText("Novo");

		// Definir o valor do campo idField como o maior ID + 1
		int maiorId = carPartsDAO.obterMaiorId();
		idField.setText(String.valueOf(maiorId + 1)); // Definindo o próximo ID para a nova peça
		idField.setDisable(true); // Desabilitar o campo para que o usuário não edite
		// Definir margem padrão como 12%
		margemField.setText("12");

		// Atualizar Valor de Venda automaticamente ao mudar Custo ou Margem
		custoField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());
		margemField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());

		// Configurar ação do botão de salvar
		salvarButton.setOnAction(event -> salvarPeca());
	}

	@FXML
	private void onEditarClick() {
		if (!isEditing) {
			// Inicia a edição
			isEditing = true;
			toggleButtonsDuringEditing();
			// Habilita os campos para edição
			disableFields(false);
		}
	}

	@FXML
	public void salvarEdicoes() {
		// Atualiza os dados da peça com os novos valores
		currentPart.setNome(nomeField.getText());
		currentPart.setMarca(marcaField.getText());
		currentPart.setQuantidade(Integer.parseInt(quantidadeField.getText()));
		currentPart.setValorVenda(Double.parseDouble(valorVendaField.getText()));
		currentPart.setDataEntrada(dataEntradaField.getValue());

		// Aqui você pode adicionar a lógica para salvar no banco de dados

		// Fechar a janela após salvar
		Stage stage = (Stage) nomeField.getScene().getWindow();
		stage.close();
	}


	@FXML
	public void cancelarEdicoes() {
		// Fechar a janela de edição sem salvar
		Stage stage = (Stage) nomeField.getScene().getWindow();
		stage.close();
	}

	// Método para salvar as alterações
	@FXML
	private void onSalvarClick() {
		if (isEditing) {
			// Lógica para salvar o item editado
			CarPartsModel editedCarPart = new CarPartsModel(Integer.parseInt(idField.getText()), nomeField.getText(),
					marcaField.getText(), Integer.parseInt(quantidadeField.getText()),
					Double.parseDouble(custoField.getText()), Double.parseDouble(margemField.getText()),
					Double.parseDouble(valorVendaField.getText()), dataEntradaField.getValue());

			boolean updateSuccess = carPartsDAO.update(editedCarPart);

			if (updateSuccess) {
				// Sucesso ao salvar
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Sucesso");
				alert.setHeaderText(null);
				alert.setContentText("Peça de carro atualizada com sucesso!");
				alert.showAndWait();

				// Após salvar, desativa a edição
				isEditing = false;
				toggleButtonsAfterEditing();

				// Desativa os campos novamente
				disableFields(true);
			} else {
				// Falha ao salvar
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText(null);
				alert.setContentText("Erro ao atualizar a peça de carro.");
				alert.showAndWait();
			}
		}
	}

	// Método para cancelar a edição ou criação
	@FXML
	private void onCancelarClick() {
		isEditing = false;
		toggleButtonsAfterEditing();

		// Desativa os campos novamente
		disableFields(true);
	}

	// Método para alternar os botões durante a edição
	private void toggleButtonsDuringEditing() {
		novoButton.setText("Cancelar Edição");
		editarButton.setDisable(true);
		salvarButton.setDisable(false);
	}

	// Método para alternar os botões após salvar ou cancelar
	private void toggleButtonsAfterEditing() {
		novoButton.setText("Novo");
		editarButton.setDisable(false);
		salvarButton.setDisable(true);
	}

	// Método para limpar os campos

	// Método para iniciar uma nova edição
	@FXML
	private void onNovoClick() {
		if (!isEditing) {
			// Limpa os campos para a criação de um novo item

			isEditing = true;
			toggleButtonsDuringEditing();

			// Habilita os campos para edição
			disableFields(false);
		}
	}

	// Método para desabilitar ou habilitar os campos de texto
	private void disableFields(boolean disable) {
		nomeField.setDisable(disable);
		marcaField.setDisable(disable);
		quantidadeField.setDisable(disable);
		custoField.setDisable(disable);
		margemField.setDisable(disable);
		valorVendaField.setDisable(disable);
		idField.setDisable(disable); // Se o ID for apenas leitura, você pode deixá-lo desabilitado sempre
		dataEntradaField.setDisable(disable);
	}

	private void atualizarValorVenda() {
		try {
			double custo = Double.parseDouble(custoField.getText().replace(",", "."));
			double margem = Double.parseDouble(margemField.getText().replace(",", "."));

			double valorVenda = custo + (custo * margem / 100);
			valorVendaField.setText(String.format("%.2f", valorVenda).replace(".", ",")); // Exibir com vírgula
		} catch (NumberFormatException e) {
			valorVendaField.setText("");
		}
	}

	public void editarPeça(CarPartsModel part) {
		// Preenche os campos com os dados da peça
		currentPart = part;
		nomeField.setText(part.getNome());
		marcaField.setText(part.getMarca());
		quantidadeField.setText(String.valueOf(part.getQuantidade()));
		valorVendaField.setText(String.valueOf(part.getValorVenda()));
		dataEntradaField.setValue(part.getDataEntrada());
	}
	
	private void salvarPeca() {
		String nome = nomeField.getText();
		String marca = marcaField.getText();
		int quantidade;
		double custo, margem, valorVenda;
		LocalDate dataEntrada = dataEntradaField.getValue();

		if (dataEntrada == null) {
			mostrarAlerta("Erro", "Por favor, selecione uma data de entrada.");
			return;
		}

		try {
			quantidade = Integer.parseInt(quantidadeField.getText());
			custo = Double.parseDouble(custoField.getText().replace(",", "."));
			margem = Double.parseDouble(margemField.getText().replace(",", "."));
			valorVenda = Double.parseDouble(valorVendaField.getText().replace(",", "."));
		} catch (NumberFormatException e) {
			mostrarAlerta("Erro", "Verifique os valores numéricos. Use números com '.' ou ','.");
			return;
		}

		CarPartsModel peca = new CarPartsModel(0, nome, marca, quantidade, custo, margem, valorVenda, dataEntrada);
		boolean sucesso = carPartsDAO.inserirPeca(peca);

		if (sucesso) {
			mostrarAlerta("Sucesso", "Peça cadastrada com sucesso!");
			// NÃO limpar os campos, para permitir edição!
		} else {
			mostrarAlerta("Erro", "Falha ao cadastrar a peça.");
		}
	}

	private void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
