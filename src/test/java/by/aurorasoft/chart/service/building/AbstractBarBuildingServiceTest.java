package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.AbstractBar;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarSeries;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public final class AbstractBarBuildingServiceTest {
    private final AbstractBarBuildingService<?> buildingService = new TestBarBuildingService();

    @Test
    public void builderShouldBeCreated() {
        final Bar actual = this.buildingService.createBuilder();
        assertNotNull(actual);
    }

    private static final class TestBarBuildingService extends AbstractBarBuildingService<AbstractBar> {

        public TestBarBuildingService() {
            super(AbstractBar.class);
        }

        @Override
        protected BarSeries mapToBuilderSeries(by.aurorasoft.chart.model.series.BarSeries series) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void appendSpecialPropertiesExceptBarAxis(AbstractBar source, Bar bar) {
            throw new UnsupportedOperationException();
        }
    }
}
