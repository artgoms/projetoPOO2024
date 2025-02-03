module com.projeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
    
    opens com.projeto.projetoFabinho to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.Admin to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.Client to javafx.fxml;
    opens com.projeto.projetoFabinho.Controllers.ClientList to javafx.fxml;
    
    exports com.projeto.projetoFabinho;
    exports com.projeto.projetoFabinho.Controllers;
    exports com.projeto.projetoFabinho.Controllers.Admin;
    exports com.projeto.projetoFabinho.Controllers.Employee;
    exports com.projeto.projetoFabinho.Models;
    exports com.projeto.projetoFabinho.Views;
}