module ru.kafpin.kafedraais {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ru.kafpin.kafedraais to javafx.fxml;
    exports ru.kafpin.kafedraais;

}