package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherController {
    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        //判断是否需要自动登录
        //如果之前登录过，cookie中存放了永和的相关信息，需要获取cookie中的信息，并进行数据校验
        boolean needLogin=true;
        String logintype=null ;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {//如果客户端禁用了cookie，那么无法获取得到cookie
            for (Cookie cookie : cookies) {
                if ("logincode".equals(cookie.getName())) {
                    String logincode = cookie.getValue();
                    System.out.println("获取到的cookie中的键值对为：" + cookie.getName() + "======" + cookie.getValue());
                    String[] split = logincode.split("&");
                    if (split.length == 3) {
                        String loginacct = split[0].split("=")[1];
                        String userpwd = split[1].split("=")[1];
                        String type= split[2].split("=")[1];
                        logintype=type;
                        Map<String, Object> paraMap = new HashMap<>();
                        paraMap.put("loginacct", loginacct);
                        paraMap.put("userpswd", userpwd);
                        paraMap.put("logintype", logintype);
                        if ("user".equals(logintype)) {
                            User dbLogin = userService.queryUserlogin(paraMap);
                            if (dbLogin != null) {
                                session.setAttribute(Const.LOGIN_USER, dbLogin);
                                needLogin = false;
                            }
                            //加载当前管理员所拥有的的权限
                            List<Permission> permissions = userService.queryPermissionsByUserId(dbLogin.getId());
                            Permission permissionRoot = null;//定义根节点
                            Map<Integer, Permission> map = new HashMap<>();
                            Set<String> myUrils = new HashSet<>();
                            for (Permission permission : permissions) {
                                map.put(permission.getId(), permission);
                                myUrils.add("/" + permission.getUrl());
                            }

                            session.setAttribute(Const.MY_URIS, myUrils);
                            for (Permission innerPermission : permissions) {   //通过子查找父
                                //子菜单
                                Permission child = innerPermission;
                                if (child.getPid() == null) {
                                    permissionRoot = child;
                                } else {
                                    //父结点
                                    Permission parent = map.get(child.getPid());
                                    parent.getChildren().add(innerPermission);
                                }

                            }
                            session.setAttribute("permissionRoot", permissionRoot);
                        } else if ("member".equals(logintype)) {
                            Member member = memberService.queryMemberLogin(paraMap);
                            if (member != null) {
                                session.setAttribute(Const.LOGIN_MEMBER, member);
                                needLogin = false;
                            }
                        }


                    }
                }
            }
        }
        if (needLogin) {
            return "login";
        } else {
            if ("member".equals(logintype)){
                return "redirect:/member/index.htm";
            } else if ("user".equals(logintype))
            return "redirect:/main.htm";
        }
        return "login";
    }
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
//    @RequestMapping("/doLogin")
//    //@ResponseBody
//    public String doLogin(String loginacct, String userpswd, String type, HttpSession session)
//    {
//        Map<String,Object> map=new HashMap<>();
//        map.put("loginacct",loginacct);
//        map.put("userpswd",userpswd);
//        map.put("type",type);
//        User user = userService.queryUserlogin(map);
//        session.setAttribute(Const.LOGIN_USER,user);
//        return "redirect:main.htm";
//    }
    @RequestMapping("/doLogin")
    @ResponseBody
    public Object doLogin(String loginacct, String userpswd, String type,
                          String rememberme, HttpSession session, HttpServletResponse response){

        AjaxResult result = new AjaxResult();
        result.setData(type);

        try {
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            if(type.equals("user")) {
                User user = userService.queryUserlogin(paramMap);

                if("1".equals(rememberme)) {
                    String logincode = "\"loginacct=" + user.getLoginacct() + "&userpwd=" + user.getUserpswd() + "&logintype=user\"";
                    //loginacct=superadmin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=user
                    System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::" + logincode);

                    Cookie c = new Cookie("logincode", logincode);

                    c.setMaxAge(60 * 60 * 24 * 14); //2周时间Cookie过期     单位秒
                    c.setPath("/"); //表示任何请求路径都可以访问Cookie

                    response.addCookie(c);
                }
                List<Permission> loginPermissions = userService.queryPermissionsByUserId(user.getId());


                Permission rootPermission = null;
                Map<Integer, Permission> map = new HashMap<>();
                Set<String> myUris = new HashSet<>();
                for (Permission permission : loginPermissions) {
                    map.put(permission.getId(), permission);
                    myUris.add("/" + permission.getUrl());
                }
                session.setAttribute(Const.MY_URIS, myUris);
                for (Permission innerPermission : loginPermissions) {
                    Permission child = innerPermission;
                    if (child.getPid() == null) {
                        rootPermission = child;
                    } else {
                        Permission parent = map.get(child.getPid());
                        parent.setOpen(true);
                        parent.getChildren().add(child);
                    }

                }
                session.setAttribute(Const.LOGIN_ROOT_PERMISSION, rootPermission);
                session.setAttribute(Const.LOGIN_USER, user);
                result.setSuccess(user!=null);
            }else if(type.equals("member")) {
                Member member = memberService.queryMemberLogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER, member);

                if("1".equals(rememberme))
                {
                    String logincode="\"loginacct="+member.getLoginacct()+"&userpwd="+member.getUserpswd()+"&logintype=member\"";
                    System.out.println("用户存放到cookie的键值对： logincode:::::"+logincode);
                    Cookie cookie=new Cookie("logincode", logincode);
                    cookie.setMaxAge(60*60*24*14);//2周时间cookie
                    cookie.setPath("/");//表示任何请求路径都可以访问cookie

                    response.addCookie(cookie);
                }
                result.setSuccess(member!=null);
            }



            // {"success":true}
        } catch (Exception e) {
            result.setMessage("登录失败!");
            e.printStackTrace();
            result.setSuccess(false);
            // {"success":false,"message":"登录失败!"}
            //throw e ;
        }

        return result ;
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();//销毁session对象
        return "redirect:/index.htm";
    }

}
