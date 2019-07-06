package info.ivicel.blog.core.filter;


import info.ivicel.blog.core.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private UserService userService;

    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.findByUsername("123123").ifPresent(t -> {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(t.getUsername(), t.getPassword(), t.getAuthorities());
            token.setDetails(t);
            SecurityContextHolder.getContext().setAuthentication(token);
        });
        filterChain.doFilter(request, response);
    }
}
