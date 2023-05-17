package by.aurorasoft.chart.eventlistener;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static java.lang.System.setProperty;

@Component
public final class WebDriverConfigurerEventListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final String HTTP_FACTORY_PROPERTY_KEY = "webdriver.http.factory";
    private static final String HTTP_FACTORY_PROPERTY_VALUE = "jdk-http-client";

    @Override
    @SuppressWarnings("unused")
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
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
