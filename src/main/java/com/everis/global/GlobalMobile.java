package com.everis.global;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.instrument.classloading.weblogic.WebLogicLoadTimeWeaver;

import java.util.List;

public class GlobalMobile {
    public AppiumDriver driver;

    public GlobalMobile(AppiumDriver d){
        this.driver=d;
    }

    public void isVisibleinMobile(WebElement element){
        try{
            WebDriverWait wait = new WebDriverWait(driver,80);
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception ex){
            System.out.println("isVisibleinPage no pudo detectar el objeto: "+ex.getMessage());
        }
    }

    public void waitUntil(By by){
        try{
            WebDriverWait wait = new WebDriverWait(driver,80);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch(Exception ex){
            System.out.println("waitUntil no pudo detectar el objeto: "+ex.getMessage());
        }
    }

    public void seleccionarValorLista(WebElement combo,String valor){
        boolean result;
        try{

            combo.click();
            By by = By.xpath("//android.widget.TextView[@text='"+valor+"']");
            waitUntil(by);
            driver.findElement(by).click();

        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo seleccionarValorLista: "+ex.getMessage());
            result=false;
        }

    }

}
