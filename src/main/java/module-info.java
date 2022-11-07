module iths.komigen.laborationtre {
    requires javafx.controls;
    requires javafx.fxml;


    opens iths.komigen.laborationtre to javafx.fxml;
    exports iths.komigen.laborationtre;
    exports iths.komigen.laborationtre.controller;
    opens iths.komigen.laborationtre.controller to javafx.fxml;
}