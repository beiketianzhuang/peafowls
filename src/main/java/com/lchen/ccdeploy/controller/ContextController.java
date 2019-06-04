package com.lchen.ccdeploy.controller;

import com.lchen.ccdeploy.model.ContextReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lchen
 * @date : 2019/6/4
 */
@RestController
public class ContextController {

    @PostMapping(value = "/contexts")
    public void addContext(@RequestBody ContextReq contextReq) {
        System.out.println(contextReq);
    }
}
