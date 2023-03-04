package home.app.service;

import com.google.common.base.Strings;
import home.app.service.exceptions.DownloadedDirectoryNotAvailableException;
import home.app.service.rest.RestMeTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Проверить, что папка существует и что это директория иначе кидать исключение, что не предоставлен доступ приложения к папке или диску, либо искомой паки не существует.
 * Файл будет поставлен на загрузку, но не отображен в базе и не факт что докачается.
 * У видоса 4 статуса (В очереди, Загружается, Загружен, Ошибка)
 * 1) идем в ютуб забираем имя у видоса, чтобы сохранить в бд (если фейлимся, то пока не сохраняем имя)
 * 2) Кидаем файл в загрузку (в бд сохраняется uri и format)
 * 3) Джоба запускается раз в 4 минут
 * <p>
 * идет в БД выберает файлы со статусом (В очереди, Загружается, Ошибка)
 * <p>
 * а) статус загружается
 * 1) идет на диск смотрим, что есть файлы с расширением .part
 * 2) если имя загружаемого файла совпадает с .part на диске, то кидаем файл снова в загрузку,
 * если файлов .part не нашлось, то среди файлов ищем по имени файл, смотрим, что у него расширение .mp4 или .mp3
 * считаем, что файл был загружен нормально и меняем статус на загружен
 * <p>
 * б) если в очереди
 * 1) идет на диск смотрим, что есть файлы с расширением .part
 * 2) если имя файла в очереди совпадает с .part на диске, то меняем статус на "загружается" и кидаем файл снова в загрузку
 * <p>
 * в) если файл в ошибке, то сообщаем пользователю о данных о файле и говорим что файл не был загружен, все равно пытаться загружать его или удалить?
 * если пытаться загружать, то не будем парсить имя
 * если удалить, то удаляем.
 * <p>
 * метод addNewFile в тг будет с проверкой на наличие в бд по урлу (Not null, unique)
 */

@Service
public class MeTubeService {

    @Value("${home-application.meTube.downloadedPath}")
    private String downloadedPath;

    @Autowired
    private RestMeTubeService restMeTubeService;

    private final static String pathError = "Не заполнен конечный путь для загрузки видео в файле properties.yml !";
    private final static String directoryError = "Нет досутпа к директории %s, возможно у системы на которой установленно приложение нет доступа к директории," +
            " либо директория указана с ошибкой";
    private final static String appErrorMessage = "Приложение отправит файл загружаться, но не будет контроллировать этот процесс, что может привести к тому, что видео не будет загруженно";


    public void addNewVideoToDownload(String url, String format) {
        checkingBeforeAddingVideoToDownload(url, format);

    }

    public void addVideoToDownload(String url, String format) {


    }


    private void checkingBeforeAddingVideoToDownload(String url, String format) {
        boolean isPathNotAvailable = Strings.isNullOrEmpty(downloadedPath);

        if (isPathNotAvailable) {
            restMeTubeService.downloadVideo(url, format);
            throw new DownloadedDirectoryNotAvailableException(pathError + appErrorMessage);
        }
        File downloadingDirectory = new File(downloadedPath);
        boolean isDirectoryNotAvailable = downloadingDirectory.isDirectory() && downloadingDirectory.canRead();
        if (isDirectoryNotAvailable) {
            restMeTubeService.downloadVideo(url, format);
            throw new DownloadedDirectoryNotAvailableException(pathError + String.format(directoryError, downloadedPath));
        }
        restMeTubeService.downloadVideo(url, format);
    }


    /*public static void main(String[] args) {
        File file = new File("Z:\\videos");

        System.out.println(Arrays.toString(file.list()));
    }*/

}
