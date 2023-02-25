package home.app.configuration;

import home.app.utils.converters.TorrentDataViewToTorrentConverter;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public WebClient webClient() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(context -> context.sslContext(sslContext));
        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .clientConnector(httpConnector)
                .build();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TorrentDataViewToTorrentConverter());
    }

}
