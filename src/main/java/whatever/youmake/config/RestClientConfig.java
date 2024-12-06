package whatever.youmake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {
    private static final String BASE_URL = "https://proma-ai.store"; // base URL 설정

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }


}
