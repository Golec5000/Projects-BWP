module org.application.bwp.airqualityapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    opens org.application.bwp.airqualityapp.entity.location to com.google.gson, javafx.base;
    opens org.application.bwp.airqualityapp.entity.params to com.google.gson, javafx.base;

    exports org.application.bwp.airqualityapp.application to javafx.graphics, javafx.fxml;
    opens org.application.bwp.airqualityapp.application to javafx.graphics, javafx.fxml;

    exports org.application.bwp.airqualityapp.controller.gui to javafx.fxml, javafx.graphics, java.base;
    opens org.application.bwp.airqualityapp.controller.gui to javafx.fxml, javafx.graphics, javafx.base;

    exports org.application.bwp.airqualityapp.entity.location to java.base;
    exports org.application.bwp.airqualityapp.entity.params to java.base;

    opens org.application.bwp.airqualityapp.controller.api to com.google.gson, java.base;
    exports org.application.bwp.airqualityapp.controller.api to com.google.gson, java.base;

    opens org.application.bwp.airqualityapp to javafx.graphics, javafx.fxml;

}