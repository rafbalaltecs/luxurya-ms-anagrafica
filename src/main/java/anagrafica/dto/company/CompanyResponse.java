package anagrafica.dto.company;

import anagrafica.dto.address.AddressResponse;
import anagrafica.dto.agent.AgentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String piva;
    private String code;
    private String description;
    private String zona;
    private String address;
    private String telephone;
    //Another fields
    private String cf;
    private AddressResponse addressResponse;
    private AgentResponse agent;
}
