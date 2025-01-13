package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class FrontService {
    private final static String URL_API = "http://127.0.0.1:8080/api";

    public ArrayList<Turismo> getTurismos(){

        String url = String.format("%s/db", URL_API);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try{
            request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Turismo>>() {}.getType();
            ArrayList<Turismo> responseObj = gson.fromJson(response.body(), listType);

            return responseObj;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Turismo getTurismo(String id) throws IOException, InterruptedException, URISyntaxException {
        if(id == null){
            System.out.println("Id no encontrada");
        }
        else {
            String url = String.format("%s/db/%s", URL_API, id);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = null;
            try {
                request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                Gson gson = new Gson();
                Turismo responseObj = gson.fromJson(response.body(), Turismo.class);

                return responseObj;
            } catch (
                    URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public Turismo editarTurismo(String id, Turismo turismo) {
        String url = String.format("%s/db/%s", URL_API, id);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(turismo);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Turismo responseObj = gson.fromJson(response.body(), Turismo.class);

            return responseObj;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Turismo agregarTurismo(Turismo turismoNew) {
        String url = String.format("%s/db", URL_API);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        Gson gson = new Gson();
        String json = gson.toJson(turismoNew);
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Si la respuesta http es 201, se ha creado la página correctamente
            if(response.statusCode() == 201){
                Turismo responseObj = gson.fromJson(response.body(), Turismo.class);
                return responseObj;
            }else{
                throw new RuntimeException("Error al agregar el turismo, por error de temporización");
            }
        } catch (URISyntaxException | IOException |InterruptedException e) {
            throw new RuntimeException("Error al realizar el POST: ", e);
        }

    }
}
