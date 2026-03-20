package anagrafica.service.voyage.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import anagrafica.client.ProductClient;
import anagrafica.dto.agent.AgentConfigurationVoyageResponse;
import anagrafica.dto.agent.AgentCurrentVoyageResponse;
import anagrafica.dto.ext.LoadVoyageProductItemResponse;
import anagrafica.dto.ext.LoadVoyageProductResponseExt;
import anagrafica.dto.ext.ProductResponse;
import anagrafica.dto.typedocument.TypeDocumentVoyageResponse;
import anagrafica.dto.typepayment.TypePaymentResponse;
import anagrafica.dto.voyage.VoyageClientResponse;
import anagrafica.dto.voyage.VoyageCompanyResponse;
import anagrafica.dto.voyage.VoyageConfigurationItemResponse;
import anagrafica.dto.voyage.VoyageConfigurationRequest;
import anagrafica.dto.voyage.VoyageCustomerFromZoneResponse;
import anagrafica.dto.voyage.VoyageCustomerFromZoneResponseItem;
import anagrafica.dto.voyage.VoyageCustomerStatusAgentResponse;
import anagrafica.dto.voyage.VoyageGeoRequest;
import anagrafica.dto.voyage.VoyageOperationCompletedRequest;
import anagrafica.dto.voyage.VoyageOperationRemoveRequest;
import anagrafica.dto.voyage.VoyageOperationRequest;
import anagrafica.dto.voyage.VoyageOperationResponse;
import anagrafica.dto.voyage.VoyageRequest;
import anagrafica.dto.voyage.VoyageResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.Address;
import anagrafica.entity.Agent;
import anagrafica.entity.AgentCurrentVoyage;
import anagrafica.entity.AgentZone;
import anagrafica.entity.Company;
import anagrafica.entity.CompanyAddress;
import anagrafica.entity.CompanyStock;
import anagrafica.entity.ConfigurationVoyage;
import anagrafica.entity.ConfigurationVoyageZone;
import anagrafica.entity.Document;
import anagrafica.entity.TypeDocumentVoyage;
import anagrafica.entity.TypePayment;
import anagrafica.entity.TypeVoyageOperation;
import anagrafica.entity.Voyage;
import anagrafica.entity.VoyageCompany;
import anagrafica.entity.VoyageCompanyOperation;
import anagrafica.entity.VoyageDocument;
import anagrafica.entity.Zone;
import anagrafica.entity.ZoneCompany;
import anagrafica.exception.BusinessError;
import anagrafica.exception.RestException;
import anagrafica.repository.agent.AgentCurrentVoyageRepository;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.company.CompanyAddressRepository;
import anagrafica.repository.company.CompanyRepository;
import anagrafica.repository.company.CompanyStockRepository;
import anagrafica.repository.payment.TypePaymentRepository;
import anagrafica.repository.voyage.ConfigurationVoyageRepository;
import anagrafica.repository.voyage.ConfigurationVoyageZoneRepository;
import anagrafica.repository.voyage.DocumentRepository;
import anagrafica.repository.voyage.TypeDocumentVoyageRepository;
import anagrafica.repository.voyage.TypeVoyageOperationRepository;
import anagrafica.repository.voyage.VoyageCompanyOperationRepository;
import anagrafica.repository.voyage.VoyageCompanyRepository;
import anagrafica.repository.voyage.VoyageDocumentRepository;
import anagrafica.repository.voyage.VoyageRepository;
import anagrafica.repository.voyage.VoyageUtilRepo;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.business.CompanyBusinessFacadeService;
import anagrafica.service.voyage.VoyageMapper;
import anagrafica.service.voyage.VoyageService;
import anagrafica.utils.HaversineUtils;
import anagrafica.utils.JwtUtil;
import anagrafica.utils.MethodUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoyageServiceImpl implements VoyageService{
	
	final private VoyageRepository voyageRepository;
	private final VoyageMapper voyageMapper;
	private final ZoneCompanyRepository zoneCompanyRepository;
	private final AgentRepository agentRepository;
	private final ZoneRepository zoneRepository;
	private final CompanyAddressRepository companyAddressRepository;
	private final JwtUtil jwtUtil;
	private final VoyageCompanyRepository voyageCompanyRepository;
	private final TypeVoyageOperationRepository typeVoyageOperationRepository;
	private final VoyageCompanyOperationRepository voyageCompanyOperationRepository;
	private final CompanyStockRepository companyStockRepository;
	private final VoyageUtilRepo voyageUtilRepo;
	private final ProductClient productClient;
	private final ConfigurationVoyageRepository configurationVoyageRepository;
	private final ConfigurationVoyageZoneRepository configurationVoyageZoneRepository;
	private final TypePaymentRepository typePaymentRepository;
	private final TypeDocumentVoyageRepository typeDocumentVoyageRepository;
	private final CompanyRepository companyRepository;
	private final VoyageDocumentRepository voyageDocumentRepository;
	private final DocumentRepository documentRepository;
	private final AgentZoneRepository agentZoneRepository;
	private final AgentCurrentVoyageRepository agentCurrentVoyageRepository;
	private final CompanyBusinessFacadeService companyBusinessFacadeService;
	
	public VoyageServiceImpl(VoyageRepository voyageRepository, 
			VoyageMapper voyageMapper, 
			ZoneCompanyRepository zoneCompanyRepository,
			AgentRepository agentRepository,
			ZoneRepository zoneRepository,
			JwtUtil jwtUtil, 
			CompanyAddressRepository companyAddressRepository,
			VoyageCompanyRepository voyageCompanyRepository,
			TypeVoyageOperationRepository typeVoyageOperationRepository,
			VoyageCompanyOperationRepository voyageCompanyOperationRepository,
			CompanyStockRepository companyStockRepository,
			VoyageUtilRepo voyageUtilRepo,
			ProductClient productClient,
			ConfigurationVoyageRepository configurationVoyageRepository,
			ConfigurationVoyageZoneRepository configurationVoyageZoneRepository,
			TypePaymentRepository typePaymentRepository,
			TypeDocumentVoyageRepository typeDocumentVoyageRepository,
			CompanyRepository companyRepository,
			VoyageDocumentRepository voyageDocumentRepository,
			DocumentRepository documentRepository,
			AgentZoneRepository agentZoneRepository,
			AgentCurrentVoyageRepository agentCurrentVoyageRepository,
			CompanyBusinessFacadeService companyBusinessFacadeService) {
		this.voyageRepository = voyageRepository;
		this.voyageMapper = voyageMapper;
		this.zoneCompanyRepository = zoneCompanyRepository;
		this.agentRepository = agentRepository;
		this.zoneRepository = zoneRepository;
		this.jwtUtil = jwtUtil;
		this.companyAddressRepository = companyAddressRepository;
		this.voyageCompanyRepository = voyageCompanyRepository;
		this.typeVoyageOperationRepository = typeVoyageOperationRepository;
		this.voyageCompanyOperationRepository = voyageCompanyOperationRepository;
		this.companyStockRepository = companyStockRepository;
		this.voyageUtilRepo = voyageUtilRepo;
		this.productClient = productClient;
		this.configurationVoyageRepository = configurationVoyageRepository;
		this.configurationVoyageZoneRepository = configurationVoyageZoneRepository;
		this.typePaymentRepository = typePaymentRepository;
		this.typeDocumentVoyageRepository = typeDocumentVoyageRepository;
		this.companyRepository = companyRepository;
		this.voyageDocumentRepository = voyageDocumentRepository;
		this.documentRepository = documentRepository;
		this.agentZoneRepository = agentZoneRepository;
		this.agentCurrentVoyageRepository = agentCurrentVoyageRepository;
		this.companyBusinessFacadeService = companyBusinessFacadeService;
	}

	@Override
	public List<VoyageResponse> findAll(Integer offset, Integer limit) {
		final List<VoyageResponse> response = new ArrayList<VoyageResponse>();
		Integer totalZones = 0;
		Integer totalOperations = 0;
		final Page<Voyage> voyages = voyageRepository.findAllNotDeleted(MethodUtils.getPagination(offset, limit));
		for(final Voyage voyage: voyages) {
			final Agent agent = voyage.getAgent();
			final List<Company> listCompanies = new ArrayList();
			final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(voyage.getZone().getId());
			if(!zoneCompanies.isEmpty()) {
				for(final ZoneCompany zc: zoneCompanies) {
					listCompanies.add(zc.getCompany());
					totalZones = totalZones + 1;
				}
			}
			final List<VoyageCompany> listVoyageCompany = voyageCompanyRepository.findAllVoyageCompanyFromVoyageId(voyage.getId(), MethodUtils.getPagination(0, 1000));
			for(final VoyageCompany vc: listVoyageCompany) {
				final List<VoyageCompanyOperation> operations = voyageCompanyOperationRepository.findAllOperationFromVoyageCompanyId(vc.getId());
				if(!operations.isEmpty()) {
					for(final VoyageCompanyOperation vco: operations) {
						totalOperations = totalOperations + 1;
					}
				}
			}
			final VoyageResponse voyageResponse = voyageMapper.toResponse(voyage, listCompanies);
			voyageResponse.setAgentName(agent.getName());
			voyageResponse.setAgentSurname(agent.getSurname());
			voyageResponse.setTotalOperations(totalOperations);
			voyageResponse.setZone(new ZoneResponse(voyage.getZone().getId(), voyage.getZone().getName(), null));
			voyageResponse.setAmount("0"); //TODO: Cambiare in Amount vero verificare come si fa per operations
			voyageResponse.setTotalZones(totalZones);
			response.add(voyageResponse);
		}
		return response;
	}

	@Override
	public VoyageResponse findById(Long id) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(id);
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final List<Company> listCompanies = new ArrayList();
		final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(optionalVoyage.get().getZone().getId());
		if(!zoneCompanies.isEmpty()) {
			zoneCompanies.forEach(zc -> {
				listCompanies.add(zc.getCompany());
			});
		}
		return voyageMapper.toResponse(optionalVoyage.get(), listCompanies);
	}

	@Override
	public List<VoyageResponse> findByAgentId(Long idAgent) {
		final List<VoyageResponse> response = new ArrayList<VoyageResponse>();
		final List<Voyage> voyages = voyageRepository.findPresentVoyageFromAgentId(idAgent);
		voyages.forEach(voyage -> {
			final List<Company> listCompanies = new ArrayList();
			final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(voyage.getZone().getId());
			if(!zoneCompanies.isEmpty()) {
				zoneCompanies.forEach(zc -> {
					listCompanies.add(zc.getCompany());
				});
			}
			response.add(voyageMapper.toResponse(voyage, listCompanies));
		});
		return response;
	}

	@Override
	@Transactional
	public VoyageResponse create(VoyageRequest request) {
		
		if(StringUtils.isBlank(request.getStartDate()) || StringUtils.isBlank(request.getEndDate())) {
			throw new RestException("Date Range is mandatory");
		}
		
		final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found");
		}
		
		final Optional<Zone> optionalZone = zoneRepository.findById(request.getZoneId());
		
		if(optionalZone.isEmpty()) {
			throw new RestException("Zone Not Found");
		}
		
		final LocalDate startDateParsed = LocalDate.parse(request.getStartDate());
		final LocalDate endDateParsed = LocalDate.parse(request.getEndDate());
		
		final List<Voyage> existVoyage = voyageRepository.findAllVoyageFromExistAgentAndZoneWeek(request.getAgentId(), 
				request.getZoneId(), 
				startDateParsed,
				endDateParsed);
		
		if(!existVoyage.isEmpty()) {
			throw new RestException("Exist Voyage For This Range Date");
		}
		
		Voyage voyage = new Voyage();
		voyage.setAgent(optionalAgent.get());
		voyage.setZone(optionalZone.get());
		voyage.setStartDate(startDateParsed);
		voyage.setEndDate(endDateParsed);
		voyage.setIsFinished(Boolean.FALSE);
		voyage.setCreatedBy(jwtUtil.getUsernameLogged());
		
		voyage = voyageRepository.save(voyage);
		
		final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(voyage.getZone().getId());
		
		if(!zoneCompanies.isEmpty()) {
			for(final ZoneCompany zc: zoneCompanies) {
				final VoyageCompany voyageCompany = new VoyageCompany();
				voyageCompany.setCompany(zc.getCompany());
				voyageCompany.setAgent(optionalAgent.get());
				voyageCompany.setIsCompleted(Boolean.FALSE);
				voyageCompany.setVoyage(voyage);
				voyageCompany.setDeleted(Boolean.FALSE);
				voyageCompanyRepository.save(voyageCompany);
			}
		}
		
		final String code = "V-" + String.format("%04d", voyage.getId());
		
		voyage.setCode(code);
		
		voyageRepository.save(voyage);
		
		return voyageMapper.toResponse(voyage);
	}

	@Override
	@Transactional
	public VoyageResponse update(Long id, VoyageRequest request) {
		
		if(StringUtils.isBlank(request.getStartDate()) || StringUtils.isBlank(request.getEndDate())) {
			throw new RestException("Date Range is mandatory");
		}
		
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(id);
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());
		
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not Found");
		}
		
		final Optional<Zone> optionalZone = zoneRepository.findById(request.getZoneId());
		
		if(optionalZone.isEmpty()) {
			throw new RestException("Zone Not Found");
		}
		
		final LocalDate startDateParsed = LocalDate.parse(request.getStartDate());
		final LocalDate endDateParsed = LocalDate.parse(request.getEndDate());
		
		final List<Voyage> existVoyage = voyageRepository.findAllVoyageFromExistAgentAndZoneWeek(request.getAgentId(), 
				request.getZoneId(), 
				startDateParsed,
				endDateParsed);
		
		if(!existVoyage.isEmpty()) {
			final Boolean isExistAnotherVoyage = existVoyage.stream()
		            .anyMatch(v -> v.getId().equals(id));
			if(!isExistAnotherVoyage) {
				throw new RestException("Exist Voyage For This Range Date");
			}
		}
		
		optionalVoyage.get().setAgent(optionalAgent.get());
		optionalVoyage.get().setZone(optionalZone.get());
		optionalVoyage.get().setStartDate(startDateParsed);
		optionalVoyage.get().setEndDate(endDateParsed);
		optionalVoyage.get().setCreatedBy(jwtUtil.getUsernameLogged());
		
		voyageRepository.save(optionalVoyage.get());
		
		
		return voyageMapper.toResponse(optionalVoyage.get());
	}

	@Override
	@Transactional
	public void delete(Long id) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(id);
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		optionalVoyage.get().setDeleted(Boolean.TRUE);
		voyageRepository.save(optionalVoyage.get());
	}
	
	private Boolean isVisitCarreiedOut(final Company c, final Voyage v) {
		if( c != null && v != null) {
			final List<VoyageCompany> voyageCompanies = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(c.getId(), v.getId());
			if(!voyageCompanies.isEmpty()) {
				final VoyageCompany firstElement = MethodUtils.firstElement(voyageCompanies);
				return firstElement.getIsCompleted();
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public List<VoyageClientResponse> findListClientsFromVoyageId(Long voyageId) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageId);
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		final List<VoyageClientResponse> result = new ArrayList<VoyageClientResponse>();
		final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(optionalVoyage.get().getZone().getId());
		if(!zoneCompanies.isEmpty()) {
			zoneCompanies.forEach(zc -> {
				final VoyageClientResponse voyageClientResponse = new VoyageClientResponse();
				voyageClientResponse.setClientName(zc.getCompany().getName());
				voyageClientResponse.setClientId(zc.getCompany().getId());
				voyageClientResponse.setPiva(zc.getCompany().getPiva());
				voyageClientResponse.setZoneName(optionalVoyage.get().getZone().getName());
				
				final List<CompanyAddress> findList = companyAddressRepository.findAllCompanyAddressFromCompanyId(zc.getCompany().getId());
				if(!findList.isEmpty()) {
					final CompanyAddress companyAddress = findList.get(0);
					voyageClientResponse.setLat(companyAddress.getLat());
					voyageClientResponse.setLon(companyAddress.getLon());
					voyageClientResponse.setAddress(companyAddress.getAddress() != null ? companyAddress.getAddress().getAddress() : null);
				}
				voyageClientResponse.setVisitCarriedOut(isVisitCarreiedOut(zc.getCompany(), optionalVoyage.get()));
				result.add(voyageClientResponse);
				
			});
		}
		
		return result;
	}

	@Override
	public VoyageCustomerStatusAgentResponse customerVisitFromVoyageId(Long voyageId) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageId);
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final VoyageCustomerStatusAgentResponse voyageCustomerStatusAgentResponse = new VoyageCustomerStatusAgentResponse();
		
		final List<ZoneCompany> zoneCompanies = zoneCompanyRepository.findAllCompanyFromZone(optionalVoyage.get().getZone().getId());
		
		if(!zoneCompanies.isEmpty()) {
			final List<VoyageClientResponse> toVisit = new ArrayList<VoyageClientResponse>();
			final List<VoyageClientResponse> toVisited = new ArrayList<VoyageClientResponse>();
			zoneCompanies.forEach(zc -> {
				final VoyageClientResponse voyageClientResponse = new VoyageClientResponse();
				voyageClientResponse.setClientName(zc.getCompany().getName());
				voyageClientResponse.setClientId(zc.getCompany().getId());
				voyageClientResponse.setPiva(zc.getCompany().getPiva());
				voyageClientResponse.setZoneName(optionalVoyage.get().getZone().getName());
				final List<CompanyAddress> findListAddress = companyAddressRepository.findAllCompanyAddressFromCompanyId(zc.getCompany().getId());
				if(!findListAddress.isEmpty()) {
					final CompanyAddress companyAddress = findListAddress.get(0);
					voyageClientResponse.setLat(companyAddress.getLat());
					voyageClientResponse.setLon(companyAddress.getLon());
					voyageClientResponse.setAddress(companyAddress.getAddress() != null ? companyAddress.getAddress().getAddress() : null);
				}
				
				if(isVisitCarreiedOut(zc.getCompany(), optionalVoyage.get())) {
					toVisited.add(voyageClientResponse);
				}else {
					toVisit.add(voyageClientResponse);
				}
			});
			
			voyageCustomerStatusAgentResponse.setCustomersToVisit(toVisit);
			voyageCustomerStatusAgentResponse.setCustomersToVisited(toVisited);
			voyageCustomerStatusAgentResponse.setCounterCustomersToVisit(toVisit.size());
			voyageCustomerStatusAgentResponse.setCounterCustomersVisited(toVisited.size());
			
		}
		
		return voyageCustomerStatusAgentResponse;
	}
	
	private void passedQuantityProductFromVoyageId(final Long voyageId, final Long productId, final Integer qnt) {
		final LoadVoyageProductResponseExt load = productClient.getLoadFromVoyageId(voyageId);
		if(load.getProducts().isEmpty()) {
			throw BusinessError.NOT_EXIST_PRODUCTS_FOR_VOYAGE.toException();
		}
		for(final LoadVoyageProductItemResponse item: load.getProducts()) {
			if(item.getProductId().equals(productId)) {
				if(qnt > item.getQuantity()) {
					//throw BusinessError.INVALID_QUANTITY.toException("Not exist this quantity for product " + item.getProductName() + " max: " + item.getQuantity());
				}
			}
		}
	}

	@Override
	@Transactional
	public void voyageOperation(final VoyageOperationRequest request) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(request.getVoyageId());
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final Optional<TypeVoyageOperation> optionalTypeVoyageOp = typeVoyageOperationRepository.findById(request.getTypeOperationId());
		
		if(optionalTypeVoyageOp.isEmpty()) {
			throw new RestException("TypeVoyageOperation Not Found");
		}
		
		final List<VoyageCompany> voyageCompanies = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(
				request.getCompanyId(), 
				request.getVoyageId());
		
		
		if(voyageCompanies.isEmpty()) {
			throw new RestException("Not Found this Company on this Voyage");
		}
		
		if(voyageCompanies.get(0).getIsCompleted()) {
			throw new RestException("This Operation is Completed for this company");
		}
		
		final Optional<VoyageCompanyOperation> optionalVoyageCompanyOperation = voyageUtilRepo.findLastOperation(voyageCompanies.get(0).getId());

		if(optionalVoyageCompanyOperation.isPresent()) {
			if(optionalVoyageCompanyOperation.get().getOperation().getCode().equals((optionalTypeVoyageOp.get().getCode()))) {
				if(request.getProductIdExt().equals(optionalVoyageCompanyOperation.get().getProductIdExt())) {
					//throw BusinessError.EXIST_OPERATION_FOR_VOYAGE.toException();
					//TODO: Da verificare con cliente
				}
			}
		}
		
		VoyageCompanyOperation voyageCompanyOperation = new VoyageCompanyOperation();
		voyageCompanyOperation.setCreatedAt(LocalDateTime.now());
		voyageCompanyOperation.setProductIdExt(request.getProductIdExt());
		voyageCompanyOperation.setOperation(optionalTypeVoyageOp.get());
		voyageCompanyOperation.setOperationValue(Double.valueOf(request.getQuantity()));
		voyageCompanyOperation.setVoyageCompany(voyageCompanies.get(0));
		voyageCompanyOperation.setDeleted(Boolean.FALSE);
		voyageCompanyOperation.setCreatedBy(jwtUtil.getUsernameLogged());
		
		voyageCompanyOperation = voyageCompanyOperationRepository.save(voyageCompanyOperation);
		
		
		final Optional<CompanyStock> optionalCompanyStock = companyStockRepository.findCompanyStockFromCompanyAndProduct(request.getCompanyId(), request.getProductIdExt());
		
		if(optionalCompanyStock.isPresent()) {
			optionalCompanyStock.get().setUpdatedAt(LocalDateTime.now());
			optionalCompanyStock.get().setOperation(voyageCompanyOperation);
			optionalCompanyStock.get().setProductIdExt(request.getProductIdExt());
			optionalCompanyStock.get().setCompany(voyageCompanies.get(0).getCompany());
			
			final Long qnt = optionalCompanyStock.get().getQuantity() != null ? optionalCompanyStock.get().getQuantity() : 0L;
			
			if("RICARICA".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				optionalCompanyStock.get().setQuantity(qnt + request.getQuantity());
			}else if("NON_RICARICA".equals(voyageCompanyOperation.getOperation().getCode()) && qnt > 0) {
				optionalCompanyStock.get().setQuantity(qnt - request.getQuantity());
			}else if("LASCIO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				optionalCompanyStock.get().setQuantity(qnt + request.getQuantity());
			}else if("RITIRO".equals(voyageCompanyOperation.getOperation().getCode()) && qnt > 0) {
				optionalCompanyStock.get().setQuantity(qnt - request.getQuantity() );
			}else if("VENDO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				optionalCompanyStock.get().setQuantity(qnt + request.getQuantity());
			}else if ("OMAGGIO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				optionalCompanyStock.get().setQuantity(qnt + request.getQuantity());
			}

			
			companyStockRepository.save(optionalCompanyStock.get());
			
		}else {
			final CompanyStock companyStock = new CompanyStock();
			companyStock.setCreatedAt(LocalDateTime.now());
			companyStock.setOperation(voyageCompanyOperation);
			companyStock.setProductIdExt(request.getProductIdExt());
			companyStock.setCompany(voyageCompanies.get(0).getCompany());
			
			final Long qnt = Long.valueOf(0);
			
			if("RICARICA".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				companyStock.setQuantity(qnt + request.getQuantity());
			}else if("NON_RICARICA".equals(voyageCompanyOperation.getOperation().getCode()) && qnt > 0) {
				companyStock.setQuantity(qnt - request.getQuantity());
			}else if("LASCIO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				companyStock.setQuantity(qnt + request.getQuantity());
			}else if("RITIRO".equals(voyageCompanyOperation.getOperation().getCode()) && qnt > 0) {
				companyStock.setQuantity(qnt - request.getQuantity());
			}else if("VENDO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				companyStock.setQuantity(qnt + request.getQuantity());
			}else if ("OMAGGIO".equals(voyageCompanyOperation.getOperation().getCode())) {
				passedQuantityProductFromVoyageId(request.getVoyageId(), request.getProductIdExt(), request.getQuantity());
				companyStock.setQuantity(qnt + request.getQuantity());
			}
			
			
			companyStockRepository.save(companyStock);
		}
	}
	

	@Override
	@Transactional
	public void removevoyageOperation(VoyageOperationRemoveRequest request) {
		
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(request.getVoyageId());
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final List<VoyageCompany> voyageCompanies = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(
				request.getCompanyId(), 
				request.getVoyageId());
		
		
		if(voyageCompanies.isEmpty()) {
			throw new RestException("Not Found this Company on this Voyage");
		}
		
		if(voyageCompanies.get(0).getIsCompleted()) {
			final Optional<VoyageCompanyOperation> optionalVoyageCompanyOperation = voyageUtilRepo.findLastOperation(request.getVoyageId());
			if(optionalVoyageCompanyOperation.isEmpty()) {
				throw new RestException("VoyageCompanyOperation not found");
			}
			
			optionalVoyageCompanyOperation.get().setDeleted(Boolean.TRUE);
			voyageCompanyOperationRepository.save(optionalVoyageCompanyOperation.get());
			
		}else {
			throw new RestException("Last operation was not completed so it cannot be canceled");
		}
		
	}

	@Override
	@Transactional
	public void voyageOperationCompleted(VoyageOperationCompletedRequest request) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(request.getVoyageId());
		
		if(optionalVoyage.isEmpty()) {
			throw new RestException("Voyage Not Found");
		}
		
		final List<VoyageCompany> voyageCompanies = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(
				request.getCompanyId(), 
				request.getVoyageId());
		
		
		if(voyageCompanies.isEmpty()) {
			throw new RestException("Not Found this Company on this Voyage");
		}
		
		voyageCompanies.get(0).setIsCompleted(Boolean.TRUE);
		
		voyageCompanyRepository.save(voyageCompanies.get(0));
		
	}

	@Override
	@Transactional
	public void createConfigurationVoyage(VoyageConfigurationRequest request) {
		final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not found");
		}
		
		if(request.getZones() != null && request.getZones().isEmpty()) {
			throw new RestException("Zones Not empty");
		}
		
		final List<ConfigurationVoyage> listFromAgent = configurationVoyageRepository.findAllConfigurationVoyageFromAgentId(request.getAgentId());
		
		if(!listFromAgent.isEmpty()) {
			for(final ConfigurationVoyage confVoyage: listFromAgent) {
				final List<ConfigurationVoyageZone> coVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(confVoyage.getId());
				if(!coVoyageZones.isEmpty()) {
					for(final ConfigurationVoyageZone coVoyageZone: coVoyageZones) {
						if(request.getZones().contains(coVoyageZone.getZone().getId())){
							throw new RestException("Exist This Zone In This Configuration Voyage");
						}
					}
				}
			}
		}
		
		final List<ConfigurationVoyage> list = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(request.getAgentId(), request.getWeek());
		if(!list.isEmpty()) {
			throw new RestException("Exist Configuration Voyage For This Agent And Week: " + request.getWeek());
		}
		
		ConfigurationVoyage configurationVoyage = new ConfigurationVoyage();
		configurationVoyage.setAgent(optionalAgent.get());
		configurationVoyage.setCreatedBy(jwtUtil.getUsernameLogged());
		configurationVoyage.setIsDisabled(Boolean.FALSE);
		configurationVoyage.setWeek(request.getWeek());
		
		configurationVoyage = configurationVoyageRepository.save(configurationVoyage);
		
		for(final Long zone: request.getZones()) {
			final Optional<Zone> optionalZone = zoneRepository.findById(zone);
			if(optionalZone.isPresent()) {
				final ConfigurationVoyageZone configurationVoyageZone = new ConfigurationVoyageZone();
				configurationVoyageZone.setVoyage(configurationVoyage);
				configurationVoyageZone.setZone(optionalZone.get());
				configurationVoyageZoneRepository.save(configurationVoyageZone);
			}
		}
	}

	@Override
	@Transactional
	public void updateConfigurationVoyage(Long id, VoyageConfigurationRequest request) {
		final Optional<Agent> optionalAgent = agentRepository.findById(request.getAgentId());
		if(optionalAgent.isEmpty()) {
			throw new RestException("Agent Not found");
		}
		
		final List<ConfigurationVoyage> list = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(request.getAgentId(), request.getWeek());
		if(list.isEmpty()) {
			throw new RestException("Not Exist Configuration Voyage For This Agent And Week: " + request.getWeek());
		}
		
		if(request.getZones() != null && request.getZones().isEmpty()) {
			throw new RestException("Zones Not empty");
		}
		
		final List<ConfigurationVoyage> listFromAgent = configurationVoyageRepository.findAllConfigurationVoyageFromAgentId(request.getAgentId());
		
		if(!listFromAgent.isEmpty()) {
			for(final ConfigurationVoyage confVoyage: listFromAgent) {
				if(!confVoyage.getId().equals(id)) {
					final List<ConfigurationVoyageZone> coVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(confVoyage.getId());
					if(!coVoyageZones.isEmpty()) {
						for(final ConfigurationVoyageZone coVoyageZone: coVoyageZones) {
							if(request.getZones().contains(coVoyageZone.getZone().getId())){
								throw new RestException("Exist This Zone In This Configuration Voyage to: " + confVoyage.getWeek());
							}
						}
					}
				}
			}
		}
		
		final ConfigurationVoyage configurationVoyage = MethodUtils.firstElement(list);
		configurationVoyage.setAgent(optionalAgent.get());
		configurationVoyage.setUpdatedBy(jwtUtil.getUsernameLogged());
		configurationVoyage.setIsDisabled(Boolean.FALSE);
		configurationVoyage.setWeek(request.getWeek());
		
		configurationVoyageRepository.save(configurationVoyage);
		
		for(final Long zone: request.getZones()) {
			final Optional<Zone> optionalZone = zoneRepository.findById(zone);
			if(optionalZone.isPresent()) {
				final ConfigurationVoyageZone configurationVoyageZone = new ConfigurationVoyageZone();
				configurationVoyageZone.setVoyage(configurationVoyage);
				configurationVoyageZone.setZone(optionalZone.get());
				configurationVoyageRepository.save(configurationVoyage);
			}
		}
		
	}

	@Override
	public List<VoyageOperationResponse> findAllOperationFormVoyageId(Long voyageId, Integer offset, Integer limit) {
		final List<VoyageCompany> findAllVoyagesCompanyFromVoyageId = voyageCompanyRepository.findAllVoyageCompanyFromVoyageId(
				voyageId,
				MethodUtils.getPagination(offset, limit));
		
		if(findAllVoyagesCompanyFromVoyageId.isEmpty()) {
			return new ArrayList<VoyageOperationResponse>();
		}
		
		final List<VoyageOperationResponse> response = new ArrayList<VoyageOperationResponse>();
		
		for(final VoyageCompany voyageCompany: findAllVoyagesCompanyFromVoyageId) {
			final List<VoyageCompanyOperation> operations = voyageCompanyOperationRepository.findAllOperationFromVoyageCompanyId(voyageCompany.getId());
			if(!operations.isEmpty()) {
				for(final VoyageCompanyOperation vco: operations) {
					
					final VoyageOperationResponse responseItem = new VoyageOperationResponse();
					
					final List<CompanyAddress> companyAddresses = companyAddressRepository.findAllCompanyAddressFromCompanyId(voyageCompany.getCompany().getId());
					if(companyAddresses.isEmpty()) {
						final CompanyAddress ca = MethodUtils.firstElement(companyAddresses);
						responseItem.setCompanyAddress(ca.getAddress().getAddress());
					}
					responseItem.setCompanyCode(voyageCompany.getCompany().getCode());
					responseItem.setCompanyName(voyageCompany.getCompany().getName());
					responseItem.setCompanyPiva(voyageCompany.getCompany().getPiva());
					responseItem.setCompanyTelephone(voyageCompany.getCompany().getTelephone());
					responseItem.setCompanyTelephoneTwo(voyageCompany.getCompany().getTelephone());
					
					responseItem.setIsNewCompany(Boolean.FALSE); //TODO: Attenzione qui bisogna verificare se e nuovo o meno
					final List<ZoneCompany> zones = zoneCompanyRepository.findZoneFromCompany(voyageCompany.getCompany().getId());
					if(!zones.isEmpty()) {
						final ZoneCompany zoneCompany = MethodUtils.firstElement(zones);
						responseItem.setNameZone(zoneCompany.getZone().getName());
					}
					
					
					responseItem.setOperationId(vco.getId());
					if(vco.getTypePayment() != null) {
						responseItem.setTypePaymentName(vco.getTypePayment().getName()); 
					}else {
						responseItem.setTypePaymentName("CONTANTI"); 
					}
					responseItem.setQuantity(vco.getOperationValue().intValue());
					responseItem.setVoyageId(voyageId);
					
					responseItem.setTotalWithdrawal("RITIRO".equals(vco.getOperation().getCode()));
					
					if(MethodUtils.calculatedPrice(vco.getOperation())) {
						Integer quantity = vco.getOperationValue() != null ? vco.getOperationValue().intValue() : 0;
						Double price = Double.valueOf("0");
						final ProductResponse productResponse = productClient.getProductById(vco.getProductIdExt());
						if(productResponse != null && productResponse.getQuantity() != null) {
							price = productResponse.getPrice() != null ? productResponse.getPrice() : price;
						}
						final Double pricestr = quantity * price;
						responseItem.setPrice(pricestr.toString());
					}else {
						responseItem.setPrice("0");
					}
					response.add(responseItem);
				}
			}
			
		}
		
		return response;
	}

	@Override
	public List<TypePaymentResponse> findAllTypePayment() {
		final List<TypePayment> findAllTypePaymens = typePaymentRepository.findAll();
		if(findAllTypePaymens.isEmpty()) {
			return new ArrayList<TypePaymentResponse>();
		}
		final List<TypePaymentResponse> response = new ArrayList<TypePaymentResponse>();
		for(final TypePayment typePayment: findAllTypePaymens) {
			final TypePaymentResponse item = new TypePaymentResponse();
			item.setCode(typePayment.getCode());
			item.setDescription(typePayment.getDescription());
			item.setId(typePayment.getId());
			item.setName(typePayment.getName());
			response.add(item);
		}
		return response;
	}

	@Override
	public List<TypeDocumentVoyageResponse> findAllDocuments() {
		final List<TypeDocumentVoyage> entityAll = typeDocumentVoyageRepository.findAllNotDeleted();
		if(entityAll.isEmpty()) {
			return new ArrayList<TypeDocumentVoyageResponse>();
		}
		final List<TypeDocumentVoyageResponse> response = new ArrayList<TypeDocumentVoyageResponse>();
		for(final TypeDocumentVoyage typePayment: entityAll) {
			final TypeDocumentVoyageResponse item = new TypeDocumentVoyageResponse();
			item.setCode(typePayment.getCode());
			item.setDescription(typePayment.getDescription());
			item.setId(typePayment.getId());
			item.setName(typePayment.getName());
			response.add(item);
		}
		return response;
	}

	@Override
	public VoyageCompanyResponse findVoyageCompany(Long voyageId, Long companyId) {
		final List<VoyageCompany> findAllVoyageCompany = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(companyId, voyageId);
		
		if(findAllVoyageCompany.isEmpty()) {
			throw new RestException("Not Exist VoyageCompany");
		}
		final VoyageCompanyResponse response = new VoyageCompanyResponse();
		final VoyageCompany voyageCompany = MethodUtils.firstElement(findAllVoyageCompany);
		
		response.setId(voyageCompany.getId());
		response.setCompanyFiscalCode(voyageCompany.getCompany().getPiva());
		response.setIdCompany(companyId);
		response.setCompanyName(voyageCompany.getCompany().getName());
		response.setIdVoyage(voyageId);
		
		final List<CompanyAddress> addresses = companyAddressRepository.findAllCompanyAddressFromCompanyId(companyId);
		if(!addresses.isEmpty()) {
			final CompanyAddress companyAddress = MethodUtils.firstElement(addresses);
			response.setCompanyAddress(companyAddress.getAddress().getAddress());
		}
		response.setIsBusinessClosure(voyageCompany.getIsBusinessClosure());
		response.setIsShipping(voyageCompany.getIsShipping());
		response.setIsTotalPickup(voyageCompany.getIsTotalPickup());
		response.setIsTravel(voyageCompany.getIsTravel());
		return response;
	}

	@Override
	@Transactional
	public void addToCompanyToVoyage(Long voyageId, Long companyId) {
		final List<VoyageCompany> findAllVoyageCompany = voyageCompanyRepository.findAllVoyageCompanyWithCompanyAndVoyage(companyId, voyageId);
		
		if(!findAllVoyageCompany.isEmpty()) {
			throw BusinessError.EXIST_VOYAGE_FOR_COMPANY_USAGE.toException();
		}
		
		final Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if(optionalCompany.isEmpty()) {
			throw BusinessError.NOT_EXIST_ENTITY.toExceptionEntity("Company");
		}
		
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageId);
		if(optionalVoyage.isEmpty()) {
			throw BusinessError.NOT_EXIST_ENTITY.toExceptionEntity("Voyage");
		}
		
		final VoyageCompany entity = new VoyageCompany();
		entity.setCompany(optionalCompany.get());
		entity.setAgent(optionalVoyage.get().getAgent());
		entity.setDeleted(Boolean.FALSE);
		entity.setIsCompleted(Boolean.FALSE);
		entity.setIsExternal(Boolean.TRUE);
		entity.setVoyage(optionalVoyage.get());
		entity.setCreatedBy(jwtUtil.getUsernameLogged());
		voyageCompanyRepository.save(entity);
	}
	
	public List<AgentConfigurationVoyageResponse> generateGeoZones(VoyageGeoRequest request) {
	    final List<AgentConfigurationVoyageResponse> response = new ArrayList<>();

	    final List<AgentZone> findAllAgentZone = agentZoneRepository.findAllZonesFromAgentId(request.getAgentId());
	    if (findAllAgentZone.isEmpty()) return response;

	    final AgentZone agentZoneElement = MethodUtils.firstElement(findAllAgentZone);

	    final Optional<Zone> optionalZone = zoneRepository.findById(request.getZoneId());
	    double startLat = optionalZone.get().getLat();
	    double startLon = optionalZone.get().getLon();

	    // 1. Ordina per distanza, zoneId di partenza INCLUSA
	    final List<AgentZone> sortedZones = findAllAgentZone.stream()
	            .sorted(Comparator.comparingDouble(z ->
	                HaversineUtils.distanceKm(startLat, startLon, z.getZone().getLat(), z.getZone().getLon())
	            ))
	            .collect(Collectors.toList());

	    // 2. Calcola clienti per zona
	    final List<Map.Entry<AgentZone, Integer>> zoneClientCounts = new ArrayList<>();
	    for (AgentZone agentZone : sortedZones) {
	        int clientCount = zoneCompanyRepository.findAllCompanyFromZone(agentZone.getZone().getId()).size();
	        if (clientCount > 0) {
	            zoneClientCounts.add(Map.entry(agentZone, clientCount));
	        }
	    }

	    int maxClientsPerWeek = request.getMaxClientsPerWeek();

	    // 3. Calcola automaticamente quante settimane servono per coprire tutti i clienti
	    int totalClients = zoneClientCounts.stream().mapToInt(Map.Entry::getValue).sum();
	    int weeksNeeded = (int) Math.ceil((double) totalClients / maxClientsPerWeek);
	    int numberOfWeeks = Math.max(request.getNumberOfWeeks(), weeksNeeded);

	    // 4. Distribuisci i clienti settimana per settimana (una zona può spezzarsi)
	    final Map<Integer, List<Map.Entry<AgentZone, Integer>>> weekMap = new LinkedHashMap<>();
	    for (int w = 1; w <= numberOfWeeks; w++) {
	        weekMap.put(w, new ArrayList<>());
	    }

	    int currentWeek = 1;
	    int clientsInCurrentWeek = 0;

	    for (Map.Entry<AgentZone, Integer> entry : zoneClientCounts) {
	        int remaining = entry.getValue();

	        while (remaining > 0 && currentWeek <= numberOfWeeks) {
	            int availableSlots = maxClientsPerWeek - clientsInCurrentWeek;

	            if (availableSlots <= 0) {
	                currentWeek++;
	                clientsInCurrentWeek = 0;
	                continue;
	            }

	            int toAssign = Math.min(remaining, availableSlots);
	            weekMap.get(currentWeek).add(Map.entry(entry.getKey(), toAssign));
	            clientsInCurrentWeek += toAssign;
	            remaining -= toAssign;

	            if (clientsInCurrentWeek >= maxClientsPerWeek) {
	                currentWeek++;
	                clientsInCurrentWeek = 0;
	            }
	        }
	    }

	    // 5. Costruisci la risposta
	    return weekMap.entrySet().stream()
	            .filter(e -> !e.getValue().isEmpty())
	            .map(e -> {
	                int week = e.getKey();
	                final Map<Long, List<Map.Entry<AgentZone, Integer>>> byZone = e.getValue().stream()
	                    .collect(Collectors.groupingBy(ze -> ze.getKey().getZone().getId()));

	                final List<VoyageConfigurationItemResponse> items = byZone.values().stream()
	                    .map(zoneSlots -> {
	                        final Zone z = MethodUtils.firstElement(zoneSlots).getKey().getZone();	                        int totalAssigned = zoneSlots.stream().mapToInt(Map.Entry::getValue).sum();
	                        final VoyageConfigurationItemResponse item = new VoyageConfigurationItemResponse();
	                        item.setZoneId(z.getId());
	                        item.setZoneName(z.getName());
	                        item.setTotalClients(totalAssigned);
	                        item.setProvinceName(z.getCitta() != null ? z.getCitta().getProvincia().getNome() : "N/A");
	                        return item;
	                    })
	                    .collect(Collectors.toList());

	                final AgentConfigurationVoyageResponse voyageResponse = new AgentConfigurationVoyageResponse();
	                voyageResponse.setId((long) week);
	                voyageResponse.setWeek(week);
	                voyageResponse.setAgentId(agentZoneElement.getAgent().getId());
	                voyageResponse.setAgentName(agentZoneElement.getAgent().getName());
	                voyageResponse.setAgentSurname(agentZoneElement.getAgent().getSurname());
	                voyageResponse.setItems(items);
	                return voyageResponse;
	            })
	            .collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public List<AgentConfigurationVoyageResponse> generateGeoZones(VoyageGeoRequest request, Boolean persistence) {
		final List<AgentConfigurationVoyageResponse> list = generateGeoZones(request);
		if(!list.isEmpty()) {
			for(final AgentConfigurationVoyageResponse itemResponse: list) {
				if(!itemResponse.getItems().isEmpty()) {
					final List<Long> zones = new ArrayList<>();
					final VoyageConfigurationRequest req = new VoyageConfigurationRequest();
					req.setAgentId(request.getAgentId());
					req.setWeek(itemResponse.getWeek());
					if(!itemResponse.getItems().isEmpty()) {
						for(final VoyageConfigurationItemResponse zoneItem: itemResponse.getItems()) {
							zones.add(zoneItem.getZoneId());
						}
					}
					req.setZones(zones);
					this.createConfigurationVoyage(req);
				}
				
			}
			return list;
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional
	public void changeConfigurationVoyage(final VoyageConfigurationRequest request) {
		final List<ConfigurationVoyage> configurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(request.getAgentId(), request.getWeek());
		if(!configurationVoyages.isEmpty()) {
			final ConfigurationVoyage configurationVoyage = MethodUtils.firstElement(configurationVoyages);
			final List<ConfigurationVoyageZone> configurationVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(configurationVoyage.getId());
			
			Boolean existZone = Boolean.FALSE;
			if(!configurationVoyageZones.isEmpty()) {
				
				for(final ConfigurationVoyageZone zone: configurationVoyageZones) {
					if(request.getZones().contains(zone.getZone().getId())) {
						existZone = Boolean.TRUE;
					}
				}
			}
				
				if(!existZone) {
					for(final Long idZone: request.getZones()) {
						
						final List<ConfigurationVoyageZone> configurationVoyageZonesSelected = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfZoneId(idZone);
						
						for(final ConfigurationVoyageZone oldZone: configurationVoyageZonesSelected) {
							if(oldZone.getVoyage().getAgent().getId().equals(request.getAgentId())) {
								oldZone.setDeleted(Boolean.TRUE);
								configurationVoyageZoneRepository.save(oldZone);
							}
						}
						
						final ConfigurationVoyageZone newZone = new ConfigurationVoyageZone();
						newZone.setVoyage(configurationVoyage);
						final Optional<Zone> optionalZone = zoneRepository.findById(idZone);
						if(!optionalZone.isEmpty()) {
							newZone.setDeleted(Boolean.FALSE);
							newZone.setZone(optionalZone.get());
							configurationVoyageZoneRepository.save(newZone);
						}
					}
					
					
				}
				
			
		}
		
	}

	@Override
	public VoyageCustomerFromZoneResponse customerToVisitFromCurrentVoyage() {
		final Optional<Agent> optionalAgent = agentRepository.findAgentFromUserId(jwtUtil.getIdProfileLogged());
		if(optionalAgent.isEmpty()) {
			throw BusinessError.USER_NOT_AGENT.toException();
		}
		final List<AgentCurrentVoyage> currentVoyages = agentCurrentVoyageRepository.findCurrentVoyageFromAgentId(optionalAgent.get().getId());
		if(currentVoyages.isEmpty()) {
			throw BusinessError.NOT_EXIST_VOYAGE_FOR_THIS_AGENT.toException();
		}
		
		final AgentCurrentVoyage agentCurrentVoyage  = MethodUtils.firstElement(currentVoyages);
		
		final List<ConfigurationVoyage> findConfigurationVoyages = configurationVoyageRepository.findAllConfigurationVoyageFromAgentIdAndWeek(agentCurrentVoyage.getAgent().getId(), agentCurrentVoyage.getVoyageNumber());
		if(findConfigurationVoyages.isEmpty()) {
			throw BusinessError.NOT_EXIST_VOYAGE_FOR_THIS_AGENT.toException();
		}
		
		final ConfigurationVoyage configurationVoyage = MethodUtils.firstElement(findConfigurationVoyages);
		
		final VoyageCustomerFromZoneResponse response = new VoyageCustomerFromZoneResponse();
		final List<VoyageCustomerFromZoneResponseItem> items = new ArrayList<>();
		
		final List<ConfigurationVoyageZone> configurationVoyageZones = configurationVoyageZoneRepository.findAllConfigurationVoyageZoneConfVoyageId(configurationVoyage.getId());
		
		if(!configurationVoyageZones.isEmpty()) {
			
			final List<Voyage> findAllvoyage = voyageRepository.findPresentVoyageFromAgentId(optionalAgent.get().getId());
			if(!findAllvoyage.isEmpty()) {
				final Voyage voyage = MethodUtils.firstElement(findAllvoyage);
				
				for(final ConfigurationVoyageZone configurationVoyageZone: configurationVoyageZones) {
					
					final VoyageCustomerFromZoneResponseItem item = new VoyageCustomerFromZoneResponseItem();
					item.setZoneId(configurationVoyageZone.getZone().getId());
					item.setZoneName(configurationVoyageZone.getZone().getName());
					item.setClients(new ArrayList<>());
					
					final List<ZoneCompany> zonesCompany = zoneCompanyRepository.findAllCompanyFromZone(configurationVoyageZone.getZone().getId());
					if(!zonesCompany.isEmpty()) {
						for(final ZoneCompany zc: zonesCompany) {
							final VoyageClientResponse clientItem = new VoyageClientResponse();
							if(!isVisitCarreiedOut(zc.getCompany(), voyage)) {
								final Company company = zc.getCompany();
								clientItem.setClientName(company.getName());
								clientItem.setClientId(company.getId());
								clientItem.setPiva(company.getPiva());
								
								clientItem.setAddress(companyBusinessFacadeService.getAddressDetailFromCompany(company.getId()));
								clientItem.setLat(companyBusinessFacadeService.getLatFromCompany(company.getId()));	
								clientItem.setLon(companyBusinessFacadeService.getLntFromCompany(company.getId()));	
								item.getClients().add(clientItem);						
								
							}
						}
					}
					item.setTotalClients(item.getClients().size());
					items.add(item);
				}
			}
		}
		
		response.setItems(items);
		response.setCurrentVoyageId(configurationVoyage.getId());
		response.setAgentId(configurationVoyage.getAgent().getId());
		return response;
	}

	@Override
	@Transactional
	public void addDocumentToVoyage(Long voyageId, Long documentId, Long typeDocumentVoyage) {
		final Optional<Voyage> optionalVoyage = voyageRepository.findById(voyageId);
		if(optionalVoyage.isEmpty()) {
			throw BusinessError.NOT_EXIST_ENTITY.toExceptionEntity("Voyage");
		}
		final Optional<Document> optionalDocument = documentRepository.findByIdNotDeleted(documentId);
		if(optionalDocument.isEmpty()) {
			throw BusinessError.NOT_EXIST_ENTITY.toExceptionEntity("Document");
		}
		final Optional<TypeDocumentVoyage> optionalTypeDocumentVoyage = typeDocumentVoyageRepository.findById(typeDocumentVoyage);
		if(optionalTypeDocumentVoyage.isEmpty()) {
			throw BusinessError.NOT_EXIST_ENTITY.toExceptionEntity("TypeDocumentVoyage");
		}
		
		final VoyageDocument voyageDocument = new VoyageDocument();
		voyageDocument.setTypeDocument(optionalTypeDocumentVoyage.get());
		voyageDocument.setVoyage(optionalVoyage.get());
		voyageDocument.setDocument(optionalDocument.get());
		voyageDocument.setCreatedBy(jwtUtil.getUsernameLogged());
		voyageDocumentRepository.save(voyageDocument);
	}
	

}
