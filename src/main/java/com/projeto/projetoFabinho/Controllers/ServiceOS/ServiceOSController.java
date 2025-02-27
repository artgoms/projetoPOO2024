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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.projeto.projetoFabinho.Controllers.CarList.CarListController;
import com.projeto.projetoFabinho.Controllers.ClientList.ClientListController;
import com.projeto.projetoFabinho.Controllers.OSPesquisa.OSPesquisaController;
import com.projeto.projetoFabinho.Controllers.SelecionarPecas.SelecionarPecasController;
import com.projeto.projetoFabinho.DAO.PecasDAO;
import com.projeto.projetoFabinho.DAO.ServiceOSDAO;
import com.projeto.projetoFabinho.Models.CarPartsModel;
import com.projeto.projetoFabinho.Models.ServiceOSModel;

public class ServiceOSController {

	@FXML
	private TextField idOSField, codigoField, veiculoIdField, tipoOSField, valorOS, situacaoOS;

	@FXML
	private DatePicker entradaOS, previsaoOS;

	@FXML
	private TextArea descricaoOS;

	@FXML
	private ComboBox<String> pecasOS;

	private List<CarPartsModel> listaDePecas = new ArrayList<>();

	@FXML
	private Button novoBtn, editarBtn, salvarBtn, finalizarBtn, cancelarBtn, reabrirBtn, selecionarPecas;

	private List<String> pecasSelecionadas = new ArrayList<>();
	private double valorTotalPecas = 0.0;

	private final ServiceOSDAO osDAO = new ServiceOSDAO();
	private ServiceOSModel serviceOS = new ServiceOSModel();
	private List<CarPartsModel> listaPecas = new ArrayList<>();

	private Map<String, Integer> mapaPecas = new HashMap<>();

	private int clienteId; // Armazena o ID do cliente selecionado

	@FXML
	public void initialize() {

		inicializarPecas();

		codigoField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.F2) {
				abrirPesquisaClientes();
			}
		});

		veiculoIdField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.F2) {
				abrirPesquisaVeiculos();
			}
		});

		habilitarCampos(false);
		desabilitarBotoes(false);

		// Adiciona um listener para detectar quando o usuário digita um código no campo
		// idOSField
		idOSField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				try {
					int codigoOS = Integer.parseInt(newValue);
					carregarOS(codigoOS);
				} catch (NumberFormatException e) {
					System.err.println("Código inválido digitado: " + newValue);
				}
			}
		});

		System.out.println("ServiceOSController OK");

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

		entradaOS.setValue(LocalDate.now()); // Inicializa a data de entrada como a data atual
		situacaoOS.setText("Aberto"); // Define o status inicial como "Aberto"

	}

	private void abrirPesquisaClientes() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ClientList.fxml"));
			Parent root = loader.load();

			ClientListController clientListController = loader.getController();
			clientListController.setSelectionListener(clienteCodigo -> {
				codigoField.setText(clienteCodigo);

				clienteId = Integer.parseInt(clienteCodigo);
			});

			// Criar a janela modal
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Pesquisar Cliente");
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void abrirPesquisaVeiculos() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/CarList.fxml"));
			Parent root = loader.load();

			CarListController carListController = loader.getController();

			// Verifica se clienteId está definido antes de passar para a tela de veículos
			if (clienteId > 0) {
				carListController.setClienteId(clienteId);
			} else {
				System.err.println("Nenhum cliente selecionado antes de pesquisar veículos.");
			}

			// Configura o listener para receber a seleção do carro
			carListController.setSelectionListener(carroId -> {
				veiculoIdField.setText(carroId); // Preenche o campo de veiculoIdField com o id do veículo selecionado
			});

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Pesquisar Veículos");
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

			// Obtendo o controlador e a OS selecionada
			OSPesquisaController pesquisaController = loader.getController();
			String osSelecionada = pesquisaController.getOSSelecionada();

			if (osSelecionada != null && !osSelecionada.trim().isEmpty()) { // Conversão de String pra int
				try {
					int codigoOS = Integer.parseInt(osSelecionada);
					carregarOS(codigoOS);
				} catch (NumberFormatException e) {
					System.err.println("Erro ao converter OS selecionada para inteiro: " + osSelecionada);
				}
			} else {
				System.out.println("Nenhuma OS foi selecionada.");
			}

		} catch (IOException e) {
			System.err.println("Erro ao abrir a janela de pesquisa de OS: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void carregarOS(int codigoOS) {
		ServiceOSModel os = osDAO.buscarOSPorId(codigoOS);

		if (os != null) {
			idOSField.setText(String.valueOf(os.getId()));
			codigoField.setText(String.valueOf(os.getClienteId()));
			veiculoIdField.setText(String.valueOf(os.getVeiculoId()));
			tipoOSField.setText(os.getTipoOS());
			descricaoOS.setText(os.getDescricao());
			valorOS.setText(String.valueOf(os.getValor()));
			situacaoOS.setText(os.getSituacao());
			entradaOS.setValue(os.getDataEntrada());
			previsaoOS.setValue(os.getDataPrevisao());

			// Buscar as peças da OS
			listaPecas = osDAO.buscarPecasPorOS(codigoOS);

			// Limpa e preenche o ComboBox apenas com os nomes das peças
			pecasOS.getItems().clear();
			for (CarPartsModel peca : listaPecas) {
				pecasOS.getItems().add(peca.getNome());
			}

		} else {
			System.out.println("Ordem de serviço não encontrada para o código: " + codigoOS);
		}
	}

	@FXML
	private void handleNovo() {
		if ("Cancelar".equals(novoBtn.getText())) {
			handleCancelar();
			novoBtn.setText("Novo");
			editarBtn.setDisable(false);
			return;
		}

		serviceOS = new ServiceOSModel(); // Novo OS

		habilitarCampos(true);
		desabilitarBotoes(true);
		editarBtn.setDisable(true);

		limparCampos();
		atualizarCodigo();

		novoBtn.setText("Cancelar");

	}

	@FXML
	private void salvarOrdemServico() {
		try {
			// Verifica se o campo ID está preenchido corretamente
			Integer id = (idOSField.getText().isEmpty() || idOSField.getText().equals("0")) ? null
					: Integer.parseInt(idOSField.getText());

			int clienteId = Integer.parseInt(codigoField.getText());
			int veiculoId = Integer.parseInt(veiculoIdField.getText());
			String tipoOS = tipoOSField.getText();
			String descricao = descricaoOS.getText();
			BigDecimal valor = new BigDecimal(valorOS.getText().replace(",", "."));
			String situacao = situacaoOS.getText();
			LocalDate dataEntrada = entradaOS.getValue();
			LocalDate dataPrevisao = previsaoOS.getValue();

			PecasDAO pecasDAO = new PecasDAO();
			ServiceOSModel os;

			if (id != null) {
				ServiceOSModel osExistente = osDAO.buscarOSPorId(id); // Verifica se a OS já existe antes de atualizar
				if (osExistente == null) {
					System.err.println("❌ Erro: A OS com ID " + id + " não existe. Criando nova OS...");
					id = null; // Força a criação de uma nova OS
				}
			}

			// Criar objeto OS (nova ou existente)
			os = (id == null)
					? new ServiceOSModel(clienteId, veiculoId, tipoOS, descricao, valor, situacao, dataEntrada,
							dataPrevisao)
					: new ServiceOSModel(id, clienteId, veiculoId, tipoOS, descricao, valor, situacao, dataEntrada,
							dataPrevisao);

			List<CarPartsModel> pecasSelecionadas = new ArrayList<>(); // Adicionar peças associadas à OS
			for (String nomePeca : pecasOS.getItems()) {
				int pecaId = pecasDAO.buscarIdPorNome(nomePeca);
				BigDecimal valorPeca = new BigDecimal(
						pecasDAO.buscarValorPecaPorId(pecaId).toString().replace(",", "."));

				pecasSelecionadas.add(new CarPartsModel(pecaId, nomePeca, valorPeca));
			}

			if (id != null) {
				boolean atualizado = osDAO.atualizarOrdemServico(os); // Se ID não for nulo, atualiza a OS existente
				if (atualizado) {
					osDAO.salvarPecasOrdemServico(id, pecasSelecionadas);
					System.out.println("✅ Ordem de Serviço ID " + id + " atualizada com sucesso!");
				} else {
					System.err.println("❌ Erro ao atualizar OS ID " + id);
				}
			} else {
				int novoOsId = osDAO.salvarOrdemServicoComPecas(os); // Se ID for nulo, cria uma nova OS
				if (novoOsId > 0) {
					osDAO.salvarPecasOrdemServico(novoOsId, pecasSelecionadas);
					System.out.println("✅ Nova OS criada com sucesso! ID: " + novoOsId);
					idOSField.setText(String.valueOf(novoOsId)); // Atualiza o campo ID com o novo ID
				} else {
					System.err.println("❌ Erro ao criar nova OS.");
				}
			}

		} catch (NumberFormatException e) {
			System.err.println("❌ Erro ao converter número: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("❌ Erro inesperado: " + e.getMessage());
			e.printStackTrace();
		}

		habilitarCampos(false);
		desabilitarBotoes(false);
		limparCampos();
	}

	@FXML
	private void handleSalvar() {
		if (serviceOS == null) {
			serviceOS = new ServiceOSModel();
		}

		atualizarOS(); // atualiza o OS

		desabilitarBotoes(true);
		habilitarCampos(false);

	}

	private void atualizarOS() {
		serviceOS.setId(Integer.parseInt(idOSField.getText()));
		serviceOS.setClienteId(Integer.parseInt(codigoField.getText()));
		serviceOS.setVeiculoId(Integer.parseInt(veiculoIdField.getText()));
		serviceOS.setTipoOS(tipoOSField.getText());
		serviceOS.setDescricao(descricaoOS.getText());
		serviceOS.setValor(new BigDecimal(valorOS.getText().replace(",", ".")));
		serviceOS.setSituacao(situacaoOS.getText());
		serviceOS.setDataEntrada(entradaOS.getValue());
		serviceOS.setDataPrevisao(previsaoOS.getValue());

	}

	@FXML
	private void handleEditar() {
		if (serviceOS == null)
			return;

		if ("Cancelado".equals(situacaoOS.getText())) {
			habilitarCampos(false);
			desabilitarBotoes(false);
			editarBtn.setDisable(true);
			return;
		}

		habilitarCampos(true);
		desabilitarBotoes(true);

		editarBtn.setDisable(true);
		salvarBtn.setDisable(false);
		novoBtn.setText("Cancelar");

	}

	@FXML
	private void handleCancelar() {

		if (serviceOS == null)
			return;

		desabilitarBotoes(false);
		habilitarCampos(false);
		salvarBtn.setDisable(true);
	}

	private void atualizarCodigo() {
		int maiorId = osDAO.obterMaiorId() + 1; // Obtém o maior ID e soma 1 para o próximo cliente
		idOSField.setText(String.valueOf(maiorId));
	}

	private void desabilitarBotoes(boolean habilitar) {

		salvarBtn.setDisable(!habilitar);
		finalizarBtn.setDisable(!habilitar);
		cancelarBtn.setDisable(!habilitar);
		reabrirBtn.setDisable(!habilitar);
		selecionarPecas.setDisable(!habilitar);

	}

	private void habilitarCampos(boolean habilitar) {

		codigoField.setDisable(!habilitar);
		veiculoIdField.setDisable(!habilitar);
		tipoOSField.setDisable(!habilitar);
		valorOS.setDisable(!habilitar);
		entradaOS.setDisable(!habilitar);
		previsaoOS.setDisable(!habilitar);
		descricaoOS.setDisable(!habilitar);
		pecasOS.setDisable(!habilitar);

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
				PecasDAO pecasDAO = new PecasDAO();
				pecasDAO.diminuirEstoque(pecaSelecionada.getId(), 1); // Diminuir a quantidade em 1
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
			Integer idPeca = mapaPecas.get(pecaSelecionada);
			if (idPeca != null) {

				pecasSelecionadas.remove(pecaSelecionada);

				PecasDAO pecasDAO = new PecasDAO();
				pecasDAO.aumentarEstoque(idPeca, 1);
				
				atualizarPecasSelecionadas();
				atualizarValorOS();
			}
		}
	}

	public void inicializarPecas() {
		 PecasDAO pecasDAO = new PecasDAO();
		 listaDePecas = pecasDAO.listarPecas();
		
		
			// aqui verifica se a lista é nula
		 if (listaDePecas == null || listaDePecas.isEmpty()) {
	            System.out.println("Erro: Lista de peças não carregada corretamente.");
	            return;  // Retorna se a lista estiver vazia ou nula
	        }
		
		
		// Suponhetemos que 'listaDePecas' seja uma lista de objetos CarPartsModel, ele
		// adiciona as peças no ComboBox e também ao mapa
		for (CarPartsModel peca : listaDePecas) {
			pecasOS.getItems().add(peca.getNome());
			mapaPecas.put(peca.getNome(), peca.getId());
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
		tipoOSField.clear();
		valorOS.clear();
		previsaoOS.setValue(null);
		descricaoOS.clear();
		pecasOS.getItems().clear(); // Remove todas as peças da lista
		pecasOS.getSelectionModel().clearSelection(); // Remove a seleção
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