package pl.pjatk.rental;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RentalService {
    private RestTemplate restTemplate;
    public RentalService(){
    }

    public Movie getMovie(int id) {
        try {
            return restTemplate.getForObject("http://localhost:8080/base/movies/{id}", Movie.class, id);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    public void returnMovie(int id) {
        try {
            restTemplate.put("http://localhost:8080/base/movies/{id}/available", null, id);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
