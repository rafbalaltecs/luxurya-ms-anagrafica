package anagrafica.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginEventDTO {
    private String email;
    private String eventDate;
}
