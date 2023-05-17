package by.aurorasoft.chart.eventlistener;

import by.aurorasoft.chart.base.AbstractContextTest;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.System.getProperty;
import static org.junit.Assert.assertEquals;

public final class WebDriverConfigurerEventListenerTest extends AbstractContextTest {
    private static final String HTTP_FACTORY_PROPERTY_KEY = "webdriver.http.factory";

    @Test
    public void contextStartedEventShouldBeHandled() {
        final String actualWebDriverHttpFactory = getProperty(HTTP_FACTORY_PROPERTY_KEY);
        final String expectedWebDriverHttpFactory = "jdk-http-client";
        assertEquals(expectedWebDriverHttpFactory, actualWebDriverHttpFactory);

        //if driver wasn't setup 'new ChromeDriver()' throws exception
        new ChromeDriver().quit();
    }

}
