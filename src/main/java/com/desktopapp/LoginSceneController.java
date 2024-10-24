package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginSceneController {

    public static Scene CreateScene() throws Exception {
        URL sceneUrl = LoginSceneController.class
                .getResource("login-scene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        return scene;
    }
    
    @FXML
    protected Button logar;
 
    @FXML
    protected TextField name_log;
 
    @FXML
    protected PasswordField pass_log;
 
    @FXML
    protected CheckBox seePass;

    @FXML
    protected void toRegister() throws Exception{
        var crrStage = (Stage)logar.getScene().getWindow();
        crrStage.close();
 
        var stage = new Stage();
        var scene = RegisterSceneController.CreateScene();
        stage.setScene(scene);
        stage.show();
    }
 
    @FXML
    protected void submit(ActionEvent e) throws Exception {

        Context ctx = new Context();

        var query = ctx.createQuery(User.class, "from User u where u.name = :name");
        query.setParameter("name", this.name_log.getText());
        var users = query.getResultList();

        if (users.size() <= 0) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Usuário não encontrado!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }
        
        var user = users.get(0);

        if (!pass_log.getText().equals(user.getPassword())) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Senha incorreta!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }
 
        var crrStage = (Stage)logar.getScene().getWindow();
        crrStage.close();
 
        var stage = new Stage();
        var scene = MainSceneController.CreateScene(user.getId());
        stage.setScene(scene);
        stage.show();
    }

}
