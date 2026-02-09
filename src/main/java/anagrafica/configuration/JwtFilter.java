package anagrafica.configuration;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import anagrafica.exception.RestException;
import anagrafica.service.auth.RouteAuthorizationService;
import anagrafica.utils.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
    public class JwtFilter implements Filter {

	private final JwtUtil jwtUtil;
    private final RouteAuthorizationService routeAuthorizationService;

    public JwtFilter(JwtUtil jwtUtil, RouteAuthorizationService routeAuthorizationService) {
        this.jwtUtil = jwtUtil;
        this.routeAuthorizationService = routeAuthorizationService;
    }
    
    private void isGuest(){
        jwtUtil.setGuest(Boolean.TRUE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Aggiungi gli header CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        resp.setHeader("Access-Control-Max-Age", "3600");

        // Se è una richiesta OPTIONS (preflight), rispondi subito senza validare il JWT
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (path.startsWith("/api/auth") || path.startsWith("/actuator") || path.startsWith("/api/login")) {
            chain.doFilter(request, response);
            isGuest();
            return;
        }

        if (path.startsWith("/api/ext/")) {
            chain.doFilter(request, response);
            isGuest();
            return;
        }

        if (path.startsWith("/swagger-ui")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Missing or invalid Authorization header\"}");
            return;
        }

        String token = authHeader.substring(7);
        jwtUtil.validateAndGetSubject(token);
        if (jwtUtil.getUsernameLogged() == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Invalid or expired token\"}");
            return;
        }

        if(!routeAuthorizationService.isAllowed(req.getRequestURI(), jwtUtil.getRoutes(), jwtUtil.getUsernameLogged())){
            throw new RestException("User Not Allowed For This Path: " + req.getRequestURI());
        }

        req.setAttribute("authenticatedUser", jwtUtil.getUsernameLogged());

        chain.doFilter(request, response);
    }
}
