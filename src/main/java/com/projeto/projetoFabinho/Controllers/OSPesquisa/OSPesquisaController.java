package com.projeto.projetoFabinho.Controllers.OSPesquisa;

import com.projeto.projetoFabinho.Models.OSModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OSPesquisaController {

    @FXML
    private TextField pesquisaField;

    @FXML
    private TableView<OSModel> tabelaOS;

    @FXML
    private TableColumn<OSModel, String> colunaOS, colunaCliente, colunaVeiculoID, colunaVeiculoModelo, colunaSituacao;

    @FXML
    private Button selecionarBtn;

    private ObservableList<OSModel> listaOS = FXCollections.observableArrayList();
    private String osSelecionada = null;

    @FXML
    public void initialize() {
        // Configurar colunas para exibir os dados da OS sem StringProperty
        colunaOS.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNumeroOS()));
        colunaCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCliente()));
        colunaVeiculoID.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getVeiculoID()));
        colunaVeiculoModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo()));
        colunaSituacao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSituacao()));

        // Carregar OS (simulação de banco de dados)
        carregarOS();

        // Filtrar automaticamente conforme o usuário digita no campo de pesquisa
        pesquisaField.setOnKeyReleased(event -> pesquisarOS());
    }

    private void carregarOS() {
        // Simulação de dados (deveria ser carregado do banco de dados)
        listaOS.add(new OSModel("001", "João Silva", "123", "Fiat Uno", "Aberto"));
        listaOS.add(new OSModel("002", "Maria Oliveira", "456", "Ford Ka", "Finalizado"));
        listaOS.add(new OSModel("003", "Carlos Souza", "789", "Chevrolet Onix", "Cancelado"));

        tabelaOS.setItems(listaOS);
    }

    @FXML
    private void pesquisarOS() {
        String filtro = pesquisaField.getText().toLowerCase();
        if (filtro.isEmpty()) {
            tabelaOS.setItems(listaOS);
            return;
        }

        ObservableList<OSModel> listaFiltrada = FXCollections.observableArrayList();
        for (OSModel os : listaOS) {
            if (os.getNumeroOS().toLowerCase().contains(filtro) ||
                os.getCliente().toLowerCase().contains(filtro) ||
                os.getModelo().toLowerCase().contains(filtro)) {
                listaFiltrada.add(os);
            }
        }

        tabelaOS.setItems(listaFiltrada);
    }

    @FXML
    private void selecionarOS() {
        OSModel os = tabelaOS.getSelectionModel().getSelectedItem();
        if (os != null) {
            osSelecionada = os.getNumeroOS();
            fecharJanela();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma OS antes de continuar!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void fecharJanela() {
        Stage stage = (Stage) selecionarBtn.getScene().getWindow();
        stage.close();
    }

    public String getOSSelecionada() {
        return osSelecionada;
    }
}
