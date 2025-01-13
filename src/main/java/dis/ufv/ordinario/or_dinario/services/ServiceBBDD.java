package dis.ufv.ordinario.or_dinario.services;

import com.google.gson.Gson;
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
        ArrayList<Turismo> listaTurismo = LeerFicheroJson(fichero);
        ArrayList<String> comunidades = new ArrayList<>();

        for (Turismo turismo : listaTurismo) {
            //Si mi string de comunidades no contiene aún esta comunidad de destino, la añado al list.
            //Así, evitamos duplicados.
            if (!comunidades.contains(turismo.getDestino().getComunidad())) {
                comunidades.add(turismo.getDestino().getComunidad());
            }
        }
        //Ordena alfabeticamente el arraylist de comunidades usando la libreria Collections.
        Collections.sort(comunidades);
        return comunidades;
    }

    public ArrayList<Turismo> getTurismoByComunidad(String comunidad){
        ArrayList<Turismo> listaTurismo = LeerFicheroJson("src/main/resources/Comunidades_Agrupadas.json");
        ArrayList<Turismo> turismosBuscados = new ArrayList<>();

        //Variable booleana usada para aprovechar que el json ya está ordenado por comunidades de Destino,
        //lo que significa que una vez encontrada la comunidad, si se encuentra en los siguientes objetos una comunidad diferente,
        //significará que no hay más objetos de esa comunidad, por lo que podemos dejar de leer dicho json y parar el bucle.
        boolean comunidad_encontrada = Boolean.FALSE;

        for (int i = 0; i < listaTurismo.size(); i++) {
            if (listaTurismo.get(i).getDestino().getComunidad().equals(comunidad)) {
                turismosBuscados.add(listaTurismo.get(i));
                comunidad_encontrada = Boolean.TRUE;
            }

            if (comunidad_encontrada & !listaTurismo.get(i).getDestino().getComunidad().equals(comunidad)) {
                break;
            }
        }
        return turismosBuscados;
    }
}
