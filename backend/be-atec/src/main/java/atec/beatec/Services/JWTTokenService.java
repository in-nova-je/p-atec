package atec.beatec.Services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static atec.beatec.config.SecurityConstants.JWT_EXPIRATION_TIME;

@Service
public class JWTTokenService {
    private final JwtEncoder jwtEncoder;

    /**
     *
     * @param jwtEncoder
     * contructor for jwttoken service
     */
    public JWTTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     *
     * @param authentication
     * generates a new jwt token
     * @return jwt token
     */
    public String generateToken(Authentication authentication) {

        Instant now = Instant.now();
    
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusMillis(JWT_EXPIRATION_TIME))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
