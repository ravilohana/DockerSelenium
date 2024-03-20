package com.lohana.tests;

import com.lohana.util.Config;
import com.lohana.util.Constants;
import listeners.TestListeners;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;


@Listeners({TestListeners.class})
public abstract class AbstractTest {

    protected WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeSuite
    public void setUpConfig(){
        Config.initialize();
    }



    @Parameters("browserName")
    @BeforeTest
    public void setDriver(String browserName, ITestContext ctx){

//        if(Boolean.parseBoolean(Config.getPropertyValue(Constants.GRID_ENABLED))){
//            this.driver = getRemoteDriver(browserName);
//        }else {
//            this.driver  = getLocalDriver(browserName);
//        }

        this.driver = Boolean.parseBoolean(Config.getPropertyValue(Constants.GRID_ENABLED)) ?
                getRemoteDriver(browserName) : getLocalDriver(browserName);


        this.driver.manage().window().maximize();
        this.driver.manage().deleteAllCookies();
        ctx.setAttribute(Constants.DRIVER,this.driver);
    }


    private WebDriver getRemoteDriver(String browserName){

        Capabilities capabilities = null;

        try {

            if(Constants.CHROME.equalsIgnoreCase(Config.getPropertyValue(Constants.BROWSER_NAME))){
                capabilities = new ChromeOptions();
            } else if (Constants.FIREFOX.equalsIgnoreCase(Config.getPropertyValue(Constants.BROWSER_NAME))){
                capabilities = new FirefoxOptions();
            } else if (Constants.MS_EDGE.equalsIgnoreCase(Config.getPropertyValue(Constants.BROWSER_NAME))){
                capabilities = new EdgeOptions();
            }else {
                throw new IllegalStateException("Invalid browser options: " + capabilities);
            }

            String urlFormat = Config.getPropertyValue(Constants.GRID_URL_FORMAT);
            String hubHost = Config.getPropertyValue(Constants.GRID_HUB_HOST);
            String url = String.format(urlFormat,hubHost);
            URL gridURL = new URL(url);
            logger.info("Grid URL: {}" , gridURL);

            return new RemoteWebDriver(gridURL,capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    private WebDriver getLocalDriver(String browserName){
        switch (Config.getPropertyValue(Constants.BROWSER_NAME)){
            case Constants.CHROME:
                this.driver = new ChromeDriver();
                break;
            case Constants.FIREFOX:
                this.driver = new FirefoxDriver();
                break;
            case Constants.MS_EDGE:
                this.driver = new EdgeDriver();
                break;
            default:
                throw new IllegalStateException("Invalid browser name: " + browserName);
        }

        return driver;
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

}
