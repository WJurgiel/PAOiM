package com.example.teachers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginBoardController extends SceneChanger {
    @FXML
    TextField usernameTextField;

    public void login(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();

        if(username.isEmpty()){
            usernameTextField.promptTextProperty().setValue("Enter username!");
            return;
        }

        changeScene(event, "main-board.fxml");
        getStage().setTitle("Main Board | " + username);
    }
}
