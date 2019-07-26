package com.lchen.funnel.router;

import com.lchen.funnel.config.FunnelProperties;
import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.http.HttpClientProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : lchen
 * @date : 2019/7/26
 */
public class ConfigurationMappingsProvider extends RouterMappingProvider {

    public ConfigurationMappingsProvider(
            ServerProperties serverProperties,
            FunnelProperties funnelProperties,
            RouterMappingsValidator mappingsValidator,
            HttpClientProvider httpClientProvider
    ) {
        super(serverProperties, funnelProperties,
                mappingsValidator, httpClientProvider);
    }


    @Override
    protected boolean shouldUpdateMappings(HttpServletRequest request) {
        return false;
    }

    @Override
    protected List<RouterMappingProperties> retrieveMappings() {
        return funnelProperties.getMappings();
    }
}
