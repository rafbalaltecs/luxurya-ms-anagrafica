package anagrafica.service.company.impl;

import anagrafica.client.CompanyExecusClient;
import anagrafica.client.response.execusbi.ExecusBICompanyInfoResponse;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.entity.Citta;
import anagrafica.entity.Company;
import anagrafica.exception.RestException;
import anagrafica.repository.company.CompanyRepository;
import anagrafica.repository.geography.CittaRepository;
import anagrafica.service.company.CompanyService;
import anagrafica.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyExecusClient client;
    private final CittaRepository cittaRepository;
    private final JwtUtil jwtUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyExecusClient client, CittaRepository cittaRepository, JwtUtil jwtUtil) {
        this.companyRepository = companyRepository;
        this.client = client;
        this.cittaRepository = cittaRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public CompanyResponse create(CompanyRequest request) {

        final Optional<Company> optionalCompanyWithPiva = companyRepository.findCompanyWithSamePiva(request.getPiva().trim());
        final Optional<Company> optionalCompanyWithSameName = companyRepository.findCompanyWithSameName(request.getName().trim());
        final Optional<Company> optionalCompanyWithCode = companyRepository.findCompanyWithSameCode(request.getCode().trim());

        if(optionalCompanyWithPiva.isPresent()){
            throw new RestException("Exist Company With Same PIVA");
        }

        if(optionalCompanyWithSameName.isPresent()){
            throw new RestException("Exist Company With Same Name");
        }

        if(optionalCompanyWithCode.isPresent()){
            throw new RestException("Exist Company With Same Code");
        }

        Company company = new Company();

        try{
            final ExecusBICompanyInfoResponse response = client.searchByCf(request.getPiva());
            company.setName(response.getRagioneSociale());
            company.setPiva(response.getPartitaIva());
            final List<Citta> optionalCitta = cittaRepository.findByNomeContainingIgnoreCase(response.getComune());
            if(!optionalCitta.isEmpty()){
                company.setCitta(optionalCitta.get(0));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

        if(StringUtils.isEmpty(company.getPiva())){
            company.setPiva(request.getPiva());
            company.setName(request.getName());
        }

        company.setDescription(request.getDescription());

        company = companyRepository.save(company);

        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getPiva(),
                company.getCode(),
                company.getDescription()
        );
    }

    @Override
    @Transactional
    public CompanyResponse update(Long id, CompanyRequest request) {

        final Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isEmpty()){
            throw new RestException("Company Not Exist");
        }

        final Optional<Company> optionalCompanyWithPiva = companyRepository.findCompanyWithSamePiva(request.getPiva().trim());
        final Optional<Company> optionalCompanyWithSameName = companyRepository.findCompanyWithSameName(request.getName().trim());
        final Optional<Company> optionalCompanyWithCode = companyRepository.findCompanyWithSameCode(request.getCode().trim());

        if(optionalCompanyWithPiva.isPresent()){
            if(!optionalCompanyWithPiva.get().getId().equals(optionalCompany.get().getId())){
                throw new RestException("Exist Company With Same PIVA");
            }
        }

        if(optionalCompanyWithSameName.isPresent()){
            if(!optionalCompanyWithSameName.get().getId().equals(optionalCompany.get().getId())) {
                throw new RestException("Exist Company With Same Name");
            }
        }

        if(optionalCompanyWithCode.isPresent()){
            if(!optionalCompanyWithCode.get().getId().equals(optionalCompany.get().getId())) {
                throw new RestException("Exist Company With Same Code");
            }
        }

        try{
            final ExecusBICompanyInfoResponse response = client.searchByCf(request.getPiva());
            optionalCompany.get().setName(response.getRagioneSociale());
            optionalCompany.get().setPiva(response.getPartitaIva());
            final List<Citta> optionalCitta = cittaRepository.findByNomeContainingIgnoreCase(response.getComune());
            if(!optionalCitta.isEmpty()){
                optionalCompany.get().setCitta(optionalCitta.get(0));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

        if(StringUtils.isEmpty(optionalCompany.get().getPiva())){
            optionalCompany.get().setPiva(request.getPiva());
            optionalCompany.get().setName(request.getName());
        }

        optionalCompany.get().setDescription(request.getDescription());

        companyRepository.save(optionalCompany.get());

        return new CompanyResponse(
                optionalCompany.get().getId(),
                optionalCompany.get().getName(),
                optionalCompany.get().getPiva(),
                optionalCompany.get().getCode(),
                optionalCompany.get().getDescription()
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isEmpty()){
            throw new RestException("Company Not Exist");
        }

        optionalCompany.get().setDeleted(Boolean.TRUE);
        companyRepository.save(optionalCompany.get());
    }

    @Override
    public List<CompanyResponse> findAll(Integer offset, Integer limit) {
        return List.of();
    }
}
