package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.model.series.Series;
import org.icepear.echarts.origin.util.SeriesOption;

public final class AbstractChartBuildingServiceTest {

    private static final class TestChartBuildingService extends AbstractChartBuildingService<
            Object, Series<Object>, Chart<Series<Object>>, SeriesOption, org.icepear.echarts.Chart<?, SeriesOption>> {

        public TestChartBuildingService() {
            super(null);
        }

        @Override
        protected org.icepear.echarts.Chart<?, SeriesOption> createBuilder() {
            return null;
        }

        @Override
        protected void appendSpecialProperties(Chart<Series<Object>> source, org.icepear.echarts.Chart<?, SeriesOption> seriesOptionChart) {

        }

        @Override
        protected SeriesOption mapToBuilderSeries(Series<Object> series) {
            return null;
        }
    }
}
