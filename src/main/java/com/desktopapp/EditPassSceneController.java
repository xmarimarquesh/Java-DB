package com.desktopapp;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EditPassSceneController {
    public static Scene CreateScene(Long id) throws Exception
    {
        URL sceneUrl = EditPassSceneController.class.getResource("edit-pass-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EditPassSceneController controller = loader.getController();
        controller.setId(id);

        return scene;
    }

    protected Long id;
    public void setId(Long id) { this.id = id; }

    @FXML
    protected void editarSenha(){
        System.out.println("Editar senha");
    }
}
