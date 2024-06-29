package pl.pjatk.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.ConnectException;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().is4xxClientError()) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            } else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            } else {
                throw new HttpClientErrorException(httpResponse.getStatusCode());
            }
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            if (httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            } else {
                throw new HttpClientErrorException(httpResponse.getStatusCode());
            }
        } else {
            throw new HttpClientErrorException(httpResponse.getStatusCode());
        }
    }

    public void handleError(IOException ex) throws IOException {
        if (ex instanceof ConnectException) {
            throw new HttpClientErrorException(HttpStatus.GATEWAY_TIMEOUT);
        }
    }
}
