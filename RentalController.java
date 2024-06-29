package pl.pjatk.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/rent")
public class RentalController {
    private final RentalService rentalService;
    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id) {
        try {
            return ResponseEntity.ok(rentalService.getMovie(id));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ResponseEntity.status(e.getStatusCode()).build();
            } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PostMapping("/movie/{id}")
    public ResponseEntity<Void> returnMovie(@PathVariable int id) {
        try {
            rentalService.returnMovie(id);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ResponseEntity.status(e.getStatusCode()).build();
            } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }


}
