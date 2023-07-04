package com.supermercado.supermercadofran;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.List;

public class controladorPrincipal {

    @FXML
    private Tab tab_empleados;

    @FXML
    private AnchorPane anchorGrafica;

    @FXML
    private Label s_beneficios;

    @FXML
    private Button btn_obtenerBeneficios;

    @FXML
    private Button btn_actualizarEmpleado;

    @FXML
    private Button btn_borrarTicket;

    @FXML
    private Button btn_agregarProducto;

    @FXML
    private Button btn_limpiarEmpleados;

    @FXML
    private Button btn_actualizarLista;

    @FXML
    private TextField txt_nombre_producto;

    @FXML
    private Button btn_limpiarProducto;

    @FXML
    private TextArea txt_consola_producto;

    @FXML
    private TextArea txt_caja;

    @FXML
    private TextField txt_descuento;

    @FXML
    private Button btn_listadoProductos;

    @FXML
    private TextArea txt_consolaEmpleados;

    @FXML
    private Button btn_buscarProducto;

    @FXML
    private RadioButton r_administrador;

    @FXML
    private Button btn_salir_principal;

    @FXML
    private Tab tab_bienvenida;

    @FXML
    private Tab tab_caja;

    @FXML
    private Tab tab_graficas;

    @FXML
    private RadioButton r_almacen;

    @FXML
    private Button btn_limpiarCaja;

    @FXML
    private Button btn_altaEmpleado;

    @FXML
    private TextField txt_stock_producto;

    @FXML
    private PasswordField txt_pass2;

    @FXML
    private TextField txt_unidades;

    @FXML
    private TextArea txt_descripcion_producto;

    @FXML
    private Button btn_listadoEmpleados;

    @FXML
    private TextField txt_precio_producto;

    @FXML
    private PasswordField txt_pass1;

    @FXML
    private Button btn_borrarProducto;

    @FXML
    private Button btn_actualizarProducto;

    @FXML
    private RadioButton r_cajero;

    @FXML
    private TextField txt_nombre_producto1;

    @FXML
    private Tab tab_almacen;

    @FXML
    private TextField txt_nombre_empleado;

    @FXML
    private Button btn_vista_previa;

    @FXML
    private Button btn_borrarEmpleado;

    @FXML
    private Button btn_alta;

    @FXML
    private Button btn_imprimir;

    @FXML
    private Button btn_vender;

    @FXML
    private TextArea txt_salidaEmpleados;

    @FXML
    private TextArea txt_S_productos;

    @FXML
    private ToggleGroup t_empleado;

    @FXML
    private ListView<String> lista_producto;

    @FXML
    private TabPane tabPane;



    @FXML protected void actualizarLista() throws SQLException {
        conexionSQL conexionActu = new conexionSQL();
        List<String> productos = conexionActu.actualizacionLista();
        ObservableList<String> items = FXCollections.observableArrayList(productos);
        lista_producto.setItems(items);

    }

    @FXML protected void listarProductos() throws SQLException, ClassNotFoundException {
        conexionSQL conexionListar = new conexionSQL();
        List<String> productos = conexionListar.listadoTodosProductos();
        StringBuilder sb = new StringBuilder();
        for (String producto : productos) {
            sb.append(producto);
        }
        txt_S_productos.setText(sb.toString());
    }


    @FXML protected void borrarProducto() throws SQLException, ClassNotFoundException {
        conexionSQL conexionBorrar = new conexionSQL();
        // TODO: Implementar función en conexionSQL que me permita ver si existe un producto.
        if (!txt_nombre_producto.getText().isEmpty()) {
            String nombreABorrar = txt_nombre_producto.getText();
            conexionBorrar.borrarProducto(nombreABorrar);
            List<String> productos = conexionBorrar.listadoTodosProductos();
            txt_S_productos.setText(productos.toString());
            txt_consola_producto.appendText("\nProducto borrado exitosamente.");

        }
        txt_consola_producto.setText("Introduce el nombre del producto para borrar en la casilla Nombre del Producto");
    }


    @FXML protected void crearProducto(){
        try{
            conexionSQL conexion = new conexionSQL();
        String nombre = txt_nombre_producto.getText();
        int stock =Integer.parseInt(txt_stock_producto.getText());
        float precio = Float.parseFloat(txt_precio_producto.getText());
        String descripcion =txt_descripcion_producto.getText();
        conexion.meterProducto(nombre, stock, precio, descripcion);
        txt_consola_producto.setText("Se ha introducido el producto correctamente.");

        }

        catch (Exception e){

        txt_consola_producto.setText("Por favor, rellene todos los campos. Stock es un número entero. Precio puede ser un número decimal escrito con . (7.5 por ejemplo)");


        }

    }

    @FXML protected void buscarProducto() {
        try {
            conexionSQL conexion = new conexionSQL();
            String nombre = txt_nombre_producto.getText();
            List listado = conexion.buscarProducto(nombre);
            if (listado.isEmpty()) {
                txt_consola_producto.setText("No se encontró ningún producto con ese nombre.");
            } else {
                txt_consola_producto.setText(listado.toString());
            }
        } catch (Exception e) {
            txt_consola_producto.setText("Introduzca en el campo Nombre el producto que quiere buscar.");
        }
    }


    @FXML protected void venderProductoInterfaz() {
        try {
            conexionSQL conexion = new conexionSQL();
            String nombre = lista_producto.getSelectionModel().getSelectedItem();
            int stock = Integer.parseInt(txt_unidades.getText());
            txt_caja.setText(conexion.venderProducto(nombre, stock));

        } catch (Exception e) {

            txt_caja.setText("Introduzca en el campo Nombre el producto que quiere buscar.");


        }
    }

    @FXML
    public void limpiarInterfaces(){
    txt_S_productos.setText("");
    txt_nombre_producto.setText("");
    txt_consola_producto.setText("");
    txt_stock_producto.setText("");
    txt_caja.setText("");
    txt_consolaEmpleados.setText("");
    txt_descripcion_producto.setText("");
    txt_descuento.setText("");
    txt_nombre_empleado.setText("");
    txt_pass1.setText("");
    txt_pass2.setText("");
    txt_precio_producto.setText("");
    txt_salidaEmpleados.setText("");
    txt_unidades.setText("");
    t_empleado.selectToggle(null);




    }

    @FXML
    public void actualizarProducto() {
        conexionSQL conexion = new conexionSQL();

        Integer stockNuevo = null;
        Float precioNuevo = null;

        try {
            if (!txt_stock_producto.getText().isEmpty()) {
                stockNuevo = Integer.parseInt(txt_stock_producto.getText());
            }

            if (!txt_precio_producto.getText().isEmpty()) {
                precioNuevo = Float.parseFloat(txt_precio_producto.getText());
            }

            txt_S_productos.setText(conexion.actualizarProductos(txt_nombre_producto.getText(),
                    txt_descripcion_producto.getText(),
                    stockNuevo,
                    precioNuevo));
        } catch (NumberFormatException e) {
            // Manejo de excepción en caso de que los valores de texto no puedan ser convertidos a números
            txt_S_productos.setText("Error: " + e.getMessage());
        } catch (SQLException e) {
            // Manejo de excepción en caso de error de conexión a la base de datos
            txt_S_productos.setText("Error en la conexión: " + e.getMessage());
        }
    }


    // TODO: los botones funcionan perfectamente por ahora, hay que actualizarlo para que se vean los mensajes de los errores correctamente.
    @FXML protected void altaEmpleado() {
        try {
            conexionSQL conexion = new conexionSQL();
            String nombre = txt_nombre_empleado.getText();
            String puesto = null;
            RadioButton selectedRadioButton = (RadioButton) t_empleado.getSelectedToggle();
            if (selectedRadioButton != null) {
                puesto = selectedRadioButton.getText();
            }
            String pass = null;
            if(!txt_pass1.getText().isEmpty() || !txt_pass2.getText().isEmpty()) {
                if(txt_pass1.getText().equals(txt_pass2.getText())) {
                    pass = txt_pass1.getText();
                } else {
                    txt_consolaEmpleados.setText("Las contraseñas no coinciden.");
                }
            }
            if (pass!=null && puesto !=null){
                conexion.altaEmpleado(nombre, pass, puesto);

            }
            else {
                txt_consolaEmpleados.setText("Ningún campo puede estar vacío, asegúrese de que los ha rellenado todos para dar de alta a un empleado.");

            }

        } catch (Exception e) {

            txt_caja.setText("Ha habido un error.");


        }
    }
    @FXML protected void actualizarEmpleado() {
        try {
            conexionSQL conexion = new conexionSQL();
            String nombre = txt_nombre_empleado.getText();
            String puesto = null;
            RadioButton selectedRadioButton = (RadioButton) t_empleado.getSelectedToggle();
            if (selectedRadioButton != null) {
                puesto = selectedRadioButton.getText();
            }
            String pass = null;
            if(!txt_pass1.getText().isEmpty() || !txt_pass2.getText().isEmpty()) {
                if(txt_pass1.getText().equals(txt_pass2.getText())) {
                    pass = txt_pass1.getText();
                } else {
                    txt_consolaEmpleados.setText("Las contraseñas no coinciden.");
                }
            }
            if (nombre!=null) {
                conexion.actualizarEmpleado(nombre, pass, puesto);
                txt_consolaEmpleados.setText("Empleado actualizado correctamente.");
            }

            else{

                txt_consolaEmpleados.setText("Introduzca el nombre del empleado que quiere actualizar.");

            }
        } catch (Exception e) {
            txt_caja.setText("Ha habido un error.");
        }
    }

    @FXML protected void bajaEmpleado() {
        try {
            conexionSQL conexion = new conexionSQL();
            String nombre = txt_nombre_empleado.getText();
            if(nombre.isEmpty()){
                txt_consolaEmpleados.setText("El nombre no puede estar vacío.");
                return;
            }
            conexion.borrarEmpleado(nombre);
        } catch (Exception e) {
            txt_caja.setText("Ha habido un error.");
        }
    }
    @FXML protected void listarEmpleados() {
        try {
            conexionSQL conexion = new conexionSQL();
            List<String> empleados = conexion.listadoEmpleados();
            String listado = "";
            for (String empleado : empleados) {
                listado += empleado + "\n";
            }
            txt_salidaEmpleados.setText(listado);
        } catch (Exception e) {
            txt_salidaEmpleados.setText("Ha habido un error.");
        }


    }

    @FXML
    protected void mostrarBeneficios() {
        try {
            conexionSQL conexion = new conexionSQL();
            float beneficios = conexion.obtenerBeneficiosTotales();
            s_beneficios.setText(Float.toString(beneficios)+"€");
        } catch (Exception e) {
            s_beneficios.setText("Error al obtener beneficios");
        }
    }

    public void initialize() {
        tab_graficas.setOnSelectionChanged(event -> {
            if (tab_graficas.isSelected()) {
                mostrarGrafica();
            }
        });
    }

    private void mostrarGrafica() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        ObservableList<XYChart.Data<String, Number>> beneficiosData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> unidadesVendidasData = FXCollections.observableArrayList();

        String sql = "SELECT NOMBRE, BENEFICIOS, UNIDADESVENDIDAS FROM PRODUCTOS ORDER BY BENEFICIOS DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                float beneficios = rs.getFloat("BENEFICIOS");
                int unidadesVendidas = rs.getInt("UNIDADESVENDIDAS");

                beneficiosData.add(new XYChart.Data<>(nombre, beneficios));
                unidadesVendidasData.add(new XYChart.Data<>(nombre, unidadesVendidas));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los datos de la base de datos");
            e.printStackTrace();
        }

        XYChart.Series<String, Number> beneficiosSeries = new XYChart.Series<>("Beneficios", beneficiosData);
        XYChart.Series<String, Number> unidadesVendidasSeries = new XYChart.Series<>("Unidades Vendidas", unidadesVendidasData);

        barChart.getData().addAll(beneficiosSeries, unidadesVendidasSeries);

        anchorGrafica.getChildren().clear();
        anchorGrafica.getChildren().add(barChart);
        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
    }

    @FXML
    protected void salir() {
        System.exit(0);

    }

    public Ticket ticketActual = new Ticket();

    public void iniciarTicket() {
        ticketActual = new Ticket();
    }
    public void mostrarTicket() {
        if (ticketActual == null || ticketActual.getProductos().isEmpty()) {
            txt_caja.setText("No hay productos en el ticket");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("TICKET:\n\n");
        for (Producto producto : ticketActual.getProductos()) {
            String nombreProducto = producto.getNombre();
            int unidadesVendidas = producto.getUnidadesVendidas();
            double precio = producto.getPrecio();

            String infoProducto = String.format("%-20s x %-2d  €%.2f\n", nombreProducto, unidadesVendidas, precio);
            sb.append(infoProducto);
        }
        txt_caja.setText(sb.toString());
    }


    public void agregarProducto() throws SQLException {
        String nombreProducto = lista_producto.getSelectionModel().getSelectedItem();
        if (!txt_unidades.getText().isEmpty()){
            try {
                int unidadesVendidas = Integer.parseInt(txt_unidades.getText());
                conexionSQL conexion = new conexionSQL();
                float precioProducto = conexion.obtenerPrecio(nombreProducto);

                Producto producto = new Producto(nombreProducto, unidadesVendidas, precioProducto);
                ticketActual.agregarProducto(producto);

                txt_unidades.clear();
            }
            catch (Exception e){
                txt_caja.setText("Ha ocurrido un error, compruebe que ha introducido un número en el campo Unidades.");


            }
        }
        else {

            txt_caja.setText("Por favor, rellene cuántas unidades quiere vender.");
        }


    }

    public void terminarTicket() {
        if (ticketActual == null || ticketActual.getProductos().isEmpty()) {
            txt_caja.setText("No hay productos en el ticket.");
            return;
        }

        System.out.println("TICKET:");
        System.out.println(ticketActual);
        txt_caja.setText("Ticket definitivo:\n");


        // Actualizar la base de datos
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db")) {
            String sql = "UPDATE PRODUCTOS SET STOCK = STOCK - ?, UNIDADESVENDIDAS = UNIDADESVENDIDAS + ?, BENEFICIOS = BENEFICIOS + ? WHERE NOMBRE = ?";
            try (PreparedStatement pstmtUpdate = c.prepareStatement(sql)) {
                for (Producto producto : ticketActual.getProductos()) {
                    String nombreProducto = producto.getNombre();
                    int unidadesVendidas = producto.getUnidadesVendidas();
                    int stockReal = 0;
                    String sql1 = "SELECT STOCK FROM PRODUCTOS WHERE NOMBRE = ?";
                    try (PreparedStatement pstmtSelect = c.prepareStatement(sql1)) {
                        pstmtSelect.setString(1, nombreProducto);
                        try (ResultSet rs = pstmtSelect.executeQuery()) {
                            if (rs.next()) {
                                stockReal = rs.getInt("STOCK");
                            }
                        }
                    }
                    if (stockReal < unidadesVendidas) {
                        unidadesVendidas = stockReal;
                    }
                    float beneficios = producto.getPrecio() * unidadesVendidas;

                    pstmtUpdate.setInt(1, unidadesVendidas);
                    pstmtUpdate.setInt(2, unidadesVendidas);
                    pstmtUpdate.setDouble(3, beneficios);
                    pstmtUpdate.setString(4, nombreProducto);

                    pstmtUpdate.executeUpdate();

                    // Agregar información del producto al TextArea
                    String infoProducto = String.format("%-20s x %-2d  €%.2f\n", nombreProducto, unidadesVendidas, producto.getPrecio());
                    txt_caja.appendText(infoProducto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ticketActual = new Ticket();
    }


}


