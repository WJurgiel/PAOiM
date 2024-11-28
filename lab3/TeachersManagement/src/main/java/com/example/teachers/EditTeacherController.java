package com.example.teachers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.teachers.ClassPreviewBoardController.currentClassID;
import static com.example.teachers.ClassPreviewBoardController.currentGroup;

public class EditTeacherController extends SceneChanger implements Initializable {
    @FXML
    private Label nameSurnameLabel;
    @FXML
    private TextField salaryToAdd;
    @FXML
    private ChoiceBox<TeacherConditions> conditionToChange;

    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    Teacher currentTeacher = SharedData.getClassesInGroup().get(currentGroup).get(currentClassID).getTeachers().get(ClassPreviewBoardController.currentTeacher);
    @FXML
    public void Submit(ActionEvent event) throws IOException {
        currentTeacher.addSalary(Double.valueOf(salaryToAdd.getText()));
        currentTeacher.setCondition(conditionToChange.getValue());

        changeScene(event, "class-preview-board.fxml");
    }
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        changeScene(event, "class-preview-board.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameSurnameLabel.setText(currentTeacher.getName() + currentTeacher.getSurname());
        salaryToAdd.setText("0");
        conditionToChange.setItems(FXCollections.observableArrayList(TeacherConditions.values()));
        conditionToChange.setValue(currentTeacher.getCondition());
    }
}
