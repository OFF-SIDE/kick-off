package offside.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import offside.auth.dto.JwtAccountPayloadDto;
import offside.auth.dto.JwtSignupPayloadDto;
import offside.auth.dto.JwtTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
    private final Key key;
    private final int ACCESS_TOKEN_EXPIRE_TIME;
    
    public JwtGenerator(@Value("${JWT_SECRET}") String secretKey, @Value("${ACCESS_TOKEN_EXPIRE_TIME}") String expiredTime) {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.ACCESS_TOKEN_EXPIRE_TIME = Integer.parseInt(expiredTime);
    }
    
    public JwtTokenDto generateLoginToken(JwtAccountPayloadDto jwtAccountPayloadDto) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        
        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(jwtAccountPayloadDto.getId()))
            .setSubject(String.valueOf(jwtAccountPayloadDto.getNickname()))
            .setSubject(String.valueOf(jwtAccountPayloadDto.getLocation()))
            .setSubject(String.valueOf(jwtAccountPayloadDto.getCategory()))
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
        
        return new JwtTokenDto(accessToken);
    }
    
    public JwtTokenDto generateSignupToken(JwtSignupPayloadDto jwtAccountPayloadDto) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        
        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(jwtAccountPayloadDto.getId()))
            .setSubject(String.valueOf(jwtAccountPayloadDto.getNickname()))
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
        
        return new JwtTokenDto(accessToken);
    }
}
