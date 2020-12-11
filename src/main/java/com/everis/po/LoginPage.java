package com.everis.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;
    public LoginPage(WebDriver d){
        this.driver = d;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "signInName")
    private WebElement userInputBox;

    @FindBy(id = "password")
    private WebElement passInputBox;

    @FindBy(id = "next")
    private WebElement iniciarSessionBtn;

    public void executeLogin(String user,String pass){
        userInputBox.sendKeys(user);
        passInputBox.sendKeys(pass);
        iniciarSessionBtn.click();
    }

    public void validarSiestaElLogo(){

    }

}
