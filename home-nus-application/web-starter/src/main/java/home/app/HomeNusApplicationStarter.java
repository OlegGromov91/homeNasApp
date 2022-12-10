package home.app;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@SpringBootApplication(scanBasePackages = "home.app")
public class HomeNusApplicationStarter implements CommandLineRunner {

    public static void main(String[] args) throws SSLException {
        SpringApplication.run(HomeNusApplicationStarter.class, args);

      String s =  webClient().get().uri("")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                })
                .block();

        JSONObject jsonObj = new JSONObject(s);

        System.out.println(jsonObj);
        System.out.println(jsonObj);
        System.out.println(jsonObj);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public static WebClient webClient() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(context -> context.sslContext(sslContext));
        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .clientConnector(httpConnector)
                .build();
    }
}
