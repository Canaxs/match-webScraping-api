package com.scrape.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    private String teams;
    private String ms1;
    private String ms0;
    private String ms2;

}
