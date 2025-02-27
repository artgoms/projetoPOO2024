package com.projeto.projetoFabinho.Controllers.NewCarController;

import com.projeto.projetoFabinho.Controllers.ClientList.ClientListController;
import com.projeto.projetoFabinho.DAO.CarDAO;
import com.projeto.projetoFabinho.Models.CarModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class NewCarController {

    @FXML
    private TextField txtId, txtCodigo, txtMarca, txtModelo, txtAnoFabricacao, txtPlaca;

    @FXML
    private TextArea txtObservacoes;
    

    @FXML
    private ChoiceBox<String> situacaoChoice;

    @FXML
    private Button btnNovo, btnEditar, btnGravar;

    private int clienteId;
    
    private boolean modoEdicao = false;
    private CarDAO carDAO = new CarDAO();
    private CarModel carroAtual = null;

    @FXML
    public void initialize() {
        txtCodigo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F2) {
                abrirClientList();
            }
        });
        System.out.println("Inicializando NewCarController...");

        // Adicionar opções ao ChoiceBox
        situacaoChoice.getItems().addAll("Concluído", "Em andamento", "Aguardando peça", "Outro");

        // 🔹 ID gerado automaticamente ao abrir a tela
        txtId.setText(String.valueOf(carDAO.getNextCarId()));
        txtId.setDisable(true); // O ID nunca pode ser editado

        // 🔹 Ativar o modo edição automaticamente ao iniciar a tela
        setModoEdicao(true);
    }

    @FXML
    private void onNovoClick() {
        if (modoEdicao) {
            // 🔹 Se estiver no modo edição, ao clicar em "Cancelar" tudo será resetado
            limparCampos();
            setModoEdicao(false);
        } else {
            // 🔹 Ativar modo edição para adicionar um novo carro
            setModoEdicao(true);
            txtId.setText(String.valueOf(carDAO.getNextCarId())); // Gerar novo ID automaticamente
        }
    }

    @FXML
    private void onEditarClick() {
        if (carroAtual != null) {
            System.out.println("Entrando no modo edição para o carro: " + carroAtual.getPlaca());
            setModoEdicao(true);
        }
    }

    @FXML
    private void onGravarClick() {
        if (!modoEdicao) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim()); // ID já foi gerado automaticamente
            int codigoCliente = Integer.parseInt(txtCodigo.getText().trim());
            String situacao = situacaoChoice.getValue();
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String anoFabricacao = txtAnoFabricacao.getText().trim();
            String placa = txtPlaca.getText().trim();
            String observacoes = txtObservacoes.getText().trim();

            if (situacao == null || situacao.isEmpty()) {
                showAlert("Erro", "Por favor, selecione uma situação válida.");
                return;
            }

            if (carroAtual == null) {
                // 🔹 Criar novo carro e mantê-lo na tela para edição futura
                CarModel novoCarro = new CarModel(id, codigoCliente, situacao, marca, modelo, anoFabricacao, placa, observacoes);
                carDAO.insertCar(novoCarro);
                carroAtual = carDAO.getCarByPlaca(placa); // Buscar o carro salvo
            } else {
                // 🔹 Atualizar carro existente
                carroAtual.setClienteId(codigoCliente);
                carroAtual.setSituacao(situacao);
                carroAtual.setMarca(marca);
                carroAtual.setModelo(modelo);
                carroAtual.setAnoFabricacao(anoFabricacao);
                carroAtual.setPlaca(placa);
                carroAtual.setObservacoes(observacoes);
                carDAO.updateCar(carroAtual);
            }

            // 🔹 Manter os dados na tela após salvar
            setModoEdicao(true);
            btnEditar.setDisable(false);

        } catch (NumberFormatException e) {
            showAlert("Erro", "O Código do Cliente deve ser um número válido!");
        }
    }
    
    private void abrirClientList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ClientList.fxml"));
            Parent scene = loader.load();

            // Obter o controlador e definir o listener para capturar o código do cliente
            ClientListController controller = loader.getController();
            controller.setSelectionListener(codigoCliente -> txtCodigo.setText(codigoCliente));

            Stage stage = new Stage();
            stage.setTitle("Lista de Clientes");
            stage.setScene(new Scene(scene));
            
            // Adiciona um evento para fechar a janela ao pressionar "Esc"
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            });
            
            stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
        System.out.println("Novo carro será associado ao cliente ID: " + clienteId);
    }

    private void setModoEdicao(boolean ativado) {
        modoEdicao = ativado;

        txtCodigo.setDisable(!ativado);
        txtMarca.setDisable(!ativado);
        txtModelo.setDisable(!ativado);
        txtAnoFabricacao.setDisable(!ativado);
        txtPlaca.setDisable(!ativado);
        txtObservacoes.setDisable(!ativado);
        situacaoChoice.setDisable(!ativado);

        btnGravar.setDisable(!ativado);
        btnEditar.setDisable(ativado);
        btnNovo.setDisable(false);
        btnNovo.setText(modoEdicao ? "Cancelar" : "Novo");
    }

    private void limparCampos() {
        txtId.setText(String.valueOf(carDAO.getNextCarId())); // Gera novo ID automaticamente
        txtCodigo.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtAnoFabricacao.clear();
        txtPlaca.clear();
        txtObservacoes.clear();
        situacaoChoice.getSelectionModel().clearSelection();
        carroAtual = null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
