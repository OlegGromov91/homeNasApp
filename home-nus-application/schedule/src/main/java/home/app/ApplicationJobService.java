package home.app;

import home.app.meTube.MeTubeJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApplicationJobService {

    @Autowired
    private MeTubeJob meTubeJob;

    @Scheduled(cron = "0 */3 * ? * *")
    public void meTubeJob() {
        log.info("MeTubeJob started");
        meTubeJob.schedule();
        log.info("MeTubeJob ended");
    }
}
