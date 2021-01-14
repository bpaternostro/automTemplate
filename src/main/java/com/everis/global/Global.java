package com.everis.global;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class Global {
    public WebDriver driver;
    public WebDriverWait wait;

    public Global(WebDriver d){
        this.driver=d;
        this.wait = new WebDriverWait(driver,30);
    }

    public void isVisibleinPage(WebElement element){
        loadPage();
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception ex){
            System.out.println("isVisibleinPage no pudo detectar el objeto: "+ex.getMessage());
        }
    }

    public void isEditable(WebElement element){
        loadPage();
        try{
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch(Exception ex){
            System.out.println("isEditable no pudo detectar el objeto: "+ex.getMessage());
        }
    }

    public void waitTillObjectHasAttr(WebElement elem,String attr,String value){
        wait.until(ExpectedConditions.attributeContains(elem,attr,value));
    }

    public void waitTillFinishLoading(){
        //wait.until(driver -> !elem.isDisplayed());
        try{
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("k-loading-mask")));
        }catch (Exception ex){
            System.out.println("No encontró el DIV que carga la pantalla");
        }

    }

    public Boolean checkIsDisplayed(WebElement elem, WebElement parent){
        Boolean check=false;
        try{
            check=elem.isDisplayed();
        }catch(Exception ex){
            check=true;
        }
        return check;
    }

    public Boolean loadPage(){
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(pageLoadCondition);
    }

    public void isVisibleinMobile(WebElement element){
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception ex){
            System.out.println("isVisibleinPage no pudo detectar el objeto: "+ex.getMessage());
        }
    }


}
