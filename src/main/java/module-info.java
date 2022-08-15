module com.example.wavefunctioncollapse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.wavefunctioncollapse to javafx.fxml;
    exports com.example.wavefunctioncollapse;
}