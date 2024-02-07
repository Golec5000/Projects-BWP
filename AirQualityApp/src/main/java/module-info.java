module org.application.bwp.airqualityapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    exports org.application.bwp.airqualityapp.application to javafx.graphics, javafx.fxml;
    exports org.application.bwp.airqualityapp.controller.gui to javafx.fxml, javafx.graphics;
    exports org.application.bwp.airqualityapp.entity.airCondition.location;
    exports org.application.bwp.airqualityapp.entity.airCondition.params;

    opens org.application.bwp.airqualityapp.application to javafx.graphics, javafx.fxml;
    opens org.application.bwp.airqualityapp.controller.gui to javafx.fxml, javafx.graphics;
    opens org.application.bwp.airqualityapp.controller.api to com.google.gson;
    opens org.application.bwp.airqualityapp to javafx.graphics, javafx.fxml;
    opens org.application.bwp.airqualityapp.entity.weather to com.google.gson;
    opens org.application.bwp.airqualityapp.entity.airCondition.location to com.google.gson;
    opens org.application.bwp.airqualityapp.entity.airCondition.params to com.google.gson;
    opens org.application.bwp.airqualityapp.entity.airCondition.molecules to com.google.gson;
}