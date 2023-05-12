package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarEmphasis;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AbstractStackBarBuildingServiceTest {
    private static final String BAR_SERIES_STACK_VALUE = "total";

    private final AbstractStackBarBuildingService<StackBar> buildingService = new TestStackBarBuildingService();

    @Test
    public void sourceSeriesShouldBeMappedToBuilderSeries() {
        final BarSeries givenSourceSeries = new BarSeries(
                "name",
                new Number[]{1, 2, 3}
        );

        final org.icepear.echarts.charts.bar.BarSeries actual = this.buildingService.mapToBuilderSeries(
                givenSourceSeries
        );
        final org.icepear.echarts.charts.bar.BarSeries expected = new org.icepear.echarts.charts.bar.BarSeries()
                .setStack(BAR_SERIES_STACK_VALUE)
                .setEmphasis(new BarEmphasis())
                .setData(givenSourceSeries.getValue());
        assertEquals(expected, actual);
    }

    private static final class TestStackBarBuildingService extends AbstractStackBarBuildingService<StackBar> {

        public TestStackBarBuildingService() {
            super(StackBar.class);
        }

        @Override
        protected void appendSpecialPropertiesExceptBarAxis(StackBar source, Bar bar) {

        }
    }
}
