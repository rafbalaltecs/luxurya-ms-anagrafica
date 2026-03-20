package anagrafica.utils;

import anagrafica.exception.RestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
	private final Key key;
    private final long expirationMs;

    private String usernameLogged;
    private String languageLogged;
    private Long idProfileLogged;
    private Set<String> routes;
    private String token;

    private Boolean isGuest;
    private Boolean isAdmin;
    private Boolean isExternalSystem;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    private void setUsernameLogged(String usernameLogged) {
        this.usernameLogged = usernameLogged;
    }

    private void setIsExternalSystem(final Boolean value) {
    	this.isExternalSystem = value;
    }
    
    public String getUsernameLogged() {
        return usernameLogged;
    }
    
    public Boolean isExternalSystem() {
        return isExternalSystem;
    }
    
    public String getToken() {
    	return token;
    }
    
    public void setToken(String token) {
    	this.token = token;
    }

    public String getLanguageLogged() {
        return languageLogged;
    }

    public Long getIdProfileLogged(){
        return idProfileLogged;
    }

    private void setLanguageLogged(String languageLogged) {
        this.languageLogged = languageLogged;
    }

    private void setIdProfileLogged(Long idProfileLogged) {
        this.idProfileLogged = idProfileLogged;
    }

    public Boolean getGuest() {
        return isGuest;
    }

    public Set<String> getRoutes(){
        return routes;
    }

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    private void setIsAdmin(Boolean val){
        isAdmin = val;
    }

    private void setCompactRoutes(final String compactRoutes){
        if (compactRoutes != null && !compactRoutes.isBlank()) {
            routes = Arrays.stream(compactRoutes.split("_"))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toSet());
        }
    }

    public boolean getIsAdmin(){
        return isAdmin;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
      
    }

    public String generateToken(String username, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public void validateAndGetSubject(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            setUsernameLogged((String) claims.getBody().get("username"));
            setIdProfileLogged(Long.valueOf( (Integer) claims.getBody().get("iduser") ));
            setIsAdmin((Boolean) claims.getBody().get("isAdmin"));
            if(claims.getBody().get("isExternalSystem") != null) {
                setIsExternalSystem((Boolean) claims.getBody().get("isExternalSystem"));
            }
            setCompactRoutes((String) claims.getBody().get("routes"));
            setToken(token);
        } catch (JwtException ex) {
            throw new RestException("Errore Validazione Token: " + ex.getMessage());
        }
    }
}
