package by.aurorasoft.chart.base;

import by.aurorasoft.chart.configuration.MainConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class AbstractContextTest {
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(MainConfiguration.class);

    protected static <T> T findBean(Class<T> type) {
        return CONTEXT.getBean(type);
    }
}
