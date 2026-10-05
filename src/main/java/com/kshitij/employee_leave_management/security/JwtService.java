package com.kshitij.employee_leave_management.security;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
@Service 
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;
    private SecretKey getSigningKey() {
         byte[] keyBytes = Decoders.BASE64.decode(secretKey);

    return Keys.hmacShaKeyFor(keyBytes);

    }
    public String generateToken(UserDetails userDetails){
        
            return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
            .signWith(getSigningKey())
            .compact();

        
    } 
    private Claims extractAllClaims(String token){
        return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }  
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    private boolean isTokenExpired(String token){
        return extractAllClaims(token)
        .getExpiration()
        .before(new Date());
    }
    public boolean isTokenValid(String token , UserDetails userDetails){
        String username =  extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}
