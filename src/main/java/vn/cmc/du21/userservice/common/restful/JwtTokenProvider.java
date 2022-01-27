package vn.cmc.du21.userservice.common.restful;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private static final String JWT_SECRET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //Thời gian có hiệu lực của chuỗi jwt
    private static final long JWT_AMOUNT_TO_ADD_TIME = 1;
    private static final ChronoUnit JWT_TIME_UNIT = ChronoUnit.DAYS;

    // Tạo ra jwt từ thông tin user
    public static String generateToken(long sessionId) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(JWT_AMOUNT_TO_ADD_TIME, JWT_TIME_UNIT));
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(String.valueOf(sessionId))
                .setIssuedAt(expiryDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin session từ jwt
    public static long getSessionIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // Lấy thông tin session từ jwt
    public static Date getExpireTimeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
}
