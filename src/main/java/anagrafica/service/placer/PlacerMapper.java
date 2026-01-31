package anagrafica.service.placer;

import anagrafica.dto.placer.PlacerRequest;
import anagrafica.dto.placer.PlacerResponse;
import anagrafica.entity.Placer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlacerMapper {

    public PlacerResponse entityToResponse(final Placer placer){
        if(placer != null){
            final PlacerResponse placerResponse = new PlacerResponse();
            placerResponse.setName(placer.getName());
            placerResponse.setSurname(placer.getSurname());
            placerResponse.setId(placer.getId());
            placerResponse.setCreatedAt(placer.getCreatedAt().toString());
            return placerResponse;
        }

        return null;
    }

}
