package home.app.service.rest;

import home.app.exception.RestQbTorrentException;
import home.app.model.TorrentData;
import home.app.util.RawTorrentDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
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
    @Value("${home-application.qbTorrent.requestTimeout}")
    private Long requestTimeout;
    private final String REQUEST_HASH_KEY = "hashes";
    private final String REQUEST_DELETE_FILES_KEY = "deleteFiles";

    @Autowired
    private WebClient webClient;
    @Autowired
    private RawTorrentDataParser rawTorrentDataParser;

    public List<TorrentData> getAllTorrents() {
        String uri = rootUri + port + allDataUri;
        try {
            String result = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
            return rawTorrentDataParser.parse(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + uri + " " + e);
        }
    }

    public Boolean pauseTorrent(String torrentHashName) {
        String uri = rootUri + port + pauseTorrentUri;
        return torrentActive(uri, torrentHashName);
    }

    public Boolean resumeTorrent(String torrentHashName) {
        String uri = rootUri + port + resumeTorrentUri;
        return torrentActive(uri, torrentHashName);
    }

    public Boolean deleteTorrent(String torrentHashName) {
        String uri = rootUri + port + deleteTorrentUri;
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(REQUEST_HASH_KEY, torrentHashName);
        body.add(REQUEST_DELETE_FILES_KEY, Boolean.FALSE.toString());
        try {
            webClient.post()
                    .uri(uri)
                    .body(BodyInserters.fromFormData(body))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + uri + " " + e);
        }
    }

    private Boolean torrentActive(String uri, String torrentHashName) {
        try {
            webClient.post()
                    .uri(uri)
                    .body(BodyInserters.fromFormData(REQUEST_HASH_KEY, torrentHashName))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + uri + " " + e);
        }
    }

}
