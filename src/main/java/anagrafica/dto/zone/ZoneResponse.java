package anagrafica.dto.zone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneResponse {
    private Long id;
    private String name;
    private String city;
}
