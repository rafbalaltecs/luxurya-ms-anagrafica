package anagrafica.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import anagrafica.dto.ext.LoadVoyageProductResponseExt;
import anagrafica.dto.ext.ProductResponse;
import anagrafica.utils.JwtUtil;

@Service
public class ProductClient {
	
	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;

    @Value("${product.api.base-url:http://56.228.41.185:8086}")
    private String baseUrl;

    public ProductClient(RestTemplateBuilder builder, JwtUtil jwtUtil) {
        this.restTemplate = builder.build();
        this.jwtUtil = jwtUtil;
    }
    
    public List<ProductResponse> getProducts() {
        String url = baseUrl + "/api/product?offset=0&limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=UTF-8");
        headers.set("Authorization", "Bearer " + jwtUtil.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ProductResponse>>() {}
        );

        return response.getBody();
    }
    
    public ProductResponse getProductById(Long id) {
        String url = baseUrl + "/api/product/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=UTF-8");
        headers.set("Authorization", "Bearer " + jwtUtil.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            ProductResponse.class
        );

        return response.getBody();
    }
    
    
    public ProductResponse getProductInfo(Long id) {
        String url = baseUrl + "/api/product/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=UTF-8");
        headers.set("Authorization", "Bearer " + jwtUtil.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            ProductResponse.class
        );

        return response.getBody();
    }
    

    public LoadVoyageProductResponseExt getLoadFromVoyageId(Long id) {
        String url = baseUrl + "/api/store/voyage/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=UTF-8");
        headers.set("Authorization", "Bearer " + jwtUtil.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<LoadVoyageProductResponseExt> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            LoadVoyageProductResponseExt.class
        );

        return response.getBody();
    }

}
