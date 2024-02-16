package org.application.bwp.rentalskiequcrud.gui.menager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.application.bwp.rentalskiequcrud.database.DatabaseService;
import org.application.bwp.rentalskiequcrud.database.DatabaseServiceImp;
import org.application.bwp.rentalskiequcrud.entity.Customer;
import org.application.bwp.rentalskiequcrud.entity.Reservation;
import org.application.bwp.rentalskiequcrud.entity.SkiEqu;
import org.application.bwp.rentalskiequcrud.entity.enums.Payment;
import org.application.bwp.rentalskiequcrud.entity.enums.Status;

import java.net.URL;
import java.time.LocalDate;
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
    @FXML
    private Button refreshReservationListButton;
    @FXML
    private Button reservationRemoveButton;
    @FXML
    private Button reservationUpdateButton;

    // TableColumns
    //Customer
    @FXML
    private TableColumn<Customer, Integer> customerIdTable;
    @FXML
    private TableColumn<Customer, String> customerNameTable;

    //SkiEqu
    @FXML
    private TableColumn<SkiEqu, Integer> equIdTable;
    @FXML
    private TableColumn<SkiEqu, String> equRodzajTable;
    @FXML
    private TableColumn<SkiEqu, Status> equStatusTable;
    @FXML
    private TableColumn<SkiEqu, Integer> skiEquDimTable;

    //Reservation
    @FXML
    private TableColumn<Reservation, Integer> reservationIdTable;
    @FXML
    private TableColumn<Reservation, Integer> reservationCustomerIdTable;
    @FXML
    private TableColumn<Reservation, Integer> resevationEquIdTable;
    @FXML
    private TableColumn<Reservation, LocalDate> reservationDateStartTable;
    @FXML
    private TableColumn<Reservation, LocalDate> reservationDateEndTable;
    @FXML
    private TableColumn<Reservation, String> resevationStatusTable;
    @FXML
    private TableColumn<Reservation, String> reservationPaymetStatusTable;

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
    @FXML
    private TextField equDimUpdateTextFiled;
    @FXML
    private TextField equDimAddTextFiled;
    @FXML
    private TextField reservationSearchBar;

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
    @FXML
    private ComboBox<String> reservationStatusSearchComboBox;
    @FXML
    private ComboBox<String> reservationPaymentSearchComboBox;
    @FXML
    private ComboBox<String> reservationStatusUpdateComboBox;
    @FXML
    private ComboBox<String> reservationPaymentUpdateComboBox;

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
    @FXML
    private TableView<Reservation> reservationTable;

    // ImageView
    @FXML
    private ImageView logoImageView;

    //DatePickers
    @FXML
    private DatePicker reservationDateStartSearch;
    @FXML
    private DatePicker reservationDateEndSearch;
    @FXML
    private DatePicker reservationDateStartUpdate;
    @FXML
    private DatePicker reservationDateEndUpdate;


    private final DatabaseService databaseService = DatabaseServiceImp.getInstance();
    private final ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    private final ObservableList<SkiEqu> skiEquObservableList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //main page initialization
        init();
        loadLogo();

        //db initialization
        initDb();
        loadSaveDb();
        saveDb();
        dropDb();

        //customers tab
        loadDataToTableCustomers();
        removeCustomer();
        updateCustomerData();
        addCustomer();

        //ski equ tab
        prepareSkiComboBox(statusComboBox);
        prepareSkiComboBox(statusAddEquComboBox);
        prepareSkiComboBox(statusUpdateComboBox);

        loadDataToTableSki();
        addSkiEqu();
        removeSkiEqu();
        updateSkiEqu();

        //reservation tab
        preparePaymentComboBox(reservationPaymentSearchComboBox);
        prepareReservationStatusComboBox(reservationStatusSearchComboBox);

        preparePaymentComboBox(reservationPaymentUpdateComboBox);
        prepareReservationStatusComboBox(reservationStatusUpdateComboBox);


        loadDataToTableReservation();
        reservationRemove();
        updateReservation();

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
            prepareReservationData();

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
            prepareReservationData();
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
                String login = (loginCustomerTextField == null || loginCustomerTextField.getText().isBlank()) ? customer.getNazwa() : loginCustomerTextField.getText();
                String password = (passwordCustomerTextField == null || passwordCustomerTextField.getText().isBlank()) ? customer.getPassword() : passwordCustomerTextField.getText();

                Customer updatedCustomer = new Customer()
                        .setId(customer.getId())
                        .setNazwa(login)
                        .setPassword(password)
                        .build();

                if (databaseService.updateCustomer(updatedCustomer)) {
                    prepareCustomerData();
                    loginCustomerTextField.clear();
                    passwordCustomerTextField.clear();
                }
            }
        });
    }

    private void addCustomer() {
        addCustomerButton.setOnAction(event -> {
            String login = loginAddCustomerTextFiled.getText();
            String password = passwordAddCustomerTextField.getText();

            if (login == null || password == null || login.isBlank() || password.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd");
                alert.setHeaderText("Puste pole");
                alert.setContentText("Wypełnij wszystkie pola");
                alert.showAndWait();
                return;
            }

            Customer customer = new Customer()
                    .setNazwa(login)
                    .setPassword(password)
                    .build();

            if (databaseService.insertCustomer(customer)) {
                prepareCustomerData();
                loginAddCustomerTextFiled.clear();
                passwordAddCustomerTextField.clear();
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
        skiEquDimTable.setCellValueFactory(new PropertyValueFactory<>("dlugosc"));

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
            boolean paramDlugoscMatches = Integer.toString(skiEqu.getDlugosc()).contains(filter);

            matchesSearch = paramRodzajMatches || paramIdMatches || paramDlugoscMatches;
        }

        return matchesSearch;

    }

    private boolean isMatchesStatusSki(SkiEqu skiEqu, String statusFilter) {

        boolean matchesSearch = true;

        if (statusFilter != null && !statusFilter.isBlank() && !statusFilter.equals("Select status")) {
            matchesSearch = skiEqu.getStatus().toString().equals(statusFilter);
        }

        return matchesSearch;

    }

    private void addSkiEqu() {
        equAddButton.setOnAction(event -> {
            String rodzaj = equAddRodzajTextField.getText();
            String status = statusAddEquComboBox.getValue();
            String dlugosc = equDimAddTextFiled.getText();

            if (rodzaj == null || rodzaj.isBlank()
                    || status == null || status.isBlank()
                    || status.equals("Select status")
                    || dlugosc == null || dlugosc.isBlank()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd");
                alert.setHeaderText("Puste pole");
                alert.setContentText("Wypełnij wszystkie pola");
                alert.showAndWait();
                return;

            }

            try {
                int dlugoscInt = Integer.parseInt(dlugosc);

                SkiEqu skiEqu = new SkiEqu()
                        .setRodzaj(rodzaj)
                        .setStatus(Status.valueOf(status))
                        .setDlugosc(dlugoscInt)
                        .build();

                if (!databaseService.insertSki(skiEqu)) return;

                prepareSkiData();
                equAddRodzajTextField.clear();
                statusAddEquComboBox.setValue("Select status");
                equDimAddTextFiled.clear();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błędna wartość");
                alert.setContentText("Wprowadź poprawną wartość długości");
                alert.showAndWait();

            }

        });
    }

    private void removeSkiEqu() {
        equRemoveItemButton.setOnAction(event -> {
            SkiEqu skiEqu = magazineTable.getSelectionModel().getSelectedItem();
            if (skiEqu != null) {
                databaseService.deleteSki(skiEqu.getId());
                skiEquObservableList.remove(skiEqu);
            }
        });
    }

    private void updateSkiEqu() {
        equUpdateButton.setOnAction(event -> {
            SkiEqu skiEqu = magazineTable.getSelectionModel().getSelectedItem();
            if (skiEqu != null) {

                String rodzaj = equRodzajTextField.getText().isBlank() ? skiEqu.getRodzaj() : equRodzajTextField.getText();
                Status status = (statusUpdateComboBox.getValue() == null || statusUpdateComboBox.getValue().equals("Select status")) ? skiEqu.getStatus() : Status.valueOf(statusUpdateComboBox.getValue());
                int dlugoscInt;

                try {
                    dlugoscInt = equDimUpdateTextFiled.getText().isBlank() ? skiEqu.getDlugosc() : Integer.parseInt(equDimUpdateTextFiled.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błędna wartość");
                    alert.setContentText("Wprowadź poprawną wartość");
                    alert.showAndWait();
                    return;
                }

                SkiEqu updatedSkiEqu = new SkiEqu()
                        .setId(skiEqu.getId())
                        .setRodzaj(rodzaj)
                        .setStatus(status)
                        .setDlugosc(dlugoscInt)
                        .build();

                if (databaseService.updateSki(updatedSkiEqu)) {
                    prepareSkiData();
                    equRodzajTextField.clear();
                    equDimUpdateTextFiled.clear();
                    statusUpdateComboBox.setValue("Select status");
                }
            }
        });
    }

    //reservation tab
    //-------------------------------------------------------------------------------------------------------------

    private void loadDataToTableReservation() {
        refreshReservationListButton.setOnAction(event -> prepareReservationData());
    }

    private void preparePaymentComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Payment.OPLACONA.toString());
        comboBox.getItems().add(Payment.NIEOPLACONA.toString());
    }

    private void prepareReservationStatusComboBox(ComboBox<String> comboBox) {
        comboBox.getItems().add("Select status");
        comboBox.getItems().add(Status.ZAKONCZONA.toString());
        comboBox.getItems().add(Status.WYCOFANA.toString());
        comboBox.getItems().add(Status.ROZPOCZETA.toString());
        comboBox.getItems().add(Status.ZALOZONA.toString());
    }

    private void prepareReservationData() {

        if (!reservationObservableList.isEmpty()) reservationObservableList.clear();

        List<Reservation> reservations = databaseService.selectAllReservation();

        reservationObservableList.addAll(reservations);
        reservationIdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservationCustomerIdTable.setCellValueFactory(new PropertyValueFactory<>("id_klient"));
        resevationEquIdTable.setCellValueFactory(new PropertyValueFactory<>("id_narty"));
        reservationDateStartTable.setCellValueFactory(new PropertyValueFactory<>("data_poczatkowa"));
        reservationDateEndTable.setCellValueFactory(new PropertyValueFactory<>("data_koncowa"));
        resevationStatusTable.setCellValueFactory(new PropertyValueFactory<>("status"));
        reservationPaymetStatusTable.setCellValueFactory(new PropertyValueFactory<>("platnosc"));

        FilteredList<Reservation> filteredData = new FilteredList<>(reservationObservableList, p -> true);

        reservationSearchBar.textProperty().addListener((observable, oldValue, newValue) -> reservationUpdateFilter(filteredData));
        reservationStatusSearchComboBox.valueProperty().addListener((observable, oldValue, newValue) -> reservationUpdateFilter(filteredData));
        reservationPaymentSearchComboBox.valueProperty().addListener((observable, oldValue, newValue) -> reservationUpdateFilter(filteredData));
        reservationDateStartSearch.valueProperty().addListener((observable, oldValue, newValue) -> reservationUpdateFilter(filteredData));
        reservationDateEndSearch.valueProperty().addListener((observable, oldValue, newValue) -> reservationUpdateFilter(filteredData));

        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(reservationTable.comparatorProperty());
        reservationTable.setItems(sortedData);

    }

    private void reservationUpdateFilter(FilteredList<Reservation> filteredData) {

        String filter = reservationSearchBar.getText();
        String statusFilter = reservationStatusSearchComboBox.getValue();
        String paymentFilter = reservationPaymentSearchComboBox.getValue();
        LocalDate dateStartFilter = reservationDateStartSearch.getValue();
        LocalDate dateEndFilter = reservationDateEndSearch.getValue();

        filteredData.setPredicate(reservation -> {
            if (reservation == null) {
                return false;
            }
            return isMatchesSearchReservation(reservation, filter) && isMatchesStatusReservation(reservation, statusFilter)
                    && isMatchesPaymentReservation(reservation, paymentFilter)
                    && isMatchesDateReservation(reservation, dateStartFilter, dateEndFilter);
        });
    }

    private boolean isMatchesDateReservation(Reservation reservation, LocalDate dateStartFilter, LocalDate dateEndFilter) {

        boolean matchesSearch = true;

        if (dateStartFilter != null && dateEndFilter != null) {
            matchesSearch = reservation.getData_poczatkowa().isAfter(dateStartFilter) && reservation.getData_koncowa().isBefore(dateEndFilter);
        }
        return matchesSearch;

    }

    private boolean isMatchesPaymentReservation(Reservation reservation, String paymentFilter) {

        boolean matchesSearch = true;

        if (paymentFilter != null && !paymentFilter.isBlank() && !paymentFilter.equals("Select status")) {
            matchesSearch = reservation.getPlatnosc().toString().equals(paymentFilter);
        }
        return matchesSearch;
    }

    private boolean isMatchesStatusReservation(Reservation reservation, String statusFilter) {

        boolean matchesSearch = true;

        if (statusFilter != null && !statusFilter.isBlank() && !statusFilter.equals("Select status")) {
            matchesSearch = reservation.getStatus().toString().equals(statusFilter);
        }
        return matchesSearch;

    }

    private boolean isMatchesSearchReservation(Reservation reservation, String filter) {

        boolean matchesSearch = true;

        if (filter != null && !filter.isBlank()) {
            boolean paramIdMatches = Integer.toString(reservation.getId()).contains(filter);
            boolean paramCustomerIdMatches = Integer.toString(reservation.getId_klient()).contains(filter);
            boolean paramEquIdMatches = Integer.toString(reservation.getId_narty()).contains(filter);

            matchesSearch = paramIdMatches || paramCustomerIdMatches || paramEquIdMatches;
        }

        return matchesSearch;
    }

    private void reservationRemove() {
        reservationRemoveButton.setOnAction(event -> {
            Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
            if (reservation != null) {
                databaseService.deleteReservation(reservation.getId());
                reservationObservableList.remove(reservation);
            }
        });
    }

    private void updateReservation() {
        reservationUpdateButton.setOnAction(event -> {
            Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
            if (reservation != null) {

                int customerId = reservation.getId_klient();
                int equId = reservation.getId_narty();
                LocalDate dateStart = reservationDateStartUpdate.getValue();
                LocalDate dateEnd = reservationDateEndUpdate.getValue();

                if (dateStart.isAfter(dateEnd) || dateStart.isEqual(dateEnd) || dateEnd.isBefore(dateStart) || dateEnd.isEqual(dateStart)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Błąd");
                    alert.setHeaderText("Błędna data");
                    alert.setContentText("Data początkowa nie może być późniejsza niż data końcowa");
                    alert.showAndWait();
                    return;
                }

                Status status = (reservationStatusUpdateComboBox.getValue() == null || reservationStatusUpdateComboBox.getValue().equals("Select status")) ? reservation.getStatus() : Status.valueOf(reservationStatusUpdateComboBox.getValue());
                Payment payment = (reservationPaymentUpdateComboBox.getValue() == null || reservationPaymentUpdateComboBox.getValue().equals("Select status")) ? reservation.getPlatnosc() : Payment.valueOf(reservationPaymentUpdateComboBox.getValue());

                Reservation updatedReservation = new Reservation()
                        .setId(reservation.getId())
                        .setId_klient(customerId)
                        .setId_narty(equId)
                        .setData_poczatkowa(dateStart)
                        .setData_koncowa(dateEnd)
                        .setStatus(status)
                        .setPlatnosc(payment)
                        .build();

                if (databaseService.updateReservation(updatedReservation)) {
                    prepareReservationData();
                    reservationDateStartUpdate.getEditor().clear();
                    reservationDateEndUpdate.getEditor().clear();
                    reservationStatusUpdateComboBox.setValue("Select status");
                    reservationPaymentUpdateComboBox.setValue("Select status");
                }
            }
        });
    }

}