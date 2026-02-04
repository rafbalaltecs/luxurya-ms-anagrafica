package anagrafica.dto.placer;

import lombok.Data;

import java.util.List;

@Data
public class PlacerRequest {
    private String name;
    private String surname;
    private Long userId;
    private List<Long> companyIds;
}
