package com.lchen.ccdeploy.service.chain;

import ch.ethz.ssh2.Connection;
import com.lchen.ccdeploy.dao.DeployPasswordRepository;
import com.lchen.ccdeploy.model.DeployPassword;
import com.lchen.ccdeploy.utils.RemoteCommandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Component
public class MachineConnectionPasswordHandler extends MachineConnectionHandler {

    @Autowired
    private DeployPasswordRepository deployPasswordRepository;


    @Override
    public void handleDeploy(String context, Integer version) {

        DeployPassword deployPassword = deployPasswordRepository.findByContext(context);
        if (Objects.isNull(deployPassword)) {
            getChain().handleDeploy(context, version);
            return;
        }

        Connection con = RemoteCommandUtil.login(deployPassword.getIp(), deployPassword.getUsername(), deployPassword.getPassword());
//        RemoteCommandUtil.execute(con, String.format("cd /usr/projects/%s/; java -jar *.jar", version));
        //停止旧服务

        //删除旧包
        RemoteCommandUtil.execute(con, String.format("rm -rf /usr/projects/deploy/*"));

        //拷包
        RemoteCommandUtil.execute(con, String.format("cp /usr/projects/%s/*.jar /usr/projects/deploy/", version));

        //部署
        RemoteCommandUtil.execute(con, "nohup java -jar *.jar");
    }
}
