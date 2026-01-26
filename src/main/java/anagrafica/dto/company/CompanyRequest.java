package anagrafica.dto.company;

import lombok.Data;

@Data
public class CompanyRequest {
    private String name;
    private String piva;
    private String code;
    private String description;
}
