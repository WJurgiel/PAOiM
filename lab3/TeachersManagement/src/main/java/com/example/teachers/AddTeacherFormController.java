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
    @FXML private ChoiceBox<String> condCB;
    @FXML private ChoiceBox<String> classCB;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        classCB.setValue(SharedData.getClassesInGroup().get(ClassPreviewBoardController.currentGroup).get(ClassPreviewBoardController.currentClassID).getTheClassName());
        condCB.setItems(FXCollections.observableArrayList(Arrays.stream(TeacherConditions.values()).map(Enum::name).toList()));
//        classCB.setItems(FXCollections.observableArrayList(SharedData.getClassesInGroup().keySet()));
    }
}
