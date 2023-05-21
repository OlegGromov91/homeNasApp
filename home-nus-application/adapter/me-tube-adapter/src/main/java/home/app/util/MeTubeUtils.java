package home.app.util;

import com.google.common.base.Strings;
import home.app.exceptions.ExtractNameFromYoutubeException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class MeTubeUtils {

    private static final String YOUTUBE_TITLE_POSTFIX = " - YouTube";

    @Value("${home-application.meTube.downloadedPath}")
    private String downloadedPath;

    public String getVideoNameFromYouTube(String uri) {
        try {
            Document doc = Jsoup.connect(uri).get();
            Elements elements = doc.getElementsByTag("title");
            return extractVideoNameFromTitle(elements.text());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public List<String> getVideoFromDisk() {
        File file = new File(downloadedPath);
        return (Objects.nonNull(file.list())) ? List.of(file.list()) : Collections.EMPTY_LIST;
    }

    private String extractVideoNameFromTitle(String rawName) {
        boolean isNameValid = !Strings.isNullOrEmpty(rawName) && rawName.length() > YOUTUBE_TITLE_POSTFIX.length();
        if (isNameValid) {
            return rawName.substring(0, rawName.length() - YOUTUBE_TITLE_POSTFIX.length());
        }
        throw new ExtractNameFromYoutubeException(String.format("Video name %s is not a valid", rawName));
    }
}
