package com.projeto.projetoFabinho.Controllers.CarParts;

import com.projeto.projetoFabinho.DAO.CarPartsDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;

public class CarPartsController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField quantidadeField;

    @FXML
    private TextField custoField;

    @FXML
    private TextField margemField;

    @FXML
    private TextField valorVendaField;

    @FXML
    private DatePicker dataEntradaField;

    @FXML
    private Button salvarButton;
    
    @FXML
    private Button editarButton;

    private CarPartsDAO carPartsDAO = new CarPartsDAO();

    @FXML
    private void initialize() {
        // Definir margem padrão como 12%
        margemField.setText("12");

        // Atualizar Valor de Venda automaticamente ao mudar Custo ou Margem
        custoField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());
        margemField.textProperty().addListener((obs, oldVal, newVal) -> atualizarValorVenda());

        // Configurar ação do botão de salvar
        salvarButton.setOnAction(event -> salvarPeca());
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


    private void salvarPeca() {
        String nome = nomeField.getText();
        String modelo = modeloField.getText();
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

        CarPartsModel peca = new CarPartsModel(0, nome, modelo, quantidade, custo, margem, valorVenda, dataEntrada);
        boolean sucesso = carPartsDAO.inserirPeca(peca);

        if (sucesso) {
            mostrarAlerta("Sucesso", "Peça cadastrada com sucesso!");
            // NÃO limpar os campos, para permitir edição!
        } else {
            mostrarAlerta("Erro", "Falha ao cadastrar a peça.");
        }
    }


//    private void limparCampos() {
//        nomeField.clear();
//        modeloField.clear();
//        quantidadeField.clear();
//        custoField.clear();
//        margemField.setText("12");
//        valorVendaField.clear();
//        dataEntradaField.setValue(null);
//    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
