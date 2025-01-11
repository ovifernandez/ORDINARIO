package org.vaadin.example.services;

import com.google.common.reflect.TypeToken;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.stereotype.Service;
import org.vaadin.example.models.Turismo;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class FrontService implements Serializable {
    private final static String URL_API = "http://localhost:8080/api";

    public ArrayList<Turismo> getTurismos(){
        String url = URL_API;
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
}
