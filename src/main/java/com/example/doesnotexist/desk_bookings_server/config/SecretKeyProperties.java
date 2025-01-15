package com.example.doesnotexist.desk_bookings_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 *
 */
@ConfigurationProperties(prefix = "secure.sendgrid.key")
public class SecretKeyProperties {
    private final String value;

    @ConstructorBinding
    public SecretKeyProperties(String value) {
        this.value = value;
        System.out.println(value);
    }

    public String value() {
        return value;
    }
}
