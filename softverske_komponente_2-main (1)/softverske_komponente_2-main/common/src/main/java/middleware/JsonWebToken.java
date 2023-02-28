package middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Rank;
import org.springframework.http.HttpStatus;
import dto.entities.TokenDto;
import exceptions.RequestException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JsonWebToken {

    private static final String SECRET = "948a42d0-5eb3-11ec-bf63-0242ac130002";
    private static final long EXPIRATION_TIME = 900_000;

    public static String createToken(Long id, String username, Rank rank, String email, String userType, boolean isBanned) throws RequestException{

        ObjectMapper mapper = new ObjectMapper();

        try {
            TokenDto userInfo = new TokenDto(id,username, rank, email, userType, isBanned);
            String data = mapper.writeValueAsString(userInfo);
            SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS256;
            long currentTime = new Date().getTime();
            Date exp = new Date(currentTime + EXPIRATION_TIME);

            byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
            Key signingKey = new SecretKeySpec(secretBytes, signatureAlg.getJcaName());

            JwtBuilder builder = Jwts.builder().setSubject(data).setExpiration(exp).signWith(signatureAlg, signingKey);
            return "Bearer " + builder.compact();
        }catch (JsonProcessingException e){
            throw new RequestException("Failed to create token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Claims decodeToken(String token){
        return Jwts.parser().
                setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(token.split(" ")[1]).getBody();

    }

}
