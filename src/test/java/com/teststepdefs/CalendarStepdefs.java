package com.teststepdefs;

import com.restapirequest.LongToShortUrlRequest;
import com.appmanager.ApplicationManager;
import com.codeborne.selenide.WebDriverRunner;
import com.pages.EventPage;
import com.pages.MainPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.*;

public class CalendarStepdefs {
    ChromeDriver driver;
    ApplicationManager app;
    private static final Logger log = Logger.getLogger(CalendarStepdefs.class);
    MainPage mainPage;
    EventPage eventPage;

    public CalendarStepdefs(){
        app = new ApplicationManager();
        try {
            driver = app.init();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        WebDriverRunner.setWebDriver(driver);
        mainPage = new MainPage(driver);
        eventPage = new EventPage(driver);
    }

    @Given("User tries to open economic calendar with link {string}")
    public void userTriesToOpenEconomicCalendarWithLink(String link) throws Throwable {
        log.info("User tries to open economic calendar with link "+link);
        open(link);
        driver.manage().window().maximize();
    }

    @When("I filter calendar by {string} period and {string} importance and {string} currency")
    public void iFilterCalendarByPeriodAndImportanceAndCurrency(String period, String importance, String currency) throws Throwable{
        log.info("I filter calendar by "+period+" period and "+importance+" importance and "+currency+" currency");
        //filter by period
        mainPage.filterByPeriod(period);

        //filter by importance - uncheck all importances except Medium
        mainPage.filterByImportance(importance);

        //filter by currency - uncheck all currencies except CHF - Swiss frank
        mainPage.filterByCurrency(currency);
    }

    @Then("I open first event with {string} currency")
    public void iOpenFirstEventWithCurrency(String currency) throws Throwable{
        log.info("I open first event with "+currency+" currency");
        LongToShortUrlRequest.longUrl = mainPage.openFirstEventWithCHFCurrency();
    }

    @And("I check that  {string} importance and {string} country of event appears")
    public void iCheckThatImportanceAndCountryOfEventAppears(String importance, String country) throws Throwable{
        log.info("I check that  "+importance+" importance and "+country+" country of event appears");
        eventPage.checkPriorityAndCountry(importance, country);
    }

    @Then("I log history of event for {int} months to log file as table")
    public void iLogHistoryOfEventForMonthToLogFileAsTable(int months) {
        log.info("I log history of event for "+months+" months to log file as table");
        eventPage.logEventHistory(months);
        app.stop();
    }
}
