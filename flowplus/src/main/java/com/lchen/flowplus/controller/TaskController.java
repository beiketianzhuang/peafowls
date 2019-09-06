package com.lchen.flowplus.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @PostMapping(value = "/")
    public void startTask() {

    }
}
