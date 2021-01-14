package mobile;

import com.everis.data.ExcelUtils;
import com.everis.global.GlobalMobile;
import com.everis.pomobile.IndexPage;
import com.everis.pomobile.LoginPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class Demo extends BaseTestAppium{

    @DataProvider
    public Object[][] mobileCases(ITestContext context) throws Exception{
        Object[][] testObjArray = ExcelUtils.getTableArray(super.getDataProviderDir()+context.getCurrentXmlTest().getParameter("dataProvider"),context.getCurrentXmlTest().getParameter("sheet"));
        return (testObjArray);
    }

    @Test(description="Ejecuto test",dataProvider="mobileCases")
    public void cosneuDemo(String username,String password,String gerenciaRegional, String unidadNegocio,String activo){

        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.executeLogin(username,password));

        IndexPage index = new IndexPage(driver);
        Assert.assertTrue(index.cargarDatosActa(gerenciaRegional,unidadNegocio,activo));

        System.out.println("Hasta aca la primer parte");
    }

}
