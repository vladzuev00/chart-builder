package by.aurorasoft.chart.eventlistener;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static java.lang.System.setProperty;

@Component
public final class WebDriverConfigurerEventListener {
    private static final String HTTP_FACTORY_PROPERTY_KEY = "webdriver.http.factory";
    private static final String HTTP_FACTORY_PROPERTY_VALUE = "jdk-http-client";

    @EventListener
    @SuppressWarnings("unused")
    public void onApplicationStarted(ContextStartedEvent event) {
        setPropertyOfHttpFactory();
        setupDriver();
    }

    private static void setPropertyOfHttpFactory() {
        setProperty(HTTP_FACTORY_PROPERTY_KEY, HTTP_FACTORY_PROPERTY_VALUE);
    }

    private static void setupDriver() {
        chromedriver().setup();
    }
}
