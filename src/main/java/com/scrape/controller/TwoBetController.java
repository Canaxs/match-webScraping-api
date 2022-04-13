package com.scrape.controller;


import com.scrape.model.Bet;
import com.scrape.service.TwoBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/two")
public class TwoBetController {

    @Autowired
    TwoBetService twoBetService;

    @GetMapping
    public List<Bet> getBets() throws IOException, InterruptedException{
        return twoBetService.getList();
    }
}
