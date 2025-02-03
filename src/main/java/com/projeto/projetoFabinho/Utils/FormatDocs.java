package com.projeto.projetoFabinho.Utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DefaultStringConverter;

import java.util.regex.Pattern;

public class FormatDocs {

    // Aplica uma máscara de formatação a um TextField
    public static void aplicarMascara(TextField textField, String mascara) {
        Pattern pattern = Pattern.compile(mascara.replace("#", "\\d"));
        TextFormatter<String> formatter = new TextFormatter<>(new DefaultStringConverter(), "", change -> {
            String newText = change.getControlNewText();
            if (pattern.matcher(newText).matches()) {
                return change;
            } else {
                return null; // Rejeita a mudança se não corresponder à máscara
            }
        });

        textField.setTextFormatter(formatter);
    }

    // Remove a máscara de um texto formatado
    public static String removerMascara(String textoFormatado) {
        return textoFormatado.replaceAll("[^\\d]", "");
    }
}