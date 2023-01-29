package org.thermoweb.rpg.front.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.thermoweb.rpg.client.MicroServiceProperties;

@Getter
@Setter
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties("rpg-arena-front")
public class RpgArenaFrontProperties {

    private MicroServiceProperties webServer;
    private MicroServiceProperties encounterManager;
}
