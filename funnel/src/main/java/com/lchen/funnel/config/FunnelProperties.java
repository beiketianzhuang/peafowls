package com.lchen.funnel.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @author : lchen
 * @date : 2019/7/26
 */
@ConfigurationProperties("funnel")
public class FunnelProperties {
    private int filterOrder = HIGHEST_PRECEDENCE + 100;
    private boolean enableProgrammaticMapping = true;
    @NestedConfigurationProperty
    private MetricsProperties metrics = new MetricsProperties();
    @NestedConfigurationProperty
    private TracingProperties tracing = new TracingProperties();
    @NestedConfigurationProperty
    private List<RouterMappingProperties> mappings = new ArrayList<>();

    private String signingSecret;

    public int getFilterOrder() {
        return filterOrder;
    }

    public void setFilterOrder(int filterOrder) {
        this.filterOrder = filterOrder;
    }

    public MetricsProperties getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricsProperties metrics) {
        this.metrics = metrics;
    }

    public TracingProperties getTracing() {
        return tracing;
    }

    public void setTracing(TracingProperties tracing) {
        this.tracing = tracing;
    }

    public List<RouterMappingProperties> getMappings() {
        return mappings;
    }

    public void setMappings(List<RouterMappingProperties> mappings) {
        this.mappings = mappings;
    }

    public boolean isEnableProgrammaticMapping() {
        return this.enableProgrammaticMapping;
    }

    public void setEnableProgrammaticMapping(boolean enableProgrammaticMapping) {
        this.enableProgrammaticMapping = enableProgrammaticMapping;
    }

    public String getSigningSecret() {
        return signingSecret;
    }

    public void setSigningSecret(String signingSecret) {
        this.signingSecret = signingSecret;
    }
}
