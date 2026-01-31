package anagrafica.service.placer;

import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.event.PlacerZoneEventDTO;
import anagrafica.dto.placer.PlacerRequest;
import anagrafica.dto.placer.PlacerResponse;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface PlacerService {
    PlacerResponse create(final PlacerRequest request);
    PlacerResponse update(final Long id, final PlacerRequest request);
    List<PlacerResponse> findAll(final Integer offset, final Integer limit);
    void delete(final Long id);

    List<ZoneResponse> findAllZonesFromPlacer(final  Long placerId);

    void auditAgentZone(final PlacerZoneEventDTO eventDTO);

    void addZoneToPlacer(Long placerId, Long zoneId);

    void removeZoneToPlacer(Long placerId, Long zoneId);
}
