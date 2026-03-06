package anagrafica.service.address;

import anagrafica.dto.address.AddressResponse;
import anagrafica.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponse toResponse(final Address address){
        if(address != null){
            final AddressResponse addressResponse = new AddressResponse();
            addressResponse.setCap(address.getCitta() != null ? address.getCitta().getCap() : null);
            addressResponse.setAddress(address.getAddress());
            addressResponse.setPr(address.getCitta() != null ? address.getCitta().getProvincia().getNome() : null);
            return addressResponse;
        }
        return null;
    }
}
