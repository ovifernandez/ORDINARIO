package dis.ufv.ordinario.or_dinario.controllers;

import dis.ufv.ordinario.or_dinario.models.Turismo;
import dis.ufv.ordinario.or_dinario.services.ServiceBBDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Controller {

    @Autowired
    private ServiceBBDD localservice;

    @GetMapping("/api")
    public ResponseEntity<ArrayList<Turismo>> getTurismos() {

       return ResponseEntity.ok().body(localservice.LeerFicheroJson());
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Turismo> getTurismoById(@PathVariable String id) {
        return ResponseEntity.ok().body(localservice.buscarTurismoPorId(id));
    }
    @PostMapping(path = "/api",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Turismo>> crearTurismo(@RequestBody Turismo turismo) {
        return ResponseEntity.ok().body(localservice.crearTurismo(turismo));
    }
    @PutMapping(path = "/api/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Turismo> actualizarTurismo(@PathVariable String id, @RequestBody Turismo turismoActualizado) {
        return ResponseEntity.ok().body(localservice.actualizarTurismo(id, turismoActualizado));
    }
    @DeleteMapping("/api/{id}")
    public ResponseEntity<ArrayList<Turismo>> eliminarTurismo(@PathVariable String id) {
        return ResponseEntity.ok().body(localservice.eliminarTurismo(id));
    }
}
