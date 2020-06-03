//Author:刘行
package javaweb.remember.filter;

import javaweb.remember.entity.User;
import javaweb.remember.service.RedisService;
import javaweb.remember.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class TokenFilter implements Filter {
    //重定向网址
    public static final String redirectUrl = "https://bbs.csdn.net/topics/392336698";
    //设置用户token过期时间为一个小时（3600秒）
    private static final Long USER_TOKEN_EXPIRE_TIME = 3600L;
    //不用过滤的路由
    private static final String[] URIS = {"/login","/sign_up","/verify_code"};

    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        for(String uri : URIS){
            if(requestURI.startsWith(uri)){
                filterChain.doFilter(request,response);
                return;
            }
        }

        String token = request.getHeader("token");
        //请求头中没有token
        if(token == null){
            //重定向
            response.sendRedirect(redirectUrl);
            return;
        }
        //通过token获取email
        String email = redisService.get(token);
        //token失效或者是伪造的
        if(email==null){
            //重定向
            response.sendRedirect(redirectUrl);
            return;
        }
        User user = userService.findByEmail(email);
        if(user == null){
            //重定向
            response.sendRedirect(redirectUrl);
            return;
        }

        request.setAttribute("id",user.getId());
        //更新token持续时间
        redisService.delete(token);
        redisService.set(token,email);
        redisService.expire(token,USER_TOKEN_EXPIRE_TIME);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
