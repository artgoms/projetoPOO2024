module com.projeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;
    
    opens com.projeto.projetoFabinho to javafx.fxml;
    exports com.projeto.projetoFabinho;
    exports com.projeto.projetoFabinho.Controllers;
    exports com.projeto.projetoFabinho.Controllers.Admin;
    exports com.projeto.projetoFabinho.Controllers.Employee;
    exports com.projeto.projetoFabinho.Models;
    exports com.projeto.projetoFabinho.Views;
}