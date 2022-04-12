package com.scrape.service;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scrape.converter.BetConverter;
import com.scrape.model.Bet;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OneBetServiceImpl implements OneBetService {

    private final BetConverter betConverter;


    @Override
    public List<Bet> getList() throws IOException {
        // load page using HTML Unit and fire scripts

        List<Bet> bets = new ArrayList<>();


        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
        String url = "https://www.nesine.com/iddaa?et=1&le=3&ocg=MS-2%2C5&gt=Pop%C3%BCler";
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.waitForBackgroundJavaScript(3000);


        HtmlPage myPage = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(3000);
        Document document = Jsoup.parse(myPage.asXml());

         Elements body = document.select("body div.container #content div.page-content #BultenContainer div.bultein-list-wrapper #bulten-event-list-container section");
       //  Elements body = document.select("body #__nuxt #__layout #misli-app #bultenlive-futbol div.container div.row div.col div.bulletinInside div.listWrapper #bulletinList div.bulletinRowWrapperPre");
        for (Element row:body) {
           String teams = row.select("div.odd-col div.code-time-name div.name a").text();
            String ms1 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(1)").text();
            String ms0 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(2)").text();
            String ms2 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(3)").text();
            Bet bet = betConverter.get(teams,ms1,ms0,ms2);
            bets.add(bet);
        }

        webClient.close();


        /*
        String url = "https://www.nesine.com/iddaa?et=1&le=3&ocg=MS-2%2C5&gt=Pop%C3%BCler";
        System.setProperty("phantomjs.binary.path", "libs/phantomjs");
        WebDriver ghostDriver = new PhantomJSDriver();
        try {
            ghostDriver.get(url);
             Document document = Jsoup.parse(ghostDriver.getPageSource());
             Elements body = document.select("body div.container #content div.page-content #BultenContainer div.bultein-list-wrapper #bulten-event-list-container");
            System.out.println(body);
        } finally {
            ghostDriver.quit();
        }
        */


        return bets;
    }
}
