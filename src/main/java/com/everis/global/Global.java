package com.everis.global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Global {
    public WebDriver driver;

    public Global(WebDriver d){
        this.driver=d;
    }

    protected Boolean isVisibleinPage(By by){
        WebDriverWait wait = new WebDriverWait(driver,10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
    }


}
