package anagrafica.service.zone;

import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.event.CompanyZoneEventDTO;
import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface ZoneService {
    ZoneResponse create(final ZoneRequest request);
    ZoneResponse update(final Long id, final ZoneRequest request);
    List<ZoneResponse> findAll();
    void delete(final Long id);

    List<CompanyResponse> findAllCompanyFromZoneId(final Long zoneId);
    void audit(final CompanyZoneEventDTO companyZoneEventDTO);
}
