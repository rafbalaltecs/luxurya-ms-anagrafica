package anagrafica.client;

import java.net.URI;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import anagrafica.client.response.geo.GeocodingResult;
import anagrafica.client.response.geo.NominatimResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeocodingClient {
	
    private final RestTemplate restTemplate;
    
    public GeocodingClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
        	.defaultHeader("User-Agent", "MySpringApp/1.0")
            .build();
    }
    
    public GeocodingResult geocode(String address) {
        
        URI uri = UriComponentsBuilder
        	    .fromHttpUrl("https://nominatim.openstreetmap.org/search")
        	    .queryParam("q", address)
        	    .queryParam("format", "json")
        	    .queryParam("limit", 1)
        	    .build(false)
        	    .toUri();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MySpringApp/1.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<NominatimResponse[]> response = restTemplate.exchange(
        	    uri,
        	    HttpMethod.GET,
        	    entity,
        	    NominatimResponse[].class
        	);

        	NominatimResponse[] body = response.getBody();

        	if (body == null || body.length == 0) {
        	    throw new RuntimeException("Indirizzo non trovato: " + address);
        	}

        	NominatimResponse r = body[0];
        	return new GeocodingResult(
        	    r.getDisplayName(),
        	    Double.parseDouble(r.getLat()),
        	    Double.parseDouble(r.getLon())
        	);
    }

}
