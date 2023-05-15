package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Pie;
import by.aurorasoft.chart.model.series.PieSeries;
import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import lombok.Getter;
import org.icepear.echarts.Chart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static java.lang.Double.MIN_VALUE;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//TODO
@RunWith(MockitoJUnitRunner.class)
public final class AbstractChartBuildingServiceTest {
    private static final String FIELD_NAME_CHART_OF_PREPARED_CHART = "chart";

    @Mock
    private org.icepear.echarts.Pie mockedBuilder;

    @Mock
    private org.icepear.echarts.charts.pie.PieSeries mockedBuilderSeries;

    private final org.icepear.echarts.charts.pie.PieDataItem[] builderSeriesValue
            = new org.icepear.echarts.charts.pie.PieDataItem[]{};

    private PieBuildingService buildingService;

    @Before
    public void initializeBuildingService() {
        this.buildingService = new PieBuildingService(
                this.mockedBuilder, this.mockedBuilderSeries, builderSeriesValue
        );
    }

    @Test
    public void chartShouldBeBuilt()
            throws Exception {
        final String givenTitle = "title";

        final PieDataItem[] givenSeriesValue = new PieDataItem[]{
                new PieDataItem(null, MIN_VALUE),
                new PieDataItem(null, MIN_VALUE),
                new PieDataItem(null, MIN_VALUE)
        };
        final PieSeries givenSeries = new PieSeries(givenSeriesValue);
        final Pie givenSource = new Pie(givenTitle, givenSeries);

        final PreparedChart actual = this.buildingService.build(givenSource);
        final PreparedChart expected = new PreparedChart(this.mockedBuilder);
        checkEquals(expected, actual);

        verify(this.mockedBuilder, times(1)).setTitle(givenTitle);
        verify(this.mockedBuilder, times(givenSeriesValue.length)).addSeries(same(this.mockedBuilderSeries));
        assertTrue(this.buildingService.isSpecialPropertiesWereAppended());
    }

    private static void checkEquals(PreparedChart expected, PreparedChart actual)
            throws Exception {
        assertSame(findChart(expected), findChart(actual));
    }

    private static Chart<?, ?> findChart(PreparedChart preparedChart)
            throws Exception {
        final Field field = PreparedChart.class.getDeclaredField(FIELD_NAME_CHART_OF_PREPARED_CHART);
        field.setAccessible(true);
        try {
            return (Chart<?, ?>) field.get(preparedChart);
        } finally {
            field.setAccessible(false);
        }
    }

    private static final class PieBuildingService extends AbstractChartBuildingService<
            PieDataItem[],
            PieSeries,
            Pie,
            org.icepear.echarts.charts.pie.PieDataItem[],
            org.icepear.echarts.charts.pie.PieSeries,
            org.icepear.echarts.Pie
            > {
        private final org.icepear.echarts.Pie builder;
        private final org.icepear.echarts.charts.pie.PieSeries builderSeries;
        private final org.icepear.echarts.charts.pie.PieDataItem[] builderSeriesValue;

        @Getter
        private boolean specialPropertiesWereAppended;

        public PieBuildingService(org.icepear.echarts.Pie builder,
                                  org.icepear.echarts.charts.pie.PieSeries builderSeries,
                                  org.icepear.echarts.charts.pie.PieDataItem[] builderSeriesValue) {
            super(Pie.class);
            this.builder = builder;
            this.builderSeries = builderSeries;
            this.builderSeriesValue = builderSeriesValue;
        }

        @Override
        protected org.icepear.echarts.Pie createBuilder() {
            return this.builder;
        }

        @Override
        protected void appendSpecialProperties(Pie source, org.icepear.echarts.Pie builder) {
            this.specialPropertiesWereAppended = true;
        }

        @Override
        protected org.icepear.echarts.charts.pie.PieSeries createBuilderSeries() {
            return this.builderSeries;
        }

        @Override
        protected org.icepear.echarts.charts.pie.PieDataItem[] mapToBuilderSeriesValue(PieDataItem[] mapped) {
            return this.builderSeriesValue;
        }

    }

}
