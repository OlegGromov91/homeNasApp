package home.app.util;

import com.google.common.base.Strings;
import home.app.exceptions.ExtractNameFromYoutubeException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class MeTubeUtils {

    private static final String YOUTUBE_TITLE_POSTFIX = " - YouTube";
    private static final String DOWNLOADING_FILE_POSTFIX_TYPE = ".part";


//Arrays.stream(file.list()).filter(i -> i.endsWith(".part")).forEach(i -> System.out.println(i));

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

    private String extractVideoNameFromTitle(String rawName) {
        boolean isNameValid = !Strings.isNullOrEmpty(rawName) && rawName.length() > YOUTUBE_TITLE_POSTFIX.length();
        if (isNameValid) {
            return rawName.substring(0, rawName.length() - YOUTUBE_TITLE_POSTFIX.length());
        }
        throw new ExtractNameFromYoutubeException(String.format("Video name %s is not a valid", rawName));
    }
}
