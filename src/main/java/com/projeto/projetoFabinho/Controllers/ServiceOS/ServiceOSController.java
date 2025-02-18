package com.projeto.projetoFabinho.Controllers.ServiceOS;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.projeto.projetoFabinho.Controllers.OSPesquisa.OSPesquisaController;
import com.projeto.projetoFabinho.Controllers.SelecionarPecas.SelecionarPecasController;
import com.projeto.projetoFabinho.DAO.PecasDAO;
import com.projeto.projetoFabinho.DAO.ServiceOSDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.Models.ServiceOSModel;

public class ServiceOSController {

    @FXML
    private TextField idOSField, codigoField, veiculoIdField, tipoOS, valorOS, situacaoOS;

    @FXML
    private DatePicker entradaOS, previsaoOS;

    @FXML
    private TextArea descricaoOS;

    @FXML
    private ComboBox<String> pecasOS;

    @FXML
    private Button novoBtn, editarBtn, finalizarBtn, cancelarBtn, reabrirBtn, selecionarPecas;

    private List<String> pecasSelecionadas = new ArrayList<>();
    private double valorTotalPecas = 0.0;
    
    private final ServiceOSDAO osDAO = new ServiceOSDAO();
    
    @FXML
    public void initialize() {
    	
        int codigoOS = 1; // Pode ser obtido dinamicamente
        carregarOS(codigoOS);
    	
        // Desativa os botões ao abrir a tela
        editarBtn.setDisable(true);
        finalizarBtn.setDisable(true);
        cancelarBtn.setDisable(true);
        reabrirBtn.setDisable(true);

        // Inicializa a data de entrada como a data atual
        entradaOS.setValue(LocalDate.now());

        // Define o status inicial como "Aberto"
        situacaoOS.setText("Aberto");

        // Configura o evento de teclado para abrir a pesquisa de OS ao pressionar F2
        idOSField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F2) {
                abrirPesquisaOS();
            }
        });
    }

    @FXML
    private void abrirPesquisaOS() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/OSPesquisa.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pesquisar OS");
            stage.showAndWait();

            OSPesquisaController pesquisaController = loader.getController();
            String osSelecionada = pesquisaController.getOSSelecionada();
            if (osSelecionada != null) {
                carregarOS(osSelecionada);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarOS(String osId) {
        idOSField.setText(osId);
        
        // Define a situação da OS com base na busca do banco
        // Exemplo: situacaoOS.setText("Finalizado"); 

        // Ativar botões e campos
        editarBtn.setDisable(false);
        finalizarBtn.setDisable(true);
        cancelarBtn.setDisable(true);
        reabrirBtn.setDisable(true);
    }

    @FXML
    private void abrirSelecionarPecas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/SelecionarPecas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Selecionar Peças");
            stage.showAndWait();

            SelecionarPecasController pecasController = loader.getController();
            CarPartsModel pecaSelecionada = pecasController.getPecaSelecionada();

            if (pecaSelecionada != null) {
                pecasSelecionadas.add(pecaSelecionada.getNome());
                valorTotalPecas += pecaSelecionada.getValorVenda();
                atualizarPecasSelecionadas();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarOS(int codigoOS) {
        ServiceOSModel os = osDAO.buscarOS(codigoOS);
        if (os != null) {
            idOSField.setText(String.valueOf(os.getId()));
            codigoField.setText(String.valueOf(os.getClienteId()));
            veiculoIdField.setText(String.valueOf(os.getVeiculoId()));
            tipoOS.setText(os.getTipoOS());
            descricaoOS.setText(os.getDescricao());
            valorOS.setText(String.valueOf(os.getValor()));
            situacaoOS.setText(os.getSituacao());
            entradaOS.setValue(os.getDataEntrada());
            previsaoOS.setValue(os.getDataPrevisao());
        } else {
            System.out.println("Ordem de serviço não encontrada.");
        }
    }
    
    private void atualizarPecasSelecionadas() {
        pecasOS.getItems().clear();
        pecasOS.getItems().addAll(pecasSelecionadas);
        valorOS.setText(String.valueOf(valorTotalPecas));
        atualizarValorOS();
    }

    @FXML
    private void removerPeca() {
        String pecaSelecionada = pecasOS.getValue();
        if (pecaSelecionada != null) {
            pecasSelecionadas.remove(pecaSelecionada);
            atualizarPecasSelecionadas();
            atualizarValorOS();
        }
    }
    
    private void atualizarValorOS() {
        double total = 0.0;
        for (String pecaNome : pecasSelecionadas) {
            for (CarPartsModel peca : new PecasDAO().listarPecas()) {
                if (peca.getNome().equals(pecaNome)) {
                    total += peca.getValorVenda();
                    break;
                }
            }
        }
        valorOS.setText(String.format("%.2f", total));
    }
    
    

    @FXML
    private void finalizarOS() {
        situacaoOS.setText("Finalizado");
    }

    @FXML
    private void cancelarOS() {
        situacaoOS.setText("Cancelado");
    }

    @FXML
    private void reabrirOS() {
        situacaoOS.setText("Aberto");
    }
}
