package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.URL;

public class BaseTestAppium {
    private static AppiumDriverLocalService service;
    public AppiumDriver<WebElement> driver;

    private String appName;
    private String deviceName;
    private String packageName;
    private String mainActivity;
    private String driverPath;
    private String projectDir;
    private String dataProviderDir;
    private String driversDir;

    public BaseTestAppium(){
        this.projectDir=System.getProperty("user.dir");
        this.dataProviderDir=projectDir+"//src//test//dataprovider//";
        this.driversDir=projectDir+"//src//test//resources//";
    }

    public String getProjectDir(){
        return projectDir;
    }

    public String getDataProviderDir() {
        return dataProviderDir;
    }

    public String getDriversDir() {
        return driversDir;
    }

    @BeforeSuite
    public void setUp(ITestContext context) throws Exception {
        appName = context.getCurrentXmlTest().getParameter("appName");
        deviceName = context.getCurrentXmlTest().getParameter("device");
        packageName = context.getCurrentXmlTest().getParameter("appPackage");
        mainActivity = context.getCurrentXmlTest().getParameter("appMain");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(!appName.equals("")){
            File app = new File(driversDir, appName);
            capabilities.setCapability("app", app.getAbsolutePath());
        }

        capabilities.setCapability("deviceName",deviceName);
        capabilities.setCapability("appPackage", packageName);
        capabilities.setCapability("appActivity", mainActivity);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterSuite
    public void tearDown() throws Exception {
        //driver.quit();
    }

    /*@BeforeSuite
    public void globalSetup () throws IOException {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @AfterSuite
    public void globalTearDown () {
        if (service != null) {
            service.stop();
        }
    }

    public URL getServiceUrl () {
        return service.getUrl();
    }*/


}


