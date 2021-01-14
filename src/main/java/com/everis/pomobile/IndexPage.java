package com.everis.pomobile;
import com.everis.global.GlobalMobile;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage{
    AppiumDriver driver;
    private GlobalMobile global;

    public IndexPage(AppiumDriver d){
        PageFactory.initElements(d,this);
        this.driver=d;
        global = new GlobalMobile(driver);
    }

    @FindBy( xpath = "//android.widget.EditText[@text='Gerencia Regional']")
    private WebElement gerenciaRegionalList;

    @FindBy(xpath = "//android.widget.EditText[@text='Unidad de Negocio']")
    private WebElement unidadNegocioList;

    @FindBy(xpath = "//android.widget.EditText[@text='Activo']")
    private WebElement activoList;

    public Boolean cargarDatosActa(String gerenciaRegional, String unidadNegocio, String activo){
        boolean result;
        try{


            global.isVisibleinMobile(gerenciaRegionalList);
            global.seleccionarValorLista(gerenciaRegionalList,gerenciaRegional);
            global.seleccionarValorLista(unidadNegocioList,unidadNegocio);
            global.seleccionarValorLista(activoList,activo);
            result=true;
            //driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        }catch (Exception ex){
            System.out.println("Existieron inconvenientes en el metodo cargarDatosActa: "+ex.getMessage());
            result=false;
        }

        return result;
    }


}
