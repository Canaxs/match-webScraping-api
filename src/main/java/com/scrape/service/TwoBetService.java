package com.scrape.service;

import com.scrape.model.Bet;

import java.io.IOException;
import java.util.List;

public interface TwoBetService {

    List<Bet> getList() throws IOException, InterruptedException;
}
