package com.example.doesnotexist.desk_bookings_server.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "sendgrid.api")
public class SendGridProperties {
    private final String key;
    private final String host;
    private final String version;

    @ConstructorBinding
    public SendGridProperties(String key, String host, String version) {
        this.key = key;
        this.host = host;
        this.version = version;
    }
}
