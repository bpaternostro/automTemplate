package Portal;

import com.everis.data.ExcelUtils;
import com.everis.po.IndexPage;
import com.everis.po.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class Login extends BaseTest{

    final static Logger log = LogManager.getLogger(Login.class);

    @DataProvider
    public Object[][] LoginCases(ITestContext context) throws Exception{
        Object[][] testObjArray = ExcelUtils.getTableArray(super.getDataProviderDir()+context.getCurrentXmlTest().getParameter("dataProvider"),context.getCurrentXmlTest().getParameter("sheet"));
        return (testObjArray);
    }

    @Test(description="Ejecuto login y valido usuario",dataProvider="LoginCases")
    public void validLogin(String sUsername,String sPassword,String sCuit,String sName){

        log.info("Inicia el caso de prueba");
        log.info("Ejecutando " + sUsername);

        LoginPage login = new LoginPage(driver);
        login.executeLogin(sUsername,sPassword);

        IndexPage index=new IndexPage(driver);

        Assert.assertTrue(index.headerIsVisible(), "Valido si el mensaje de bienvenida aparecio correctamente.");
        Assert.assertEquals(index.getHeaderText(), sName);

    }

    @Test(description="Ejecuto login y espero la falla",dataProvider="LoginCases")
    public void invalidLogin(String sUsername,String sPassword,String sCuit,String sName){

        log.info("Inicia el caso de prueba");
        log.info("Ejecutando " + sUsername);

        LoginPage login = new LoginPage(driver);
        login.executeLogin(sUsername,sPassword);


    }

}
