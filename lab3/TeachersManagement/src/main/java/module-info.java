module com.example.teachers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jdk.jdi;

    opens com.example.teachers to javafx.fxml;
    exports com.example.teachers;
}