module org.example.trabajo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires com.google.gson;
    requires org.apache.logging.log4j;


    opens org.example.trabajo to javafx.fxml;
    opens org.example.trabajo.Domain.Modelo to javafx.base, com.google.gson;

    exports org.example.trabajo;
    exports org.example.trabajo.UI;
    exports org.example.trabajo.Domain.Modelo;
    opens org.example.trabajo.DAO to com.google.gson;
    opens org.example.trabajo.UI to javafx.fxml;
}