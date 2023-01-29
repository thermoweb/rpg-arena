package org.thermoweb.rpg.rest.config;

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
@ConfigurationProperties("rpg-web")
public class RpgArenaWebProperties {

    private MicroServiceProperties encounterManager;
}
