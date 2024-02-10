module org.application.bwp.airqualityapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    exports org.application.bwp.airqualityapp.application;
    exports org.application.bwp.airqualityapp.controller.gui;
    exports org.application.bwp.airqualityapp.entity.airCondition.location;
    exports org.application.bwp.airqualityapp.entity.airCondition.params;

    opens org.application.bwp.airqualityapp.application;
    opens org.application.bwp.airqualityapp.controller.gui;
    opens org.application.bwp.airqualityapp.controller.api;
    opens org.application.bwp.airqualityapp.entity.weather;
    opens org.application.bwp.airqualityapp.entity.airCondition.location;
    opens org.application.bwp.airqualityapp.entity.airCondition.params;
    opens org.application.bwp.airqualityapp.entity.airCondition.molecules;
}