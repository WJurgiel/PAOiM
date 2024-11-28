package com.example.teachers;

import com.sun.jdi.ClassType;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    public TableView<ClassTeacher> classesList;
    @FXML
    private TableColumn<ClassTeacher, String> classNameColumn;
    @FXML
    private TableColumn<ClassTeacher, String> classFillingColumn;

    @FXML
    private AnchorPane groupsPanel;

    @FXML
    private AnchorPane statesPanel;

    @FXML
    private Button addClassBtn;

    @FXML
    PieChart pieChart;

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
        result.ifPresent(s -> {
            SharedData.addGroup(s);
            groupList.setValue(s);
        });
    }
    @FXML
    public void toggleTeachersPanel(ActionEvent event) throws IOException {
        groupsPanel.setOpacity(0);
        statesPanel.setOpacity(0);

        groupsPanel.setDisable(true);
        statesPanel.setDisable(true);
    }
    @FXML void toggleGroupsPanel(ActionEvent event) throws IOException {
        statesPanel.setOpacity(0);
        groupsPanel.setOpacity(1);

        groupsPanel.setDisable(false);
        statesPanel.setDisable(true);
    }
    @FXML void toggleSummaryPanel(ActionEvent event) throws IOException {
        statesPanel.setOpacity(1);
        groupsPanel.setOpacity(0);

        groupsPanel.setDisable(true);
        statesPanel.setDisable(false);
    }
    @FXML
    public void openClassPreviewScene(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2){
            ClassTeacher selectedClass = classesList.getSelectionModel().getSelectedItem();
            System.out.println(selectedClass);

            if(selectedClass == null) return;

            try{
                ClassPreviewBoardController.currentClassID = classesList.getSelectionModel().getFocusedIndex();
                ClassPreviewBoardController.currentGroup = groupList.getValue();
                changeScene(event, "class-preview-board.fxml");
                setStageTitle(selectedClass.getTheClassName() + "|" + groupList.getValue());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void deleteClass(KeyEvent event){
        if(event.getCode() == KeyCode.DELETE){
            //modify here so it changes the SharedData element, possible kind of UpdateScene/updateTable method will become in handy
            ObservableList<ClassTeacher> selectedItems = classesList.getSelectionModel().getSelectedItems();
            classesList.getItems().removeAll(selectedItems);
        }

    }
    @FXML
    public void addClassToGroup(ActionEvent event) throws IOException {
        TextInputDialog classAdderDialog = new TextInputDialog();
        classAdderDialog.setTitle("Add Class");
        classAdderDialog.setHeaderText("Insert name of the new class");
        classAdderDialog.setContentText("Name: ");
        Optional<String> nameResult = classAdderDialog.showAndWait();
        nameResult.ifPresent(name ->{
            classAdderDialog.setContentText("Max size: ");
            Optional<String> sizeResult = classAdderDialog.showAndWait();

            sizeResult.ifPresent(size ->{
                try{
                    int maxSize = Integer.parseInt(size);
                    SharedData.addClassToGroup(groupList.getValue(), new ClassTeacher(name, maxSize));
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            });
        });
    }
    @Override
    public void initialize(URL args0, ResourceBundle args1) {
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("theClassName"));
        classFillingColumn.setCellValueFactory(new PropertyValueFactory<>("filling"));

        // the ChoiceBox and Tableview are getting Observable type that sends signal to update, everytime the content changes
        groupList.setItems(SharedData.getGroupList());
        classesList.setItems(SharedData.getClassesList());

        // dynamically change content of tableview with listener, depending on chosen group
        groupList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                ObservableList<ClassTeacher> selectedClass = SharedData.getClassesInGroup().get(newValue);
                classesList.setItems(selectedClass);
            }
        });

        // Set state of add class btn dynamically
        addClassBtn.setDisable(SharedData.getGroupList().isEmpty());
        SharedData.getGroupList().addListener((ListChangeListener<String>) change ->{
            addClassBtn.setDisable(SharedData.getGroupList().isEmpty());
        });

        //Piechart
        ObservableList<PieChart.Data> data = TeacherCondChart.getTeacherConditionData();

        pieChart.setData(data);
        pieChart.setTitle("Summary");
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);
    }
}
