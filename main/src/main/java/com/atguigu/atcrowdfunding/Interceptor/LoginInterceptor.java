package com.atguigu.atcrowdfunding.Interceptor;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter{



    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

           //获取请求路径
          String requestURI = request.getServletPath();
           //白名单
          Set<String>url=new HashSet<>();
          url.add("/doLogin.do");
          url.add("/login.htm");
          url.add("/index.htm");
          url.add("/logout.htm");
         if(url.contains(requestURI))
         {
           return true;
         }
         //判断用户是否已经登录，登录边放行
             HttpSession session=request.getSession();
             User user=(User)session.getAttribute(Const.LOGIN_USER);
             Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);

           if(user!=null || member!=null)
             {
                 return true;
             }else {
               response.sendRedirect(request.getContextPath()+"/login.htm");
               return false;


         }


    }


}
