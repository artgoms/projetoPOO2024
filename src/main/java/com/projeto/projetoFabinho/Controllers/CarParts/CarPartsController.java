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
	    setModoEdicao(false);

	    // Não gere um novo ID se já houver uma peça carregada (edição)
	    if (currentPart == null) {
	        int maiorId = carPartsDAO.obterMaiorId();
	        idField.setText(String.valueOf(maiorId + 1));
	    }

	    idField.setDisable(true); // Impede edição manual do ID
	    margemField.setText("12");

	    // Atualizar Valor de Venda automaticamente ao mudar Custo ou Margem
	    custoField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());
	    margemField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());

	    // Configurar ação do botão de salvar
	    salvarButton.setOnAction(event -> handleSalvar());
	}



	@FXML
	private void handleEditar() {
		if (!isEditing) {
			// Inicia a edição
			isEditing = true;
			toggleButtonsDuringEditing();
			// Habilita os campos para edição
			setModoEdicao(true);
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

	// Método para limpar os campos
	
	// Método para iniciar uma nova edição
	@FXML
	private void handleNovo() {
		 if (!isEditing) {
		        // Se não estiver editando, começa uma nova edição
		        isEditing = true;
		        setModoEdicao(true);

		        // Alterar o botão "Novo" para "Cancelar"
		        novoButton.setText("Cancelar");
		        editarButton.setDisable(true);
		        salvarButton.setDisable(false);
		    } else {
		        // Se já estiver editando, cancelar a edição
		        handleCancelar();
		    }
	}

	// Método para salvar as alterações
	@FXML
	private void handleSalvar() {
	    if (nomeField.getText().isEmpty() || marcaField.getText().isEmpty() || quantidadeField.getText().isEmpty()) {
	        mostrarAlerta("Erro", "Preencha todos os campos obrigatórios!");
	        return;
	    }

	    try {
	        int id = idField.getText().isEmpty() ? 0 : Integer.parseInt(idField.getText());
	        String nome = nomeField.getText();
	        String marca = marcaField.getText();
	        int quantidade = Integer.parseInt(quantidadeField.getText());
	        double custo = Double.parseDouble(custoField.getText().replace(",", "."));
	        double margem = Double.parseDouble(margemField.getText().replace(",", "."));
	        double valorVenda = Double.parseDouble(valorVendaField.getText().replace(",", "."));
	        LocalDate dataEntrada = dataEntradaField.getValue();

	        CarPartsModel peca = new CarPartsModel(id, nome, marca, quantidade, custo, margem, valorVenda, dataEntrada);

	        boolean sucesso;

	        if (isEditing && currentPart != null && currentPart.getId() > 0) {
	            // **Atualizar peça existente**
	            sucesso = carPartsDAO.update(peca);
	            System.out.println("Atualizando peça ID: " + id);
	        } else {
	            // **Criar nova peça**
	            sucesso = carPartsDAO.inserirPeca(peca);
	            System.out.println("Criando nova peça ID: " + id);
	        }

	        if (sucesso) {
	            mostrarAlerta("Sucesso", "Peça salva com sucesso!");
	            limparCampos();
	            setModoEdicao(false);
	            isEditing = false;
	        } else {
	            mostrarAlerta("Erro", "Erro ao salvar a peça.");
	        }
	    } catch (NumberFormatException e) {
	        mostrarAlerta("Erro", "Verifique os valores numéricos.");
	    }
	}


	// Método para cancelar a edição ou criação
	@FXML
	private void handleCancelar() {
	    if (isEditing) {
	        // Restaurar os valores originais da peça se houver edição em andamento
	        if (currentPart != null) {
	            nomeField.setText(currentPart.getNome());
	            marcaField.setText(currentPart.getMarca());
	            quantidadeField.setText(String.valueOf(currentPart.getQuantidade()));
	            custoField.setText(String.valueOf(currentPart.getCusto()));
	            margemField.setText(String.valueOf(currentPart.getMargemLucro()));
	            valorVendaField.setText(String.valueOf(currentPart.getValorVenda()));
	            dataEntradaField.setValue(currentPart.getDataEntrada());
	        } else {
	            // Se não houver peça selecionada, limpar os campos
	            limparCampos();
	        }

	        // Desativar edição
	        isEditing = false;
	        setModoEdicao(false);

	        // Restaurar botões ao estado original
	        novoButton.setText("Novo");
	        novoButton.setDisable(false);
	        editarButton.setDisable(false);
	        salvarButton.setDisable(true);
	    }
	}
	
	// Método para alternar os botões durante a edição
	private void toggleButtonsDuringEditing() {
		novoButton.setText("Cancelar");
		editarButton.setDisable(true);
		salvarButton.setDisable(false);
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

	public void editarPeca(CarPartsModel part) {
	    if (part == null) return;

	    // **Atualiza a peça em edição**
	    currentPart = part;

	    idField.setText(String.valueOf(part.getId()));  // Mantém o ID correto
	    nomeField.setText(part.getNome());
	    marcaField.setText(part.getMarca());
	    quantidadeField.setText(String.valueOf(part.getQuantidade()));
	    custoField.setText(String.valueOf(part.getCusto()));
	    margemField.setText(String.valueOf(part.getMargemLucro()));
	    valorVendaField.setText(String.valueOf(part.getValorVenda()));
	    dataEntradaField.setValue(part.getDataEntrada());

	    setModoEdicao(true);
	    isEditing = true;  // **Indica que estamos editando**
	}


	private void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}

	private void limparCampos() {
		nomeField.clear();
		marcaField.clear();
		quantidadeField.clear();
		custoField.clear();
		valorVendaField.clear();
		dataEntradaField.setValue(null);
	}

	private void setModoEdicao(boolean ativado) {
		isEditing = ativado;

		nomeField.setDisable(!ativado);
		marcaField.setDisable(!ativado);
		quantidadeField.setDisable(!ativado);
		custoField.setDisable(!ativado);
		margemField.setDisable(!ativado);
		valorVendaField.setDisable(!ativado);
		dataEntradaField.setDisable(!ativado);

		salvarButton.setDisable(!ativado);
		editarButton.setDisable(ativado);
		novoButton.setDisable(false);
		novoButton.setText(isEditing ? "Cancelar" : "Novo");
	}

}
