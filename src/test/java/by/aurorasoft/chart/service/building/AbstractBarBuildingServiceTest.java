package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.AbstractBar;
import lombok.Getter;
import org.icepear.echarts.Bar;
import org.icepear.echarts.Chart;
import org.icepear.echarts.Option;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.components.legend.Legend;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public final class AbstractBarBuildingServiceTest {
    private static final String FIELD_NAME_CHART_OPTION = "option";

    private TestBarBuildingService buildingService;

    @Before
    public void initializeBuildingService() {
        this.buildingService = new TestBarBuildingService();
    }

    @Test
    public void builderShouldBeCreated()
            throws Exception {
        final Bar actual = this.buildingService.createBuilder();
        assertNotNull(actual);

        final Option actualOption = findOption(actual);
        final Option expectedOption = new Option().setLegend(new Legend());
        assertEquals(expectedOption, actualOption);
    }

    @Test
    public void sourceSeriesValueShouldBeMappedToBuilderSeriesValue() {
        final Number[] givenSourceSeriesValues = new Number[]{1, 2, 3};

        final Number[] actual = this.buildingService.mapToBuilderSeriesValue(givenSourceSeriesValues);
        assertSame(givenSourceSeriesValues, actual);
    }

    @Test
    public void builderSeriesShouldBeCreated() {
        final BarSeries actual = this.buildingService.createBuilderSeries();
        assertNotNull(actual);
        assertTrue(this.buildingService.isBuilderSeriesConfigured());
    }

    @Getter
    private static final class TestBarBuildingService extends AbstractBarBuildingService<AbstractBar> {
        private boolean builderSeriesConfigured;

        public TestBarBuildingService() {
            super(AbstractBar.class);
        }

        @Override
        protected void configureBuilderSeries(BarSeries builderSeries) {
            this.builderSeriesConfigured = true;
        }

        @Override
        protected void appendSpecialPropertiesExceptAxis(AbstractBar source, Bar bar) {
            throw new UnsupportedOperationException();
        }
    }

    private static Option findOption(Bar bar)
            throws Exception {
        final Field optionField = Chart.class.getDeclaredField(FIELD_NAME_CHART_OPTION);
        optionField.setAccessible(true);
        try {
            return (Option) optionField.get(bar);
        } finally {
            optionField.setAccessible(false);
        }
    }
}
