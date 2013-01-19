package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SampleSeleniumTest {

    private String port;
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void hasLinkLol() {
        driver.get(baseUrl);
        Assert.assertTrue("lolo", driver.getPageSource().contains("LOL"));
    }
}
