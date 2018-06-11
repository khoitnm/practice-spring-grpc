package org.tnmk.practice.batch.pro01simplegrpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practice.batch.pro01simplegrpc.joblauncher.FileProcessingJobLauncherHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunJobsInSequenceApplicationTests {

    @Autowired
    FileProcessingJobLauncherHelper fileProcessingJobLauncherHelper;

    @Test
    public void startFileProcessingBatchJob() {
        fileProcessingJobLauncherHelper.startFileProcessJob("/not_exist_file.csv", 3, 3);
        fileProcessingJobLauncherHelper.startFileProcessJob("/heroes_error_at_3_4.csv", 2, 3);
    }

}
