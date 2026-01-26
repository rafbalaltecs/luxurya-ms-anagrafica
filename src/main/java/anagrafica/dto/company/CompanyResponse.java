package anagrafica.dto.company;

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
}
