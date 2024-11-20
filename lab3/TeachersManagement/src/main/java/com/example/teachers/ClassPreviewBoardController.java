package com.example.teachers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassPreviewBoardController extends SceneChanger implements Initializable {

    public String currentSceneName;
    public static String currentGroup;
    public static int currentClassID;
    @FXML public TableView<Teacher> teachersList;
    @FXML public TableColumn<Teacher, String> nameCol;
    @FXML public TableColumn<Teacher, String> surnameCol;
    @FXML public TableColumn<Teacher, String> birthCol;
    @FXML public TableColumn<Teacher, String> salaryCol;
    @FXML public TableColumn<Teacher, String> condCol;



    @FXML
    public void goBack(ActionEvent event) throws IOException {
        changeScene(event, "main-board.fxml");
        setStageTitle("Main board");
    }
    @FXML
    public void addTeacher(ActionEvent event) throws IOException{
        changeScene(event, "add-teacher-form.fxml");
        setStageTitle("Add teacher");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        condCol.setCellValueFactory(new PropertyValueFactory<>("teacherCondition"));


        teachersList.setItems(SharedData.getClassesInGroup().get(currentGroup).get(currentClassID).getTeachers());
    }
}
