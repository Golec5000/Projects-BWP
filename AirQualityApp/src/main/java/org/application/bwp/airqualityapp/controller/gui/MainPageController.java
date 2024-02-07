package org.application.bwp.airqualityapp.controller.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.application.bwp.airqualityapp.controller.api.ApiAirConditionController;
import org.application.bwp.airqualityapp.controller.api.ApiWeatherController;
import org.application.bwp.airqualityapp.entity.airCondition.location.Station;
import org.application.bwp.airqualityapp.entity.weather.WeatherStation;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MainPageController implements Initializable {

    @FXML
    private Tab airConditionPanel;

    @FXML
    private ComboBox<String> cityNameComboBoxAirCondition;

    @FXML
    private ComboBox<String> cityNameComboBoxByBoth;

    @FXML
    private ComboBox<String> cityNameComboBoxByWeather;

    @FXML
    private TextField cityNameSearchBar;

    @FXML
    private TableColumn<Station, String> cityNameTable;

    @FXML
    private TableColumn<Station, Integer> idTable;

    @FXML
    private ImageView locationImageBox;

    @FXML
    private Label locationLabel;

    @FXML
    private Tab mainPagePanel;

    @FXML
    private ImageView mainPictureBox;

    @FXML
    private ImageView pressureImageBox;

    @FXML
    private Label pressureLabel;

    @FXML
    private ImageView rainFallImageBox;

    @FXML
    private Label rainFallLabel;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<Station, String> stationAddressTable;

    @FXML
    private TableView<Station> stationTable;

    @FXML
    private Label tempLabel;

    @FXML
    private ImageView timeImageBox;

    @FXML
    private Label timeLabel;

    @FXML
    private Tab weatherPagePanel;

    @FXML
    private ImageView windSpeedImageBox;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private ImageView tempImageBox;

    private final ApiAirConditionController apiAirConditionController = ApiAirConditionController.getInstance();
    private final ApiWeatherController apiWeatherController = ApiWeatherController.getInstance();
    private final ObservableList<Station> stationObservableList = FXCollections.observableArrayList();

    //String -> city name, boolean[] -> [0] - airCondition, [1] - weather
    private final NavigableMap<String, boolean[]> cityNameCombinedMap = new TreeMap<>();

    private List<Station> stations;

    private List<WeatherStation> weatherStations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (!cityNameCombinedMap.isEmpty()) cityNameCombinedMap.clear();

        createCityNamesCombineDataTree();

        tabsSetup(true, true);

        fillAndSetupComboBoxes();
        setComboBoxActions();
        setTextFieldAction();


    }

    //Main Page functions
    //-------------------------------------------------------------------------------------------

    private void createCityNamesCombineDataTree() {
        stations = apiAirConditionController.getAllData();
        weatherStations = apiWeatherController.getAllData();

        stations.forEach(station -> {
            if (station.getAddressStreet() == null || station.getAddressStreet().isBlank()) station.setAddressStreet("No address");

            cityNameCombinedMap.put(station.getCity().getName(), new boolean[]{true, false});
        });

        weatherStations.forEach(weatherStation -> {
            boolean[] combined = cityNameCombinedMap.getOrDefault(weatherStation.getStacja(), new boolean[2]);
            combined[1] = true;
            cityNameCombinedMap.put(weatherStation.getStacja(), combined);
        });
    }

    private void setComboBoxActions() {
        setComboBoxAction(cityNameComboBoxByBoth);
        setComboBoxAction(cityNameComboBoxAirCondition);
        setComboBoxAction(cityNameComboBoxByWeather);
    }

    private void setComboBoxAction(ComboBox<String> comboBox) {
        comboBox.setOnAction(event -> {
            String selectedCity = comboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected city: " + selectedCity);
            if (checkIsPresentGaveData(selectedCity)) {
                setDataForSelectedCity(selectedCity);
            }
        });
    }

    private void fillComboBox(NavigableMap<String, boolean[]> cityNameCombinedMap, ComboBox<String> comboBoxes) {
        comboBoxes.getItems().addAll(cityNameCombinedMap.keySet());
    }

    private void fillAndSetupComboBoxes() {
        fillComboBox(filterCityNameCombinedMap(true, true), cityNameComboBoxByBoth);
        fillComboBox(filterCityNameCombinedMap(true, false), cityNameComboBoxAirCondition);
        fillComboBox(filterCityNameCombinedMap(false, true), cityNameComboBoxByWeather);
    }

    private NavigableMap<String, boolean[]> filterCityNameCombinedMap(boolean airCondition, boolean weather) {
        return cityNameCombinedMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue()[0] == airCondition && entry.getValue()[1] == weather)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, TreeMap::new));
    }

    private void setTextFieldAction() {
        cityNameSearchBar.setOnAction(event -> {
            String enteredText = cityNameSearchBar.getText();
            System.out.println("Entered text: " + enteredText);
            if (checkIsPresentGaveData(enteredText)) {
                setDataForSelectedCity(enteredText);
            } else {
                showAlert("City not found", "Please enter a valid city name");
            }
        });
    }

    private boolean checkIsPresentGaveData(String selectedCity) {
        if (cityNameCombinedMap.containsKey(selectedCity)) {
            boolean[] combined = cityNameCombinedMap.get(selectedCity);
            tabsSetup(combined[0], combined[1]);
            return true;
        }
        return false;
    }

    private void setDataForSelectedCity(String selectedCity) {
        boolean[] combined = cityNameCombinedMap.get(selectedCity);
        if (combined[0]) {
            processAirConditionData(selectedCity);
        }
        if (combined[1]) {
            processWeatherData(selectedCity);
        }
        tabsSetup(!combined[0], !combined[1]);
    }

    private void processAirConditionData(String selectedCity) {
        List<Station> selectedCityStations = stations.stream()
                .filter(s -> s.getCity().getName().equals(selectedCity))
                .toList();
        stationObservableList.clear();
        fillTable(selectedCityStations);
        getData();
    }

    private void processWeatherData(String selectedCity) {
        Optional<WeatherStation> optionalWeatherStation = weatherStations.stream()
                .filter(w -> w.getStacja().equals(selectedCity))
                .findFirst();
        if (optionalWeatherStation.isPresent()) {

            setUpWeatherPage(optionalWeatherStation.get());
        } else {
            showAlert("Weather station not found", "Weather station for selected city not found");
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void tabsSetup(boolean airCondition, boolean weather) {
        mainPagePanel.setDisable(false);

        airConditionPanel.setDisable(airCondition);

        weatherPagePanel.setDisable(weather);

        stationTable.setEditable(false);

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

    public void getData() {
        stationTable.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/application/bwp/airqualityapp/airCondition/air-stats.fxml"));

            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                showAlert("Error while loading page", "Error while loading air status page");
                return;
            }

            Station selectedStation = stationTable.getSelectionModel().getSelectedItem();

            AirStatusPageController airStatusPageController = loader.getController();
            if (selectedStation != null) {
                airStatusPageController.setStationId(selectedStation.getId());
                airStatusPageController.prepareData();


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Station not found", "Station for selected city not found");
            }

        });
    }

    //Weather Page functions
    //-------------------------------------------------------------------------------------------

    private void setUpWeatherPage(WeatherStation weatherStation) {

        pictureLoad(mainPictureBox, "sun.jpg");

        tempLabel.setText(weatherStation.getTemperatura() + " °C");
        pictureLoad(tempImageBox, "temp.jpg");

        timeLabel.setText(weatherStation.getGodzina_pomiaru() + ":00");
        pictureLoad(timeImageBox, "clock.jpg");

        locationLabel.setText(weatherStation.getStacja());
        pictureLoad(locationImageBox, "location.jpg");

        windSpeedLabel.setText(weatherStation.getPredkosc_wiatru() + " m/s");
        pictureLoad(windSpeedImageBox, "wind.jpg");

        pressureLabel.setText(weatherStation.getCisnienie() + " hPa");
        pictureLoad(pressureImageBox, "pressure.jpg");

        rainFallLabel.setText(weatherStation.getSuma_opadu() + " mm");
        pictureLoad(rainFallImageBox, "rainfall.jpg");


    }

    private void pictureLoad(ImageView imageView, String path){
        try{
            String mainPicturePath = "/org/application/bwp/airqualityapp/image/";
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(mainPicturePath + path)));
            imageView.setImage(image);
        }catch (Exception e){
            e.getStackTrace();
            showAlert("Error while loading image", "Error while loading " + path + " image");
        }
    }
}

