package anagrafica.service.address;

import anagrafica.dto.address.AddressResponse;
import anagrafica.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponse toResponse(final Address address){
        if(address != null){
            final AddressResponse addressResponse = new AddressResponse();
            addressResponse.setCap(address.getCitta().getCap());
            addressResponse.setAddress(address.getAddress());
            addressResponse.setPr(address.getCitta().getProvincia().getNome());
            return addressResponse;
        }
        return null;
    }
}
