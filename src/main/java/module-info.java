module com.projeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.desktop;

    
    opens com.projeto.projetoFabinho to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.Admin to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.Client to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.ClientList to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.CarParts to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.NewCarController to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.StockList to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.ServiceOS to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.OSPesquisa to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.SelecionarPecas to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.CarList to javafx.fxml;
    
    
    exports com.projeto.projetoFabinho;
    exports com.projeto.projetoFabinho.Controllers;
    exports com.projeto.projetoFabinho.Controllers.Admin;
    exports com.projeto.projetoFabinho.Controllers.NewCarController;
    exports com.projeto.projetoFabinho.Controllers.CarList;
    exports com.projeto.projetoFabinho.Models;
    exports com.projeto.projetoFabinho.Views;
}