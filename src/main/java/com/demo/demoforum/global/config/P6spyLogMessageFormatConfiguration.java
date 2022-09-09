package com.demo.demoforum.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("!prod")
public class P6spyLogMessageFormatConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance()
                .setLogMessageFormat(P6spySqlFormatConfiguration.class.getName());
    }
}