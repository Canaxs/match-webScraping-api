package com.scrape.converter;

import com.scrape.model.Bet;
import org.springframework.stereotype.Component;

@Component
public class BetConverter {

    public Bet get(String teams,String ms1,String ms0,String ms2) {
        return  Bet.builder()
                .teams(teams)
                .ms1(ms1)
                .ms0(ms0)
                .ms2(ms2)
                .build();
    }
}
