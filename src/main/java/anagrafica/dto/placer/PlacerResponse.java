package anagrafica.dto.placer;

import anagrafica.dto.company.CompanyResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlacerResponse {
    private Long id;
    private String name;
    private String surname;
    private String createdAt;
    private List<CompanyResponse> companies;
}
