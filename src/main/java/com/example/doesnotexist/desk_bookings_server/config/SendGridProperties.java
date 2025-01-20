package com.example.doesnotexist.desk_bookings_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 *
 */
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
        System.out.println(key);
        System.out.println(host);
    }

    public String getKey() {
        return key;
    }

    public String getHost() {
        return host;
    }

    public String getVersion() {
        return version;
    }
}
