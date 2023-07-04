package com.supermercado.supermercadofran;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lanzador extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        conexionSQL conexion = new conexionSQL();
        FXMLLoader fxmlLoader = new FXMLLoader(Lanzador.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        conexion.crearBase();
        stage.setTitle("SuperGest");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}