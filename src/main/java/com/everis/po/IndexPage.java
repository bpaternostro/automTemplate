package com.everis.po;
import com.everis.global.Global;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage extends Global {

    public IndexPage(WebDriver d){
        super(d);
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "header-users-name")
    private WebElement headerUserName;

    public Boolean headerIsVisible(){
        return super.isVisibleinPage(By.id("header-users-name"));
    }

    public String getHeaderText(){
        return headerUserName.getText();
    }
}
