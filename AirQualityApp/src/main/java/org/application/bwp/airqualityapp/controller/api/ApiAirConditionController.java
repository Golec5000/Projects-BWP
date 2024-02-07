package org.application.bwp.airqualityapp.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.application.bwp.airqualityapp.entity.airCondition.location.Station;
import org.application.bwp.airqualityapp.entity.airCondition.molecules.AirMolecules;
import org.application.bwp.airqualityapp.entity.airCondition.params.SensorData;
import org.application.bwp.airqualityapp.typeAdapters.LocalDateAdapter;
import org.application.bwp.airqualityapp.typeAdapters.LocalDateTimeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ApiAirConditionController {

    private static ApiAirConditionController apiAirService;

    private ApiAirConditionController() {
    }

    public static ApiAirConditionController getInstance() {
        if (apiAirService == null) apiAirService = new ApiAirConditionController();
        return apiAirService;
    }

    public List<Station> getAllData() {

        try {

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.gios.gov.pl/pjp-api/rest/station/findAll"))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();


            HttpResponse<String> response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());

            Type stationListType = new TypeToken<List<Station>>() {
            }.getType();

            return new Gson().fromJson(response.body(), stationListType);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            return Collections.emptyList();
        }

    }

    public List<SensorData> getStationSensorData(int stationId) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationId))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());

            Type sensorListType = new TypeToken<List<SensorData>>() {
            }.getType();

            return new Gson().fromJson(response.body(), sensorListType);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            return Collections.emptyList();
        }
    }

    public AirMolecules getAirMolecules(int sensorId) {
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.gios.gov.pl/pjp-api/rest/data/getData/" + sensorId))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            return gson.fromJson(response.body(), AirMolecules.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }
}
