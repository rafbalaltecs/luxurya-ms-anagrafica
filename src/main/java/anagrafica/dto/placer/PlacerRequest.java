package anagrafica.dto.placer;

import lombok.Data;

@Data
public class PlacerRequest {
    private String name;
    private String surname;
    private Long userId;
}
