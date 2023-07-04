package com.supermercado.supermercadofran;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class controladorLogin {

    @FXML
    private TextArea txt_consola;

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_entrar;

    @FXML
    private PasswordField txt_contrasena;

    @FXML
    private TextField txt_usuario;

    @FXML
    private Button btn_borrar;

    @FXML
    protected void salir() {
    System.exit(0);

    }

    @FXML
    protected void entrar(ActionEvent event) throws IOException, SQLException {

        conexionSQL conexion = new conexionSQL();
        String usuario = txt_usuario.getText();
        String pass = txt_contrasena.getText();
        if (usuario != null && pass != null) {
            int conectado = conexion.autentificacion(usuario, pass);
                if (conectado == 1 || conectado  ==  2 || conectado == 3){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaPrincipal.fxml"));
                    Parent root = loader.load();


                    TabPane tabPane = (TabPane) root.lookup("#tabPane");

                    // Obtener el Tab de "Empleados"
                    Tab tabEmpleados = tabPane.getTabs().stream()
                            .filter(tab -> tab.getId().equals("tab_empleados"))
                            .findFirst()
                            .orElse(null);

                    Tab tabCaja = tabPane.getTabs().stream()
                            .filter(tab -> tab.getId().equals("tab_caja"))
                            .findFirst()
                            .orElse(null);

                    Tab tabProducto = tabPane.getTabs().stream()
                            .filter(tab -> tab.getId().equals("tab_almacen"))
                            .findFirst()
                            .orElse(null);

                // Obtener el Stage actual de la ventana
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Cerrar el Stage actual
                stage.close();


                // Crear un nuevo Stage y establecer la escena
                Stage nuevoStage = new Stage();
                Scene scene = new Scene(root, 1000, 800);
                nuevoStage.setScene(scene);

                    if (conectado == 2) {
                        tabEmpleados.setDisable(true);
                        tabProducto.setDisable(true);
                    }
                    if (conectado == 3) {
                        tabEmpleados.setDisable(true);
                        tabCaja.setDisable(true);
                    }

                // Mostrar la nueva ventana
                nuevoStage.show();

            }
                else {

                    txt_consola.setText("El usuario y la contraseña no coinciden con ningún empleado.");


                }
        }
        else{

            txt_consola.setText("Introduzca usuario y contraseña, por favor.");

        }

    }
    @FXML
    protected void borrar() {
        txt_contrasena.setText("");
        txt_usuario.setText("");
        txt_consola.setText("");
    }

}
