package com.lchen.ccdeploy.service.chain;

import ch.ethz.ssh2.Connection;
import com.lchen.ccdeploy.dao.DeployPasswordRepository;
import com.lchen.ccdeploy.dao.DeploymentRepository;
import com.lchen.ccdeploy.model.DeployPassword;
import com.lchen.ccdeploy.model.DeploymentResult;
import com.lchen.ccdeploy.model.constants.DeploymentStatus;
import com.lchen.ccdeploy.service.DeploymentHistoryService;
import com.lchen.ccdeploy.utils.RemoteCommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.lchen.ccdeploy.model.DeploymentProcess.*;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Slf4j
@Component
public class MachineConnectionPasswordHandler extends MachineConnectionHandler {

    @Autowired
    private DeployPasswordRepository deployPasswordRepository;
    @Autowired
    private DeploymentRepository deploymentRepository;
    @Autowired
    private DeploymentHistoryService deploymentHistoryService;


    @Override
    public void handleDeploy(String context, Integer version) {

        DeployPassword deployPassword = deployPasswordRepository.findByContext(context);
        if (Objects.isNull(deployPassword)) {
            getChain().handleDeploy(context, version);
            return;
        }

        DeploymentResult deploymentResult = DeploymentResult.builder()
                .context(context)
                .buildVersion(version)
                .deployStatus(DeploymentStatus.DEPLOYING)
                .build();
        DeploymentResult result = deploymentRepository.save(deploymentResult);

        Connection con;
        try {
            deploymentHistoryService.save(version, CONNECT_START, result.getId());
            con = RemoteCommandUtil.login(deployPassword.getIp(), deployPassword.getUsername(), deployPassword.getPassword());
            deploymentHistoryService.save(version, CONNECT_SUCCESS, result.getId());
        } catch (Exception e) {
            deploymentHistoryService.save(version, CONNECT_FAILED, result.getId());
            log.error("连接服务失败:{}", e);
            return;
        }
        try {
            deploymentHistoryService.save(version, STOP_SERVICE_START, result.getId());
//        RemoteCommandUtil.execute(con, String.format("cd /usr/projects/%s/; java -jar *.jar", version));
            deploymentHistoryService.save(version, STOP_SERVICE_SUCCESS, result.getId());
            //停止旧服务
        } catch (Exception e) {
            deploymentHistoryService.save(version, STOP_SERVICE_FAILED, result.getId());
            return;
        }


        try {
            deploymentHistoryService.save(version, DELETE_PACKAGE_START, result.getId());
            //删除旧包
            RemoteCommandUtil.execute(con, String.format("rm -rf /usr/projects/deploy/*"));
            deploymentHistoryService.save(version, DELETE_PACKAGE_SUCCESS, result.getId());

        } catch (Exception e) {
            deploymentHistoryService.save(version, DELETE_PACKAGE_FAILED, result.getId());
            return;
        }


        try {
            deploymentHistoryService.save(version, COPY_PACKAGE_START, result.getId());
            //拷包
            RemoteCommandUtil.execute(con, String.format("cp /usr/projects/%s/*.jar /usr/projects/deploy/", version));
            deploymentHistoryService.save(version, COPY_PACKAGE_SUCCESS, result.getId());
        } catch (Exception e) {
            deploymentHistoryService.save(version, COPY_PACKAGE_FAILED, result.getId());
            return;
        }

        try {
            deploymentHistoryService.save(version, SERVICE_RUN, result.getId());

            //部署
            RemoteCommandUtil.execute(con, "nohup java -jar *.jar");
            deploymentHistoryService.save(version, SERVICE_RUN_SUCCESS, result.getId());

        } catch (Exception e) {
            deploymentHistoryService.save(version, SERVICE_RUN_FAILED, result.getId());
            return;
        }

        //todo 部署验证

    }
}
