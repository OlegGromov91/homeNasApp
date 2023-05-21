package home.app.service;

import home.app.exception.RestQbTorrentException;
import home.app.model.files.TelegramFiles;
import home.app.model.files.types.ApplicationFileTypes;
import home.app.model.user.ApplicationUser;
import home.app.repository.TelegramFilesRepository;
import home.app.service.rest.RestTelegramBotService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class BotCommonService {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTelegramBotService restTelegramBotService;
    @Autowired
    private TelegramFilesRepository telegramFilesRepository;
    @Autowired
    protected QbTorrentService qbTorrentService;

    @Transactional
    public ApplicationUser getUserByTelegramId(Long telegramId) {
        return userService.getUserByTelegramId(telegramId);
    }

    @Transactional
    public void getFileInfo(String fileId, String fileName, Long userId) {
        ApplicationUser user = getUserByTelegramId(userId);
        JSONObject fileInfo = restTelegramBotService.getFileInfo(fileId);

        if (!fileInfo.keySet().contains("ok")) {
            throw new RestQbTorrentException("when try get fileInfo response get wrong code");
        }

        String filePath = String.valueOf(fileInfo
                .getJSONObject("result")
                .getString("file_path"));

        TelegramFiles torrentFile = TelegramFiles.builder()
                .telegramFileType(ApplicationFileTypes.TORRENT)
                .user(user)
                .filePath(filePath)
                .fileName(fileName)
                .creatingDate(LocalDateTime.now())
                .build();

        telegramFilesRepository.save(torrentFile);
    }

    @Transactional
    public String uploadFileToTorrentApplication(Long userId, String path, String category) {
        ApplicationUser user = getUserByTelegramId(userId);
        List<TelegramFiles> tgFiles = telegramFilesRepository.findByUser(user);
        TelegramFiles tgFile = tgFiles.stream().max(Comparator.comparing(TelegramFiles::getCreatingDate)).orElseThrow(RuntimeException::new);

        byte[] file = restTelegramBotService.downloadFileFromTelegram(tgFile.getFilePath());

        qbTorrentService.downloadTorrent(file, tgFile.getFileName(), path, category);
        return tgFile.getFileName();
    }
}
