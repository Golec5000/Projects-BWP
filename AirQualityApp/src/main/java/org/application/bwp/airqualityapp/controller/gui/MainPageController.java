package org.application.bwp.airqualityapp.controller.gui;

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
import org.application.bwp.airqualityapp.controller.api.ApiAirConditionController;
import org.application.bwp.airqualityapp.controller.api.ApiWeatherController;
import org.application.bwp.airqualityapp.entity.airCondition.location.Station;
import org.application.bwp.airqualityapp.entity.weather.WeatherStation;

import java.net.URL;
import java.util.List;
import java.util.NavigableMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class MainPageController implements Initializable {

    @FXML
    private Tab airConditionPanel;

    @FXML
    private ComboBox<String> cityNameComboBox;

    @FXML
    private TextField cityNameSearchBar;

    @FXML
    private TableView<Station> stationTable;

    @FXML
    private TableColumn<Station, String> cityNameTable;

    @FXML
    private TableColumn<Station, Integer> idTable;

    @FXML
    private TableColumn<Station, String> stationAddressTable;

    @FXML
    private Tab mainPagePanel;

    @FXML
    private Button selectedDataButton;

    @FXML
    private TextField searchBar;

    @FXML
    private Tab weatherPagePanel;

    private final ApiAirConditionController apiAirConditionController = ApiAirConditionController.getInstance();
    private final ApiWeatherController apiWeatherController = ApiWeatherController.getInstance();
    private final ObservableList<Station> stationObservableList = FXCollections.observableArrayList();

    //String -> city name, boolean[] -> [0] - airCondition, [1] - weather
    private final NavigableMap<String, boolean[]> cityNameCombinedMap = new TreeMap<>();

    private List<Station> stations;

    private List<WeatherStation> weatherStations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!cityNameCombinedMap.isEmpty()) cityNameCombinedMap.clear();

        createCityNamesCombineDataTree();

        tabsSetup(true, true);

        fillComboBox(cityNameCombinedMap);

        setComboBoxAction();
        setTextFieldAction();

    }

    //Main Page functions
    //-------------------------------------------------------------------------------------------

    private void createCityNamesCombineDataTree() {
        stations = apiAirConditionController.getAllData();

        stations.stream()
                .filter(s -> s.getAddressStreet() == null || s.getAddressStreet().isBlank() || s.getAddressStreet().isEmpty())
                .forEach(s -> s.setAddressStreet("No address"));

        weatherStations = apiWeatherController.getAllData();

        stations.forEach(station -> {
            boolean[] combined = new boolean[2];
            combined[0] = true;
            cityNameCombinedMap.put(station.getCity().getName(), combined);
        });

        weatherStations.forEach(weatherStation -> {
            boolean[] combined = cityNameCombinedMap.computeIfAbsent(weatherStation.getStacja(), k -> new boolean[2]);
            combined[1] = true;
        });
    }

    private void setComboBoxAction() {
        cityNameComboBox.setOnAction(event -> {
            String selectedCity = cityNameComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected city: " + selectedCity);
            if(checkIsPresentGaveData(selectedCity)){
                setDataForSelectedCity(selectedCity);
            }
        });
    }

    private void setTextFieldAction() {
        cityNameSearchBar.setOnAction(event -> {
            String enteredText = cityNameSearchBar.getText();
            System.out.println("Entered text: " + enteredText);
            if(checkIsPresentGaveData(enteredText)){
                setDataForSelectedCity(enteredText);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("City not found");
                alert.setContentText("Please enter a valid city name");
                alert.showAndWait();
            }
        });
    }

    private boolean checkIsPresentGaveData(String selectedCity) {
        if(cityNameCombinedMap.containsKey(selectedCity)) {
            boolean[] combined = cityNameCombinedMap.get(selectedCity);
            tabsSetup(combined[0], combined[1]);
            return true;
        }
        return false;
    }

    private void setDataForSelectedCity(String selectedCity) {

        boolean[] combined = cityNameCombinedMap.get(selectedCity);
        if(combined[0]) {
            List<Station> selectedCityStations = stations.stream()
                    .filter(s -> s.getCity().getName().equals(selectedCity))
                    .toList();

            selectedCityStations.forEach(System.out::println);

            stationObservableList.clear();

            fillTable(selectedCityStations);

        }
        if(combined[1]) {
            boolean isPresent = weatherStations.stream()
                    .anyMatch(w -> w.getStacja().equals(selectedCity));

            if(isPresent) {
                WeatherStation selectedCityWeatherStation = weatherStations.stream()
                        .filter(w -> w.getStacja().equals(selectedCity))
                        .findFirst()
                        .get();

                System.out.println(selectedCityWeatherStation);
            }else {
                System.out.println("No weather data for this city");
            }

        }

        System.out.println(combined[0] + " " + combined[1]);

        tabsSetup(!combined[0], !combined[1]);


    }

    private void tabsSetup(boolean airCondition, boolean weather) {
        mainPagePanel.setDisable(false);

        airConditionPanel.setDisable(airCondition);

        weatherPagePanel.setDisable(weather);

    }

    private void fillComboBox(NavigableMap<String, boolean[]> cityNameCombinedMap) {
        cityNameComboBox.getItems().addAll(cityNameCombinedMap.keySet());
    }

    //Air Condition Page functions
    //-------------------------------------------------------------------------------------------

    private void fillTable(List<Station> stations) {

        stationObservableList.addAll(stations);

        idTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        stationAddressTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getAddressStreet()));
        cityNameTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getCity().getName()));

        FilteredList<Station> filteredData = new FilteredList<>(stationObservableList, p -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> updateFilter(filteredData));

        SortedList<Station> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(stationTable.comparatorProperty());

        stationTable.setItems(sortedData);
    }

    private void updateFilter(FilteredList<Station> filteredData) {
        String searchText = searchBar.getText();

        filteredData.setPredicate(station -> {
            if (station == null) {
                return false;
            }

            return isMatchesSearch(station, searchText);
        });
    }

    private boolean isMatchesSearch(Station station, String searchText) {
        boolean matchesSearch = true;
        if (searchText != null && !searchText.isBlank()) {
            boolean matchesCity = station.getCity().getName().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesAddress = station.getAddressStreet() != null && station.getAddressStreet().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesStationId = Integer.toString(station.getId()).contains(searchText);
            matchesSearch = matchesCity || matchesAddress || matchesStationId;
        }
        return matchesSearch;
    }

    public void getData(ActionEvent event) {


    }

    //Weather Page functions
    //-------------------------------------------------------------------------------------------


}

