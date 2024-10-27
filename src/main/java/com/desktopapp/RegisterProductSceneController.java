package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.Product;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterProductSceneController {
    public static Scene CreateScene(Long id) throws Exception
    {
        URL sceneUrl = RegisterProductSceneController.class.getResource("register-product-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        RegisterProductSceneController controller = loader.getController();
        controller.setId(id);

        return scene;
    }

    protected long id;
    public long getId() {return id;}
    public void setId(long id) { this.id = id; }

    @FXML
    protected TextField nameProd;

    @FXML
    protected TextField descProd;

    @FXML
    protected TextField precoProd;
    
    @FXML
    protected TextField qtdProd;

    @FXML
    protected void toMain() throws Exception {
        var crrStage = (Stage)nameProd.getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = MainSceneController.CreateScene(id);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void adicionar() throws Exception {
        Context ctx = new Context();
        Product produto = new Product();

        if (precoProd.getText().isEmpty() || qtdProd.getText().isEmpty() || nameProd.getText().isEmpty() || descProd.getText().isEmpty()) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Os campos não podem estar vazios!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }
        
        produto.setName(nameProd.getText());
        produto.setDescricao(descProd.getText());

        try{
            produto.setPreco(Double.parseDouble(precoProd.getText()));
            produto.setQuantidade(Integer.parseInt(qtdProd.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Formato de preco ou quantidade inválido. " + e);
            Alert alert = new Alert(
                AlertType.ERROR,
                "Os campos PREÇO e QUANTIDADE devem ser preenchidos com números!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        ctx.begin();
        ctx.persist(produto);
        ctx.commit();

        var crrStage = (Stage)qtdProd.getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = MainSceneController.CreateScene(id);
        stage.setScene(scene);
        stage.show();
    }
}
