package com.lchen.funnel.config;

import com.lchen.funnel.http.HttpClientProvider;
import com.lchen.funnel.router.ConfigurationMappingsProvider;
import com.lchen.funnel.router.RouterMappingProvider;
import com.lchen.funnel.router.RouterMappingsValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lchen
 * @date : 2019/7/26
 */
@EnableConfigurationProperties({FunnelProperties.class})

@Configuration
public class FunnelConfiguration {
    protected final FunnelProperties funnelProperties;
    protected final ServerProperties serverProperties;

    @Value("${spring.profiles.active:NA}")
    private String activeProfile;

    public FunnelConfiguration(FunnelProperties funnelProperties,
                               ServerProperties serverProperties) {
        this.funnelProperties = funnelProperties;
        this.serverProperties = serverProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public RouterMappingsValidator faradayMappingsValidator() {
        return new RouterMappingsValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClientProvider faradayHttpClientProvider() {
        return new HttpClientProvider();
    }

    @Bean
    public EnvConfig envConfig() {
        return EnvConfig.getEnvConfg(activeProfile);
    }

    @Bean
    @ConditionalOnMissingBean
    public RouterMappingProvider faradayConfigurationMappingsProvider(EnvConfig envConfig,
                                                                      RouterMappingsValidator mappingsValidator,
                                                                      HttpClientProvider httpClientProvider) {
        return new ConfigurationMappingsProvider(
                serverProperties,
                funnelProperties, mappingsValidator,
                httpClientProvider);
    }
}
