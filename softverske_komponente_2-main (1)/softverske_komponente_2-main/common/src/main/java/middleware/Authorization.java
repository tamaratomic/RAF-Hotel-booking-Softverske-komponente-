package middleware;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import dto.entities.TokenDto;
import enums.UserTypes;
import exceptions.RequestException;

public class Authorization {

    public static String checkAuthorization(String bearerToken) throws RequestException {
        try {
            if(bearerToken == null)
                throw new RequestException("Authorization headers are missing", HttpStatus.UNAUTHORIZED);
            if(!bearerToken.startsWith("Bearer"))
                throw new RequestException("Jwt malformed", HttpStatus.FORBIDDEN);
            Claims claims = JsonWebToken.decodeToken(bearerToken);
            if(checkBanned(claims.getSubject()))
                throw new RequestException("You have been banned by the administrator", HttpStatus.FORBIDDEN);
            return claims.getSubject();
        }catch (ExpiredJwtException jw){
            throw new RequestException("Your session has expired, please login again", HttpStatus.FORBIDDEN);
        }
    }

    public static void checkAdministrator(String userData) throws RequestException{
        Gson gson = new Gson();
        TokenDto userInfo = gson.fromJson(userData, TokenDto.class);
        if(!userInfo.getUserType().equals(UserTypes.ADMINISTRATOR))
            throw new RequestException("Only administrators can perform this action", HttpStatus.FORBIDDEN);
    }

    public static Long checkManager(String userData) throws RequestException{
        Gson gson = new Gson();
        TokenDto userInfo = gson.fromJson(userData, TokenDto.class);
        if(!(userInfo.getUserType().equals(UserTypes.MANAGER)))
            throw new RequestException("You don't have permission to perform this action", HttpStatus.FORBIDDEN);
        return userInfo.getId();

    }

    public static void checkSelfUpdate(String stringInfo, Long id) throws RequestException{
        Gson gson = new Gson();
        TokenDto userInfo = gson.fromJson(stringInfo, TokenDto.class);
        if(!userInfo.getId().equals(id))
            throw new RequestException("User cannot change other user info", HttpStatus.FORBIDDEN);
    }

    private static boolean checkBanned(String stringInfo){
        Gson gson = new Gson();
        TokenDto userInfo = gson.fromJson(stringInfo, TokenDto.class);
        return userInfo.isBanned();
    }


}
