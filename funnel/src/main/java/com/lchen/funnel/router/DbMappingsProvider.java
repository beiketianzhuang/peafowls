package com.lchen.funnel.router;

import com.google.common.collect.Lists;
import com.lchen.funnel.config.FunnelProperties;
import com.lchen.funnel.config.RouterMappingProperties;
import com.lchen.funnel.dao.RouterMappingRepository;
import com.lchen.funnel.dao.model.RouterMapping;
import com.lchen.funnel.http.HttpClientProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * @author : lchen
 * @date : 2019/7/30
 */
public class DbMappingsProvider extends RouterMappingProvider {

    private RouterMappingRepository routerMappingRepository;

    public DbMappingsProvider(ServerProperties serverProperties,
                              FunnelProperties funnelProperties,
                              RouterMappingsValidator routerMappingsValidator,
                              HttpClientProvider httpClientProvider) {
        super(serverProperties, funnelProperties, routerMappingsValidator, httpClientProvider);
    }

    @Override
    protected boolean shouldUpdateMappings(HttpServletRequest request) {
        return true;
    }

    @Override
    protected List<RouterMappingProperties> retrieveMappings() {
//        List<RouterMapping> routerMappings = routerMappingRepository.findAll();
//        if (isNotEmpty(routerMappings)) {
//            return routerMappings.stream().map(RouterMapping::copy).collect(toList());
//        }
        return Lists.newArrayList();
    }

    public void setRouterMappingRepository(RouterMappingRepository routerMappingRepository) {
        this.routerMappingRepository = routerMappingRepository;
    }
}
