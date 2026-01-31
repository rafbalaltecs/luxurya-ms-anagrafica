package anagrafica.service.placer.impl;

import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.event.PlacerZoneEventDTO;
import anagrafica.dto.placer.PlacerRequest;
import anagrafica.dto.placer.PlacerResponse;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.*;
import anagrafica.entity.audit.AgentZoneAudit;
import anagrafica.entity.audit.OperationAuditEnum;
import anagrafica.entity.audit.PlacerZoneAudit;
import anagrafica.exception.RestException;
import anagrafica.publisher.PlacerZonePublisher;
import anagrafica.repository.placer.AuditPlacerZoneRepository;
import anagrafica.repository.placer.PlaceRepository;
import anagrafica.repository.placer.PlacerZoneRepository;
import anagrafica.repository.user.UserRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.placer.PlacerMapper;
import anagrafica.service.placer.PlacerService;
import anagrafica.service.zone.ZoneService;
import anagrafica.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PlacerServiceImpl implements PlacerService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlacerZoneRepository placerZoneRepository;
    private final AuditPlacerZoneRepository auditPlacerZoneRepository;
    private final ZoneRepository zoneRepository;

    private final PlacerMapper placerMapper;
    private final PlacerZonePublisher publisher;

    private final JwtUtil jwtUtil;

    public PlacerServiceImpl(PlaceRepository placeRepository, UserRepository userRepository, PlacerZoneRepository placerZoneRepository, AuditPlacerZoneRepository auditPlacerZoneRepository, ZoneRepository zoneRepository, PlacerMapper placerMapper, PlacerZonePublisher publisher, JwtUtil jwtUtil) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.placerZoneRepository = placerZoneRepository;
        this.auditPlacerZoneRepository = auditPlacerZoneRepository;
        this.zoneRepository = zoneRepository;
        this.placerMapper = placerMapper;
        this.publisher = publisher;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public PlacerResponse create(PlacerRequest request) {
        final Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }
        final Optional<Placer> optionalPlacer = placeRepository.findPlacerWithUserId(request.getUserId());
        if(optionalPlacer.isPresent()){
            throw new RestException("User Not Found");
        }
        Placer placer = new Placer();
        placer.setName(request.getName());
        placer.setSurname(request.getSurname());
        placer.setIsActive(Boolean.TRUE);
        placer.setDeleted(Boolean.FALSE);
        placer.setUser(optionalUser.get());
        placer = placeRepository.save(placer);
        return placerMapper.entityToResponse(placer);
    }

    @Override
    @Transactional
    public PlacerResponse update(Long id, PlacerRequest request) {
        final Optional<Placer> optionalPlacer = placeRepository.findById(id);
        if(optionalPlacer.isEmpty()){
            throw new RestException("Placer Not Found");
        }

        final Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }
        final Optional<Placer> optionalPlacerUser = placeRepository.findPlacerWithUserId(request.getUserId());
        if(optionalPlacerUser.isPresent()){
            if(!optionalPlacerUser.get().getId().equals(optionalPlacer.get().getId())){
                throw new RestException("User Not Found");
            }
        }

        optionalPlacer.get().setName(request.getName());
        optionalPlacer.get().setSurname(request.getSurname());
        optionalPlacer.get().setIsActive(Boolean.TRUE);
        optionalPlacer.get().setDeleted(Boolean.FALSE);
        optionalPlacer.get().setUser(optionalUser.get());
        placeRepository.save(optionalPlacer.get());
        return placerMapper.entityToResponse(optionalPlacer.get());
    }

    @Override
    public List<PlacerResponse> findAll(Integer offset, Integer limit) {
        final List<PlacerResponse> responses = new ArrayList<>();
        final Pageable pageable = PageRequest.of(offset, limit);
        final Page<Placer> placers = placeRepository.findAll(pageable);
        if(!placers.isEmpty()){
            placers.stream().forEach( placer -> {
                responses.add(placerMapper.entityToResponse(placer));
            });
        }
        return responses;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Optional<Placer> optionalPlacer = placeRepository.findById(id);
        if(optionalPlacer.isEmpty()){
            throw new RestException("Placer Not Found");
        }
        optionalPlacer.get().setDeleted(Boolean.TRUE);
        placeRepository.save(optionalPlacer.get());
    }

    @Override
    public List<ZoneResponse> findAllZonesFromPlacer(Long placerId) {
        final Optional<Placer> optionalPlacer = placeRepository.findById(placerId);
        if(optionalPlacer.isEmpty()){
            throw new RestException("Placer Not Found");
        }
        final List<ZoneResponse> responses = new ArrayList<>();
        final List<PlacerZone> findZoneFromPlacer = placerZoneRepository.findAllZonesFromPlacerId(placerId);
        if(!findZoneFromPlacer.isEmpty()){
            findZoneFromPlacer.forEach(item -> {
                responses.add(
                        new ZoneResponse(item.getZone().getId(),
                                item.getZone().getName(),
                                item.getZone().getCitta().getNome())
                );
            });

        }

        return responses;
    }

    @Override
    public void auditAgentZone(PlacerZoneEventDTO eventDTO) {
        final PlacerZoneAudit audit = new PlacerZoneAudit();
        audit.setCreatedAt(eventDTO.getOperationDate());
        audit.setOperation(OperationAuditEnum.valueOf(eventDTO.getOperationAudit()));
        audit.setIdZone(Long.valueOf(eventDTO.getZoneId()));
        audit.setIdPlacer(Long.valueOf(eventDTO.getPlacerId()));
        audit.setUserOperationId(Long.valueOf(eventDTO.getOperationBy()));
        auditPlacerZoneRepository.save(audit);
    }

    @Override
    @Transactional
    public void addZoneToPlacer(Long placerId, Long zoneId) {
        final Optional<Placer> optionalPlacer = placeRepository.findById(placerId);
        if(optionalPlacer.isEmpty()){
            throw new RestException("Placer Not Found");
        }
        final Optional<Zone> optionalZone = zoneRepository.findById(zoneId);
        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<PlacerZone> optionalPlacerZone = placerZoneRepository.findPlacerZoneWithSameData(
                placerId, zoneId
        );

        if(optionalPlacerZone.isPresent()){
            log.warn("Exist This Associate With Placer ID: {} And Zone: {} ", optionalPlacer.get().getId(), optionalZone.get().getName());
            throw new RestException("Exist This Associate");
        }

        final List<PlacerZone> placerZoneList = placerZoneRepository.findAllPlacerZoneFromZone(zoneId);

        if(!placerZoneList.isEmpty()){
            for(final PlacerZone placerZoneItem: placerZoneList){
                placerZoneItem.setDeleted(Boolean.TRUE);
                placerZoneRepository.save(placerZoneItem);
            }
            publishRevokeAllZone(placerZoneList);
        }

        final PlacerZone placerZone = new PlacerZone();
        placerZone.setZone(optionalZone.get());
        placerZone.setPlacer(optionalPlacer.get());

        placerZoneRepository.save(placerZone);

        final PlacerZoneEventDTO audit = new PlacerZoneEventDTO();
        audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
        audit.setOperationDate(LocalDateTime.now());
        audit.setOperationAudit(OperationAuditEnum.ADD.name());
        audit.setZoneId(String.valueOf(placerZone.getZone().getId()));
        audit.setZoneId(String.valueOf(placerZone.getPlacer().getId()));
        publisher.publish(audit);

    }

    private void publishRevokeAllZone(final List<PlacerZone> placerZoneList){
        final LocalDateTime now = LocalDateTime.now();
        for(final PlacerZone placerZone: placerZoneList){
            final PlacerZoneEventDTO audit = new PlacerZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(now);
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(placerZone.getZone().getId()));
            audit.setPlacerId(String.valueOf(placerZone.getPlacer().getId()));
            publisher.publish(audit);
        }
    }

    @Override
    @Transactional
    public void removeZoneToPlacer(Long placerId, Long zoneId) {
        final Optional<Placer> optionalPlacer = placeRepository.findById(placerId);
        if(optionalPlacer.isEmpty()){
            throw new RestException("Placer Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(zoneId);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<PlacerZone> optionalPlacerZone = placerZoneRepository.findPlacerZoneWithSameData(
                placerId, zoneId
        );

        if(optionalPlacerZone.isPresent()){
            optionalPlacerZone.get().setDeleted(Boolean.TRUE);
            placerZoneRepository.save(optionalPlacerZone.get());

            final PlacerZoneEventDTO audit = new PlacerZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(LocalDateTime.now());
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(optionalPlacerZone.get().getZone().getId()));
            audit.setZoneId(String.valueOf(optionalPlacerZone.get().getPlacer().getId()));
            publisher.publish(audit);
        }else{
            throw new RestException("Not Exist This Associate");
        }
    }
}
