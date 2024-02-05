package org.application.bwp.airqualityapp.controller.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.application.bwp.airqualityapp.entity.location.Station;
import org.application.bwp.airqualityapp.entity.params.SensorData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class ApiController {

    private static ApiController apiService;

    private ApiController() {
    }

    public static ApiController getInstance() {
        if (apiService == null) apiService = new ApiController();
        return apiService;
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
}
