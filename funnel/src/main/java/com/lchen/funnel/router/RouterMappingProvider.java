package com.lchen.funnel.router;

import com.lchen.funnel.config.FunnelProperties;
import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.http.HttpClientProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author : lchen
 * @date : 2019/7/26
 */
@Slf4j
public abstract class RouterMappingProvider {

    protected final ServerProperties serverProperties;
    protected final FunnelProperties funnelProperties;
    protected final RouterMappingsValidator routerMappingsValidator;
    protected final HttpClientProvider httpClientProvider;
    protected List<RouterMappingProperties> mappings;

    public RouterMappingProvider(
            ServerProperties serverProperties,
            FunnelProperties funnelProperties,
            RouterMappingsValidator routerMappingsValidator,
            HttpClientProvider httpClientProvider
    ) {
        this.serverProperties = serverProperties;
        this.funnelProperties = funnelProperties;
        this.routerMappingsValidator = routerMappingsValidator;
        this.httpClientProvider = httpClientProvider;
    }

    public RouterMappingProperties resolveMapping(String originHost, HttpServletRequest request) {
        if (shouldUpdateMappings(request)) {
            updateMappings();
        }
        List<RouterMappingProperties> resolvedMappings = mappings.stream()
                .filter(mapping -> originHost.toLowerCase().equals(mapping.getHost().toLowerCase()))
                .collect(Collectors.toList());
        if (isEmpty(resolvedMappings)) {
            return null;
        }
        return resolvedMappings.get(0);
    }

    @PostConstruct
    protected synchronized void updateMappings() {
        List<RouterMappingProperties> newMappings = retrieveMappings();
        routerMappingsValidator.validate(newMappings);
        mappings = newMappings;
        httpClientProvider.updateHttpClients(mappings);
        log.info("Destination mappings updated", mappings);
    }

    protected abstract boolean shouldUpdateMappings(HttpServletRequest request);

    protected abstract List<RouterMappingProperties> retrieveMappings();
}
