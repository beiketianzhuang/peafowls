package com.lchen.walleapiadmin.model.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lchen
 * @date : 2019/6/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResp {
    private int status;
    private String message;
    private String errorMessage;
}
