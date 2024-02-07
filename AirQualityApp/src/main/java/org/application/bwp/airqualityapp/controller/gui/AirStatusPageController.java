package org.application.bwp.airqualityapp.controller.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.application.bwp.airqualityapp.controller.api.ApiAirConditionController;
import org.application.bwp.airqualityapp.entity.airCondition.molecules.AirMolecules;
import org.application.bwp.airqualityapp.entity.airCondition.molecules.MoleculeValue;
import org.application.bwp.airqualityapp.entity.airCondition.params.SensorData;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AirStatusPageController implements Initializable {

    @FXML
    private TableColumn<SensorData, Integer> paramIdTable;

    @FXML
    private TableColumn<SensorData, String> paramNameTable;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<SensorData, Integer> sensorIdTable;

    @FXML
    private TableView<SensorData> stationTable;

    @FXML
    private LineChart<String, Double> paramChart;

    private final ObservableList<SensorData> sensorDataObservableList = FXCollections.observableArrayList();

    private final ApiAirConditionController apiAirConditionController = ApiAirConditionController.getInstance();

    private int stationId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();
    }

    public void prepareData() {
        List<SensorData> sensorDataList = apiAirConditionController.getStationSensorData(stationId);

        sensorDataObservableList.addAll(sensorDataList);

        sensorIdTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        paramIdTable.setCellValueFactory(dataCell -> new SimpleIntegerProperty(dataCell.getValue().getParam().getIdParam()).asObject());
        paramNameTable.setCellValueFactory(dataCell -> new SimpleStringProperty(dataCell.getValue().getParam().getParamName()));

        FilteredList<SensorData> filteredData = new FilteredList<>(sensorDataObservableList, p -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> updateFilter(filteredData));

        SortedList<SensorData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(stationTable.comparatorProperty());

        stationTable.setItems(sortedData);
    }

    private void updateFilter(FilteredList<SensorData> filteredData) {
        String searchText = searchBar.getText();

        filteredData.setPredicate(sensorData -> {
            if (sensorData == null) {
                return false;
            }
            return isMatchesSearch(sensorData, searchText);
        });
    }

    private boolean isMatchesSearch(SensorData sensorData, String searchText) {
        boolean matchesSearch = true;
        if (searchText != null && !searchText.isBlank()) {
            boolean paramNameMatches = sensorData.getParam().getParamName().toLowerCase().contains(searchText.toLowerCase());
            boolean paramIdMatches = Integer.toString(sensorData.getParam().getIdParam()).contains(searchText);
            matchesSearch = paramNameMatches || paramIdMatches;
        }
        return matchesSearch;
    }

    public void getData() {
        stationTable.setOnMouseClicked(event -> {
            SensorData sensorData = stationTable.getSelectionModel().getSelectedItem();
            if(sensorData == null) {
                showAlert("Error while loading page", "Error while loading air status page");
                return;
            }

            AirMolecules airMolecules = getAirMolecules(sensorData);
            updateChart(sensorData, airMolecules);
        });
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private AirMolecules getAirMolecules(SensorData sensorData) {
        AirMolecules airMolecules = apiAirConditionController.getAirMolecules(sensorData.getId());
        List<MoleculeValue> moleculeValuesList = new ArrayList<>(airMolecules.getValues());
        Collections.reverse(moleculeValuesList);
        airMolecules.setValues(new LinkedHashSet<>(moleculeValuesList));
        return airMolecules;
    }

    private void updateChart(SensorData sensorData, AirMolecules airMolecules) {
        paramChart.getData().clear();
        paramChart.setTitle("Param: " + sensorData.getParam().getParamName() + " chart");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        airMolecules.getValues().forEach(moleculeValue ->
                series.getData().add(new XYChart.Data<>(moleculeValue.getDate().toString(), moleculeValue.getValue()))
        );
        paramChart.getData().add(series);
    }

    public void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/application/bwp/airqualityapp/main-page-v2.fxml"));

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while loading page");
            alert.setContentText("Error while loading air status page");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
}
