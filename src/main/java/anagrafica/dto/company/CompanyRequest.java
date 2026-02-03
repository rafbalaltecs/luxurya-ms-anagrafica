package anagrafica.dto.company;

import anagrafica.dto.address.AddressRequest;
import lombok.Data;

@Data
public class CompanyRequest {
    private String name;
    private String piva;
    private String description;
    private AddressRequest address;
    private String telephone;
    private String telephoneTwo;
    private String mobile;
    private Long agentId;
}
