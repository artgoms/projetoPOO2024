package com.projeto.projetoFabinho.Controllers.OSPesquisa;

import com.projeto.projetoFabinho.DAO.OSPesquisaDAO;
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

    private final OSPesquisaDAO osDAO = new OSPesquisaDAO(); // DAO para buscar OS do banco

    @FXML
    public void initialize() {
        // Configurar colunas da tabela
        colunaOS.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNumeroOS()));
        colunaCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCliente()));
        colunaVeiculoID.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getVeiculoID()));
        colunaVeiculoModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo()));
        colunaSituacao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSituacao()));

        // Carregar OS do banco de dados ao iniciar
        carregarOS("");

        // Filtrar automaticamente conforme o usuário digita no campo de pesquisa
        pesquisaField.textProperty().addListener((obs, oldValue, newValue) -> carregarOS(newValue));
    }

    private void carregarOS(String filtro) {
        listaOS = osDAO.buscarOS(filtro);
        tabelaOS.setItems(listaOS);
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

    @FXML
    public void pesquisarOS() {
        String filtro = pesquisaField.getText().toLowerCase();
        carregarOS(filtro);
    }

    private void fecharJanela() {
        Stage stage = (Stage) selecionarBtn.getScene().getWindow();
        stage.close();
    }

    public String getOSSelecionada() {
        return osSelecionada;
    }
}
