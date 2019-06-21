package com.lchen.walleapiadmin.controller;

import com.lchen.walleapiadmin.model.req.ApiDocumentReq;
import com.lchen.walleapiadmin.model.resp.ApiDocumentResp;
import com.lchen.walleapiadmin.service.ApiDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/21
 */
@RestController
public class ApiDocumentController {

    @Autowired
    private ApiDocumentService apiDocumentService;


    @GetMapping("/api/document")
    public Mono<List<ApiDocumentResp>> listApiDocumentsPaging(@RequestParam("contextId") Long contextId,
                                                              @RequestParam("groupId") Long groupId,
                                                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        apiDocumentService.listApiDocumentsPaging(contextId, groupId, pageNum, pageSize);
        return Mono.create(a -> a.success(apiDocumentService.listApiDocumentsPaging(contextId, groupId, pageNum, pageSize)));
    }

    @DeleteMapping("/api/document/{id}")
    public void deleteApiDocumentById(@PathVariable("id") Long id) {

    }

    @PostMapping("/api/document")
    public void addApiDocument(@RequestBody ApiDocumentReq apiDocumentReq) {

    }
}
