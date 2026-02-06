package anagrafica.service.company.impl;

import anagrafica.client.CompanyExecusClient;
import anagrafica.client.response.execusbi.ExecusBICompanyInfoResponse;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.*;
import anagrafica.exception.RestException;
import anagrafica.repository.address.AddressRepository;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.company.CompanyAddressRepository;
import anagrafica.repository.company.CompanyRepository;
import anagrafica.repository.geography.CittaRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.company.CompanyService;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyExecusClient client;
    private final CittaRepository cittaRepository;
    private final ZoneCompanyRepository zoneCompanyRepository;
    private final AgentZoneRepository agentZoneRepository;
    private final ZoneRepository zoneRepository;
    private final AgentRepository agentRepository;
    private final AddressRepository addressRepository;
    private final CompanyAddressRepository companyAddressRepository;
    private final JwtUtil jwtUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyExecusClient client, CittaRepository cittaRepository, ZoneCompanyRepository zoneCompanyRepository, AgentZoneRepository agentZoneRepository, ZoneRepository zoneRepository, AgentRepository agentRepository, AddressRepository addressRepository, CompanyAddressRepository companyAddressRepository, JwtUtil jwtUtil) {
        this.companyRepository = companyRepository;
        this.client = client;
        this.cittaRepository = cittaRepository;
        this.zoneCompanyRepository = zoneCompanyRepository;
        this.agentZoneRepository = agentZoneRepository;
        this.zoneRepository = zoneRepository;
        this.agentRepository = agentRepository;
        this.addressRepository = addressRepository;
        this.companyAddressRepository = companyAddressRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public CompanyResponse create(CompanyRequest request) {

        if(!MethodUtils.isPartitaIvaValida(request.getPiva())){
            throw new RestException("Piva is Not Valid");
        }

        final Optional<Company> optionalCompanyWithPiva = companyRepository.findCompanyWithSamePiva(request.getPiva().trim());
        final Optional<Company> optionalCompanyWithSameName = companyRepository.findCompanyWithSameName(request.getName().trim());
       // final Optional<Company> optionalCompanyWithCode = companyRepository.findCompanyWithSameCode(request.getCode().trim());

        if(optionalCompanyWithPiva.isPresent()){
            throw new RestException("Exist Company With Same PIVA");
        }

        if(optionalCompanyWithSameName.isPresent()){
            throw new RestException("Exist Company With Same Name");
        }

        if(request.getAddress() == null){
            throw new RestException("Address is Mandatory");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(request.getAddress().getZoneId());

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());

        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }


        AgentZone agentZone = agentZoneRepository
                .findAllZonesFromAgentId(optionalAgent.get().getId())
                .stream()
                .filter(az -> az.getZone().getId().equals(optionalZone.get().getId()))
                .findFirst()
                .orElseThrow(() -> new RestException("Zona non trovata per l'agente selezionato"));


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
        company.setCode("C-" + company.getId().toString());
        company.setDeleted(Boolean.FALSE);
        company.setTelephone(request.getTelephone());
        companyRepository.save(company);


        final ZoneCompany zoneCompany = new ZoneCompany();
        zoneCompany.setCompany(company);
        zoneCompany.setZone(optionalZone.get());
        zoneCompany.setDeleted(Boolean.FALSE);
        zoneCompanyRepository.save(zoneCompany);

        Address address = new Address();
        address.setAddress(request.getAddress().getAddress());
        address.setCitta(optionalZone.get().getCitta());
        address.setDeleted(Boolean.FALSE);

        address = addressRepository.save(address);

        final CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setAddress(address);
        companyAddress.setCompany(company);
        companyAddress.setDeleted(Boolean.FALSE);

        companyAddressRepository.save(companyAddress);

        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getPiva(),
                company.getCode(),
                company.getDescription(),
                optionalZone.get().getName(),
                address.getAddress(),
                request.getTelephone()
        );
    }

    @Override
    @Transactional
    public CompanyResponse update(Long id, CompanyRequest request) {

        if(!MethodUtils.isPartitaIvaValida(request.getPiva())){
            throw new RestException("Piva is Not Valid");
        }

        final Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isEmpty()){
            throw new RestException("Company Not Exist");
        }

        final Optional<Company> optionalCompanyWithPiva = companyRepository.findCompanyWithSamePiva(request.getPiva().trim());
        final Optional<Company> optionalCompanyWithSameName = companyRepository.findCompanyWithSameName(request.getName().trim());
       // final Optional<Company> optionalCompanyWithCode = companyRepository.findCompanyWithSameCode(request.getCode().trim());

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

        if(request.getAddress() == null){
            throw new RestException("Address is Mandatory");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(request.getAddress().getZoneId());

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());

        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }


        AgentZone agentZone = agentZoneRepository
                .findAllZonesFromAgentId(optionalAgent.get().getId())
                .stream()
                .filter(az -> az.getZone().getId().equals(optionalZone.get().getId()))
                .findFirst()
                .orElseThrow(() -> new RestException("Zona non trovata per l'agente selezionato"));

        /*if(optionalCompanyWithCode.isPresent()){
            if(!optionalCompanyWithCode.get().getId().equals(optionalCompany.get().getId())) {
                throw new RestException("Exist Company With Same Code");
            }
        }*/

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
        optionalCompany.get().setTelephone(request.getTelephone());

        companyRepository.save(optionalCompany.get());

        final List<ZoneCompany> findAllZoneFromCompany = zoneCompanyRepository.findZoneFromCompany(id);

        if(!findAllZoneFromCompany.isEmpty()){
            for(final ZoneCompany zoneCompany: findAllZoneFromCompany){
                zoneCompany.setZone(optionalZone.get());
                zoneCompanyRepository.save(zoneCompany);
            }
        }

        Address address = new Address();
        address.setAddress(request.getAddress().getAddress());
        address.setCitta(optionalZone.get().getCitta());
        address.setDeleted(Boolean.FALSE);
        address = addressRepository.save(address);

        final List<CompanyAddress> companyAddressList = companyAddressRepository.findAllCompanyAddressFromCompanyId(id);

        for(final CompanyAddress companyAddress: companyAddressList){
            companyAddress.setDeleted(Boolean.TRUE);
            companyAddressRepository.save(companyAddress);
            companyAddress.getAddress().setDeleted(Boolean.TRUE);
            addressRepository.save(companyAddress.getAddress());
        }

        final CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setAddress(address);
        companyAddress.setCompany(optionalCompany.get());
        companyAddress.setDeleted(Boolean.FALSE);

        companyAddressRepository.save(companyAddress);


        return new CompanyResponse(
                optionalCompany.get().getId(),
                optionalCompany.get().getName(),
                optionalCompany.get().getPiva(),
                optionalCompany.get().getCode(),
                optionalCompany.get().getDescription(),
                optionalZone.get().getName(),
                address.getAddress(),
                request.getTelephone()
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
        final List<CompanyResponse> responses = new ArrayList<>();
        final Pageable pageable = PageRequest.of(offset, limit);
        final Page<Company> companyList = companyRepository.findAllNotDeleted(pageable);

        for(final Company company: companyList){

            CompanyAddress companyAddress = null;
            ZoneCompany zoneCompany = null;

            final List<CompanyAddress> companyAddressList = companyAddressRepository.findAllCompanyAddressFromCompanyId(company.getId());

            if(!companyAddressList.isEmpty()) {
                companyAddress = companyAddressList.get(0);
            }

            final List<ZoneCompany> zoneCompanyList = zoneCompanyRepository.findZoneFromCompany(company.getId());

            if(!zoneCompanyList.isEmpty()){
                zoneCompany = zoneCompanyList.get(0);
            }

            responses.add(
                    new CompanyResponse(
                            company.getId(),
                            company.getName(),
                            company.getPiva(),
                            company.getCode(),
                            company.getDescription(),
                            zoneCompany != null ? zoneCompany.getZone().getName() : null,
                            companyAddress != null ? companyAddress.getAddress().getAddress() : null,
                            company.getTelephone()
                    )
            );

        }
        return responses;
    }

    @Override
    public List<AgentResponse> findAllAgentsFromCompanyId(Long companyId) {
        final List<AgentResponse> responses = new ArrayList<>();
        final Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if(optionalCompany.isEmpty()){
            throw new RestException("Company Not Found");
        }

        final List<ZoneCompany> optionalZoneCompany = zoneCompanyRepository.findZoneFromCompany(companyId);

        if(!optionalZoneCompany.isEmpty()){
            for(final ZoneCompany zoneCompany: optionalZoneCompany){
                final List<AgentZone> agentZoneList = agentZoneRepository.findAllZoneWithIdZoneAndAgents(zoneCompany.getZone().getId());
                if(!agentZoneList.isEmpty()){
                    for(final AgentZone agentZone: agentZoneList){

                        final ZoneResponse zoneResponse = new ZoneResponse();
                        zoneResponse.setId(agentZone.getZone().getId());
                        zoneResponse.setCity(agentZone.getZone().getCitta().getNome());
                        zoneResponse.setName(agentZone.getZone().getName());

                        responses.add(
                                new AgentResponse(
                                        agentZone.getAgent().getId(),
                                        agentZone.getAgent().getName(),
                                        agentZone.getAgent().getSurname(),
                                        null,
                                        zoneResponse,
                                        null
                                )
                        );
                    }
                }
            }

        }
        return responses;
    }
}
