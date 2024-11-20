package com.example.teachers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class SceneChanger {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Map<String, Parent> sceneCache = new HashMap<>();

    public void changeScene(Event event, String scenePath) throws IOException {
        if (event.getSource() instanceof Node) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            throw new IllegalArgumentException("Unsupported event type");
        }


        if(sceneCache.containsKey(scenePath)) {
            root = sceneCache.get(scenePath);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            root = loader.load();
            sceneCache.put(scenePath, root);
        }

        for(Parent sceneRoot : sceneCache.values()){
            sceneRoot.setVisible(false);
        }
        root.setVisible(true);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setStageTitle(String title) {
        stage.setTitle(title);
    }
    public Stage getStage(){
        return stage;
    }
    public Scene getScene(){
        return scene;
    }
    public Parent getRoot(){
        return root;
    }
}
