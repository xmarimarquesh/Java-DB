package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;


import com.desktopapp.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
 
public class MainSceneController implements Initializable{
     
    public static Scene CreateScene() throws Exception
    {
        URL sceneUrl = MainSceneController.class.getResource("main-scene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        return scene;
    }

    @FXML
    protected TableView<Product> table;

    @FXML
    protected TableColumn<Product, String> nome_col;

    @FXML
    protected TableColumn<Product, String> desc_col;

    @FXML
    protected TableColumn<Product, Double> preco_col;

    @FXML
    protected TableColumn<Product, Integer> qtd_col;

    @FXML
    protected TableColumn<Product, Void> action_col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        preco_col.setCellValueFactory(new PropertyValueFactory<>("preco"));
        qtd_col.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        action_col.setCellFactory(param -> new TableCell<Product, Void>() {
            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("Excluir");

            {
                editButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    System.out.println("Editar: " + product.getName());
                });

                deleteButton.setOnAction(event -> {
                    getTableView().getItems().remove(getIndex());
                });

                HBox hbox = new HBox(editButton, deleteButton);
                hbox.setSpacing(10);
                setGraphic(hbox);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(getGraphic());
                }
            } 
        });

        table.setItems(listaDeProducts());
    }




    private ObservableList<Product> listaDeProducts() {
        Context ctx = new Context();
        var query = ctx.createQuery(Product.class, "from Product");
        var produtos = query.getResultList();
        
        return FXCollections.observableArrayList(produtos);
    }

    @FXML
    protected void adicionarProduto() throws Exception{
        var crrStage = (Stage)table.getScene().getWindow();
        crrStage.close();

        var stage = new Stage();
        var scene = RegisterProductSceneController.CreateScene();
        stage.setScene(scene);
        stage.show();
    }

    
}