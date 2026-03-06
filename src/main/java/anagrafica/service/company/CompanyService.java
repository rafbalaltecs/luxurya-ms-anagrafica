package anagrafica.service.company;

import anagrafica.client.response.geo.GeocodingResult;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyInfoResponse;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.company.CompanyStockResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.Citta;
import anagrafica.entity.CompanyStock;

import java.util.List;

public interface CompanyService {
    CompanyResponse create(final CompanyRequest request);
    CompanyResponse createFromLegacy(final CompanyRequest request);
    CompanyResponse update(final Long id, CompanyRequest request);
    void delete(final Long id);
    List<CompanyResponse> findAll(final Integer offset, final Integer limit);

    List<AgentResponse> findAllAgentsFromCompanyId(final Long companyId);
    
    GeocodingResult findGeofromcompanyId(final Long companyId);
    
    void syncGeoCompany();
    
    Citta findCittaExistName(final String name);
    
    CompanyInfoResponse findAllStockFromCompany(final Long companyId);
}
