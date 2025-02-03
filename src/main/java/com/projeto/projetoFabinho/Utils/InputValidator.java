package com.projeto.projetoFabinho.Utils;

import javafx.scene.control.TextField;

public class InputValidator {

    public static void aplicarValidacoes(TextField codigoField, TextField inscricaoNumeroField,
                                         TextField nomeField, TextField telefone1Field, 
                                         TextField telefone2Field, TextField cepField, 
                                         TextField numeroField) {

        // Código: Apenas números
        codigoField.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (newValue != null && !newValue.matches("\\d*")) {
                codigoField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Inscrição Número: Apenas números e separadores
        inscricaoNumeroField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[0-9./-]*")) {
                inscricaoNumeroField.setText(newValue.replaceAll("[^0-9./-]", ""));
            }
        });

        // Nome: Apenas letras e espaços
        nomeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[a-zA-ZÀ-ÿ\\s]*")) {
                nomeField.setText(newValue.replaceAll("[^a-zA-ZÀ-ÿ\\s]", ""));
            }
        });

        // Telefone: Apenas números, parênteses, traço e espaço
        telefone1Field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[0-9()\\s-]*")) {
                telefone1Field.setText(newValue.replaceAll("[^0-9()\\s-]", ""));
            }
        });

        telefone2Field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[0-9()\\s-]*")) {
                telefone2Field.setText(newValue.replaceAll("[^0-9()\\s-]", ""));
            }
        });

        // CEP: Apenas números e traço
        cepField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d{0,5}-?\\d{0,3}")) {
                cepField.setText(oldValue); // Mantém o valor antigo se a entrada for inválida
            }
        });

        // Número da residência: Apenas números e letras (para casos como "10A")
        numeroField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[0-9A-Za-z]*")) {
                numeroField.setText(newValue.replaceAll("[^0-9A-Za-z]", ""));
            }
        });
    }
}
