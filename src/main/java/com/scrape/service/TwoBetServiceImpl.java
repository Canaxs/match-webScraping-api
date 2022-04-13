package com.scrape.service;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TwoBetServiceImpl implements TwoBetService{

    private final BetConverter betConverter;

    @Override
    public List<Bet> getList() throws IOException, InterruptedException {
        List<Bet> bets = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver","C:\\Users\\90553\\chromedriver2.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://www.iddaa.com/program/futbol?menuRoute=false&muk=1_1,2_88,2_100,2_101_2.5,2_89&m=false";

        driver.get(url);

        Document document = Jsoup.parse(driver.getPageSource(),url);

        Elements body = document.select("body #__next div.dWtzkM div.TYyyN div.bTIQQD div.idYZRo div.cEACgU");
        for (Element row:body) {
            String teams = row.select("a").text();
            String ms1 = row.select("button:nth-of-type(1)").text();
            String ms0 = row.select("button:nth-of-type(2)").text();
            String ms2 = row.select("button:nth-of-type(3)").text();
            Bet bet = betConverter.get(teams,ms1,ms0,ms2);
            bets.add(bet);
        }
        driver.quit();

        return bets;
    }
}
