package ru.kafpin.kafedraais.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.kafpin.kafedraais.dao.SectionDAO;
import ru.kafpin.kafedraais.models.Section;

import java.util.List;

public class MainController {

    @FXML
    private TableView<Section> sectionsTable;

    @FXML
    private TableColumn<Section, Integer> sectionIdCol;

    @FXML
    private TableColumn<Section, String> sectionNameCol;

    @FXML
    private TableColumn<Section, String> sectionDescCol;

    private SectionDAO sectionDAO = new SectionDAO();

    @FXML
    public void initialize() {
        // Привязка столбцов к полям класса Section (используются геттеры getId, getName, getDescription)
        sectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        sectionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadSectionsData();
    }

    private void loadSectionsData() {
        List<Section> sections = sectionDAO.getAll();
        ObservableList<Section> observableList = FXCollections.observableArrayList(sections);
        sectionsTable.setItems(observableList);
    }
}
