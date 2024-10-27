package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;

import java.util.List;

import com.desktopapp.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
 
public class MainSceneController implements Initializable{
     
    public static Scene CreateScene(Long id) throws Exception
    {
        URL sceneUrl = MainSceneController.class.getResource("main-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        MainSceneController controller = loader.getController();
        controller.setId(id);

        return scene;
    }

    public static Scene CreateScene() throws Exception
    {
        URL sceneUrl = MainSceneController.class.getResource("main-scene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);

        return scene;
    }

    protected long id;
    public void setId(long id) { this.id = id; }

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

    @FXML
    protected TextField tfPesquisa;

    @FXML 
    protected void pesquisar() throws Exception {
        realizarPesquisa();
    }

    @FXML 
    protected void pesquisarEnter(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            realizarPesquisa();
        }
    }

    private void realizarPesquisa() {
        String textoPesquisa = tfPesquisa.getText().trim().toLowerCase();
        if (textoPesquisa.isEmpty()) {
            initialize(null, null);
        } else {
            Context ctx = new Context();
            var query = ctx.createQuery(Product.class, 
                "SELECT p FROM Product p WHERE LOWER(p.name) LIKE :searchText OR LOWER(p.descricao) LIKE :searchText");
            query.setParameter("searchText", "%" + textoPesquisa + "%");

            List<Product> produtosFiltrados = query.getResultList();
            ObservableList<Product> listaFiltrada = FXCollections.observableArrayList(produtosFiltrados);

            table.setItems(listaFiltrada);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
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
                    System.out.println("Nome: " + product.getName());
                    System.out.println("Desc: " + product.getDescricao());
                    System.out.println("Preco: " + product.getPreco());
                    System.out.println("Qtd: " + product.getQuantidade());

                    var newStage = new Stage();
                    Scene newScene;
                    try {
                        var crrStage = (Stage) table.getScene().getWindow();
                        crrStage.close();
                        newScene = EditProductSceneController.CreateScene(product);
                        newStage.setScene(newScene);
                        newStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                deleteButton.setOnAction(event -> {
                    Alert alert = new Alert(
                        AlertType.CONFIRMATION,
                        "Você tem certeza que deseja apagar este item?",
                        ButtonType.OK,
                        ButtonType.CANCEL
                    );

                    alert.showAndWait().filter(res -> res == ButtonType.OK).ifPresent(res -> {
                        System.out.println("deletar");
                        Context ctx = new Context();
                        ctx.begin();
                        Product product = getTableView().getItems().remove(getIndex());
                        ctx.delete(product);
                    });
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

    @FXML
    protected void toProfile() throws Exception{
        var stage = new Stage();
        var scene = EditProfileSceneController.CreateScene(id);
        stage.setScene(scene);
        stage.show();
    }

    
}