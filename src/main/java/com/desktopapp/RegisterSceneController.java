package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterSceneController {
    public static Scene CreateScene() throws Exception {
        URL sceneUrl = LoginSceneController.class.getResource("register-scene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        return scene;
    }

    @FXML
    protected TextField name_cad;

    @FXML
    protected DatePicker data_cad;

    @FXML
    protected PasswordField pass_cad;

    @FXML
    protected PasswordField confPass_cad;

    @FXML
    protected void toLogin() throws Exception{
        var crrStage = (Stage)data_cad.getScene().getWindow();
        crrStage.close();
 
        var stage = new Stage();
        var scene = LoginSceneController.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void cadastrar() throws Exception {
        Context ctx = new Context();
        User user = new User();
        user.setName(name_cad.getText());
        user.setPassword(pass_cad.getText());

        if(!pass_cad.getText().equals(confPass_cad.getText())){
            Alert alert = new Alert(
                AlertType.ERROR,
                "Senhas incompatíveis!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        ctx.begin();
        ctx.persist(user);
        ctx.commit();
        
        var crrStage = (Stage)name_cad.getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = LoginSceneController.CreateScene();
        stage.setScene(scene);
        stage.show();

    }
}
