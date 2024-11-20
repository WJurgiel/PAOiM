package com.example.teachers;

import com.sun.jdi.ClassType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainBoardController extends SceneChanger implements Initializable {


    @FXML
    public ChoiceBox<String> groupList;
    @FXML
    public TableView classesList;

    @FXML
    private TableColumn<ClassTeacher, String> classNameColumn;
    @FXML
    private TableColumn<ClassTeacher, String> classFillingColumn;

    @FXML
    private AnchorPane groupsPanel;
    @FXML
    private AnchorPane teachersPanel;
    private String[] classesOnStart = {"Nauczyciele sp2", "Nauczyciele 3LO", "Nauczyciele 4T"};

    Teacher t1 = new Teacher("Karol", "Laudański", TeacherConditions.ABSENT, 1080, 3000 );
    Teacher t2 = new Teacher("Melania", "Laudańska", TeacherConditions.DELEGATION, 1850, 4000 );
    ClassTeacher mathematicians1 = new ClassTeacher("Mathematicians", 10);
    @FXML
    public void logout(ActionEvent event) throws IOException {
        changeScene(event, "login-board.fxml");
        setStageTitle("Login");
    }
    @FXML
    public void addGroup(ActionEvent event) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Group");
        dialog.setHeaderText("Insert name of the new group");
        dialog.setContentText("Name: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> SharedData.getGroupList().add(s));
    }
    @FXML
    public void toggleTeachersPanel(ActionEvent event) throws IOException {
        teachersPanel.setOpacity(1);
        groupsPanel.setOpacity(0);
    }
    @FXML void toggleGroupsPanel(ActionEvent event) throws IOException {
        teachersPanel.setOpacity(0);
        groupsPanel.setOpacity(1);
    }
    @Override
    public void initialize(URL args0, ResourceBundle args1) {
        groupList.setItems(SharedData.getGroupList());
        mathematicians1.addTeacher(t1);
        mathematicians1.addTeacher(t2);

        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("theClassName"));
        classFillingColumn.setCellValueFactory(new PropertyValueFactory<>("filling"));

        classesList.setItems(FXCollections.observableArrayList(mathematicians1));
    }
}
