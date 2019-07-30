package com.lchen.funnel.dao.model;

import com.lchen.funnel.config.RouterMappingProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    private String host;
    private List<String> ips;
    private String user;
    private Integer connect = 2000;
    private int read = 20000;

    public RouterMappingProperties copy() {
        RouterMappingProperties properties = new RouterMappingProperties();
        properties.setName(context);
        properties.setDestinations(ips);
        properties.setHost(host);
        RouterMappingProperties.TimeoutProperties timeoutProperties = new RouterMappingProperties.TimeoutProperties();
        timeoutProperties.setConnect(connect);
        timeoutProperties.setRead(read);
        properties.setTimeout(timeoutProperties);
        return properties;
    }

}
