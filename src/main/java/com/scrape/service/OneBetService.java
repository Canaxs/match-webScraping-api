package com.scrape.service;

import com.scrape.model.Bet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface OneBetService {

    List<Bet> getList() throws IOException;

}
