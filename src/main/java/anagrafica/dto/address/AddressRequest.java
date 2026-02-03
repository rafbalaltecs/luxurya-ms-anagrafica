package anagrafica.dto.address;

import lombok.Data;

@Data
public class AddressRequest {
    private String address;
    private String cap;
    private Integer zoneId;
    private String pr;
}
