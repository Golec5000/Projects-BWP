package org.application.bwp.rentalskiequcrud.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.application.bwp.rentalskiequcrud.database.DatabaseService;
import org.application.bwp.rentalskiequcrud.database.DatabaseServiceImp;
import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.SkiEqu;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenagerMainPageController implements Initializable {

    // Buttons
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button dropBbButton;
    @FXML
    private Button equAddButton;
    @FXML
    private Button equRefreshListButton;
    @FXML
    private Button equRemoveItemButton;
    @FXML
    private Button equUpdateButton;
    @FXML
    private Button initDbButton;
    @FXML
    private Button loadSaveDbButton;
    @FXML
    private Button refreshCustomersListButton;
    @FXML
    private Button removeCustomerButton;
    @FXML
    private Button saveDbButton;
    @FXML
    private Button updateCustomerDataButton;

    // TableColumns
    @FXML
    private TableColumn<Customer, Integer> customerIdTable;
    @FXML
    private TableColumn<Customer, String> customerNameTable;
    @FXML
    private TableColumn<SkiEqu, Integer> equIdTable;
    @FXML
    private TableColumn<SkiEqu, String> equRodzajTable;
    @FXML
    private TableColumn<SkiEqu, Status> equStatusTable;

    // TextFields
    @FXML
    private TextField customerSearchBar;
    @FXML
    private TextField equAddRodzajTextField;
    @FXML
    private TextField equRodzajTextField;
    @FXML
    private TextField equSearchBar;
    @FXML
    private TextField loginAddCustomerTextFiled;
    @FXML
    private TextField loginCustomerTextField;

    // PasswordFields
    @FXML
    private PasswordField passwordAddCustomerTextField;
    @FXML
    private PasswordField passwordCustomerTextField;

    // ComboBoxes
    @FXML
    private ComboBox<String> statusAddEquComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private ComboBox<String> statusUpdateComboBox;

    // Tabs
    @FXML
    private Tab customersTab;
    @FXML
    private Tab magazineTab;
    @FXML
    private Tab reservationTab;

    // TableViews
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableView<SkiEqu> magazineTable;

    // ImageView
    @FXML
    private ImageView logoImageView;


    private final DatabaseService databaseService = DatabaseServiceImp.getInstance();
    private final ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    private final ObservableList<SkiEqu> skiEquObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //main page and db initialization
        init();
        loadLogo();

        initDb();
        loadSaveDb();
        saveDb();
        dropDb();

        //customers tab
        loadDataToTableCustomers();
        removeCustomer();
        updateCustomerData();

        //ski equ tab
        prepareSkiComboBox(statusComboBox);
        prepareSkiComboBox(statusAddEquComboBox);
        prepareSkiComboBox(statusUpdateComboBox);
        loadDataToTableSki();

    }

    //main page and db initialization
    //-------------------------------------------------------------------------------------------------------------
    private void init() {

        if (databaseService.checkIfDatabaseExist()) {

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);

            initDbButton.setDisable(true);

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);

            prepareCustomerData();
            prepareSkiData();

            return;

        }

        magazineTab.setDisable(true);
        reservationTab.setDisable(true);
        customersTab.setDisable(true);
    }

    private void initDb() {
        initDbButton.setOnAction(event -> {

            databaseService.createDatabase();

            initDbButton.setDisable(true);

            databaseService.createSkiTable();
            databaseService.createCustomerTable();
            databaseService.createReservationTable();

            magazineTab.setDisable(false);
            reservationTab.setDisable(false);
            customersTab.setDisable(false);
        });
    }

    private void loadSaveDb() {
        loadSaveDbButton.setOnAction(event -> {

            databaseService.insertSkiData();
            databaseService.insertCustomerData();
            databaseService.insertReservationData();

            prepareCustomerData();
            prepareSkiData();
        });
    }

    private void saveDb() {
        saveDbButton.setOnAction(event -> {
            databaseService.saveSkiDataToFile();
            databaseService.saveCustomerDataToFile();
            databaseService.saveReservationDataToFile();
        });
    }

    private void dropDb() {
        dropBbButton.setOnAction(event -> {
            databaseService.dropDatabase();
            initDbButton.setDisable(false);
            init();
        });
    }

    private void loadLogo() {
        logoImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/org/application/bwp/rentalskiequcrud/images/skiImage.jpg")).toString()));
    }

    //customers tab
    //-------------------------------------------------------------------------------------------------------------
    private void loadDataToTableCustomers() {
        refreshCustomersListButton.setOnAction(event -> prepareCustomerData());
    }

    private void prepareCustomerData() {
        if (!customerObservableList.isEmpty()) customerObservableList.clear();

        List<Customer> customers = databaseService.selectAllCustomer();

        customerObservableList.addAll(customers);

        customerIdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameTable.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        FilteredList<Customer> filteredData = new FilteredList<>(customerObservableList, p -> true);

        customerSearchBar.textProperty().addListener((observable, oldValue, newValue) -> customerUpdateFilter(filteredData));

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customersTable.comparatorProperty());
        customersTable.setItems(sortedData);
    }

    private void customerUpdateFilter(FilteredList<Customer> filteredData) {
        String filter = customerSearchBar.getText();

        filteredData.setPredicate(customer -> {
            if (customer == null) {
                return false;
            }
            return isMatchesSearchCustomer(customer, filter);
        });
    }

    private boolean isMatchesSearchCustomer(Customer customer, String searchText) {
        boolean matchesSearch = true;
        if (searchText != null && !searchText.isBlank()) {
            boolean paramNameMatches = customer.getNazwa().toLowerCase().contains(searchText.toLowerCase());
            boolean paramIdMatches = Integer.toString(customer.getId()).contains(searchText);
            matchesSearch = paramNameMatches || paramIdMatches;
        }
        return matchesSearch;
    }

    private void removeCustomer() {
        removeCustomerButton.setOnAction(event -> {
            Customer customer = customersTable.getSelectionModel().getSelectedItem();
            if (customer != null) {
                databaseService.deleteCustomer(customer.getId());
                customerObservableList.remove(customer);
            }
        });
    }

    private void updateCustomerData() {
        updateCustomerDataButton.setOnAction(event -> {
            Customer customer = customersTable.getSelectionModel().getSelectedItem();
            if (customer != null) {

                String login;
                String password;

                if (!loginCustomerTextField.getText().isBlank())
                    login = loginCustomerTextField.getText();
                else login = customer.getNazwa();

                if (!passwordCustomerTextField.getText().isBlank())
                    password = passwordCustomerTextField.getText();
                else password = customer.getPassword();


                Customer updatedCustomer = new Customer()
                        .setId(customer.getId())
                        .setNazwa(login)
                        .setPassword(password)
                        .build();

                if (!databaseService.updateCustomer(updatedCustomer)) return;

                prepareCustomerData();
                loginCustomerTextField.clear();
                passwordCustomerTextField.clear();
            }
        });
    }

    //ski equ tab
    //-------------------------------------------------------------------------------------------------------------
    private void loadDataToTableSki() {
        equRefreshListButton.setOnAction(event -> prepareSkiData());
    }

    private void prepareSkiComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Status.DOSTEPNE.toString());
        comboBox.getItems().add(Status.NIEDOSTEPNE.toString());

    }

    private void prepareSkiData() {

        if (!skiEquObservableList.isEmpty()) skiEquObservableList.clear();

        List<SkiEqu> skiEqus = databaseService.selectAllSki();

        skiEquObservableList.addAll(skiEqus);
        equIdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        equRodzajTable.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));
        equStatusTable.setCellValueFactory(new PropertyValueFactory<>("status"));

        FilteredList<SkiEqu> filteredData = new FilteredList<>(skiEquObservableList, p -> true);

        equSearchBar.textProperty().addListener((observable, oldValue, newValue) -> skiUpdateFilter(filteredData));
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> skiUpdateFilter(filteredData));

        SortedList<SkiEqu> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(magazineTable.comparatorProperty());
        magazineTable.setItems(sortedData);

    }

    private void skiUpdateFilter(FilteredList<SkiEqu> filteredData) {

        String filter = equSearchBar.getText();
        String statusFilter = statusComboBox.getValue();

        filteredData.setPredicate(ski -> {
            if (ski == null) {
                return false;
            }
            return isMatchesSearchSki(ski, filter) && isMatchesStatusSki(ski, statusFilter);
        });

    }

    private boolean isMatchesSearchSki(SkiEqu skiEqu, String filter) {

        boolean matchesSearch = true;

        if (filter != null && !filter.isBlank()) {
            boolean paramRodzajMatches = skiEqu.getRodzaj().toLowerCase().contains(filter.toLowerCase());
            boolean paramIdMatches = Integer.toString(skiEqu.getId()).contains(filter);
            matchesSearch = paramRodzajMatches || paramIdMatches;
        }

        return matchesSearch;

    }

    private boolean isMatchesStatusSki(SkiEqu skiEqu, String statusFilter) {

        boolean matchesSearch = true;

        if(statusFilter != null && !statusFilter.isBlank() && !statusFilter.equals("Select status")){
            matchesSearch = skiEqu.getStatus().toString().equals(statusFilter);
        }

        return matchesSearch;

    }

}