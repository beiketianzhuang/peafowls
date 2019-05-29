package com.lchen.ccdeploy;

import ch.ethz.ssh2.Connection;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
public class SshTest {

    public static void main(String[] args) {
        Connection conn = RemoteCommandUtil.login("111.231.16.204", "root", "Ww4544989.");
        String result = RemoteCommandUtil.execute(conn, "cd /usr/projects/18/; java -jar *.jar");
        System.out.println(result);
    }

}
