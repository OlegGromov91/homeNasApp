package home.app.util;

import com.google.common.collect.Lists;
import home.app.exception.ExtractRawDataException;
import home.app.model.RawKeysQbTorrent;
import home.app.model.TorrentData;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class RawTorrentDataParser {

    private static final String ROOT_TORRENTS_KEY = "torrents";

    @Lookup
    protected RawTorrent getRawData() {
        return null;
    }

    public List<TorrentData> parse(String rawData) {
        RawTorrent rawTorrent = getRawData();
        rawTorrent.writeData(rawData);
        convertData(rawTorrent);
        return null;
    }

    private List<TorrentData> convertData(RawTorrent rawTorrent) {
        List<TorrentData> torrentData = Lists.newArrayList();
        Map<String, Object> torrents = rawTorrent.getRawData().getJSONObject(ROOT_TORRENTS_KEY).toMap();

        for (String key : torrents.keySet()) {
            HashMap<String, Object> raw = (HashMap<String, Object>) torrents.get(key);
            TorrentData extractingData = extractData(key, raw);
            extractingData.setHash(key);
            torrentData.add(extractingData);
        }
        return torrentData;
    }

    private TorrentData extractData(String key, HashMap<String, Object> raw) {
        if (Objects.isNull(raw) || raw.isEmpty()) {
            throw new ExtractRawDataException("There is empty data by torrent hash " + key);
        }
        try {
            return TorrentData.builder()
                    .name(String.valueOf(raw.get(RawKeysQbTorrent.NAME.getKey())))
                    .rootPath(String.valueOf(raw.get(RawKeysQbTorrent.ROOT_PATH.getKey())))
                    .contentPath(String.valueOf(raw.get(RawKeysQbTorrent.CONTENT_PATH.getKey())))
                    .totalSize((Long) raw.get(RawKeysQbTorrent.TOTAL_SIZE.getKey()))
                    .downloaded((Integer) raw.get(RawKeysQbTorrent.DOWNLOADED.getKey()))
                    .state(String.valueOf(raw.get(RawKeysQbTorrent.STATE.getKey())))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExtractRawDataException("Error when try extract data by torrent hash " + key, e);
        }
    }
}
