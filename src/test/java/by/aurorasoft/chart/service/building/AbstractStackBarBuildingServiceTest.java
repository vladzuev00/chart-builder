package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBar;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarEmphasis;
import org.icepear.echarts.charts.bar.BarSeries;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AbstractStackBarBuildingServiceTest {
    private static final String BAR_SERIES_STACK_VALUE = "total";

    private final AbstractStackBarBuildingService<StackBar> buildingService = new TestStackBarBuildingService();

    @Test
    public void builderServiceShouldBeConfigured() {
        final BarSeries givenBarSeries = new BarSeries();
        this.buildingService.configureBuilderSeries(givenBarSeries);

        final BarSeries expected = new BarSeries()
                .setStack(BAR_SERIES_STACK_VALUE)
                .setEmphasis(new BarEmphasis());
        assertEquals(expected, givenBarSeries);
    }

    private static final class TestStackBarBuildingService extends AbstractStackBarBuildingService<StackBar> {

        public TestStackBarBuildingService() {
            super(StackBar.class);
        }

        @Override
        protected void appendSpecialPropertiesExceptAxis(StackBar source, Bar bar) {

        }
    }
}
