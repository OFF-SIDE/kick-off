package offside.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import offside.auth.service.AuthService;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class LoginCheckFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final AuthService authService;
    private static final String[] whitelist = {"/auth/login/*", "/auth/signup/*", "stadium/crawler"};
    
    @Autowired
    public LoginCheckFilter(AuthService authService){
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
    
        if(isLoginCheckPath(requestURI)){
            String jwt = this.authService.getTokenFromHeader(httpServletRequest);
            authService.getAccountDataFromJwt(jwt);
        }
        filterChain.doFilter(request,response);
    }
    
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
