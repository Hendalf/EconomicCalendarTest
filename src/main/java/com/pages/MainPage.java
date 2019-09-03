package com.pages;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class MainPage extends Page{
    private String[] economicCalendarFilterPeriodList;
    private String[] economicCalendarFilterImportanceList;
    private String[] economicCalendarFilterCurrencyList;
    private SelenideElement economicCalendarFilterPeriods;
    private SelenideElement economicCalendarFilterImportances;
    private SelenideElement economicCalendarFilterCurrencies;
    private ElementsCollection economicCalendarTableItems;



    public MainPage(ChromeDriver driver){
        super(driver);
        economicCalendarFilterPeriodList = new String[]{"Current week","Previous week","Next week","Current month","Previous month","Next month"};
        economicCalendarFilterImportanceList = new String[]{"Holidays","Low","Medium","High"};
        economicCalendarFilterCurrencyList = new String[]{"USD - US dollar","EUR - Euro","JPY - Japanese yen","GBP - Pound sterling",
        "CAD - Canadian dollar","AUD - Australian Dollar","CHF - Swiss frank","CNY - Chinese yuan","NZD - New Zealand dollar",
        "BRL - Brazilian real","KRW - South Korean won","HKD - Hong Kong dollar","SGD - Singapore dollar","MXN - Mexican peso"};
        economicCalendarFilterPeriods = $("#economicCalendarFilterDate");
        economicCalendarFilterImportances = $("#economicCalendarFilterImportance");
        economicCalendarFilterCurrencies = $("#economicCalendarFilterCurrency");
    }

    public void filterByPeriod(String period){
        economicCalendarFilterPeriods.$(byText(period)).click();
    }

    public void filterByImportance(String importance){
        for (String calendarImportance:economicCalendarFilterImportanceList) {
            if(!calendarImportance.equals(importance)){
                economicCalendarFilterImportances.$(byText(calendarImportance)).click();
            }
        }
    }

    public void filterByCurrency(String currency){
        for (String calendarCurrency:economicCalendarFilterCurrencyList) {
            if(!calendarCurrency.equals(currency)){
                WebElement ele = economicCalendarFilterCurrencies.$(byText(calendarCurrency));
                //I have use JavaScript because get ElementClickInterceptedException
                executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", ele);
            }
        }
    }

    public String openFirstEventWithCHFCurrency(){
        executor.executeScript("window.scrollBy(0, -250)", "");

        economicCalendarTableItems = $("#economicCalendarTable").$$(byClassName("ec-table__item"));

        for(SelenideElement item:economicCalendarTableItems){
            if(item.find(byClassName("ec-table__curency-name")).shouldHave(text("CHF")).exists()){
                item.$("a").click();
                break;
            }
        }
        return driver.getCurrentUrl();
    }
}
