package com.example.teachers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

public class AddTeacherFormController extends SceneChanger {
    Teacher t1 = new Teacher("Karol", "Laudański", TeacherConditions.ABSENT, 1080, 3000 );
    Teacher t2 = new Teacher("Melania", "Laudańska", TeacherConditions.DELEGATION, 1850, 4000 );
    @FXML
    public void addExampleTeacher(ActionEvent event) throws IOException {
        ObservableList<ClassTeacher> group = SharedData.getClassesInGroup().get(ClassPreviewBoardController.currentGroup);
        if(group != null && !group.isEmpty()){
            ClassTeacher chosenClass = group.get(ClassPreviewBoardController.currentClassID); // tryout for first class only
            if(chosenClass.getTeachers().size() >= chosenClass.maxTeachers){
                displayNoMoreRoomDialog();
                return;
            }
            chosenClass.addTeacher(t1);
        }
        changeScene(event, "class-preview-board.fxml");

    }
    private void displayNoMoreRoomDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There's no more room left in this class");
        alert.showAndWait();
    }

}
