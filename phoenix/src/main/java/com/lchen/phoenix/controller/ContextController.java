package com.lchen.phoenix.controller;

import com.lchen.phoenix.model.Context;
import com.lchen.phoenix.model.ContextReq;
import com.lchen.phoenix.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/4
 */
@RestController
public class ContextController {

    @Autowired
    private ContextService contextService;

    @PostMapping(value = "/contexts")
    public void addContext(@RequestBody ContextReq contextReq) {
        contextService.saveContext(contextReq);
    }

    @GetMapping(value = "/contexts")
    public List<Context> contextsPaging() {
        return contextService.listContexts();
    }

    @GetMapping(value = "/contexts/{id}")
    public Context context(@PathVariable("id") Long id) {
        return contextService.findContext(id);
    }
}
