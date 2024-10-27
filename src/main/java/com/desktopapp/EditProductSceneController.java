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

public class EditProductSceneController {
    public static Scene CreateScene(Product product, Long id) throws Exception
    {
        URL sceneUrl = MainSceneController.class.getResource("edit-product-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EditProductSceneController controller = loader.getController();
        controller.setProduto(product);
        controller.setId(id); 
        controller.loadData();

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

    protected Product produto;
    public void setProduto(Product produto) { this.produto = produto; }

    public void loadData() {
        Context ctx = new Context();
        ctx.begin();
        var query = ctx.createQuery(this.produto.getClass(), "from Product p where p.id = :id");
        query.setParameter("id", this.produto.getId());
        var p = query.getResultList();

        nameProd.setText(p.get(0).getName());
        descProd.setText(p.get(0).getDescricao());
        precoProd.setText(String.valueOf(p.get(0).getPreco()));
        qtdProd.setText(String.valueOf(p.get(0).getQuantidade()));
    }

    @FXML
    protected void editar() throws Exception {
        Context ctx = new Context();
        ctx.begin();
        
        if (precoProd.getText().isEmpty() || qtdProd.getText().isEmpty() || nameProd.getText().isEmpty() || descProd.getText().isEmpty()) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Os campos não podem estar vazios!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        this.produto.setName(nameProd.getText());
        this.produto.setDescricao(descProd.getText());
        
        try {
            this.produto.setPreco(Double.parseDouble(precoProd.getText()));
            this.produto.setQuantidade(Integer.parseInt(qtdProd.getText()));
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

        ctx.update(this.produto);

        var crrStage = (Stage) qtdProd.getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = MainSceneController.CreateScene(id);
        stage.setScene(scene);
        stage.show();
    }
}
