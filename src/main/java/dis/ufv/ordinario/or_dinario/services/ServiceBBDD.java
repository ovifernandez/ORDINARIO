package dis.ufv.ordinario.or_dinario.services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dis.ufv.ordinario.or_dinario.models.Turismo;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class ServiceBBDD {

    public ArrayList<Turismo> LeerFicheroJson(String fichero) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fichero))) {
            return new Gson().fromJson(reader, new TypeToken<ArrayList<Turismo>>() {
            }.getType());
        } catch (
                IOException e) {
            return new ArrayList<>();
        }
    }

    public boolean writeJsonFile(String fichero, ArrayList<Turismo> listaTurismo) {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(fichero));
            writer.write(new Gson().toJson(listaTurismo));
            writer.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Turismo buscarTurismoPorId(String id) {
        ArrayList<Turismo> listaTurismo = LeerFicheroJson("src/main/resources/TurismoComunidades.json");
        for (Turismo turismo : listaTurismo) {
            if (turismo.get_id().equals(id)) {
                return turismo;
            }
        }
        System.out.println("No se ha encontrado ningún elemento con ese ID.");
        return null;
    }

    public Turismo actualizarTurismo(String id, Turismo turismoActualizado) {
        ArrayList<Turismo> listaTurismo = LeerFicheroJson("src/main/resources/TurismoComunidades.json");
        for (int i = 0; i < listaTurismo.size(); i++) {
            if (listaTurismo.get(i).get_id().equals(id)) {
                listaTurismo.set(i, turismoActualizado);
                if (writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)) {
                    return listaTurismo.get(i);
                } else {
                    System.out.println("No se puede leer la base de datos.");
                    return null;
                }
            }
        }
        System.out.println("No se ha encontrado ningún elemento con ese ID.");
        return null;
    }

    public ArrayList<Turismo> crearTurismo(Turismo turismoNuevo) {
        ArrayList<Turismo> listaTurismo = LeerFicheroJson("src/main/resources/TurismoComunidades.json");
        listaTurismo.add(turismoNuevo);
        if (writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)) {
            return listaTurismo;
        } else {
            System.out.println("No se puede leer la base de datos");
            return null;
        }
    }

    public ArrayList<Turismo> eliminarTurismo(String id) {
        ArrayList<Turismo> listaTurismo = LeerFicheroJson("src/main/resources/TurismoComunidades.json");
        for (int i = 0; i < listaTurismo.size(); i++) {
            if (listaTurismo.get(i).get_id().equals(id)) {
                listaTurismo.remove(i);
                if (writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)) {
                    return listaTurismo;
                } else {
                    System.out.println("No se puede leer la base de datos.");
                    return null;
                }
            }
        }
        return null;
    }

    public ArrayList<String> leerComunidades(String fichero) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fichero));
            JsonObject objectAgrupados = JsonParser.parseReader(reader).getAsJsonObject();

            ArrayList<String> comunidades = new ArrayList<>();

            for (Map.Entry<String, JsonElement> claveComunidad : objectAgrupados.entrySet()) {

                String comunidad = claveComunidad.getKey();
                if (!comunidades.contains(comunidad)){
                    comunidades.add(comunidad);
                }
            }
            //Ordena alfabeticamente el arraylist de comunidades usando la libreria Collections.
            Collections.sort(comunidades);
            return comunidades;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Turismo> getTurismoByComunidad(String comunidad, String fichero){
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(fichero));
            JsonObject objectAgrupados = JsonParser.parseReader(reader).getAsJsonObject();
            ArrayList<Turismo> turismosBuscados = new ArrayList<>();

            for (Map.Entry<String, JsonElement> claveComunidad : objectAgrupados.entrySet()) {
                String comunidadActual = claveComunidad.getKey();
                JsonArray arrayComunidad = claveComunidad.getValue().getAsJsonArray();

                if(comunidadActual.equals(comunidad)){
                    // Convertir los elementos del JsonArray a objetos Turismo y añadirlos al ArrayList
                    for (JsonElement element : arrayComunidad) {
                        Turismo turismo = new Gson().fromJson(element, Turismo.class);
                        turismosBuscados.add(turismo);
                    }
                    // Una vez encontrada la comunidad, ya no es necesario continuar buscando, así que salimos del bucle
                    break;
                }
            }
            return turismosBuscados;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
