package com.projeto.projetoFabinho.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
        carregarTela("/fxml/Admin/StockList.fxml", "Estoque");
    }

    // Método para abrir a tela de Verificar Cliente
    @FXML
    private void abrirVerificarCliente() {
        carregarTela("/fxml/Admin/Client.fxml", "Cliente");
    }

    // Método para abrir a tela de Relatórios
    @FXML
    private void abrirRelatorios() {
        carregarTela("/fxml/Admin/relatorios.fxml", "Relatórios");
    }
    
    
    
    // Método para abrir a tela de Novo Veiculo
    @FXML
    private void abrirNovoVeiculo() {
        carregarTela("/fxml/Admin/NewCar.fxml", "Novo Veículo");
    }
    
    // Método para abrir a tela de Nova Peça
    @FXML
    private void abrirNovaPeca() {
        carregarTela("/fxml/Admin/CarParts.fxml", "Cadastro de Peças");
    }

    private void carregarTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle(titulo);
            stage.setScene(scene);

            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            });
            
            
            // centraliza a janela na tela
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
