module com.supermercado.supermercadofran {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;


    opens com.supermercado.supermercadofran to javafx.fxml;
    exports com.supermercado.supermercadofran;


}