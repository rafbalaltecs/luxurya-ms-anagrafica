package anagrafica.service.business;

import java.util.List;

import org.springframework.stereotype.Service;

import anagrafica.entity.CompanyAddress;
import anagrafica.entity.CompanyStock;
import anagrafica.repository.company.CompanyAddressRepository;
import anagrafica.repository.company.CompanyRepository;
import anagrafica.repository.company.CompanyStockRepository;
import anagrafica.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyBusinessFacadeService {
	private final CompanyRepository companyRepository;
	private final CompanyAddressRepository companyAddressRepository;
	private final CompanyStockRepository companyStockRepository;
	
	public CompanyBusinessFacadeService(CompanyRepository companyRepository,
			CompanyAddressRepository companyAddressRepository,
			CompanyStockRepository companyStockRepository) {
		this.companyRepository = companyRepository;
		this.companyAddressRepository = companyAddressRepository;
		this.companyStockRepository = companyStockRepository;
	}
	
	
	public CompanyAddress getAddressFromCompany(final Long companyId) {
		final List<CompanyAddress> entityList = companyAddressRepository.findAllCompanyAddressFromCompanyId(companyId);
		if(entityList.isEmpty()) {
			return null;
		}
		return MethodUtils.firstElement(entityList);
	}
	
	public String getLatFromCompany(final Long companyId) {
		final CompanyAddress companyAddress = getAddressFromCompany(companyId);
		if(companyAddress == null) {
			return null;
		}
		return companyAddress.getLat();
	}
	
	public String getLntFromCompany(final Long companyId) {
		final CompanyAddress companyAddress = getAddressFromCompany(companyId);
		if(companyAddress == null) {
			return null;
		}
		return companyAddress.getLon();
	}
	
	public String getAddressDetailFromCompany(final Long companyId) {
		final List<CompanyAddress> entityList = companyAddressRepository.findAllCompanyAddressFromCompanyId(companyId);
		if(entityList.isEmpty()) {
			return null;
		}
		return MethodUtils.firstElement(entityList).getAddress().getAddress();
	}
	
	public CompanyStock getStockFromCompany(final Long companyId) {
		final List<CompanyStock> entityList = companyStockRepository.findAllStockFromCompanyId(companyId);
		if(entityList.isEmpty()) {
			return null;
		}
		return MethodUtils.firstElement(entityList);
	}
	
}
