package offside.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import offside.auth.service.AuthService;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtLoginAuthenticationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final AuthService authService;
    private static final String[] whitelist = {"/auth/login/*", "/auth/signup/*"};
    
    @Autowired
    public JwtLoginAuthenticationFilter(AuthService authService){
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
    
        if(isLoginCheckPath(requestURI)){
            String jwt = getTokenFromHeader(httpServletRequest);
            System.out.println(jwt);
            if(jwt.equals("")) {
                System.out.println("equ");
                return;
            }
            System.out.println("hi?");
            final var accountData = authService.getAccountDataFromJwt(jwt);
        }
        chain.doFilter(servletRequest,response);
    }

    private String getTokenFromHeader(HttpServletRequest request) throws CustomException{
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return "";
//        throw new CustomException(CustomExceptionTypes.TOKEN_NOT_FOUND);
    }
    
    private boolean isLoginCheckPath(String requestURI) {
        System.out.println(requestURI);
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
