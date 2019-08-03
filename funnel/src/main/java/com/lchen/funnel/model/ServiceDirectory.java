package com.lchen.funnel.model;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ServiceDirectory {

    private static Map<String, Service> serviceMap;

    static {

        Map<String, Service> map = new TreeMap<>();

        Service service = Service.builder()
                .security(SecurityConstant.SEC_AUTHENTICATED)
                .restrictDev(false)
                .backendDomain("peafowls-service")
                .build();
        map.put("peafowls", service);

        serviceMap = Collections.unmodifiableMap(map);
    }

    public static Map<String, Service> getMapping() {
        return serviceMap;
    }
}
