package com.jy.activiti.listener;

import com.jy.activiti.helper.ContextHelper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class AppStartupListener implements ApplicationListener {

    private final Logger LOGGER = LogManager.getLogger(AppStartupListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof ContextRefreshedEvent) {
            System.out.println("AppStartupListener works....");
            //设置用户权限
            findDefaultUserAndSerAuthentication("admin");
            //获取部署
            Deployment deployment = getDefaultDeployment();
            if (deployment != null) {
                //获取定义流程
                ProcessDefinition pd = getDeploymentProcessDefinition(deployment);
                System.out.println("pd: " + pd);
            }
        }

    }


    public void findDefaultUserAndSerAuthentication(String userId) {
        IdentityService identityService = ContextHelper.getBean(IdentityService.class);
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) {
            user = identityService.newUser(userId);
            user.setEmail("362961910@qq.com");
            user.setFirstName("jian");
            user.setLastName("yang");
            user.setPassword("123456");
            identityService.saveUser(user);
        }
        identityService.setAuthenticatedUserId(user.getId());
    }

    public Deployment getDefaultDeployment() {
        RepositoryService repositoryService = ContextHelper.getBean(RepositoryService.class);
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        return deployments.get(0);
    }

    public ProcessDefinition getDeploymentProcessDefinition(Deployment deployment) {
        RepositoryService repositoryService = ContextHelper.getBean(RepositoryService.class);
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        return pd;
    }

}
