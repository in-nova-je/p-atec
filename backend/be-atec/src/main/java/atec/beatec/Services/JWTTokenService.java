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
    public JWTTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public String generateToken(Authentication authentication) {

        long now = System.currentTimeMillis();
        Instant nowEpochs= Instant.ofEpochMilli(now);
        Instant expEpochs= Instant.ofEpochMilli(now+JWT_EXPIRATION_TIME);
        long exp= now + JWT_EXPIRATION_TIME;
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(nowEpochs)
                .expiresAt(expEpochs)
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
