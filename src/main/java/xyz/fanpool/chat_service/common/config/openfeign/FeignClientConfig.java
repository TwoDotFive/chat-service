package xyz.fanpool.chat_service.common.config.openfeign;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableFeignClients
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${service-token}") String serviceToken) {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + serviceToken);
    }

}
