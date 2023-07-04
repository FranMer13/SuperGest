package com.supermercado.supermercadofran;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class conexionSQL {

   public void crearBase() {
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         System.out.println("Base de datos abierta correctamente");

         stmt = c.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS PRODUCTOS " +
                 "(NOMBRE TEXT PRIMARY KEY NOT NULL," +
                 " STOCK INT DEFAULT 0, " +
                 " UNIDADESVENDIDAS INT DEFAULT 0, " +
                 " PRECIO FLOAT NOT NULL, " +
                 " BENEFICIOS FLOAT DEFAULT 0, " +
                 "DESCRIPCION TEXT)";
         stmt.executeUpdate(sql);
         sql = "CREATE TABLE IF NOT EXISTS EMPLEADOS " +
                 "(NOMBRE TEXT PRIMARY KEY NOT NULL," +
                 "PASSWORD TEXT NOT NULL," +
                 "PUESTO TEXT)";
         stmt.executeUpdate(sql);
         sql = "INSERT OR IGNORE INTO EMPLEADOS (NOMBRE, PASSWORD, PUESTO) " +
                 "VALUES ('admin', 'admin', 'admin')";
         stmt.executeUpdate(sql);
         sql = "INSERT OR IGNORE INTO EMPLEADOS (NOMBRE, PASSWORD, PUESTO) " +
                 "VALUES ('cajero', '1234', 'cajero')";
         stmt.executeUpdate(sql);
         sql = "INSERT OR IGNORE INTO EMPLEADOS (NOMBRE, PASSWORD, PUESTO) " +
                 "VALUES ('almacen', '1234', 'almacen')";
         stmt.executeUpdate(sql);


         stmt.close();
         c.close();
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      System.out.println("Tabla creada exitosamente");
   }

   public int autentificacion(String usuario, String password) throws SQLException {
      Connection c = null;
      Statement stmt = null;
      String nombre = usuario;
      String contra = password;
      int logueo = 0;
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         System.out.println("Base de datos abierta correctamente");

         stmt = c.createStatement();
         String sql = "SELECT * FROM EMPLEADOS WHERE NOMBRE = '" + nombre + "' AND PASSWORD = '" + contra + "'";
         ResultSet rs = stmt.executeQuery(sql);
         if (rs.next()) {
            String puesto = rs.getString("PUESTO");
            switch (puesto.toLowerCase()) {
               case "admin":
                  logueo = 1;
                  stmt.close();
                  c.close();
                  return logueo;

               case "cajero":
                  logueo = 2;
                  stmt.close();
                  c.close();
                  return logueo;

               case "almacen":
                  logueo = 3;
                  stmt.close();
                  c.close();
                  return logueo;

            }


         } else {

            stmt.close();
            c.close();
            return logueo;

         }
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
         stmt.close();
         c.close();
         return logueo;
      }


      return logueo;

   }

   public List actualizacionLista() throws SQLException {

      List<String> productos = new ArrayList<>();
      Connection c = null;
      Statement stmt = null;
      String sql = "SELECT NOMBRE FROM PRODUCTOS";
      ResultSet rs = null;
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            String nombre = rs.getString("NOMBRE");
            productos.add(nombre);
         }
         rs.close();
         stmt.close();
         c.close();
         return productos;
      } catch (SQLException ex) {

         System.out.println("Ha habido un error");
         rs.close();
         stmt.close();
         c.close();
         return productos;
      } catch (ClassNotFoundException e) {
         rs.close();
         stmt.close();
         c.close();
         throw new RuntimeException(e);
      }
      finally {

         rs.close();
         stmt.close();
         c.close();


      }

   }

   public void meterProducto(String nombreProducto, int stockProducto, float precioProducto, String descripcionProducto){
      Connection c = null;
      Statement stmt = null;
      String sql;
      String nombreProductoF =nombreProducto;
      int stockProductoF = stockProducto;
      float precioProductoF = precioProducto;
      String descripcionProductoF = descripcionProducto;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();

         sql = "INSERT OR IGNORE INTO PRODUCTOS (NOMBRE, STOCK, PRECIO, DESCRIPCION) " +
                 "VALUES ('" + nombreProductoF + "', " + stockProductoF + ", " + precioProductoF + ", '" + descripcionProductoF + "')";
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      }
      catch (Exception e){
         String problema = e.getMessage();
         System.out.println(problema);

         }

   }

   public void borrarProducto(String nombreProducto) throws SQLException {
      Connection c = null;
      Statement stmt = null;
      String nombreABorrar = nombreProducto;
      String sql = "DELETE FROM PRODUCTOS WHERE NOMBRE = '" + nombreABorrar + "'";
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      }
      catch (Exception e){
         stmt.close();
         c.close();

      }
      finally {
         stmt.close();
         c.close();
      }

   }
   // TODO: hacer este botón metiendo los beneficios en la base de datos.
   public String venderProducto(String nombreProducto, int stock) throws SQLException {
      Connection c = null;
      Statement stmt = null;
      ResultSet rs = null;
      String mensaje = "Ha ocurrido un error";
      String sqlStock = "SELECT STOCK FROM PRODUCTOS WHERE NOMBRE = '" + nombreProducto + "'";
      String sqlPrecio = "SELECT PRECIO FROM PRODUCTOS WHERE NOMBRE = '" + nombreProducto + "'";
      int stockReal = 0;
      float precio = 0.0f;
      int stockAVender = stock;

      try {
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         rs = stmt.executeQuery(sqlStock);

         if (rs.next()) {
            stockReal = rs.getInt("STOCK");
         }

         if (stockReal < stockAVender) {
            stockAVender = stockReal;
         }

         rs.close();
         stmt.close();

         stmt = c.createStatement();
         rs = stmt.executeQuery(sqlPrecio);

         if (rs.next()) {
            precio = rs.getFloat("PRECIO");
         }

         float beneficioActual = precio * stockAVender;
         String sqlUpdate = "UPDATE PRODUCTOS SET BENEFICIOS = BENEFICIOS + " + beneficioActual + ", STOCK = STOCK - " + stockAVender +
                 ", UNIDADESVENDIDAS = UNIDADESVENDIDAS + " + stockAVender +" WHERE NOMBRE = '"
                 + nombreProducto + "'";
         System.out.println(sqlUpdate);
         stmt.executeUpdate(sqlUpdate);

         mensaje = "Se han vendido " + stockAVender + " unidades. Los beneficios han sido de " + beneficioActual + "€.";

         rs.close();
         stmt.close();
         c.close();
      } catch (SQLException e) {
         System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
      } finally {
         if (rs != null) {
            rs.close();
         }

         if (stmt != null) {
            stmt.close();
         }

         if (c != null) {
            c.close();
         }
      }

      return mensaje;
   }

   public List<String> listadoTodosProductos() {
      Connection c = null;
      Statement stmt = null;
      ResultSet rs = null;
      List<String> productos = new ArrayList<>();

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         String sql = "SELECT * FROM PRODUCTOS";
         rs = stmt.executeQuery(sql);

         while (rs.next()) {
            String nombre = rs.getString("NOMBRE");
            String descripcion = rs.getString("DESCRIPCION");
            double precio = rs.getDouble("PRECIO");
            int stock = rs.getInt("STOCK");
            float beneficios = rs.getFloat("BENEFICIOS");

            String productoString = nombre + " - " + descripcion + " - " + precio + "€ - Stock: " + stock
                    + " - Beneficios: " + beneficios + "€\n";

            productos.add(productoString);
         }
      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (stmt != null) {
               stmt.close();
            }
            if (c != null) {
               c.close();
            }
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
         }
      }

      return productos;
   }

   public List buscarProducto(String nombreMetido) throws SQLException {
      String nombreBuscar = nombreMetido;
      List<String> productos = new ArrayList<>();
      Connection c = null;
      Statement stmt = null;
      String sql = "SELECT * FROM PRODUCTOS WHERE NOMBRE = '" + nombreBuscar + "'";
      ResultSet rs = null;

      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            String nombre = rs.getString("NOMBRE");
            String descripcion = rs.getString("DESCRIPCION");
            float precio = rs.getFloat("PRECIO");
            int stock = rs.getInt("STOCK");
            float beneficios = rs.getFloat("BENEFICIOS");
            int unidadesVendidas = rs.getInt("UNIDADESVENDIDAS");
            String precioProducto = Float.toString(precio);
            String stockProducto = Integer.toString(stock) ;
            String beneficiosProducto = Float.toString(beneficios);
            String unidadesProducto = Integer.toString(unidadesVendidas) ;
            productos.add("Nombre: " + nombre);
            productos.add("\nDescripción: " +descripcion);
            productos.add("\nPrecio: " +precioProducto + "€");
            productos.add("\nStock: " +stockProducto);
            productos.add("\nBeneficios:  " +beneficiosProducto);
            productos.add("\nUnidades vendidas: " +unidadesProducto);
            productos.add("\n");
         }
         rs.close();
         stmt.close();
         c.close();
         return productos;
      } catch (SQLException ex) {

         System.out.println("Ha habido un error");

         if (rs != null) {
            rs.close();
         }
         if (stmt != null) {
            stmt.close();
         }
         if (c != null) {
            c.close();
         }
         return productos;
      } catch (ClassNotFoundException e) {

         if (rs != null) {
            rs.close();
         }
         if (stmt != null) {
            stmt.close();
         }
         if (c != null) {
            c.close();
         }

         throw new RuntimeException(e);

      }

   }


   public String actualizarProductos(String nombre, String descripcion, Integer stock, Float precio) throws SQLException {
      String nombreProducto = nombre;
      Integer stockNuevo = stock;
      Float precioNuevo = precio;
      String descripcionNueva = descripcion;
      Connection c = null;
      Statement stmt = null;
      String mensaje = "Ha habido un error";

      StringBuilder sqlBuilder = new StringBuilder("UPDATE PRODUCTOS SET ");

      if (stockNuevo != null) {
         sqlBuilder.append("STOCK = ").append(stockNuevo).append(", ");
      }

      if (precioNuevo != null) {
         sqlBuilder.append("PRECIO = ").append(precioNuevo).append(", ");
      }

      if (descripcionNueva != null && !descripcionNueva.isEmpty()) {
         sqlBuilder.append("DESCRIPCION = '").append(descripcionNueva).append("', ");
      }

      sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
      sqlBuilder.append(" WHERE NOMBRE = '").append(nombreProducto).append("'");
      try {
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();

         stmt.executeUpdate(sqlBuilder.toString());

         stmt.close();
         c.close();
         mensaje = "Se ha actualizado correctamente";
         return mensaje;
      }
      catch (Exception e){
         stmt.close();
         c.close();
         mensaje = "Se ha actualizado correctamente";
         return mensaje;

      }
      finally {
         stmt.close();
         c.close();
         return mensaje;
      }

   }


      public void altaEmpleado(String nombre, String password, String puesto) throws SQLException {
         Connection c = null;
         Statement stmt = null;
         String sql;
         try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
            stmt = c.createStatement();
            sql = "INSERT INTO EMPLEADOS (NOMBRE, PASSWORD, PUESTO) " +
                    "VALUES ('" + nombre + "', '" + password + "', '" + puesto + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
         }
         finally {
            stmt.close();
            c.close();
         }
         System.out.println("Empleado dado de alta correctamente");
      }

      public void borrarEmpleado(String nombre) throws SQLException {
         Connection c = null;
         Statement stmt = null;
         String sql = "DELETE FROM EMPLEADOS WHERE NOMBRE = '" + nombre + "'";
         try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
         }
         finally {
            stmt.close();
            c.close();
         }

      }

      public List<String> listadoEmpleados() throws SQLException {
         Connection c = null;
         Statement stmt = null;
         String sql = "SELECT * FROM EMPLEADOS";
         ResultSet rs = null;
         List<String> empleados = new ArrayList<>();
         try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
               String nombre = rs.getString("NOMBRE");
               String password = rs.getString("PASSWORD");
               String puesto = rs.getString("PUESTO");
               String empleadoString = nombre + " - " + password + " - " + puesto;
               empleados.add(empleadoString);
            }
            rs.close();
            stmt.close();
            c.close();
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
         }
         finally {
            rs.close();
            stmt.close();
            c.close();

         }
         return empleados;

   }
   public String actualizarEmpleado(String nombre, String password, String puesto) throws SQLException {
      String nombreEmpleado = nombre;
      String passwordNueva = password;
      String puestoNuevo = puesto;
      Connection c = null;
      Statement stmt = null;
      String mensaje = "Ha habido un error";

      StringBuilder sqlBuilder = new StringBuilder("UPDATE EMPLEADOS SET ");

      if (passwordNueva != null && !passwordNueva.isEmpty()) {
         sqlBuilder.append("PASSWORD = '").append(passwordNueva).append("', ");
      }

      if (puestoNuevo != null && !puestoNuevo.isEmpty()) {
         sqlBuilder.append("PUESTO = '").append(puestoNuevo).append("', ");
      }

      sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
      sqlBuilder.append(" WHERE NOMBRE = '").append(nombreEmpleado).append("'");
      try {
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();

         stmt.executeUpdate(sqlBuilder.toString());

         stmt.close();
         c.close();
         mensaje = "Se ha actualizado correctamente";
         return mensaje;
      }
      catch (Exception e){
         stmt.close();
         c.close();
         mensaje = "Ha habido un error al actualizar";
         return mensaje;

      }
      finally {
         stmt.close();
         c.close();
      }

   }

   public float obtenerBeneficiosTotales() {
      Connection c = null;
      Statement stmt = null;
      ResultSet rs = null;
      float totalBeneficios = 0;
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");

         stmt = c.createStatement();
         String sql = "SELECT SUM(BENEFICIOS) AS TOTAL FROM PRODUCTOS";
         rs = stmt.executeQuery(sql);
         if (rs.next()) {
            totalBeneficios = rs.getFloat("TOTAL");
         }

      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
            if (stmt != null) {
               stmt.close();
            }
            if (c != null) {
               c.close();
            }
         } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
         }
      }
      return totalBeneficios;
   }

   public float obtenerPrecio(String nombre) throws SQLException {
      Connection c = null;
      Statement stmt = null;
      String nombreBuscar = nombre;
      ResultSet rs = null;
      String sql = "SELECT PRECIO FROM PRODUCTOS WHERE NOMBRE = '" + nombreBuscar + "'";

      float precio = 0;
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:baseDeDatos.db");
         stmt = c.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {

            precio = rs.getFloat("PRECIO");

         }
         rs.close();
         stmt.close();
         c.close();
         return precio;
      } catch (SQLException ex) {

         System.out.println("Ha habido un error");

         if (rs != null) {
            rs.close();
         }
         if (stmt != null) {
            stmt.close();
         }
         if (c != null) {
            c.close();
         }
         return precio;
      } catch (ClassNotFoundException e) {

         if (rs != null) {
            rs.close();
         }
         if (stmt != null) {
            stmt.close();
         }
         if (c != null) {
            c.close();
         }

         throw new RuntimeException(e);

      }


   }







}





