package dis.ufv.ordinario.or_dinario.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dis.ufv.ordinario.or_dinario.models.Turismo;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class ServiceBBDD {

    public ArrayList<Turismo> LeerFicheroJson() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/TurismoComunidades.json"))) {
            return new Gson().fromJson(reader, new TypeToken<ArrayList<Turismo>>() {
            }.getType());
        } catch (
                IOException e) {
            return new ArrayList<>();
        }
    }

    public boolean writeJsonFile(String fichero, ArrayList<Turismo> listaTurismo){
        try{
            Writer writer = Files.newBufferedWriter(Paths.get(fichero));
            writer.write( new Gson().toJson(listaTurismo));
            writer.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Turismo buscarTurismoPorId(String id){
        ArrayList<Turismo> listaTurismo = LeerFicheroJson();
        for(Turismo turismo : listaTurismo){
            if(turismo.get_id().equals(id)){
                return turismo;
            }
        }
        System.out.println("No se ha encontrado ningún elemento con ese ID.");
        return null;
    }
    public Turismo actualizarTurismo(String id, Turismo turismoActualizado){
        ArrayList<Turismo> listaTurismo = LeerFicheroJson();
        for(int i = 0; i<listaTurismo.size(); i++ ){
            if(listaTurismo.get(i).get_id().equals(id)){
                listaTurismo.set(i, turismoActualizado);
                if(writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)){
                    return listaTurismo.get(i);
                }else{
                    System.out.println("No se puede leer la base de datos.");
                    return null;
                }
            }
        }
        System.out.println("No se ha encontrado ningún elemento con ese ID.");
        return null;
    }
    public ArrayList<Turismo> crearTurismo(Turismo turismoNuevo){
        ArrayList<Turismo> listaTurismo = LeerFicheroJson();
        listaTurismo.add(turismoNuevo);
        if(writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)){
            return listaTurismo;
        }else{
            System.out.println("No se puede leer la base de datos");
            return null;
        }
    }
    public ArrayList<Turismo> eliminarTurismo(String id){
        ArrayList<Turismo> listaTurismo = LeerFicheroJson();
        for(int i = 0; i<listaTurismo.size(); i++ ){
            if(listaTurismo.get(i).get_id().equals(id)){
                listaTurismo.remove(i);
                if(writeJsonFile("src/main/resources/TurismoComunidades.json", listaTurismo)){
                    return listaTurismo;
                }else{
                    System.out.println("No se puede leer la base de datos.");
                    return null;
                }
            }
        }
        return null;
    }
}
