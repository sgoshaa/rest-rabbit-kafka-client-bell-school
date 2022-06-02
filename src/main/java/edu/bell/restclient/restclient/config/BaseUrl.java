package edu.bell.restclient.restclient.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Getter
public class BaseUrl {
    @Value("${hostAddress}")
    private String url;

    public String getUrl() {
        return "http://" + url;
    }
}
