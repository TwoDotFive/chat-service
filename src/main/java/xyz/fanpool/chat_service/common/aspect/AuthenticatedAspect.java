package xyz.fanpool.chat_service.common.aspect;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.fanpool.chat_service.common.UserPrincipal;
import xyz.fanpool.chat_service.common.exception.CustomException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Base64;

@Aspect
@Component
public class AuthenticatedAspect {

    private final SecretKey secretKey;

    public AuthenticatedAspect(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    @Pointcut("@annotation(xyz.fanpool.chat_service.common.aspect.Authenticated)")
    public void authenticationRequiredMethod() {}

    @Around("authenticationRequiredMethod()")
    public Object authenticate(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Authorization header is missing or invalid");
        }

        String token = bearerToken.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = claims.get("identification", Long.class);
            UserPrincipal userDetails = new UserPrincipal(userId);

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Parameter[] parameters = method.getParameters();
            Object[] args = joinPoint.getArgs();

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType().equals(UserPrincipal.class) &&
                        UserPrincipal.class.isAssignableFrom(parameters[i].getType())) {
                    args[i] = userDetails;
                    break;
                }
            }

            return joinPoint.proceed(args);
        } catch (CustomException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}