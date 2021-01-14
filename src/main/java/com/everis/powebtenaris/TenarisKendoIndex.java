package com.everis.powebtenaris;
import com.everis.global.Global;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class TenarisKendoIndex {
    WebDriver driver;
    Global glo;

    public TenarisKendoIndex(WebDriver d){
        glo = new Global(d);
        PageFactory.initElements(d,this);
        this.driver=d;
    }

    @FindBy(id = "contentIFrame0")
    private WebElement iframe;

    @FindBy(id = "dashboardSelectorLink")
    private WebElement dashBoardHeader;

    @FindBy(id = "Componentdebf58b_findCriteria")
    private WebElement orderEdit;

    @FindBy(id = "Componentdebf58b_findCriteriaButton")
    private WebElement searchOrder;

    @FindBy(id = "gridBodyTable")
    private WebElement grillaOffersResultados;

    public Boolean searchOrder(String order) throws InterruptedException {
        Boolean result=false;
        try{

            driver.switchTo().frame(iframe);
            glo.isVisibleinPage(dashBoardHeader);
            orderEdit.sendKeys(order);
            searchOrder.click();
            result=selectOrderInGrilla(order);
            driver.switchTo().defaultContent();

        }catch(Exception ex){
            System.out.println("Existieron inconvenientes en el metodo searchOrder "+ex.getMessage());
        }


        return result;
    }

    public Boolean selectOrderInGrilla(String order){
        Boolean result=false;
        List<WebElement> tr = grillaOffersResultados.findElements(By.className("ms-crm-List-Row-Lite"));

        if(tr.size()!=0){
             for(WebElement elem:tr){

                 try{
                     elem.findElement(By.linkText(order)).click();
                 }catch (Exception ex){
                     System.out.println("No se encuentra la orden en la fila.");
                 }

             }
             result=true;

        }else{
            System.out.println("No fue posible encontrar ningun registro con ese numero de orden.");
        }

        return result;
    }

}
