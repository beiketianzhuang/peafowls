package com.lchen.funnel.config;

import com.lchen.funnel.filter.RequestDataExtractor;
import com.lchen.funnel.filter.RequestForwarder;
import com.lchen.funnel.filter.ReverseProxyFilter;
import com.lchen.funnel.http.HttpClientProvider;
import com.lchen.funnel.interceptor.AuthRequestInterceptor;
import com.lchen.funnel.interceptor.CacheResponseInterceptor;
import com.lchen.funnel.interceptor.PostForwardResponseInterceptor;
import com.lchen.funnel.interceptor.PreForwardRequestInterceptor;
import com.lchen.funnel.router.ConfigurationMappingsProvider;
import com.lchen.funnel.router.RouterMappingProvider;
import com.lchen.funnel.router.RouterMappingsValidator;
import com.lchen.funnel.router.balancer.LoadBalancer;
import com.lchen.funnel.router.balancer.RandomLoadBalancer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

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

    @Bean
    @ConditionalOnMissingBean
    public ReverseProxyFilter faradayReverseProxyFilter(
            RequestDataExtractor extractor,
            RouterMappingProvider mappingsProvider,
            RequestForwarder requestForwarder,
            PreForwardRequestInterceptor requestInterceptor
    ) {
        return new ReverseProxyFilter(funnelProperties, extractor, mappingsProvider,
                requestForwarder, requestInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestForwarder faradayRequestForwarder(
            HttpClientProvider httpClientProvider,
            RouterMappingProvider mappingsProvider,
            LoadBalancer loadBalancer,
            Optional<MeterRegistry> meterRegistry,
            PostForwardResponseInterceptor responseInterceptor
    ) {
        return new RequestForwarder(
                serverProperties, funnelProperties, httpClientProvider,
                mappingsProvider, loadBalancer, meterRegistry, responseInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer faradayLoadBalancer() {
        return new RandomLoadBalancer();
    }

    @Bean
    @ConditionalOnMissingBean
    public PostForwardResponseInterceptor faradayPostForwardResponseInterceptor() {
        return new CacheResponseInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public PreForwardRequestInterceptor faradayPreForwardRequestInterceptor(EnvConfig envConfig) {
        return new AuthRequestInterceptor(funnelProperties.getSigningSecret(), envConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestDataExtractor faradayRequestDataExtractor() {
        return new RequestDataExtractor();
    }

}
