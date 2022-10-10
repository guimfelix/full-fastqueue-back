package gmf.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import gmf.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${security.jwt.signing-key}")
  private String jwtSecret;

  @Value("${security.jwt.signing-expiration}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Assinatura JWT invalida: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Token JWT invalido: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token nao suportado: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT vazio: {}", e.getMessage());
    }

    return false;
  }
}
