package home.app.service;

import com.google.common.base.Strings;
import home.app.exceptions.DownloadedDirectoryNotAvailableException;
import home.app.model.meTube.MeTubeVideo;
import home.app.model.meTube.enums.MeTubeVideoStatus;
import home.app.model.meTube.enums.VideoFormat;
import home.app.repository.MeTubeRepository;
import home.app.service.rest.RestMeTubeService;
import home.app.util.MeTubeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class MeTubeService {

    @Value("${home-application.meTube.downloadedPath}")
    private String downloadedPath;

    @Autowired
    private RestMeTubeService restMeTubeService;
    @Autowired
    private MeTubeUtils meTubeUtils;
    @Autowired
    private MeTubeRepository meTubeRepository;

    private final static String pathError = "Не заполнен конечный путь для загрузки видео в файле properties.yml !";
    private final static String directoryError = "Нет досутпа к директории %s, возможно у системы на которой установленно приложение нет доступа к директории, либо директория указана с ошибкой";
    private final static String appErrorMessage = "Приложение отправит файл загружаться, но не будет контроллировать этот процесс, что может привести к тому, что видео не будет загруженно";

    @Transactional
    public void addNewVideoToDownload(String url, String format) {

        String videoName = meTubeUtils.getVideoNameFromYouTube(url);
        MeTubeVideoStatus status = (Strings.isNullOrEmpty(videoName)) ? MeTubeVideoStatus.ERROR : MeTubeVideoStatus.IN_QUEUE;
        download(url, format);

        MeTubeVideo video = MeTubeVideo.builder()
                .creatingDate(LocalDateTime.now())
                .url(url)
                .videoFormat(VideoFormat.extractVideoFormat(format))
                .status(status)
                .name(videoName)
                .build();

        meTubeRepository.save(video);
    }

    public void retryVideoDownloading(MeTubeVideo video) {
        download(video.getUrl(),  video.getVideoFormat().getFormat());
    }


    private void download(String url, String format) {
        boolean isPathNotAvailable = Strings.isNullOrEmpty(downloadedPath);

        if (isPathNotAvailable) {
            restMeTubeService.downloadVideo(url, format);
            throw new DownloadedDirectoryNotAvailableException(pathError + appErrorMessage);
        }
        File downloadingDirectory = new File(downloadedPath);
        boolean isDirectoryNotAvailable = !downloadingDirectory.isDirectory() && !downloadingDirectory.canRead();
        if (isDirectoryNotAvailable) {
            restMeTubeService.downloadVideo(url, format);
            throw new DownloadedDirectoryNotAvailableException(pathError + String.format(directoryError, downloadedPath));
        }
        restMeTubeService.downloadVideo(url, format);
    }

}
