package com.everis.powebtenaris;
import com.everis.global.Global;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TenarisKendoMain {
    WebDriver driver;
    Global glo;

    public TenarisKendoMain(WebDriver d){
        glo = new Global(d);
        PageFactory.initElements(d,this);
        this.driver=d;
    }

    @FindBy(xpath = "//*[@id='ccr_SalesApp']")
    private WebElement salesLink;

    @FindBy(id = "Tabnav_app_modules-main")
    private WebElement myAppNav;

    @FindBy(id = "contentIFrame0")
    private WebElement iframe;


    public void clickSalesLink() throws InterruptedException {
        glo.isVisibleinPage(myAppNav);
        driver.switchTo().frame(iframe);
        salesLink.click();
        driver.switchTo().defaultContent();
    }


}
