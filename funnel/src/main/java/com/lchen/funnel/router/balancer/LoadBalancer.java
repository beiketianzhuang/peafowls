package com.lchen.funnel.router.balancer;

import java.util.List;

public interface LoadBalancer {
    String chooseDestination(List<String> destnations);
}