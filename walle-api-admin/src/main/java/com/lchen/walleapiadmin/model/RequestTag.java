package com.lchen.walleapiadmin.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@Data
@Builder
public class RequestTag {
    private String key;
    private String value;
    private String description;
}
