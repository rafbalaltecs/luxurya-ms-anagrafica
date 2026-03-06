package anagrafica.service.company.impl;

import anagrafica.client.CompanyExecusClient;
import anagrafica.client.GeocodingClient;
import anagrafica.client.ProductClient;
import anagrafica.client.response.execusbi.ExecusBICompanyInfoResponse;
import anagrafica.client.response.geo.GeocodingResult;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.company.CompanyInfoResponse;
import anagrafica.dto.company.CompanyMovementInner;
import anagrafica.dto.company.CompanyRequest;
import anagrafica.dto.company.CompanyResponse;
import anagrafica.dto.company.CompanyStockResponse;
import anagrafica.dto.ext.ProductResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.*;
import anagrafica.exception.RestException;
import anagrafica.repository.address.AddressRepository;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.company.CompanyAddressRepository;
import anagrafica.repository.company.CompanyRepository;
import anagrafica.repository.company.CompanyStockRepository;
import anagrafica.repository.geography.CittaRepository;
import anagrafica.repository.voyage.VoyageCompanyOperationRepository;
import anagrafica.repository.voyage.VoyageCompanyRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.address.AddressMapper;
import anagrafica.service.agent.AgentMapper;
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
import java.util.HashMap;
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
    private final AddressMapper addressMapper;
    private final AgentMapper agentMapper;
    private final JwtUtil jwtUtil;
    private final GeocodingClient geocodingClient;
    private final CompanyStockRepository companyStockRepository;
    private final ProductClient productClient;
    private final VoyageCompanyRepository voyageCompanyRepository;
    private final VoyageCompanyOperationRepository voyageCompanyOperationRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, 
    		CompanyExecusClient client, 
    		CittaRepository cittaRepository, 
    		ZoneCompanyRepository zoneCompanyRepository, 
    		AgentZoneRepository agentZoneRepository, 
    		ZoneRepository zoneRepository, 
    		AgentRepository agentRepository, 
    		AddressRepository addressRepository, 
    		CompanyAddressRepository companyAddressRepository, 
    		AddressMapper addressMapper, 
    		AgentMapper agentMapper, 
    		JwtUtil jwtUtil, 
    		GeocodingClient geocodingClient, 
    		CompanyStockRepository companyStockRepository, 
    		ProductClient productClient,
    		VoyageCompanyRepository voyageCompanyRepository,
    		VoyageCompanyOperationRepository voyageCompanyOperationRepository) {
        this.companyRepository = companyRepository;
        this.client = client;
        this.cittaRepository = cittaRepository;
        this.zoneCompanyRepository = zoneCompanyRepository;
        this.agentZoneRepository = agentZoneRepository;
        this.zoneRepository = zoneRepository;
        this.agentRepository = agentRepository;
        this.addressRepository = addressRepository;
        this.companyAddressRepository = companyAddressRepository;
        this.addressMapper = addressMapper;
        this.agentMapper = agentMapper;
        this.jwtUtil = jwtUtil;
        this.geocodingClient = geocodingClient;
        this.companyStockRepository = companyStockRepository;
        this.productClient = productClient;
        this.voyageCompanyRepository = voyageCompanyRepository;
        this.voyageCompanyOperationRepository = voyageCompanyOperationRepository;
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
        
        try {
        	final String sAddress = address.getAddress() + " , " + address.getCitta().getNome();
        	log.info("Search address GEO {}",sAddress);
        	final GeocodingResult geoResult = geocodingClient.geocode(sAddress);
        	companyAddress.setLat(String.valueOf(geoResult.latitude()));
        	companyAddress.setLon(String.valueOf(geoResult.longitude()));
        }catch (Exception e) {
			log.error(e.getMessage());
		}

        companyAddressRepository.save(companyAddress);

        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getPiva(),
                company.getCode(),
                company.getDescription(),
                optionalZone.get().getName(),
                address.getAddress(),
                request.getTelephone(),
                company.getPiva(),
                addressMapper.toResponse(address),
                agentMapper.toResponseLight(optionalAgent.get())
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
        
        try {
        	final String sAddress = address.getAddress() + " , " + address.getCitta().getNome();
        	log.info("Search address GEO {}",sAddress);
        	final GeocodingResult geoResult = geocodingClient.geocode(sAddress);
        	companyAddress.setLat(String.valueOf(geoResult.latitude()));
        	companyAddress.setLon(String.valueOf(geoResult.longitude()));
        }catch (Exception e) {
			log.error(e.getMessage());
		}

        companyAddressRepository.save(companyAddress);


        return new CompanyResponse(
                optionalCompany.get().getId(),
                optionalCompany.get().getName(),
                optionalCompany.get().getPiva(),
                optionalCompany.get().getCode(),
                optionalCompany.get().getDescription(),
                optionalZone.get().getName(),
                address.getAddress(),
                request.getTelephone(),
                optionalCompany.get().getPiva(),
                addressMapper.toResponse(address),
                agentMapper.toResponseLight(optionalAgent.get())
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
            AgentZone agentZone = null;

            final List<CompanyAddress> companyAddressList = companyAddressRepository.findAllCompanyAddressFromCompanyId(company.getId());

            if(!companyAddressList.isEmpty()) {
                companyAddress = companyAddressList.get(0);
            }

            final List<ZoneCompany> zoneCompanyList = zoneCompanyRepository.findZoneFromCompany(company.getId());

            if(!zoneCompanyList.isEmpty()){
                zoneCompany = zoneCompanyList.get(0);

                final List<AgentZone> findAllAgentZone = agentZoneRepository.findAllZoneWithIdZoneAndAgents(zoneCompany.getZone().getId());

                if(!findAllAgentZone.isEmpty()){
                    agentZone = findAllAgentZone.get(0);
                }
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
                            company.getTelephone(),
                            company.getPiva(),
                            addressMapper.toResponse(companyAddress != null ? companyAddress.getAddress() : null),
                            agentMapper.toResponseLight(agentZone != null ? agentZone.getAgent() : null)
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
                                        null,
                                        null
                                )
                        );
                    }
                }
            }

        }
        return responses;
    }
    

	@Override
	public GeocodingResult findGeofromcompanyId(Long companyId) {
		final Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if(optionalCompany.isEmpty()){
            throw new RestException("Company Not Exist");
        }
        
        final List<CompanyAddress> listCompanyAddress = companyAddressRepository.findAllCompanyAddressFromCompanyId(companyId);
        
        if(listCompanyAddress.isEmpty()) {
        	throw new RestException("Not exist Address for this company");
        }
        
        final Address address = listCompanyAddress.get(0).getAddress();
        
        try {
        	final String sAddress = address.getAddress() + " , " + address.getCitta().getNome();
        	log.info("Search address GEO {}",sAddress);
        	return geocodingClient.geocode(sAddress);
        }catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	@Override
	@Transactional
	public void syncGeoCompany() {
		final List<Company> findAllCompany = companyRepository.findAll();
		for(final Company c: findAllCompany) {
			if(Boolean.FALSE.equals(c.getDeleted())) {
				final List<CompanyAddress> listCompanyAddress = companyAddressRepository.findAllCompanyAddressFromCompanyId(c.getId());
		        
		        if(listCompanyAddress.isEmpty()) {
		        	throw new RestException("Not exist Address for this company");
		        }
		        
		        final CompanyAddress companyAddress = listCompanyAddress.get(0);
		        
		        try {
		        	final String sAddress = companyAddress.getAddress().getAddress() + " , " + companyAddress.getAddress().getCitta().getNome();
		        	
		        	log.info("Search address GEO {}",sAddress);
		        	
		        	final GeocodingResult geoResult =  geocodingClient.geocode(sAddress);
		        	companyAddress.setLat(String.valueOf(geoResult.latitude()));
		        	companyAddress.setLon(String.valueOf(geoResult.longitude()));
		        	
		        	companyAddressRepository.save(companyAddress);
		        	
		        }catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		
	}

	@Override
	@Transactional
	public CompanyResponse createFromLegacy(CompanyRequest request) {

	        final Optional<Company> optionalCompanyWithPiva = companyRepository.findCompanyWithSamePiva(request.getPiva() != null ? request.getPiva().trim() : null);
	        final Optional<Company> optionalCompanyWithSameName = companyRepository.findCompanyWithSameName(request.getName().trim());
	       // final Optional<Company> optionalCompanyWithCode = companyRepository.findCompanyWithSameCode(request.getCode().trim());

	        if(optionalCompanyWithPiva.isPresent()){
	        	if(StringUtils.isEmpty(optionalCompanyWithPiva.get().getCodeLegacy())) {
	        		log.info("Non esiste codice legacy per questa piva {} Aggiungo codice legacy {} ", request.getPiva(), request.getCodeLegacy());
	        		optionalCompanyWithPiva.get().setCodeLegacy(request.getCodeLegacy());
	        		companyRepository.save(optionalCompanyWithPiva.get());
	        	}
	        	return null;
	        }

	        if(optionalCompanyWithSameName.isPresent()){
	        	return null;
	        }

	        if(request.getAddress() == null){
	        	return null;
	        }

	        final Optional<Zone> optionalZone = zoneRepository.findById(request.getAddress().getZoneId());

	        if(optionalZone.isEmpty()){
	            return null;
	        }


	        
	        Company company = new Company();

	        if(StringUtils.isEmpty(company.getPiva())){
	            company.setPiva(request.getPiva());
	            company.setName(request.getName());
	        }

	        company.setDescription(request.getDescription());

	        company = companyRepository.save(company);
	        company.setCode("C-" + company.getId().toString());
	        company.setDeleted(Boolean.FALSE);
	        company.setTelephone(request.getTelephone());
	        company.setCodeLegacy(request.getCodeLegacy());
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
	        
	        try {
	        	final String sAddress = address.getAddress() + " , " + address.getCitta().getNome();
	        	log.info("Search address GEO {}",sAddress);
	        	final GeocodingResult geoResult = geocodingClient.geocode(sAddress);
	        	companyAddress.setLat(String.valueOf(geoResult.latitude()));
	        	companyAddress.setLon(String.valueOf(geoResult.longitude()));
	        }catch (Exception e) {
	        	log.error("Err: address GEO");
				log.error(e.getMessage());
			}

	        companyAddressRepository.save(companyAddress);

	        return new CompanyResponse(
	                company.getId(),
	                company.getName(),
	                company.getPiva(),
	                company.getCode(),
	                company.getDescription(),
	                optionalZone.get().getName(),
	                address.getAddress(),
	                request.getTelephone(),
	                company.getPiva(),
	                addressMapper.toResponse(address),
	                null
	        );
	}

	@Override
	public Citta findCittaExistName(String name) {
		final List<Citta> optionalCitta = cittaRepository.findByNomeContainingIgnoreCase(name);
		if(!optionalCitta.isEmpty()) {
			return optionalCitta.get(0);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyInfoResponse findAllStockFromCompany(Long companyId) {
		
		final HashMap<Long, ProductResponse> mapProductsCache = new HashMap<Long, ProductResponse>();
		
		final CompanyInfoResponse response = new CompanyInfoResponse();
		response.setStock(new ArrayList<CompanyStockResponse>());
		response.setMovements(new ArrayList<CompanyMovementInner>());
		
		final List<CompanyStock> findAllEntities = companyStockRepository.findAllStockFromCompanyId(companyId);
		
		if(findAllEntities.isEmpty()) {
			return response;
		}
		
		for(final CompanyStock cs: findAllEntities) {
			
			final CompanyStockResponse companyStockResponse = new CompanyStockResponse();
			companyStockResponse.setCompanyId(cs.getCompany().getId());
			companyStockResponse.setCompanyStockId(cs.getId());
			companyStockResponse.setQuantity(cs.getQuantity());
			if(!mapProductsCache.containsKey(cs.getProductIdExt())) {
				mapProductsCache.put(cs.getProductIdExt(), productClient.getProductById(cs.getProductIdExt()));
			}
			final ProductResponse productResponse = mapProductsCache.get(cs.getProductIdExt());
			if(productResponse != null) {
				companyStockResponse.setProductCode(productResponse.getCode());
				companyStockResponse.setProductName(productResponse.getName());
			}
			
			response.getStock().add(companyStockResponse);
			
			final List<VoyageCompany> findAllVoyageCompany = voyageCompanyRepository.findAllVoyageCompanyFromCompanyId(companyId);
			
			for(final VoyageCompany voyageCompany: findAllVoyageCompany) {
				final List<VoyageCompanyOperation> operations = voyageCompanyOperationRepository.findAllOperationFromVoyageCompanyId(voyageCompany.getId());
				if(!operations.isEmpty()) {
					final CompanyMovementInner movement = new CompanyMovementInner();
					for(final VoyageCompanyOperation operation: operations) {
						movement.setCode(operation.getId().toString());
						movement.setTypePaymentName(operation.getTypePayment() != null ? operation.getTypePayment().getName() : "CONTANTI");
						movement.setNote("");
						movement.setDocumentName("");
						movement.setOperationId(operation.getId());
						movement.setOperationName(operation.getOperation() != null ? operation.getOperation().getName() : "NA");
						response.getMovements().add(movement);
					}
				}
			}
			
		}
		
		return response;
	}
}
