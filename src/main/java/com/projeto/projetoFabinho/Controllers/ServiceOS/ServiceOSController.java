package com.projeto.projetoFabinho.Controllers.ServiceOS;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;
import java.math.BigDecimal;
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
    
    private boolean editando = false; // Variável de controle
    
    @FXML
    public void initialize() {
	  	
    	
    	novoBtn.setOnAction(event -> alternarModoEdicao());
    
        // Configura o evento de teclado para abrir a pesquisa de OS ao pressionar F2
        idOSField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F2) {
                abrirPesquisaOS();
            }
        });

        // Configura o evento de teclado para carregar a OS ao pressionar Enter
        idOSField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String texto = idOSField.getText();
                if (!texto.isEmpty()) {
                    try {
                        int codigoOS = Integer.parseInt(texto);
                        carregarOS(codigoOS);
                    } catch (NumberFormatException e) {
                        System.out.println("Número inválido para OS.");
                    }
                }
            }
        });
        // Desativa os botões ao abrir a tela
        editarBtn.setDisable(false);
        finalizarBtn.setDisable(true);
        cancelarBtn.setDisable(true);
        reabrirBtn.setDisable(true);

        // Inicializa a data de entrada como a data atual
        entradaOS.setValue(LocalDate.now());

        // Define o status inicial como "Aberto"
        situacaoOS.setText("Aberto");


     
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
	
	private void novoAction() {
		
	}
	
	
	private void alternarModoEdicao() {
	    if (!editando) {
	    	habilitarCampos(true);
	    	desabilitarBotoes(true);
	    	
	        // Definir que está no modo de edição
	        editando = true;

	        // Muda o texto do botão para "Salvar" e "Cancelar"
	        editarBtn.setText("Salvar");
	        novoBtn.setText("Cancelar");

	    } else {
	    	habilitarCampos(false);
	    	desabilitarBotoes(false);
	        limparCampos();

	        // Muda o botão de volta para "Novo"
	        novoBtn.setText("Novo");
	        
	        // Mudar botão para "Editar"
	        editarBtn.setText("Editar");

	        // Sai do modo de edição
	        editando = false;
	        
	    }
	}
	
	private void desabilitarBotoes(boolean habilitar) {
        
        // Ativa botões úteis
        finalizarBtn.setDisable(!habilitar);
        cancelarBtn.setDisable(!habilitar);
        reabrirBtn.setDisable(!habilitar);
        selecionarPecas.setDisable(!habilitar);
        
	}

	
	private void habilitarCampos(boolean habilitar) {
		// Ativa os campos para nova OS ou
        // Cancela a edição 
        codigoField.setDisable(!habilitar);
        veiculoIdField.setDisable(!habilitar);
        tipoOS.setDisable(!habilitar);
        valorOS.setDisable(!habilitar);
        entradaOS.setDisable(!habilitar);
        previsaoOS.setDisable(!habilitar);
        descricaoOS.setDisable(!habilitar);
        pecasOS.setDisable(!habilitar);
	
	}

	
	@FXML
    public void salvarOrdemServico() {
        try {
            ServiceOSModel ordemServico = new ServiceOSModel();
            
            // Pegando valores dos campos da UI
            ordemServico.setClienteId(Integer.parseInt(codigoField.getText()));
            ordemServico.setVeiculoId(Integer.parseInt(veiculoIdField.getText()));
            ordemServico.setTipoOS(tipoOS.getText());
            String valorTexto = valorOS.getText().replace(",", "."); // Troca vírgula por ponto se necessário
            ordemServico.setValor(new BigDecimal(valorTexto));
            ordemServico.setSituacao(situacaoOS.getText());
            ordemServico.setDescricao(descricaoOS.getText());
            ordemServico.setDataEntrada(entradaOS.getValue());
            ordemServico.setDataPrevisao(previsaoOS.getValue());

            // Chamando DAO para salvar no banco
            ServiceOSDAO serviceOSDAO = new ServiceOSDAO();
            int novoId = serviceOSDAO.salvar(ordemServico);  // Recebe o ID retornado

            // Atualiza o campo de ID na tela
            idOSField.setText(String.valueOf(novoId));

            // Mensagem de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ordem de Serviço salva com sucesso!", ButtonType.OK);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao salvar Ordem de Serviço: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
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
    
    
     
    private void limparCampos() {
        idOSField.clear();
        codigoField.clear();
        veiculoIdField.clear();
        tipoOS.clear();
        valorOS.clear();
        previsaoOS.setValue(null);
        descricaoOS.clear();
        pecasOS.getSelectionModel().clearSelection();
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
