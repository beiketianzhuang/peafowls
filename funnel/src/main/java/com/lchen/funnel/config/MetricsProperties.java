package com.lchen.funnel.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lchen
 * @date : 2019/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsProperties {
    /**
     * Global metrics names prefix.
     */
    private String namesPrefix = "faraday";
}
