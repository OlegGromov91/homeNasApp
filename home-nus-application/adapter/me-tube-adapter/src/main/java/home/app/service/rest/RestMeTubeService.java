package home.app.service.rest;

import home.app.exceptions.RestMeTubeException;
import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class RestMeTubeService {

    @Value("${home-application.uri.root}")
    private String rootUri;
    @Value("${home-application.meTube.port}")
    private String port;
    @Value("${home-application.meTube.quality}")
    private String quality;
    @Value("${home-application.meTube.uri.addVideoUri}")
    private String addVideoUri;
    @Value("${home-application.q-bit-torrent.request-timeout}")
    private Long requestTimeout;

    @Autowired
    private WebClient webClient;

    public void downloadVideo(@NonNull String url, @NonNull String format) {
        String uri = rootUri + port + addVideoUri;
        JSONObject body = new JSONObject(Map.of(
                "url", url,
                "quality", quality,
                "format", format
        ));
        try {
            webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromValue(body.toString()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestMeTubeException("get error when try request to " + uri + " cause " + e);
        }
    }


}
