package home.app.meTube;

import home.app.bot.TelegramBot;
import home.app.model.meTube.MeTubeVideo;
import home.app.model.meTube.enums.MeTubeVideoStatus;
import home.app.repository.MeTubeRepository;
import home.app.service.MeTubeService;
import home.app.util.MeTubeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static home.app.model.meTube.enums.MeTubeVideoStatus.*;

/**
 * 1) идем на диск собираем имена файлов
 * <p>
 * 2) Статус "В очереди"
 * <p>
 * - фильтруем список файлов на диске по имени видео
 * <p>
 * - если в оставшемся списке есть файл с раширением .part, то переводим в статус "загружается" вызываем метод retryDownloading,
 * если .part нет, но есть .mp4, то переводим в статус "загружено",
 * если нет ни того, ни другого ничего не делаем.
 * <p>
 * 3) Статус "Загружается"
 * <p>
 * - фильтруем список файлов на диске по имени видео
 * <p>
 * - если в оставшемся списке есть файл с раширением .part, то вызываем метод retryDownloading,
 * если .part нет, но есть .mp4, то переводим в статус "загружено".
 */
@Component
public class MeTubeJob {

    private static final String DOWNLOADING_FILE_POSTFIX_TYPE = ".part";

    @Autowired
    private MeTubeRepository meTubeRepository;
    @Autowired
    private MeTubeUtils meTubeUtils;
    @Autowired
    private MeTubeService meTubeService;
    @Autowired
    private TelegramBot telegramBot;

    private static final List<MeTubeVideoStatus> IN_JOB_STATUSES = List.of(IN_QUEUE, DOWNLOADING);

    /* TODO: обработать статус Ошибка
     если файл в ошибке, то сообщаем пользователю о данных о файле (не здесь, а в тг резолвере) и говорим что файл не был загружен, все равно пытаться загружать его или удалить?
     * если пытаться загружать, то не будем парсить имя
     * если удалить, то удаляем.

    TODO: отправлять юзеру сообщения о том, что файл загружен
     */

    public void schedule() {
        List<String> filesInDisk = meTubeUtils.getVideoFromDisk();
        if (filesInDisk.isEmpty()) {
            return;
        }
        List<MeTubeVideo> videos = meTubeRepository.findByStatusIn(IN_JOB_STATUSES);
        if (videos.isEmpty()) {
            return;
        }
        videos.forEach(video -> retryJob(video, filesInDisk));
        meTubeRepository.saveAll(videos);
    }


    /**
     * А) MeTubeService:

     * 1) оборачивать в try catch метод загрузки
     * 2) перед загрузкой лезим в базу, смотрим, если такая ссылка уже есть, то обновляем статус у файла и кидаем
     * в загрузки

     * Б) джоба:
     * 1)
     *
     */


    private void retryJob(MeTubeVideo video, List<String> filesInDisk) {

        boolean isFileDownloadingMark = false;
        boolean isFileDownloadedMark = false;

        for (String file : filesInDisk) {
            if (!file.contains(video.getName())) {
                continue;
            }
            if (file.endsWith(DOWNLOADING_FILE_POSTFIX_TYPE)) {
                isFileDownloadingMark = true;
            } else if (file.endsWith(video.getVideoFormat().getFormat())) {
                isFileDownloadedMark = true;
            }
        }

        if (isFileDownloadingMark) {
            video.setStatus(DOWNLOADING);
            meTubeService.retryVideoDownloading(video);
        } else if (isFileDownloadedMark) {
            video.setStatus(DONE);
            //telegramBot.execute()
        }

    }
}
