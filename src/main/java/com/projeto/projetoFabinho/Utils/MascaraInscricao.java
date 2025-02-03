package com.projeto.projetoFabinho.Utils;

import javafx.scene.control.TextField;

public class MascaraInscricao {

    // Máscara para telefone: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
    public static void aplicarMascaraTelefone(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return; // Evita erro de null

            String digits = newValue.replaceAll("\\D", ""); // Remove tudo que não for número

            if (digits.length() > 11) {
                digits = digits.substring(0, 11);
            }

            if (digits.length() >= 11) { // Formato (XX) XXXXX-XXXX
                textField.setText("(" + digits.substring(0, 2) + ") " + digits.substring(2, 7) + "-" + digits.substring(7));
            } else if (digits.length() >= 6) { // Formato (XX) XXXX-XXXX
                textField.setText("(" + digits.substring(0, 2) + ") " + digits.substring(2, 6) + "-" + digits.substring(6));
            } else if (digits.length() >= 2) { // Formato (XX)
                textField.setText("(" + digits.substring(0, 2) + ") " + digits.substring(2));
            } else {
                textField.setText(digits);
            }
        });
    }

    // Máscara para CEP: XXXXX-XXX
    public static void aplicarMascaraCEP(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return; // Evita erro de null

            String digits = newValue.replaceAll("\\D", ""); // Remove tudo que não for número

            if (digits.length() > 8) {
                digits = digits.substring(0, 8);
            }

            if (digits.length() >= 5) {
                textField.setText(digits.substring(0, 5) + "-" + digits.substring(5));
            } else {
                textField.setText(digits);
            }
        });
    }

    // Máscara para Inscrição: CPF (XXX.XXX.XXX-XX) ou CNPJ (XX.XXX.XXX/XXXX-XX)
    public static void aplicarMascaraInscricao(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals(oldValue)) return; // Evita loop infinito

            String digits = newValue.replaceAll("\\D", ""); // Remove tudo que não for número

            if (digits.length() > 14) {
                digits = digits.substring(0, 14);
            }

            String formatted = digits;
            if (digits.length() == 11) { // CPF: XXX.XXX.XXX-XX
                formatted = digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6, 9) + "-" + digits.substring(9);
            } else if (digits.length() == 14) { // CNPJ: XX.XXX.XXX/XXXX-XX
                formatted = digits.substring(0, 2) + "." + digits.substring(2, 5) + "." + digits.substring(5, 8) + "/" + digits.substring(8, 12) + "-" + digits.substring(12);
            }

            if (!formatted.equals(textField.getText())) { // Evita alteração desnecessária
                textField.setText(formatted);
                textField.positionCaret(formatted.length()); // Mantém o cursor na posição correta
            }
        });
    }

}
