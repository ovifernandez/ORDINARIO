package dis.ufv.ordinario.or_dinario.controller;

import dis.ufv.ordinario.or_dinario.models.Turismo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping
    public ResponseEntity<Turismo> getTurismos() {
       return null;
    }

    @GetMapping
    public ResponseEntity<Turismo> getTurismoById(@RequestParam String id) {
        return null;
    }
    @PostMapping
    public ResponseEntity<Turismo> crearTurismo(@RequestBody Turismo turismo) {
        return null;
    }
    @PutMapping
    public ResponseEntity<Turismo> actualizarTurismo(@RequestBody String id) {
        return null;
    }
    @DeleteMapping
    public ResponseEntity<Turismo> eliminarTurismo(@RequestBody String id) {
        return null;
    }
}
