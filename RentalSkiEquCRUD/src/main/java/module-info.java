module org.application.bwp.rentalskiequcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires com.google.gson;

    exports org.application.bwp.rentalskiequcrud.main.menager;
    exports org.application.bwp.rentalskiequcrud.database;
    exports org.application.bwp.rentalskiequcrud.entity;
    exports org.application.bwp.rentalskiequcrud.gui;
    exports org.application.bwp.rentalskiequcrud.jsonFile.adapters;
    exports org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile;
    exports org.application.bwp.rentalskiequcrud.jsonFile.writerToFile;
    exports org.application.bwp.rentalskiequcrud.entity.enums;
    exports org.application.bwp.rentalskiequcrud.entity.buildres;

    opens org.application.bwp.rentalskiequcrud.main.menager;
    opens org.application.bwp.rentalskiequcrud.database;
    opens org.application.bwp.rentalskiequcrud.entity;
    opens org.application.bwp.rentalskiequcrud.gui;
    opens org.application.bwp.rentalskiequcrud.jsonFile.adapters;
    opens org.application.bwp.rentalskiequcrud.jsonFile.readerFromFile;
    opens org.application.bwp.rentalskiequcrud.jsonFile.writerToFile;
    opens org.application.bwp.rentalskiequcrud.entity.enums;
    opens org.application.bwp.rentalskiequcrud.entity.buildres;
}