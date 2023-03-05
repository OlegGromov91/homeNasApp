package home.app;

import home.app.meTube.MeTubeJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ApplicationJobService {

    @Autowired
    private MeTubeJob meTubeJob;

    @Scheduled(cron = "0 */3 * ? * *")
    public void meTubeJob() {
        meTubeJob.schedule();
    }
}
