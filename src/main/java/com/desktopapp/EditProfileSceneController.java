package com.desktopapp;

import java.net.URL;
import java.time.LocalDate;

import java.util.List;

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
    
        // Carregar o usuário específico
        user = ctx.find(User.class, this.id); // Corrigir: deve usar o ID passado
        if (user != null) {
            name.setText(user.getName());
            if (user.getData() != null) { // Verifique se data não é null
                data.setValue(LocalDate.of(user.getData().getYear(), user.getData().getMonth(), user.getData().getDayOfMonth()));
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Usuário não encontrado!", ButtonType.OK);
            alert.showAndWait();
        }
    
        // Buscar e imprimir todos os usuários no banco de dados
        System.out.println("=== Lista de Usuários no Banco de Dados ===");
        List<User> allUsers = ctx.findAll(User.class);
        for (User u : allUsers) {
            System.out.println("ID: " + u.getId() + ", Nome: " + u.getName() + ", Data: " + u.getData());
        }
        System.out.println("==========================================");
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
