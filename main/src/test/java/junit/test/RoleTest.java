package junit.test;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RoleTest {
    public static void main(String[] args)
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring/spring*.xml");
        RoleService s = context.getBean(RoleService.class);
        for(int i=3;i<=101;i++)
        {
            Role role=new Role();
            role.setId(i);
            role.setName("test"+i);
            s.saveRole(role);
        }
    }
}
