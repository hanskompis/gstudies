package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SampleSeleniumTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        baseUrl = "http://localhost:8080/gstudies/app/index.html";
    }

    @Test
    public void hasLinkLol() {
        driver.get(baseUrl);
        Assert.assertTrue("gstudies not found", driver.getPageSource().toLowerCase().contains("gstudies"));
    }
}
