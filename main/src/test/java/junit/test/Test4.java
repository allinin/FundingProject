package junit.test;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test4 {
    public static void main(String[] args) {
        ApplicationContext ioc= new ClassPathXmlApplicationContext("spring/spring*.xml") ;
        UserService userService=ioc.getBean(UserService.class);
        for(int i=0;i<=50;i++)
        {
            User user=new User();
            user.setLoginacct("test"+i);
            user.setUserpswd(MD5Util.digest("123"));
            user.setUsername("test"+i);
            user.setEmail("test"+i+"@atguigu.com");
            user.setCreatetime("2019-07-24 14:17:00");
            userService.saveUser(user);

        }
    }
}
