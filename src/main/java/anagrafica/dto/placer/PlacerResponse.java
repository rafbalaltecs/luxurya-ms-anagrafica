package anagrafica.dto.placer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlacerResponse {
    private Long id;
    private String name;
    private String surname;
    private String createdAt;
}
