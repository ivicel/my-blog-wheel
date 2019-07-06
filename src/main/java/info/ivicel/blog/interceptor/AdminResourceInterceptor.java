package info.ivicel.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminResourceInterceptor implements HandlerInterceptor {
    private PathMatcher matcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();

        if (matcher.match("/admin/**", path) && !matcher.match("/admin/static/**", path) &&
                !matcher.match("/admin/index.html", path)) {
            // 登录检查
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!matcher.match("/admin/login", path) && (auth == null ||
                    "anonymousUser".equals(auth.getPrincipal()))) {
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return false;
            }

            // 重定向 /admin 到 /admin/
            if ("/admin".equals(path)) {
                response.sendRedirect(request.getContextPath() + "/admin/");
                return false;
            }

            request.getRequestDispatcher("/admin/index.html").forward(request, response);
            return false;
        }

        return true;
    }
}
