package anagrafica.utils;

import anagrafica.exception.RestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
	private final Key key;
    private final long expirationMs;

    private String usernameLogged;
    private String languageLogged;
    private Long idProfileLogged;
    private Boolean isGuest;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    private void setUsernameLogged(String usernameLogged) {
        this.usernameLogged = usernameLogged;
    }

    public String getUsernameLogged() {
        return usernameLogged;
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

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateAndGetSubject(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            setUsernameLogged((String) claims.getBody().get("username"));
        } catch (JwtException ex) {
            throw new RestException("Errore Validazione Token: " + ex.getMessage());
        }
    }
}
