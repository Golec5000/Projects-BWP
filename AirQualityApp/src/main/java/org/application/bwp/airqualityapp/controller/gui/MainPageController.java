package org.application.bwp.airqualityapp.controller.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.application.bwp.airqualityapp.controller.api.ApiController;
import org.application.bwp.airqualityapp.entity.location.Station;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private TableView<Station> stationsTable;

    @FXML
    private TableColumn<Station, String> addressTable;

    @FXML
    private TableColumn<Station, Integer> cityIdTable;

    @FXML
    private TableColumn<Station, String> cityNameTable;

    @FXML
    private TableColumn<Station, Integer> idTable;

    @FXML
    private TableColumn<Station, String> provinceNameTable;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private TextField searchBar;

    @FXML
    private Button selectedDataButton;

    private final ApiController apiController = ApiController.getInstance();
    private final ObservableList<Station> stationObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Station> stations = apiController.getAllData();

        stations.stream()
                .filter(s -> s.getAddressStreet() == null || s.getAddressStreet().isBlank())
                .forEach(s -> s.setAddressStreet("No address"));

        fillComboBox(stations);
        fillTable(stations);

    }

    private void fillComboBox(List<Station> stations) {

        List<String> regions = new ArrayList<>(stations.stream()
                .map(station -> station.getCity().getCommune().getProvinceName())
                .distinct()
                .toList());

        regions.addFirst("Regions");

        regionComboBox.setItems(FXCollections.observableArrayList(regions));
    }

    private void fillTable(List<Station> stations) {

        stationObservableList.addAll(stations);

        idTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getAddressStreet()));
        cityIdTable.setCellValueFactory(dataCell -> new SimpleIntegerProperty(dataCell.getValue().getCity().getId()).asObject());
        cityNameTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getCity().getName()));
        provinceNameTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getCity().getCommune().getProvinceName()));

        FilteredList<Station> filteredData = new FilteredList<>(stationObservableList, p -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> updateFilter(filteredData));
        regionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter(filteredData));

        SortedList<Station> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(stationsTable.comparatorProperty());

        stationsTable.setItems(sortedData);
    }

    private void updateFilter(FilteredList<Station> filteredData) {
        String searchText = searchBar.getText();
        String selectedCategory = regionComboBox.getSelectionModel().getSelectedItem();

        filteredData.setPredicate(station -> {
            if (station == null) {
                return false;
            }
            boolean matchesSearch = isMatchesSearch(station, searchText);
            if (selectedCategory != null && !selectedCategory.isBlank()) {
                if (selectedCategory.equals("Regions")) {
                    return matchesSearch;
                } else if (!station.getCity().getCommune().getProvinceName().equals(selectedCategory)) {
                    return false;
                }
            }

            return matchesSearch;
        });
    }

    private boolean isMatchesSearch(Station station, String searchText) {
        boolean matchesSearch = true;
        if (searchText != null && !searchText.isBlank()) {
            boolean matchesCity = station.getCity().getName().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesAddress = station.getAddressStreet() != null && station.getAddressStreet().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesCityId = Integer.toString(station.getCity().getId()).contains(searchText);
            boolean matchesStationId = Integer.toString(station.getId()).contains(searchText);
            matchesSearch = matchesCity || matchesAddress || matchesCityId || matchesStationId;
        }
        return matchesSearch;
    }

    public void getData(ActionEvent event) {

    }
}

