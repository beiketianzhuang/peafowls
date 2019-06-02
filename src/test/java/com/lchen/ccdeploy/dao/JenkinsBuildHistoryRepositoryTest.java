package com.lchen.ccdeploy.dao;

import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JenkinsBuildHistoryRepositoryTest {

    @Autowired
    private JenkinsBuildHistoryRepository jenkinsBuildHistoryRepository;

    @Test
    public void findLatelyByVersionAndJobNameTest() {
        List<JenkinsBuildHistory> jenkinsBuildHistories = jenkinsBuildHistoryRepository.findLatelyByVersionAndJobName("demo");
        assert jenkinsBuildHistories != null;
    }


}