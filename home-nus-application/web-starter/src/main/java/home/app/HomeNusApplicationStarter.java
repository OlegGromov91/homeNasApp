package home.app;

import home.app.bot.TelegramBot;
import home.app.model.meTube.MeTubeVideo;
import home.app.model.meTube.enums.MeTubeVideoStatus;
import home.app.model.meTube.enums.VideoFormat;
import home.app.model.user.ApplicationUser;
import home.app.model.user.TelegramUser;
import home.app.repository.MeTubeRepository;
import home.app.repository.TelegramFilesRepository;
import home.app.repository.TorrentRepository;
import home.app.repository.UserRepository;
import home.app.service.rest.RestQbTorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication(scanBasePackages = "home.app")
@EnableJpaRepositories(basePackages = {"home.app"})
@EntityScan(basePackages = {"home.app"})
@EnableScheduling
public class HomeNusApplicationStarter implements CommandLineRunner {


    //    @Autowired
//    WebClient webClient;
//
    @Autowired
    RestQbTorrentService restQbTorrentService;

    @Autowired
    TorrentRepository torrentRepository;

    @Autowired
    TelegramBot telegramBot;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TelegramFilesRepository telegramFilesRepository;
//    @Autowired
//    FileStorage fileStorage;

    public static void main(String[] args) {
        SpringApplication.run(HomeNusApplicationStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        doWeb();
    }

    @Autowired
    MeTubeRepository meTubeRepository;

    public void test() {

        List<MeTubeVideo> videos = List.of(
                MeTubeVideo.builder()
                        .creatingDate(LocalDateTime.now())
                        .url(UUID.randomUUID().toString())
                        .videoFormat(VideoFormat.MP_4)
                        .status(MeTubeVideoStatus.ERROR)
                        .name("videoName")
                        .build(),
                MeTubeVideo.builder()
                        .creatingDate(LocalDateTime.now())
                        .url(UUID.randomUUID().toString())
                        .videoFormat(VideoFormat.MP_4)
                        .status(MeTubeVideoStatus.ERROR)
                        .name("videoName")
                        .build(),
                MeTubeVideo.builder()
                        .creatingDate(LocalDateTime.now())
                        .url(UUID.randomUUID().toString())
                        .videoFormat(VideoFormat.MP_4)
                        .status(MeTubeVideoStatus.IN_QUEUE)
                        .name("videoName")
                        .build(),
                MeTubeVideo.builder()
                        .creatingDate(LocalDateTime.now())
                        .url(UUID.randomUUID().toString())
                        .videoFormat(VideoFormat.MP_4)
                        .status(MeTubeVideoStatus.DONE)
                        .name("videoName")
                        .build(),
                MeTubeVideo.builder()
                        .creatingDate(LocalDateTime.now())
                        .url(UUID.randomUUID().toString())
                        .videoFormat(VideoFormat.MP_4)
                        .status(MeTubeVideoStatus.DOWNLOADING)
                        .name("videoName")
                        .build()
        );


        meTubeRepository.saveAll(videos);

    }


    public void doWeb() throws IOException {

        test();


        TelegramUser tgUser = TelegramUser.builder()
                .id(544908050L)
                .firstName("Oleg")
                .secondName("Gromov")
                .userName("OlegGromov13")
                .isBot(false)
                .build();
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("AA")
                .telegramUser(tgUser)
                .build();
        userRepository.save(applicationUser);

        TelegramUser tgUser2 = TelegramUser.builder()
                .id(243327770L)
                .firstName("nastya")
                .isBot(false)
                .build();
        ApplicationUser applicationUse2r = ApplicationUser.builder()
                .firstName("asdsad")
                .telegramUser(tgUser2)
                .build();
        userRepository.save(applicationUser);
        userRepository.save(applicationUse2r);

        Optional<ApplicationUser> user = userRepository.findByTelegramUserId(544908050L);
        System.out.println();
        //
        //    List<TorrentData> getAllTorrents = restQbTorrentService.getAllTorrents();
        //    String resp =  restQbTorrentService.test(null);

        //   System.out.println(getAllTorrents);
        //fileStorage.processDoc();
    }

}
