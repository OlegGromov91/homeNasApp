package home.app.utils.calculator.torrent;

import home.app.utils.calculator.base.Calculator;
import org.springframework.stereotype.Component;

@Component
public class TorrentCalculator implements Calculator {

    public Double calculateDownloadingPercent(Number totalSize, Number downloaded) {
        return 100 * downloaded.doubleValue() / totalSize.doubleValue();
    }

}
