module ru.kafpin.kafedraais {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ru.kafpin.kafedraais to javafx.fxml;
    exports ru.kafpin.kafedraais;
    
    opens ru.kafpin.kafedraais.controllers to javafx.fxml;
    exports ru.kafpin.kafedraais.controllers;
    
    opens ru.kafpin.kafedraais.models to javafx.base;
    exports ru.kafpin.kafedraais.models;
}