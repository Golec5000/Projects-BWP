package org.application.bwp.airqualityapp.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.application.bwp.airqualityapp.entity.weather.WeatherStation;
import org.application.bwp.airqualityapp.typeAdapters.LocalDateAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class ApiWeatherController {

    private static ApiWeatherController apiWeatherController;

    private ApiWeatherController() {
    }

    public static ApiWeatherController getInstance() {
        if (apiWeatherController == null) apiWeatherController = new ApiWeatherController();
        return apiWeatherController;
    }

    public List<WeatherStation> getAllData() {

        try {

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://danepubliczne.imgw.pl/api/data/synop"))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());

            Type stationListType = new TypeToken<List<WeatherStation>>() {
            }.getType();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            return gson.fromJson(response.body(), stationListType);


        }catch (URISyntaxException | IOException | InterruptedException e){
            return Collections.emptyList();
        }

    }

}
