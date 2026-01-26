package anagrafica.service.zone;

import anagrafica.dto.zone.ZoneRequest;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface ZoneService {
    ZoneResponse create(final ZoneRequest request);
    ZoneResponse update(final Long id, final ZoneRequest request);
    List<ZoneResponse> findAll();
    void delete(final Long id);
}
