package anagrafica.service.voyage.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import anagrafica.dto.voyage.VoyageRequest;
import anagrafica.dto.voyage.VoyageResponse;
import anagrafica.entity.Agent;
import anagrafica.entity.Company;
import anagrafica.entity.Voyage;
import anagrafica.entity.Zone;
import anagrafica.entity.ZoneCompany;
import anagrafica.exception.RestException;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.voyage.VoyageRepository;
import anagrafica.repository.zone.ZoneCompanyRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.voyage.VoyageMapper;
import anagrafica.service.voyage.VoyageService;
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
	private final JwtUtil jwtUtil;
	
	public VoyageServiceImpl(VoyageRepository voyageRepository, 
			VoyageMapper voyageMapper, 
			ZoneCompanyRepository zoneCompanyRepository,
			AgentRepository agentRepository,
			ZoneRepository zoneRepository,
			JwtUtil jwtUtil) {
		this.voyageRepository = voyageRepository;
		this.voyageMapper = voyageMapper;
		this.zoneCompanyRepository = zoneCompanyRepository;
		this.agentRepository = agentRepository;
		this.zoneRepository = zoneRepository;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public List<VoyageResponse> findAll(Integer offset, Integer limit) {
		final List<VoyageResponse> response = new ArrayList<VoyageResponse>();
		final Page<Voyage> voyages = voyageRepository.findAllNotDeleted(MethodUtils.getPagination(offset, limit));
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
		voyage.setCreatedBy(jwtUtil.getUsernameLogged());
		
		voyage = voyageRepository.save(voyage);
		
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

}
