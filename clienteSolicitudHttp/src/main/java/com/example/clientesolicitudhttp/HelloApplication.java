package com.example.clientesolicitudhttp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;
import java.net.http.HttpClient;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        //Se cargan los elementos
        Parent root = fxmlLoader.load();
        HelloController controladorMain = fxmlLoader.getController();

        controladorMain.cliente = HttpClient.newHttpClient();
        controladorMain.llenarCombo();
        controladorMain.crearGrupoRb();

        Scene scene = new Scene(root, 720, 600);
        stage.setTitle("Solicitudes HTTP");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}