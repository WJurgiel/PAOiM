package com.example.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddTeacherFormController extends SceneChanger implements Initializable {

    @FXML private TextField nameTF;
    @FXML private TextField surnameTF;
    @FXML private TextField birthTF;
    @FXML private TextField salaryTF;
    @FXML private ChoiceBox<String> condCB;
    Teacher t1 = new Teacher("Karol", "Laudański", TeacherConditions.ABSENT, 1080, 3000 );
    Teacher t2 = new Teacher("Melania", "Laudańska", TeacherConditions.DELEGATION, 1850, 4000 );
    @FXML
    public void addExampleTeacher(ActionEvent event) throws IOException {

        ObservableList<ClassTeacher> group = SharedData.getClassesInGroup().get(ClassPreviewBoardController.currentGroup);
        if(group != null && !group.isEmpty()){
            ClassTeacher chosenClass = group.get(ClassPreviewBoardController.currentClassID);
            if(chosenClass.getTeachers().size() >= chosenClass.maxTeachers){
                displayNoMoreRoomDialog();
                return;
            }

            if(isDataWrong()){
                displayWrongTeacher();
            }
            else{
                String name = nameTF.getText();
                String surname = surnameTF.getText();
                Integer birtYear = Integer.valueOf(birthTF.getText());
                Integer salary = Integer.valueOf(salaryTF.getText());
                TeacherConditions cond = TeacherConditions.valueOf(condCB.getValue());
                Teacher teacherToBeAdded = new Teacher(name,surname,cond,birtYear,salary);
                chosenClass.addTeacher(teacherToBeAdded);
                changeScene(event, "class-preview-board.fxml");
            }

        }


    }
    private void displayNoMoreRoomDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There's no more room left in this class");
        alert.showAndWait();
    }
    private void displayWrongTeacher(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Data is incorrect");
        alert.showAndWait();
    }
    private boolean isDataWrong(){
        boolean nameWrong = nameTF.getText().trim().isEmpty() || nameTF.getText().matches(".*\\d.*");
        boolean surnameWrong = (surnameTF.getText().trim().isEmpty() || surnameTF.getText().matches(".*\\d.*"));
        boolean birthWrong = (birthTF.getText().trim().isEmpty() || birthTF.getText().matches(".*\\D.*") || Integer.parseInt(birthTF.getText()) > 2024);
        boolean salaryWrong = salaryTF.getText().trim().isEmpty() || !salaryTF.getText().matches("\\d+");
        return nameWrong || surnameWrong || birthWrong || salaryWrong;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         condCB.setItems(FXCollections.observableArrayList(Arrays.stream(TeacherConditions.values()).map(Enum::name).toList()));
         condCB.setValue(TeacherConditions.PRESENT.name());
    }
}
