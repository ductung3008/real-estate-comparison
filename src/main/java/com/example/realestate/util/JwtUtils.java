package com.example.realestate.util;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 10:59 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.domain.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${auth.token.jwtSecret}")
    private              String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private              int    jwtExpirationTime;

    public String generateJwtTokenForUser(Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(loggedInUser.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
                   .signWith(key(), SignatureAlgorithm.HS256)
                   .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public Date getExpirationTimeFromToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getExpiration();
    }

    public boolean validateToken(String token) {
        return checkToken(token) == null;
    }

    public String checkToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token);
            return null;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return ErrorMessage.Auth.ERR_INVALID_TOKEN;
        } catch (ExpiredJwtException e) {
            logger.error("Expired token: {}", e.getMessage());
            return ErrorMessage.Auth.ERR_EXPIRED_SESSION;
        } catch (UnsupportedJwtException e) {
            logger.error("This token is not supported: {}", e.getMessage());
            return ErrorMessage.Auth.ERR_UNSUPPORTED_TOKEN;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            return ErrorMessage.Auth.ERR_INVALID_SIGNATURE;
        }
    }
}
