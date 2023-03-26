package home.app.util;

import com.google.common.collect.Lists;
import home.app.exception.ExtractRawDataException;
import home.app.model.RawKeysQbTorrent;
import home.app.view.qbTorrent.TorrentView;
import home.app.utils.calculator.information.InformationConverter;
import home.app.utils.calculator.torrent.TorrentCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class RawTorrentDataParser {

    private static final String ROOT_TORRENTS_KEY = "torrents";
    private static final String ROOT_DISK_KEY = "server_state";
    private static final String FREE_SPACE_DISK_KEY = "free_space_on_disk";

    @Autowired
    private InformationConverter informationConverter;
    @Autowired
    private TorrentCalculator torrentCalculator;

    @Lookup
    protected RawTorrent getRawData() {
        return null;
    }

    public List<TorrentView> parse(String rawData) {
        RawTorrent rawTorrent = getRawData();
        rawTorrent.writeData(rawData);
        return convertData(rawTorrent);
    }

    private List<TorrentView> convertData(RawTorrent rawTorrent) {
        List<TorrentView> torrentDatumViews = Lists.newArrayList();
        Map<String, Object> torrents = rawTorrent.getRawData().getJSONObject(ROOT_TORRENTS_KEY).toMap();

        for (String key : torrents.keySet()) {
            try {
                HashMap<String, Object> raw = (HashMap<String, Object>) torrents.get(key);
                TorrentView extractingData = extractData(key, raw);
                torrentDatumViews.add(finishBuilding(extractingData, key));
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ExtractRawDataException("Cannot cast data " + torrents + " to HashMap<String, Object>", e);
            }
        }
        return torrentDatumViews;
    }

    private TorrentView extractData(String key, HashMap<String, Object> raw) {
        if (Objects.isNull(raw) || raw.isEmpty()) {
            throw new ExtractRawDataException("There is empty data by torrent hash " + key);
        }
        try {
            return TorrentView.builder()
                    .name(String.valueOf(raw.get(RawKeysQbTorrent.NAME.getKey())))
                    .rootPath(String.valueOf(raw.get(RawKeysQbTorrent.ROOT_PATH.getKey())))
                    .contentPath(String.valueOf(raw.get(RawKeysQbTorrent.CONTENT_PATH.getKey())))
                    .rawTotalSize((Number) raw.get(RawKeysQbTorrent.TOTAL_SIZE.getKey()))
                    .rawDownloadedSize((Number) raw.get(RawKeysQbTorrent.DOWNLOADED.getKey()))
                    .state(String.valueOf(raw.get(RawKeysQbTorrent.STATE.getKey())))
                    .torrentCategory(String.valueOf(raw.get(RawKeysQbTorrent.CATEGORY.getKey())))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExtractRawDataException("Error when try extract data by torrent hash " + key, e);
        }
    }

    private TorrentView finishBuilding(TorrentView torrentView, String key) {
        torrentView.setHash(key);
        torrentView.setConvertedTotalSize(informationConverter.autoConvert(torrentView.getRawTotalSize()));
        torrentView.setConvertedDownloadedSize(informationConverter.autoConvert(torrentView.getRawDownloadedSize()));
        torrentView.setDownloadedPercent(torrentCalculator.calculateDownloadingPercent(torrentView.getRawTotalSize(), torrentView.getRawDownloadedSize()));
        return torrentView;
    }

//    public void updateDiskSpace(RawTorrent rawTorrent, List<TorrentData> data) {
//        Map<String, Object> serverInfo = rawTorrent.getRawData().getJSONObject(ROOT_DISK_KEY).toMap();
//        Long freeSpaceValue = (Long) serverInfo.get(FREE_SPACE_DISK_KEY);
//        if (Objects.nonNull(freeSpaceValue)) {
//
//        }
//    }
}
