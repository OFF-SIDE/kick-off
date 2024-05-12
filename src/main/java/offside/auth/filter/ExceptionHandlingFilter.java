package offside.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import offside.response.exception.CustomException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(0)
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    
    private final ObjectMapper objectMapper;
    
    public ExceptionHandlingFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            response.setStatus(e.getHttpStatus());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter()
                .write(objectMapper.writeValueAsString(new CustomException(e.getErrorCode(), e.getHttpStatus() , e.getMessage()).getModel()));
        }
    }
}