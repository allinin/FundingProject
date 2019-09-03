package com.atguigu.atcrowdfunding.manager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartSystemListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //将项目的上下文路径放到application域中
        ServletContext application=servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH", contextPath);
        System.out.println("APP_PATH");
        System.out.println(contextPath);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
