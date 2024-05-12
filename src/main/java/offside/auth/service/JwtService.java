package offside.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import offside.auth.dto.JwtAccountPayloadDto;
import offside.auth.dto.JwtTokenDto;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final Key key;
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    
    public JwtService(@Value("${ACCESS_TOKEN_EXPIRE_TIME}") String expiredTime) {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.ACCESS_TOKEN_EXPIRE_TIME = Long.parseLong(expiredTime);
    }
    
    public JwtTokenDto createLoginToken(JwtAccountPayloadDto jwtAccountPayloadDto){
        Claims claims = Jwts.claims();
        claims.put("id", jwtAccountPayloadDto.getId());
        claims.put("name", jwtAccountPayloadDto.getName());
        claims.put("nickname", jwtAccountPayloadDto.getNickname());
        claims.put("location", jwtAccountPayloadDto.getLocation());
        claims.put("category", jwtAccountPayloadDto.getCategory());
        
        return new JwtTokenDto(createToken(claims));
    }
    
    public String createToken(Claims claims) {
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
        
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
    
    /**
     * JWT 에서 Claims 추출. 오류 시 에러
     * @param token
     * @return IsValidate
     * @throw TOKEN_UNAUTHORIZED_ERROR, TOKEN_TIME_OUT
     */
    public Claims validateToken(String token) throws CustomException {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            throw new CustomException(CustomExceptionTypes.TOKEN_TIME_OUT);
//            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
        }
        throw new CustomException(CustomExceptionTypes.TOKEN_UNAUTHORIZED_ERROR);
    }
    
    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
