package com.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;

public class Page {
    protected ChromeDriver driver;
    protected JavascriptExecutor executor;

    public Page(ChromeDriver driver){
        this.driver = driver;
        executor = (JavascriptExecutor)driver;
    }

}
