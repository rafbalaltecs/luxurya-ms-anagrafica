package anagrafica.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import anagrafica.configuration.req.CachedBodyHttpServletRequest;
import anagrafica.configuration.req.CachedBodyHttpServletResponse;
import anagrafica.entity.audit.ExternalSystemAudit;
import anagrafica.repository.audit.ExternalSystemAuditRepository;
import anagrafica.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExternalSystemAuditFilter extends OncePerRequestFilter {
	
	private final ExternalSystemAuditRepository externalSystemAuditRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    
    public ExternalSystemAuditFilter(
    		ExternalSystemAuditRepository externalSystemAuditRepository,
    		JwtUtil jwtUtil,
    		ObjectMapper objectMapper) {
    	this.externalSystemAuditRepository = externalSystemAuditRepository;
    	this.jwtUtil = jwtUtil;
    	this.objectMapper = objectMapper;
    }
    

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String path = request.getRequestURI();
		String method = request.getMethod(); 
		String fullPath = request.getRequestURI() + 
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
		

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		if (path.startsWith("/api/auth") || path.startsWith("/actuator") || path.startsWith("/api/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(response);

        LocalDateTime requestDate = LocalDateTime.now();
        String requestBody = new String(wrappedRequest.getCachedBody(), StandardCharsets.UTF_8);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        try {
            String token = extractToken(request);
            
            jwtUtil.validateAndGetSubject(token);
            
            if (token != null && jwtUtil.isExternalSystem() != null && jwtUtil.isExternalSystem()) {

                final Long idUser = jwtUtil.getIdProfileLogged();

                final String responseBody = wrappedResponse.getCapturedBody();

                final ExternalSystemAudit audit = new ExternalSystemAudit();
                audit.setIdUser(idUser);
                audit.setRequestDate(requestDate);
                audit.setResponseDate(LocalDateTime.now());
                audit.setRequest(requestBody);
                audit.setResponse(responseBody);
                audit.setPath(fullPath);
                audit.setMethod(method);
                externalSystemAuditRepository.save(audit);
            }
        } catch (Exception e) {
            log.error("Errore salvataggio audit sistema esterno: {}", e.getMessage());
        }

        wrappedResponse.copyBodyToResponse();
		
	}
	
	private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

}
