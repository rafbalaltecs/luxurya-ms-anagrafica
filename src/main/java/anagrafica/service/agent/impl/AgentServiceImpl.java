package anagrafica.service.agent.impl;

import anagrafica.dto.agent.AgentRequest;
import anagrafica.dto.agent.AgentResponse;
import anagrafica.dto.event.AgentZoneEventDTO;
import anagrafica.dto.zone.ZoneResponse;
import anagrafica.entity.Agent;
import anagrafica.entity.AgentZone;
import anagrafica.entity.User;
import anagrafica.entity.Zone;
import anagrafica.entity.audit.AgentZoneAudit;
import anagrafica.entity.audit.OperationAuditEnum;
import anagrafica.exception.RestException;
import anagrafica.publisher.AgentZonePublisher;
import anagrafica.repository.agent.AgentRepository;
import anagrafica.repository.agent.AgentZoneRepository;
import anagrafica.repository.agent.AuditAgentZoneRepository;
import anagrafica.repository.user.UserRepository;
import anagrafica.repository.zone.ZoneRepository;
import anagrafica.service.agent.AgentService;
import anagrafica.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final ZoneRepository zoneRepository;
    private final AgentZoneRepository agentZoneRepository;
    private final AuditAgentZoneRepository auditAgentZoneRepository;
    private final UserRepository userRepository;
    private final AgentZonePublisher agentZonePublisher;
    private final JwtUtil jwtUtil;

    public AgentServiceImpl(AgentRepository agentRepository, ZoneRepository zoneRepository, AgentZoneRepository agentZoneRepository, AuditAgentZoneRepository auditAgentZoneRepository, UserRepository userRepository, AgentZonePublisher agentZonePublisher, JwtUtil jwtUtil) {
        this.agentRepository = agentRepository;
        this.zoneRepository = zoneRepository;
        this.agentZoneRepository = agentZoneRepository;
        this.auditAgentZoneRepository = auditAgentZoneRepository;
        this.userRepository = userRepository;
        this.agentZonePublisher = agentZonePublisher;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public List<AgentResponse> findAll(Integer offset, Integer limit) {
        final List<AgentResponse> responses = new ArrayList<>();
        final List<Agent> list = agentRepository.findAll();
        for(final Agent agent: list){
            responses.add(
                    new AgentResponse(
                            agent.getId(),
                            agent.getName(),
                            agent.getSurname(),
                            null
                    )
            );
        }
        return responses;
    }

    @Override
    @Transactional
    public AgentResponse create(AgentRequest request) {
        final Optional<Agent> optionalAgent = agentRepository.findAgentFromUserId(request.getUserId());
        if(optionalAgent.isPresent()){
            throw new RestException("This user owns an agent");
        }

        final Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }

        Agent agent = new Agent();
        agent.setName(request.getName());
        agent.setSurname(request.getSurname());
        agent.setUser(optionalUser.get());
        agent.setCreatedBy(jwtUtil.getIdProfileLogged());

        agent = agentRepository.save(agent);

        return new AgentResponse(
                agent.getId(),
                request.getName(),
                request.getSurname(),
                request.getTelephone()
                );
    }

    @Override
    @Transactional
    public AgentResponse update(Long id, AgentRequest request) {
        final Optional<Agent> optionalAgent = agentRepository.findById(id);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Agent> optionalAgentExistUser = agentRepository.findAgentFromUserId(request.getUserId());
        if(optionalAgentExistUser.isPresent()){
            if(!optionalAgentExistUser.get().getId().equals(optionalAgent.get().getId())){
                throw new RestException("This user owns an agent");
            }
        }

        final Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if(optionalUser.isEmpty()){
            throw new RestException("User Not Found");
        }

        optionalAgent.get().setName(request.getName());
        optionalAgent.get().setSurname(request.getSurname());
        optionalAgent.get().setUpdatedBy(jwtUtil.getIdProfileLogged());
        agentRepository.save(optionalAgent.get());

        return new AgentResponse(
                optionalAgent.get().getId(),
                request.getName(),
                request.getSurname(),
                request.getTelephone()
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Optional<Agent> optionalAgent = agentRepository.findById(id);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final List<AgentZone> agentZones = agentZoneRepository.findAllZonesFromAgentId(id);
        if(agentZones.isEmpty()){
            log.warn("Not Zone For This Agent , name: {} , surname: {}", optionalAgent.get().getName(), optionalAgent.get().getSurname());
        }else{
            for(final AgentZone agentZone: agentZones){
                log.info("This Agent cancelled in this Zone, name: {} , surname: {}", agentZone.getAgent().getName(), agentZone.getAgent().getSurname());
                agentZone.setIsActive(Boolean.FALSE);
                agentZone.setDeleted(Boolean.FALSE);
                agentZoneRepository.save(agentZone);
            }

            publishRevokeAllZone(agentZones);
        }

        optionalAgent.get().setDeleted(Boolean.TRUE);
        agentRepository.save(optionalAgent.get());
    }

    @Override
    public List<ZoneResponse> findZonesFromAgent(Long agentId) {
        final Optional<Agent> optionalAgent = agentRepository.findById(agentId);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Exist");
        }
        final List<AgentZone> agentZones = agentZoneRepository.findAllZonesFromAgentId(agentId);
        if(agentZones.isEmpty()){
            log.warn("Not Zone For This Agent , name: {} , surname: {}", optionalAgent.get().getName(), optionalAgent.get().getSurname());
        }
        final List<ZoneResponse> zoneResponses = new ArrayList<>();
        for(final AgentZone agentZone: agentZones){
            final ZoneResponse zoneResponse = new ZoneResponse();
            zoneResponse.setCity(agentZone.getZone().getCitta().getNome());
            zoneResponse.setName(zoneResponse.getName());
            zoneResponses.add(zoneResponse);
        }
        return zoneResponses;
    }

    @Override
    public void auditAgentZone(AgentZoneEventDTO eventDTO) {
        final AgentZoneAudit audit = new AgentZoneAudit();
        audit.setCreatedAt(eventDTO.getOperationDate());
        audit.setOperation(OperationAuditEnum.valueOf(eventDTO.getOperationAudit()));
        audit.setIdZone(Long.valueOf(eventDTO.getZoneId()));
        audit.setIdAgent(Long.valueOf(eventDTO.getAgentId()));
        audit.setUserOperationId(Long.valueOf(eventDTO.getOperationBy()));
        auditAgentZoneRepository.save(audit);
    }

    @Override
    @Transactional
    public void addZoneToAgent(Long idAgent, Long idZone) {
        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZone = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZone.isPresent()){
            log.warn("Exist This Associate With Agent ID: {} And Zone: {} ", optionalAgent.get().getId(), optionalZone.get().getName());
            throw new RestException("Exist This Associate");
        }

        final List<AgentZone> agentZoneList = agentZoneRepository.findAllZoneWithIdZoneAndAgents(idZone);

        if(!agentZoneList.isEmpty()){
            for(final AgentZone agentZoneItem: agentZoneList){
                agentZoneItem.setDeleted(Boolean.TRUE);
                agentZoneRepository.save(agentZoneItem);
            }
            publishRevokeAllZone(agentZoneList);
        }

        final AgentZone agentZone = new AgentZone();
        agentZone.setZone(optionalZone.get());
        agentZone.setAgent(optionalAgent.get());

        agentZoneRepository.save(agentZone);

        final AgentZoneEventDTO audit = new AgentZoneEventDTO();
        audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
        audit.setOperationDate(LocalDateTime.now());
        audit.setOperationAudit(OperationAuditEnum.ADD.name());
        audit.setZoneId(String.valueOf(agentZone.getZone().getId()));
        audit.setZoneId(String.valueOf(agentZone.getAgent().getId()));
        agentZonePublisher.publish(audit);

    }

    @Override
    @Transactional
    public void removeZoneToAgent(Long id, Long idAgent, Long idZone) {
        final Optional<AgentZone> optionalAgentZone = agentZoneRepository.findById(id);

        if(optionalAgentZone.isEmpty()){
            throw new RestException("AgentZone Not Found");
        }

        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZoneExist = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZoneExist.isPresent()){
            if(optionalAgentZoneExist.get().getId().equals(id)){

                optionalAgentZoneExist.get().setDeleted(Boolean.TRUE);
                agentZoneRepository.save(optionalAgentZoneExist.get());

                final AgentZoneEventDTO audit = new AgentZoneEventDTO();
                audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
                audit.setOperationDate(LocalDateTime.now());
                audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
                audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getZone().getId()));
                audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getAgent().getId()));
                agentZonePublisher.publish(audit);
            }else{
                log.error("ATTENTION: Exist Another Associate For AgentZone , Agent: {} - Zone: {}", optionalAgent.get().getId(), optionalZone.get().getId());
                throw new RestException("Exist another associate to AgentZone");
            }
        }else{
            throw new RestException("Not Exist This Associate");
        }
    }

    @Override
    @Transactional
    public void removeZoneToAgent(Long idAgent, Long idZone) {

        final Optional<Agent> optionalAgent = agentRepository.findById(idAgent);
        if(optionalAgent.isEmpty()){
            throw new RestException("Agent Not Found");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(idZone);

        if(optionalZone.isEmpty()){
            throw new RestException("Zone Not Found");
        }

        final Optional<AgentZone> optionalAgentZoneExist = agentZoneRepository.findAgentZoneWithSameData(
                idAgent, idZone
        );

        if(optionalAgentZoneExist.isPresent()){
            optionalAgentZoneExist.get().setDeleted(Boolean.TRUE);
            agentZoneRepository.save(optionalAgentZoneExist.get());

            final AgentZoneEventDTO audit = new AgentZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(LocalDateTime.now());
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getZone().getId()));
            audit.setZoneId(String.valueOf(optionalAgentZoneExist.get().getAgent().getId()));
            agentZonePublisher.publish(audit);
        }else{
            throw new RestException("Not Exist This Associate");
        }
    }


    private void publishRevokeAllZone(final List<AgentZone> agentZones){
        final LocalDateTime now = LocalDateTime.now();
        for(final AgentZone agentZone: agentZones){
            final AgentZoneEventDTO audit = new AgentZoneEventDTO();
            audit.setOperationBy(String.valueOf(jwtUtil.getIdProfileLogged()));
            audit.setOperationDate(now);
            audit.setOperationAudit(OperationAuditEnum.REVOKE.name());
            audit.setZoneId(String.valueOf(agentZone.getZone().getId()));
            audit.setAgentId(String.valueOf(agentZone.getAgent().getId()));
            agentZonePublisher.publish(audit);
        }
    }


}
