package com.scrape.controller;


import com.scrape.model.Bet;
import com.scrape.service.OneBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/one")
public class OneBetController {

    @Autowired
    OneBetService oneBetService;

    @GetMapping
    private List<Bet> getAll() throws IOException {
        return oneBetService.getList();
    }

}
