package com.pages;

import com.DataModel.EventHistoryData;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class EventPage extends Page{
    private SelenideElement eventPriority;
    private SelenideElement eventCountry;
    private SelenideElement historyTab;
    private ElementsCollection eventHistoryTable;
    private static final Logger log = Logger.getLogger(EventPage.class);

    public EventPage(ChromeDriver driver){
        super(driver);
    }

    public void checkPriorityAndCountry(String importance, String country){
        eventPriority = $(byClassName("event-table__importance"));
        eventPriority.shouldHave(text(importance));

        eventCountry = $(byClassName("ec-event__header__content")).$("a");
        eventCountry.shouldHave(text(country));
    }

    public void logEventHistory(int months){
        //go to History tab
        historyTab = $(byClassName("tab__menu")).$$(byClassName("item")).get(2);
        historyTab.click();

        eventHistoryTable = $$(byClassName("event-table-history__item"));
        log.info("| Date | Actual | Forecast | Previous |");
        for(int i =0; i < months; i++){
            EventHistoryData data;
            data = new EventHistoryData();
            data.setDate(eventHistoryTable.get(i).find(byClassName("event-table-history__date")).find(byCssSelector("span")).getText());
            data.setActual(eventHistoryTable.get(i).find(byClassName("event-table-history__actual")).find(byCssSelector("span")).getText());
            data.setForecast(eventHistoryTable.get(i).find(byClassName("event-table-history__forecast")).getText());
            data.setPrevious(eventHistoryTable.get(i).find(byClassName("event-table-history__previous")).find(byCssSelector("span")).getText());
            log.info("|"+data.getDate()+"|"+data.getActual()+"|"+data.getForecast()+"|"+data.getPrevious()+"|");
        }
    }
}
