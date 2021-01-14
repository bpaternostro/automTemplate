package com.everis.pomobile;

import com.everis.global.GlobalMobile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private AppiumDriver driver;
    private GlobalMobile global;
    public LoginPage(AppiumDriver d){
        this.driver = d;
        PageFactory.initElements(driver,this);
        global = new GlobalMobile(driver);
    }

    @FindBy(xpath = "//android.widget.EditText[@text='Usuario']")
    private WebElement userInputBox;

    @FindBy(xpath = "//android.widget.EditText[@text='Clave']")
    private WebElement passInputBox;

    @FindBy(xpath = "//android.widget.Button[@text='INGRESAR']")
    private WebElement iniciarSessionBtn;

    public Boolean executeLogin(String user, String pass){
        boolean result;
        try{
            global.isVisibleinMobile(userInputBox);
            userInputBox.sendKeys(user);
            passInputBox.sendKeys(pass);
            iniciarSessionBtn.click();
            result=true;
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo executeLogin : "+ex.getMessage());
            result=false;
        }
        return result;
    }

    public void validarSiestaElLogo(){

    }

}
