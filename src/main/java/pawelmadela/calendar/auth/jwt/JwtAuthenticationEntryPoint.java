package pawelmadela.calendar.auth.jwt;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if(authException.getCause() == null){
            System.out.println();
            response.sendError(HttpServletResponse.SC_FOUND, "Wrong credentails");
        }else
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}