package com.example.teachers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassPreviewBoardController extends SceneChanger implements Initializable {

    public String currentSceneName;
    public static String currentGroup;
    public static int currentClassID;
    public static int currentTeacher;
    @FXML public TableView<Teacher> teachersList;
    @FXML public TableColumn<Teacher, String> nameCol;
    @FXML public TableColumn<Teacher, String> surnameCol;
    @FXML public TableColumn<Teacher, String> birthCol;
    @FXML public TableColumn<Teacher, String> salaryCol;
    @FXML public TableColumn<Teacher, String> condCol;
    @FXML public TextField searchTeacherTF;

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
    @FXML
    public void deleteTeacher(KeyEvent event){
        if(event.getCode() == KeyCode.DELETE){
            //modify here so it changes the SharedData element, possible kind of UpdateScene/updateTable method will become in handy
//            ObservableList<Teacher> selectedItems = teachersList.getSelectionModel().getSelectedItems();
//            classesList.getItems().removeAll(selectedItems);
            ObservableList<Integer> selectedIndices = teachersList.getSelectionModel().getSelectedIndices();
            for (int i = selectedIndices.size() - 1; i >= 0; i--) {
                SharedData.getClassesInGroup().get(currentGroup).get(currentClassID).getTeachers().remove((int) selectedIndices.get(i));
            }
            System.out.println("Selected: " + selectedIndices);
        }
    }
    @FXML
    public void modifyTeacher(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2){
            Integer selectedIndex = teachersList.getSelectionModel().getSelectedIndex();
            currentTeacher = selectedIndex;
            changeScene(event, "EditTeacher.fxml");
        }
    }
    @FXML
    public void SearchTeachers(){
        if(searchTeacherTF.getText().equals("")) {
            teachersList.setItems(SharedData.getClassesInGroup().get(currentGroup).get(currentClassID).getTeachers());
        }else{
            teachersList.setItems(SharedData.getClassesInGroup().get(currentGroup).get(currentClassID).searchTeacher(searchTeacherTF.getText()));
        }
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
