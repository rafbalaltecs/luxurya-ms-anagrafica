package anagrafica.service.zone;

import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface ZoneService {
    ZoneResponse create(final ZoneRequest request);
    ZoneResponse findByName(final String name);
    ZoneResponse update(final Long id, final ZoneRequest request);
    List<ZoneResponse> findAll();
    List<ZoneResponse> findAll(final Integer offset, final Integer limit);
    List<ZoneResponse> search(final Integer offset, final Integer limit, final String name);
    void delete(final Long id);

    List<CompanyResponse> findAllCompanyFromZoneId(final Long zoneId);
    List<AgentResponse> findAllAgentsFromZoneId(final Long zoneId);
    void addCompanyToZone(final Long placerId, final Long zoneId, final Long companyId);
    void audit(final CompanyZoneEventDTO companyZoneEventDTO);
    
    ZoneResponse createForImport(final ZoneRequest request);
    
    void populateCoordinate();
}
