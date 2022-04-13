package com.scrape.service;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scrape.converter.BetConverter;
import com.scrape.model.Bet;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OneBetServiceImpl extends Thread implements OneBetService {

    private final BetConverter betConverter;


    @Override
    public List<Bet> getList() throws IOException, InterruptedException {

        List<Bet> bets = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver","C:\\Users\\90553\\chromedriver2.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();


        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        String url = "https://www.nesine.com/iddaa?et=1&le=3&ocg=MS-2%2C5&gt=Pop%C3%BCler";
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);

        driver.get(url);
        Thread.sleep(3000);
        //HtmlPage myPage = webClient.getPage(url);
        //webClient.waitForBackgroundJavaScript(30*1000);
        Document document = Jsoup.parse(driver.getPageSource(),url);

         Elements body = document.select("body div.container #content div.page-content #BultenContainer div.bultein-list-wrapper #bulten-event-list-container section");
        for (Element row:body) {
            String teams = row.select("div.odd-col div.code-time-name div.name a").text();
            String ms1 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(1)").text();
            String ms0 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(2)").text();
            String ms2 = row.select("div.odd-col div.odds-content dl dd:nth-of-type(2) div.d-table div.body div.row div.cell:nth-of-type(3)").text();
            Bet bet = betConverter.get(teams,ms1,ms0,ms2);
            bets.add(bet);
        }
        driver.quit();
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
