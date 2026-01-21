package anagrafica.client;

import anagrafica.properties.ExecusProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import anagrafica.client.response.execusbi.ExecusBICompanyInfoResponse;
import jakarta.annotation.PostConstruct;

@Component
public class CompanyExecusClient {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private final ExecusProperties execusProperties;

    public CompanyExecusClient(ExecusProperties execusProperties) {
        this.execusProperties = execusProperties;
    }

    @PostConstruct
    private void init() {
    	restTemplate = new RestTemplate();
    	objectMapper = new ObjectMapper();
    }
    
    public ExecusBICompanyInfoResponse searchByCf(String cf) throws JsonMappingException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;charset=UTF-8");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        execusProperties.getExecusbiurl() + "/api/company/search?cf={cf}",
                        HttpMethod.GET,
                        entity,
                        String.class,
                        cf
                );

        if(response.getStatusCode().is2xxSuccessful()) {
        	return objectMapper.readValue(response.getBody(), ExecusBICompanyInfoResponse.class);
        }
        
        return null;
    }
    
}
