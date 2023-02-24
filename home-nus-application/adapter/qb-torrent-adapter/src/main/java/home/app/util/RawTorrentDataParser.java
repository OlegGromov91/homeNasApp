package home.app.util;

import com.google.common.collect.Lists;
import home.app.exception.ExtractRawDataException;
import home.app.model.RawKeysQbTorrent;
import home.app.view.qbTorrent.TorrentDataView;
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

    public List<TorrentDataView> parse(String rawData) {
        RawTorrent rawTorrent = getRawData();
        rawTorrent.writeData(rawData);
        return convertData(rawTorrent);
    }

    private List<TorrentDataView> convertData(RawTorrent rawTorrent) {
        List<TorrentDataView> torrentDatumViews = Lists.newArrayList();
        Map<String, Object> torrents = rawTorrent.getRawData().getJSONObject(ROOT_TORRENTS_KEY).toMap();

        for (String key : torrents.keySet()) {
            try {
                HashMap<String, Object> raw = (HashMap<String, Object>) torrents.get(key);
                TorrentDataView extractingData = extractData(key, raw);
                torrentDatumViews.add(finishBuilding(extractingData, key));
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ExtractRawDataException("Cannot cast data " + torrents + " to HashMap<String, Object>", e);
            }
        }
        return torrentDatumViews;
    }

    private TorrentDataView extractData(String key, HashMap<String, Object> raw) {
        if (Objects.isNull(raw) || raw.isEmpty()) {
            throw new ExtractRawDataException("There is empty data by torrent hash " + key);
        }
        try {
            return TorrentDataView.builder()
                    .name(String.valueOf(raw.get(RawKeysQbTorrent.NAME.getKey())))
                    .rootPath(String.valueOf(raw.get(RawKeysQbTorrent.ROOT_PATH.getKey())))
                    .contentPath(String.valueOf(raw.get(RawKeysQbTorrent.CONTENT_PATH.getKey())))
                    .rawTotalSize((Long) raw.get(RawKeysQbTorrent.TOTAL_SIZE.getKey()))
                    .rawDownloadedSize((Integer) raw.get(RawKeysQbTorrent.DOWNLOADED.getKey()))
                    .state(String.valueOf(raw.get(RawKeysQbTorrent.STATE.getKey())))
                    .torrentCategory(String.valueOf(raw.get(RawKeysQbTorrent.CATEGORY.getKey())))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExtractRawDataException("Error when try extract data by torrent hash " + key, e);
        }
    }

    private TorrentDataView finishBuilding(TorrentDataView torrentDataView, String key) {
        torrentDataView.setHash(key);
        torrentDataView.setConvertedTotalSize(informationConverter.autoConvert(torrentDataView.getRawTotalSize()));
        torrentDataView.setConvertedDownloadedSize(informationConverter.autoConvert(torrentDataView.getRawDownloadedSize()));
        torrentDataView.setDownloadedPercent(torrentCalculator.calculateDownloadingPercent(torrentDataView.getRawTotalSize(), torrentDataView.getRawDownloadedSize()));
        return torrentDataView;
    }

//    public void updateDiskSpace(RawTorrent rawTorrent, List<TorrentData> data) {
//        Map<String, Object> serverInfo = rawTorrent.getRawData().getJSONObject(ROOT_DISK_KEY).toMap();
//        Long freeSpaceValue = (Long) serverInfo.get(FREE_SPACE_DISK_KEY);
//        if (Objects.nonNull(freeSpaceValue)) {
//
//        }
//    }
}
