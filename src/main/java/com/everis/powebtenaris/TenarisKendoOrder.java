package com.everis.powebtenaris;
import com.everis.global.Global;
import com.everis.global.Td;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class TenarisKendoOrder {
    WebDriver driver;
    Global glo;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public TenarisKendoOrder(WebDriver d){
        glo = new Global(d);
        PageFactory.initElements(d,this);
        this.driver=d;
    }

    @FindBy(className = "k-loading-mask")
    private WebElement loading;

    @FindBy(xpath = "//*[@id='HeaderTab_header_h2']")
    private WebElement headerLink;

    @FindBy(xpath = "//*[@id='tab_16_header_h2']")
    private WebElement productLink;

    @FindBy(id = "contentIFrame0")
    private WebElement iframe0;

    @FindBy(id = "IFRAME_OfferProductDataKendo")
    private WebElement iframe;

    //@FindBy(xpath = "//div[contains(@class, 'k-grid-content k-auto-scrollable')]")
    @FindBy(xpath = "//div[@class='k-grid-content k-auto-scrollable']")

    private WebElement gridContentDiv;

    @FindBy(className = "k-grid-content-locked")
    private WebElement gridContentLockedDiv;

    @FindBy(id = "btnNew")
    private WebElement addNewProductButton;

    @FindBy(id = "btnsaveItems")
    private WebElement saveButton;

    @FindBy(id = "btncopy")
    private WebElement copyProductButton;

    @FindBy(id = "btndelete")
    private WebElement borrarButton;

    @FindBy(id = "inpCopy")
    private WebElement copyVecesInput;

    @FindBy(xpath = "//button[@class='alert-js-RefreshDialog-Button alert-js-RefreshDialog-Button-focus']")
    private WebElement okPopUpButton;

    @FindBy(className = "alert-js-background")
    private WebElement alertBackground;

    public void goToProducts(){
        try{

            Thread.sleep(3000);
            driver.switchTo().frame(iframe0);

            glo.isVisibleinPage(headerLink);
            headerLink.click();

            glo.isVisibleinPage(productLink);
            productLink.click();

        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo goToProductos "+ex.getMessage());
        }
    }

    private WebElement getLastRow(){
        WebElement lastRow = null;

        glo.isVisibleinPage(gridContentLockedDiv);

        try{
            //List<WebElement> trLocked= gridContentLockedDiv.findElements(By.tagName("tr"));
            List<WebElement> trContent= gridContentDiv.findElements(By.tagName("tr"));
            lastRow = trContent.get(trContent.size()-1);

        }catch(Exception ex){
            System.out.println("Existieron inconvenientes obteniendo los datos de la grilla en getValues "+ex.getMessage());
        }

        return lastRow;
    }

    private WebElement getLastRowLocked(){
        WebElement lastRow = null;

        glo.isVisibleinPage(gridContentLockedDiv);

        try{
            List<WebElement> trLocked= gridContentLockedDiv.findElements(By.tagName("tr"));
            lastRow = trLocked.get(trLocked.size()-1);

        }catch(Exception ex){
            System.out.println("Existieron inconvenientes obteniendo los datos de la grilla en getValues "+ex.getMessage());
        }

        return lastRow;
    }

    public Boolean addProduct(String[] val) throws InterruptedException {
        Boolean result = false;
        boolean flag = true;
        driver.switchTo().frame(iframe);
        Thread.sleep(5000);
        glo.isEditable(addNewProductButton);
        addNewProductButton.click();

        List<WebElement> tds = null;
        List<Td> tdProcess = null;
        int initValue=0;

        do{
            //+2 para evitar las 2 primeras columnas
            tds=getTds(initValue + 2);
            tdProcess=createTDMaps(tds,val,initValue);
            tdProcess=loadTd(tdProcess);
            tdProcess.removeIf(td -> (td.getWasFilled()));

            try{
                initValue=tdProcess.get(0).getIndex();
            }catch (Exception ex){
                flag = false;
            }

        }while(flag);

        saveButton.click();
        glo.waitTillFinishLoading();

        try{
            loading.isDisplayed();
        }catch (Exception ex){
            result=true;
        }

        //result = okPopUp();
        return result;

    }

    private List<WebElement> getTds(int initValue){
        WebElement lastRow = getLastRow();
        List<WebElement> tds= lastRow.findElements(By.tagName("td"));
        tds.removeIf(td -> (td.getAttribute("style").contains("display")));
        List<WebElement> tdsFinal=tds.subList(initValue,tds.size());
        return tdsFinal;
    }

    public Boolean validarProducto(){
        Boolean result=false;
        try{

            WebElement validButton = getValidateRowButton();
            validButton.click();
            glo.waitTillFinishLoading();

            validButton = getValidateRowButton();
            WebElement span = validButton.findElement(By.tagName("span"));
            String spanStyle = span.getAttribute("style");

            WebElement lastRow = getLastRow();
            String spansoCode=getSpansoCode(lastRow);

            if(spanStyle.equals("color: rgb(0, 190, 0);") && spansoCode!=null){
                result=true;
            }

        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: validarProducto. "+ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

    public Boolean seleccionarUltimoProducto(){
        Boolean result;
        try{
            WebElement lastRow=getLastRowLocked();
            WebElement checkBox=lastRow.findElement(By.className("k-checkbox"));
            do{
                Thread.sleep(2000);
                Actions act= new Actions(driver);
                act.moveToElement(checkBox).click().build().perform();
            }while(checkBox.getAttribute("aria-checked").equals("false"));

            result=checkBox.getAttribute("aria-checked").equals("true");

        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: validarProducto. "+ex.getMessage());
            result=false;
        }

        return result;
    }

    public Boolean copiarProducto(int veces){
        Boolean result;
        try {
            copyVecesInput.clear();
            copyVecesInput.sendKeys(String.valueOf(veces));
            copyProductButton.click();
            glo.waitTillFinishLoading();
            result=true;
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: copiarRegistro. "+ex.getMessage());
            result=false;
        }
        return result;
    }

    public Boolean validarSpansoInRows(int veces){
        Boolean result;
        try {
            List<WebElement> trs= gridContentDiv.findElements(By.tagName("tr"));
            //Integer rows=trLocked.size();
            trs.removeIf(tr -> (getSpansoCode(tr).isEmpty()));
            if(trs.size()==veces){
                result=true;
            }else{
                result=false;
            }
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: validarSpansoInRows. "+ex.getMessage());
            result=false;
        }
        return result;
    }

    public Boolean borrarRegistro(){
        Boolean result = null;
        try {
            int actualRows = gridContentDiv.findElements(By.tagName("tr")).size();
            seleccionarUltimoProducto();
            borrarButton.click();
            //apruebo el popUP
            approvePopUp();

            //valido que se haya realizo el borrado del registro
            int afterRows = gridContentDiv.findElements(By.tagName("tr")).size();
            result = (actualRows == afterRows + 1);

        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: borrarRegistro. "+ex.getMessage());
            result=false;
        }
        return result;
    }

    private String getSpansoCode(WebElement row){
        String spanso = null;
        try {
            List<WebElement> tds= row.findElements(By.tagName("td"));
            spanso=tds.get(tds.size()-1).getText();
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: getSpansoCode. "+ex.getMessage());
            ex.printStackTrace();
        }
        return spanso;
    }

    private WebElement getValidateRowButton(){
        WebElement validButton = null;
        try{
            WebElement lastRowLocked=getLastRowLocked();
            validButton=lastRowLocked.findElement(By.className("validateThisRow"));
            glo.isEditable(validButton);
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo: getValidateRowButton. "+ex.getMessage());
        }

        return validButton;
    }

    private Boolean approvePopUp(){
        Boolean result=true;
        try {
            driver.switchTo().defaultContent();
            WebElement yesButton = driver.findElements(By.xpath("//button[@class='alert-js-RefreshDialog-Button']")).get(0);
            yesButton.click();
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo approvePopUp: "+ex.getMessage());
            result=false;
        }
        driver.switchTo().frame(iframe);
        return result;
    }

    private Boolean okPopUp(){
        Boolean result=true;
        try {

            driver.switchTo().defaultContent();
            if(alertBackground.isDisplayed()){
                okPopUpButton.click();
                result=false;
            }
        }catch (Exception ex){
            System.out.println("No se desplego el popup de campos faltantes: "+ex.getMessage());
        }

        driver.switchTo().frame(iframe);
        return result;
    }

    private List<Td> createTDMaps(List<WebElement> tds, String[] val, int initValue){
        int i = initValue;
        List<Td> finalTd = new ArrayList<>();

        for(WebElement td:tds){
            try{
                Td tdObject = new Td(td, i, val[i], false);
                finalTd.add(tdObject);
            }catch (Exception ex){

            }

            i++;
        }
        return finalTd;
    }

    public List<Td> loadTd(List<Td> map) {

        String val;
        WebElement input = null;
        WebElement td = null;
        WebElement select = null;
        //JavascriptExecutor executor = (JavascriptExecutor) driver;

        for(Td tdObject: map){

            td = tdObject.getObject();
            val= tdObject.getValue();

            try {

                if(!val.equals("")){

                    Boolean flag=false;
                    //intento hacerle click
                    td.click();
                    Thread.sleep(1000);

                    //primero trato de llenar un input
                    try {
                        List<WebElement> inputs = td.findElements(By.tagName("input"));
                        inputs.removeIf(in -> (in.getAttribute("style").contains("display: none;")));
                        input = inputs.get(0);
                        if (!input.getAttribute("value").isEmpty()) {
                            input.sendKeys(Keys.CONTROL, "a");
                            input.sendKeys(Keys.DELETE);
                        }

                        Thread.sleep(1000);
                        input.sendKeys(val);

                        if (!input.getAttribute("class").contains("k-textbox")) {
                            WebElement li = getLi(val);
                            if (li != null) {
                                li.click();
                            }
                        }

                    } catch (Exception ex) {
                        //System.out.println("No existe ningun input en esta columna");
                        flag = true;
                    }

                    if (flag) {

                        try {
                            Select dropDown = new Select(td.findElement(By.tagName("select")));
                            js.executeScript("var select = arguments[0]; for(var i = 0; i < select.options.length; i++){ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }", dropDown, val);
                        } catch (Exception ex) {
                            //System.out.println("No existe ningun select en esta columna");
                        }
                    }

                }

                tdObject.setWasFilled(true);

            } catch (Exception ex) {
                //System.out.println("Falló al hacer click en la celda: " + ex.getMessage());
                ex.getStackTrace();
            }

        }

        return map;

    }

    private WebElement getLi(String val){
        WebElement ul = null;
        WebElement liFinal = null;
        try{
            ul=driver.findElement(By.id("autocompleteeditableinput_listbox"));
            List<WebElement> lis = ul.findElements(By.tagName("li"));
            for(WebElement li:lis){
                if(li.getText().equals(val)){
                    liFinal = li;
                }
            }
        }catch(Exception ex){
            //System.out.println("Existieron inconvenientes en el metodo getLi: "+ex.getMessage());
            //ex.getStackTrace();
        }
        return liFinal;
    }

}
