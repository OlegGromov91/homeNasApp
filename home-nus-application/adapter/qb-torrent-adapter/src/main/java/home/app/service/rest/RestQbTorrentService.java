package home.app.service.rest;

import home.app.exception.RestQbTorrentException;
import home.app.util.RawTorrentDataParser;
import home.app.view.qbTorrent.TorrentView;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class RestQbTorrentService {

    @Value("${home-application.uri.root}")
    private String rootUri;
    @Value("${home-application.q-bit-torrent.port}")
    private String port;
    @Value("${home-application.q-bit-torrent.uri.get-all-data}")
    private String allDataUri;
    @Value("${home-application.q-bit-torrent.uri.delete-torrent}")
    private String deleteTorrentUri;
    @Value("${home-application.q-bit-torrent.uri.pause-torrent}")
    private String pauseTorrentUri;
    @Value("${home-application.q-bit-torrent.uri.resume-torrent}")
    private String resumeTorrentUri;
    @Value("${home-application.q-bit-torrent.uri.download-torrent}")
    private String downloadTorrentUri;
    @Value("${home-application.q-bit-torrent.login.uri}")
    private String loginUri;
    @Value("${home-application.q-bit-torrent.login.username}")
    private String loginUserName;
    @Value("${home-application.q-bit-torrent.login.password}")
    private String loginUserPassword;
    @Value("${home-application.q-bit-torrent.request-timeout}")
    private Long requestTimeout;

    private final String REQUEST_HASH_KEY = "hashes";
    private final String REQUEST_DELETE_FILES_KEY = "deleteFiles";

    //TODO replace by noSql
    private ThreadLocal<String> cookie = new ThreadLocal<>();

    @Autowired
    private WebClient webClient;
    @Autowired
    private RawTorrentDataParser rawTorrentDataParser;
    @Autowired
    private OkHttpClient okHttpClient;

    public List<TorrentView> getAllDownloadingTorrents() {

        String uri = rootUri + port + allDataUri;
        try {
            String result = webClient.get()
                    .uri(uri)
                    .headers(header -> {
                        header.add("Connection", "keep-alive");
                        header.add("Cookie", cookie.get());
                    })
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

    public void pauseTorrent(String torrentHashName) {
        String uri = rootUri + port + pauseTorrentUri;
        torrentStatusAction(uri, torrentHashName);
    }

    public void resumeTorrent(String torrentHashName) {
        String uri = rootUri + port + resumeTorrentUri;
        torrentStatusAction(uri, torrentHashName);
    }

    public void downloadTorrent(@NotNull byte[] file, @NotNull String fileName, String category, String savePath) {
        login();
        String uri = rootUri + port + downloadTorrentUri;

        RequestBody body = (Objects.nonNull(category)) ?
                new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file", fileName, RequestBody.create(file))
                        .addFormDataPart("category", category)
                        .addFormDataPart("savepath", savePath)
                        .build()
                :
                new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file", fileName, RequestBody.create(file))
                        .build();

        Request request = new Request.Builder()
                .url(uri)
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", cookie.get())
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!Objects.equals(response.message(), "OK")) {
                throw new RestQbTorrentException("get error when try request to " + downloadTorrentUri + " with file " + fileName);
            }
        } catch (IOException e) {
            throw new RestQbTorrentException("get error when try request to " + downloadTorrentUri + " with file " + fileName + "cause " + e);
        }
    }

    public void loginBefore() {
        if (Objects.isNull(cookie.get())) {
            login();
        }
    }

    private void login() {
        AtomicReference<String> extractingCookie = new AtomicReference<>();
        String uri = rootUri + port + loginUri;
        try {
            webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("username", loginUserName).with("password", loginUserPassword))
                    .exchangeToMono(clientResponse -> {
                        Optional<ResponseCookie> responseCookie = Optional.ofNullable(clientResponse.cookies().getFirst("SID"));
                        responseCookie.map(value -> value.getName() + "=" + value.getValue()).ifPresent(extractingCookie::set);
                        return clientResponse.bodyToMono(String.class);
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
            this.cookie.set(extractingCookie.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + " " + e);
        }
    }

    public void deleteTorrent(String torrentHashName, boolean isNeedDeleteData) {
        String uri = rootUri + port + deleteTorrentUri;
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(REQUEST_HASH_KEY, torrentHashName);
        body.add(REQUEST_DELETE_FILES_KEY, Boolean.toString(isNeedDeleteData));
        try {
            webClient.post()
                    .uri(uri)
                    .headers(header -> {
                        header.add("Connection", "keep-alive");
                        header.add("Cookie", cookie.get());
                    })
                    .body(BodyInserters.fromFormData(body))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + uri + " " + e);
        }
    }

    private void torrentStatusAction(String uri, String torrentHashName) {
        try {
            webClient.post()
                    .uri(uri)
                    .headers(header -> {
                        header.add("Connection", "keep-alive");
                        header.add("Cookie", cookie.get());
                    })
                    .body(BodyInserters.fromFormData(REQUEST_HASH_KEY, torrentHashName))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to " + uri + " " + e);
        }
    }

}
