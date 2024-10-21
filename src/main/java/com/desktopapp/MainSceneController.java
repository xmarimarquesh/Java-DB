package com.desktopapp;

import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
 
public class MainSceneController {
     
    public static Scene CreateScene() throws Exception
    {
        URL sceneUrl = MainSceneController.class
            .getResource("main-scene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        return scene;
    }
}