package com.desktopapp;

import com.desktopapp.model.User;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setName("mari");
        user.setPassword("mari");

        Context ctx = new Context();
        ctx.begin();
        ctx.persist(user);
        ctx.commit();
        
        Scene scene = LoginSceneController.CreateScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }  
}
