package home.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "home.app")

public class HomeNusApplicationStarter implements CommandLineRunner {


//    @Autowired
//    WebClient webClient;
//

//    @Autowired
//    FileStorage fileStorage;

    public static void main(String[] args) {
        SpringApplication.run(HomeNusApplicationStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        doWeb();
    }


    public void doWeb() throws IOException {
       //
   //     List<TorrentData> getAllTorrents = restQbTorrentService.getAllTorrents();
    //    String resp =  restQbTorrentService.test(null);

    //    System.out.println(getAllTorrents);
        //fileStorage.processDoc();
    }

}
