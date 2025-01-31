package com.projeto.projetoFabinho.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminController {
	   // Método para abrir a tela de Consulta OS
    @FXML
    private void abrirConsultaOS() {
        carregarTela("/fxml/Admin/ServiceOS.fxml", "Consulta OS");
    }

    // Método para abrir a tela de Nova OS
    @FXML
    private void abrirNovoOS() {
        carregarTela("/fxml/Admin/ServiceOS.fxml", "Nova OS");
    }

    // Método para abrir a tela de Verificar Estoque
    @FXML
    private void abrirVerificarEstoque() {
        carregarTela("/fxml/Admin/verificar_estoque.fxml", "Verificar Estoque");
    }

    // Método para abrir a tela de Verificar Cliente
    @FXML
    private void abrirVerificarCliente() {
        carregarTela("/fxml/Admin/Client.fxml", "Verificar Cliente");
    }

    // Método para abrir a tela de Relatórios
    @FXML
    private void abrirRelatorios() {
        carregarTela("/fxml/Admin/relatorios.fxml", "Relatórios");
    }

    // Método genérico para carregar uma tela
    private void carregarTela(String fxmlPath, String titulo) {
        try {
            // Carrega o arquivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Cria uma nova cena
            Scene scene = new Scene(root);

            // Cria um novo stage (janela)
            Stage stage = new Stage();

            stage.setTitle(titulo);
            stage.setScene(scene);

            
            // Centraliza a janela na tela
            stage.centerOnScreen();
            stage.setResizable(false);
            
            // Exibe a nova janela
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + titulo);
        }
    }
}
