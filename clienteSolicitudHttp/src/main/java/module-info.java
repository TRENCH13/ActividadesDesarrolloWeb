module com.example.clientesolicitudhttp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires javafx.web;

    opens com.example.clientesolicitudhttp to javafx.fxml;
    exports com.example.clientesolicitudhttp;
}