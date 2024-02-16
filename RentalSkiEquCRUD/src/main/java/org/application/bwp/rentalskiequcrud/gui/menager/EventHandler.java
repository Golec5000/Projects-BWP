package org.application.bwp.rentalskiequcrud.gui.menager;

public class EventHandler {

    private UIOperations uiOperations;
    private DatabaseOperations databaseOperations;

    public EventHandler(UIOperations uiOperations, DatabaseOperations databaseOperations) {
        this.uiOperations = uiOperations;
        this.databaseOperations = databaseOperations;
    }

}
