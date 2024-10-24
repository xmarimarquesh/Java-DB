package com.desktopapp;

import java.net.URL;
import java.time.LocalDate;

import com.desktopapp.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class EditProfileSceneController {
    public static Scene CreateScene(Long id) throws Exception
    {
        URL sceneUrl = MainSceneController.class.getResource("edit-profile-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EditProfileSceneController controller = loader.getController();
        controller.setId(id);
        controller.loadData();

        return scene;
    }

    protected Long id;
    public void setId(Long id) { this.id = id; }

    @FXML
    protected TextField name;

    @FXML
    protected DatePicker data;

    User user = new User();
    private void loadData() throws Exception {
        Context ctx = new Context();
        ctx.begin();
        user = ctx.find(user.getClass(), this.id);
        name.setText(user.getName());
        data.setValue(LocalDate.of(user.getData().getYear(), user.getData().getMonth(), user.getData().getDayOfMonth()));
    }

    @FXML
    protected void editarPerfil(){
        Context context = new Context();

        if(name.getText().isEmpty() || data.hasProperties() || data.getValue() == null ){
            Alert alert = new Alert(
                AlertType.ERROR,
                "Os campos não podem estar vazios!",
                ButtonType.OK
            );

            alert.showAndWait();
            return;
        }

        user.setName(name.getText());
        user.setData(data.getValue());

        context.begin();
        context.update(user);

        var crrStage = (Stage) name.getScene().getWindow();
        crrStage.close();
    }

    @FXML
    protected void editarSenha() throws Exception{
        var stage = new Stage();
        var scene = EditPassSceneController.CreateScene(user.getId());
        stage.setScene(scene);
        stage.show();
    }
}
