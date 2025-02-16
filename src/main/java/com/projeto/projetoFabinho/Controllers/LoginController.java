package com.projeto.projetoFabinho.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class LoginController {

    @FXML
    private TextField usuarioField; // Campo de texto para o usuário

    @FXML
    private TextField senhaField; // Campo de texto para a senha

    @FXML
    private Label mensagemLabel; // Label para exibir mensagens de erro/sucesso

    
    @FXML
    public void initialize() {
        // Configura o evento de tecla pressionada para o campo de usuário
        usuarioField.setOnKeyPressed(this::handleKeyPressed);

        // Configura o evento de tecla pressionada para o campo de senha
        senhaField.setOnKeyPressed(this::handleKeyPressed);
    }

    // Método para lidar com o evento de tecla pressionada
    private void handleKeyPressed(KeyEvent event) {
        // Verifica se a tecla pressionada foi "Enter"
        if (event.getCode() == KeyCode.ENTER) {
            handleEntrarButtonAction(); // Chama o método de login
        }
    }
    
    @FXML
    protected void handleEntrarButtonAction() {
        String usuario = usuarioField.getText();
        String senha = senhaField.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mensagemLabel.setTextFill(Color.RED);
            mensagemLabel.setText("Preencha todos os campos!");
        } else if ("admin".equals(usuario) && "1234".equals(senha)) {
            mensagemLabel.setTextFill(Color.GREEN);
            mensagemLabel.setText("Login bem-sucedido!");

            // Navegar para a tela principal
            try {
                // Carrega o arquivo FXML da tela principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Admin.fxml"));
                
                Parent root = loader.load();

                // Obtém a janela atual (Stage)
                Stage stage = (Stage) usuarioField.getScene().getWindow();
                stage.centerOnScreen();

                // Define a nova cena (tela principal)
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Tela Principal");

                // Centraliza a janela na tela
                stage.centerOnScreen();
                stage.setResizable(false);
                
                
                
                stage.show();
 
            } catch (Exception e) {
                e.printStackTrace();
                mensagemLabel.setTextFill(Color.RED);
                mensagemLabel.setText("Erro ao carregar a tela principal.");
            }
        } else {
            mensagemLabel.setTextFill(Color.RED);
            mensagemLabel.setText("Usuário ou senha incorretos!");
        }

    }
}