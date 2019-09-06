package com.lchen.funnel.router;

import com.lchen.funnel.config.FunnelProperties;
import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.http.HttpClientProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newConcurrentMap;
import static java.util.stream.Collectors.toMap;

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
    protected Map<String,RouterMappingProperties> mappingsMap;

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

    public RouterMappingProperties resolveMapping(String context, HttpServletRequest request) {
        if (shouldUpdateMappings(request)) {
            updateMappings();
        }
        return mappingsMap.get(context);
    }

    @PostConstruct
    protected synchronized void updateMappings() {
        List<RouterMappingProperties> newMappings = retrieveMappings();
        routerMappingsValidator.validate(newMappings);
        mappingsMap = Optional.ofNullable(newMappings)
                .map(mappings-> mappings.stream()
                        .collect(toMap(RouterMappingProperties::getName, a -> a)))
                .orElse(newConcurrentMap());
        httpClientProvider.updateHttpClients(newMappings);
        log.info("Destination mappings updated", newMappings);
    }

    protected abstract boolean shouldUpdateMappings(HttpServletRequest request);

    protected abstract List<RouterMappingProperties> retrieveMappings();
}
