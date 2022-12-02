package home.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "home.app")
public class HomeNusApplicationStarter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HomeNusApplicationStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
