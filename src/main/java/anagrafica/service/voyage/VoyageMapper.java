package anagrafica.service.voyage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.voyage.VoyageResponse;
import anagrafica.entity.Company;
import anagrafica.entity.Voyage;

@Component
public class VoyageMapper {

	public VoyageResponse toResponse(final Voyage voyage) {
		if (voyage != null) {
			final VoyageResponse voyageResponse = new VoyageResponse();
			voyageResponse.setAgentId(voyage.getAgent().getId());
			voyageResponse.setZoneId(voyage.getZone().getId());
			voyageResponse.setCode(voyage.getCode());
			voyageResponse.setStartDate(voyage.getStartDate() != null ? voyage.getStartDate().toString() : null);
			voyageResponse.setEndDate(voyage.getEndDate() != null ? voyage.getEndDate().toString() : null);
			voyageResponse.setId(voyage.getId());
			return voyageResponse;
		}
		return null;
	}

	public VoyageResponse toResponse(final Voyage voyage, final List<Company> companies) {
		if(voyage != null) {
			final VoyageResponse voyageResponse = new VoyageResponse();
			voyageResponse.setAgentId(voyage.getAgent().getId());
			voyageResponse.setZoneId(voyage.getZone().getId());
			voyageResponse.setCode(voyage.getCode());
			voyageResponse.setStartDate(voyage.getStartDate() != null ? voyage.getStartDate().toString() : null);
			voyageResponse.setEndDate(voyage.getEndDate() != null ? voyage.getEndDate().toString() : null);
			voyageResponse.setId(voyage.getId());
			if(companies != null && !companies.isEmpty()) {
				final List<CompanyResponse> companyResponses = new ArrayList<CompanyResponse>();
				for(final Company company: companies) {
					companyResponses.add(new CompanyResponse(company.getId(), 
							company.getName(), 
							company.getPiva(), 
							company.getCode(), 
							company.getDescription(), null, null, null, null, null, null));
				}
				voyageResponse.setCompany(companyResponses);
			}
			return voyageResponse;
		}
		return null;
	}

}
