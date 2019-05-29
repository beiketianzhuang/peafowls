package com.lchen.ccdeploy.service;

import ch.ethz.ssh2.Connection;
import com.lchen.ccdeploy.utils.RemoteCommandUtil;
import org.springframework.stereotype.Component;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Component
public class DeployHelper {

    public void deploy(String context, Integer version) {

        Connection con = RemoteCommandUtil.login("", "", "");
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
