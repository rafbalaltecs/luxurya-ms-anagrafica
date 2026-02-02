package anagrafica.service.company;

import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.zone.ZoneResponse;

import java.util.List;

public interface CompanyService {
    CompanyResponse create(final CompanyRequest request);
    CompanyResponse update(final Long id, CompanyRequest request);
    void delete(final Long id);
    List<CompanyResponse> findAll(final Integer offset, final Integer limit);

    List<AgentResponse> findAllAgentsFromCompanyId(final Long companyId);
}
