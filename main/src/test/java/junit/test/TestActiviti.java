package junit.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestActiviti{

    ApplicationContext ioc= new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = ioc.getBean(ProcessEngine.class);

    //创建流程引擎，创建23张表
   @Test
    public void test01()
   {
       System.out.println("ProcessEngine= "+processEngine);
   }

   //部署流程定义
    @Test
    public void test2()
    {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("auth.bpmn").deploy();
        System.out.println(deploy);
    }
     //查询部署流程定义
    @Test
    public void test3()
    {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for(ProcessDefinition processDefinition:list)
        {
            System.out.println("Id="+processDefinition.getId());
            System.out.println("Key="+processDefinition.getKey());
            System.out.println("Name="+processDefinition.getName());
            System.out.println("Version="+processDefinition.getVersion());
            System.out.println("-----------------------");
        }
        long count = processDefinitionQuery.count();
        System.out.println("count= "+count);
    }

    //4.启动流程实例
    /**
     * act_hi_actinst, 历史的活动的任务表.
     * act_hi_procinst, 历史的流程实例表.
     * act_hi_taskinst, 历史的流程任务表
     * act_ru_execution, 正在运行的任务表.
     * act_ru_task, 运行的任务数据表.
     */
    @Test
    public void test04(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance="+processInstance);
    }
}
