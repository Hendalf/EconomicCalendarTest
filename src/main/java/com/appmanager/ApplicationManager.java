package com.appmanager;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    ChromeDriver driver;


    public ApplicationManager() {
        properties = new Properties();
    }

    public ChromeDriver init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chromeDriver"));
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("User-Agent="+properties.getProperty("user.agent"));
        return driver;
    }

    public void stop() {
        driver.quit();
    }
}
