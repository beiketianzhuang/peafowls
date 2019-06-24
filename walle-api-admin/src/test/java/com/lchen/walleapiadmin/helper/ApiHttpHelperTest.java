package com.lchen.walleapiadmin.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lchen.walleapiadmin.model.ApiRunnerHistory;
import com.lchen.walleapiadmin.model.RequestTag;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiHttpHelperTest {

    @Autowired
    private ApiHttpHelper apiHttpHelper;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void call() throws Exception {
        String s = mapper.writeValueAsString(Lists.newArrayList(1));
        ApiRunnerHistory history = ApiRunnerHistory.builder()
                .requestUrl("http://localhost:7000/sqcs/message/have-read")
                .requestParams(Lists.newArrayList(RequestTag.builder().key("ids").value(s).build()))
                .requestHeaders(Lists.newArrayList(RequestTag.builder().key("Content-Type").value(ContentType.APPLICATION_JSON.toString()).build()))
                .method(HttpMethod.POST)
                .build();
        HttpRequestBase requestBase = apiHttpHelper.buildHttpRequestBase(history);
        String call = apiHttpHelper.call(requestBase);
        System.out.println(call);
    }
}