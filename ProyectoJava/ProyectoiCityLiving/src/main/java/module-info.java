module org.example.proyectocityliving {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens org.example.proyectoicityliving to javafx.fxml;
    exports org.example.proyectoicityliving;
}