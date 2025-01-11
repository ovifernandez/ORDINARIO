package dis.ufv.ordinario.or_dinario.controllers;

import dis.ufv.ordinario.or_dinario.models.Turismo;
import dis.ufv.ordinario.or_dinario.services.ServiceBBDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private ServiceBBDD localservice;

    @GetMapping("/api")
    public ResponseEntity<Turismo> getTurismos() {
       return null;
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Turismo> getTurismoById(@RequestParam String id) {
        return null;
    }
    @PostMapping(path = "/api",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Turismo> crearTurismo(@RequestBody Turismo turismo) {
        return null;
    }
    @PutMapping(path = "/api/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Turismo> actualizarTurismo(@RequestBody String id) {
        return null;
    }
    @DeleteMapping(path = "/api",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Turismo> eliminarTurismo(@RequestBody String id) {
        return null;
    }
}
