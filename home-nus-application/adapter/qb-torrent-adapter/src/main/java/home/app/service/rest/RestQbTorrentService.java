package home.app.service.rest;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class RestQbTorrentService {

    @Value("${home-application.uri.root}")
    private String rootUri;
    @Value("${home-application.qbTorrent.port}")
    private String port;
    @Value("${home-application.qbTorrent.uri.getAllData}")
    private String allDataUri;
    @Value("${home-application.qbTorrent.uri.deleteTorrent}")
    private String deleteTorrentUri;
    @Value("${home-application.qbTorrent.uri.pauseTorrent}")
    private String pauseTorrentUri;
    @Value("${home-application.qbTorrent.uri.resumeTorrent}")
    private String resumeTorrentUri;



}
