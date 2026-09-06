package isayen.lets_play.config;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import isayen.lets_play.users.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver exceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }else{
            try {
                String token = authHeader.substring(7);
                String userId = jwtService.extractId(token);
                if (jwtService.isTokenExpired(token)) {
                    filterChain.doFilter(request, response);
                }
    
                UserDetails user = userRepository.findById(userId).get();
    
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                exceptionResolver.resolveException(request, response, null, e);
            }
            
        }


    }

}
