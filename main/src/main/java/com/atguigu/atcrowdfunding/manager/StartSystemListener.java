package com.atguigu.atcrowdfunding.manager;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {


    //在服务器启动时，创建application对象时需要执行额方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将项目的上下文路径放到application域中
        ServletContext application=servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH", contextPath);

        //加载所有的许可路径
        ApplicationContext ioc= WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService=ioc.getBean(PermissionService.class);
        List<Permission>queryAllPermissions=permissionService.queryAllPermission();
        Set<String>allUris=new HashSet<>();
        for(Permission permission:queryAllPermissions)
        {
            allUris.add("/"+permission.getUrl());
        }
        application.setAttribute(Const.ALL_PERMISSION_URI, allUris);

        System.out.println("APP_PATH");
        System.out.println(contextPath);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
