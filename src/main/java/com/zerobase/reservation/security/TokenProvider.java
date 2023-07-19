package com.zerobase.reservation.security;

import com.zerobase.reservation.exception.ExpiredTokenException;
import com.zerobase.reservation.type.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    private final UserDetailsService userDetailsService;

    @Value("{spring.jwt.secret}")
    private String secretKey;

    public String generateToken(Long userId, Role roles) {

        Claims claims = Jwts.claims().setSubject(userId + "");
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {

        UserDetails userDetails =
                this.userDetailsService.loadUserByUsername(this.getUserId(jwt));

        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return this.parseClaims(token).getSubject();
    }


    private Claims parseClaims(String token) {

        try {
            return Jwts.parser().setSigningKey(this.secretKey)
                                .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }

    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }
}
