package home.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "home.app")
@EnableJpaRepositories(basePackages = {"home.app"})
@EntityScan(basePackages = {"home.app"})
@EnableScheduling
public class HomeNusApplicationStarter implements CommandLineRunner {

    @Autowired
    TestData preFill;

    public static void main(String[] args) {
        SpringApplication.run(HomeNusApplicationStarter.class, args);
    }

    @Override
    public void run(String... args) {
        preFill.preFilledTestUsers();
        //preFill.preFilledTestTorrents();
    }

}
