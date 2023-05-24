package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.model.chart.Pie;
import by.aurorasoft.chart.model.series.PieSeries;
import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import by.aurorasoft.chart.model.series.Series;
import lombok.Getter;
import org.icepear.echarts.origin.util.SeriesOption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbstractChartBuildingServiceTest {
    private static final String FIELD_NAME_CHART_OF_PREPARED_CHART = "chart";

    private static final String METHOD_NAME_APPENDING_TITLE = "appendTitle";
    private static final String METHOD_NAME_APPENDING_SERIES = "appendSeries";
    private static final String METHOD_NAME_APPENDING_SERIES_NAME = "appendName";
    private static final String METHOD_NAME_APPENDING_SERIES_VALUE = "appendValue";
    private static final String METHOD_NAME_MAPPING_SOURCE_TO_BUILDER_SERIES = "mapToBuilderSeries";

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
                this.mockedBuilder, this.mockedBuilderSeries, this.builderSeriesValue
        );
    }

    @Test
    public void titleShouldBeAppended()
            throws Exception {
        final String givenTitle = "title";
        final Pie givenSource = new Pie(givenTitle, (PieSeries) null);

        final Method methodAppendingTitle = this.findMethod(
                METHOD_NAME_APPENDING_TITLE, Chart.class, org.icepear.echarts.Chart.class
        );
        this.callMethod(methodAppendingTitle, givenSource, this.mockedBuilder);

        verify(this.mockedBuilder, times(1)).setTitle(givenTitle);
    }

    @Test
    public void titleShouldNotBeAppended()
            throws Exception {
        final Pie givenSource = new Pie(null, (PieSeries) null);

        final Method methodAppendingTitle = this.findMethod(
                METHOD_NAME_APPENDING_TITLE, Chart.class, org.icepear.echarts.Chart.class
        );
        this.callMethod(methodAppendingTitle, givenSource, this.mockedBuilder);

        verify(this.mockedBuilder, times(0)).setTitle(anyString());
    }

    @Test
    public void seriesShouldBeAppended()
            throws Exception {
        final PieSeries givenSeries = new PieSeries(null);
        final Pie givenSource = new Pie(null, givenSeries);

        final Method methodAppendingSeries = this.findMethod(
                METHOD_NAME_APPENDING_SERIES, Chart.class, org.icepear.echarts.Chart.class
        );
        this.callMethod(methodAppendingSeries, givenSource, this.mockedBuilder);

        verify(this.mockedBuilder, times(1)).addSeries(this.mockedBuilderSeries);
    }

    @Test
    public void seriesNameShouldBeAppended()
            throws Exception {
        final String givenSeriesName = "name";
        final PieSeries givenSeries = mock(PieSeries.class);
        when(givenSeries.findName()).thenReturn(Optional.of(givenSeriesName));

        final Method methodAppendingName = this.findMethod(
                METHOD_NAME_APPENDING_SERIES_NAME, Series.class, SeriesOption.class
        );
        this.callMethod(methodAppendingName, givenSeries, this.mockedBuilderSeries);

        verify(this.mockedBuilderSeries, times(1)).setName(givenSeriesName);
    }

    @Test
    public void seriesNameShouldNotBeAppended() throws Exception {
        final PieSeries givenSeries = new PieSeries(null);

        final Method methodAppendingName = this.findMethod(
                METHOD_NAME_APPENDING_SERIES_NAME, Series.class, SeriesOption.class
        );
        this.callMethod(methodAppendingName, givenSeries, this.mockedBuilderSeries);

        verify(this.mockedBuilderSeries, times(0)).setName(anyString());
    }

    @Test
    public void seriesValueShouldBeAppended()
            throws Exception {
        final PieSeries givenSeries = new PieSeries(null);

        final Method methodAppendingValue = this.findMethod(
                METHOD_NAME_APPENDING_SERIES_VALUE, Series.class, SeriesOption.class
        );
        this.callMethod(methodAppendingValue, givenSeries, this.mockedBuilderSeries);

        verify(this.mockedBuilderSeries, times(1)).setData((Object) this.builderSeriesValue);
    }

    @Test
    public void sourceSeriesShouldBeMappedToBuilderSeries()
            throws Exception {
        final PieSeries givenSeries = new PieSeries(null);

        final Method methodMapping = this.findMethod(
                METHOD_NAME_MAPPING_SOURCE_TO_BUILDER_SERIES, Series.class
        );

        final org.icepear.echarts.charts.pie.PieSeries actual = this.callMethod(
                methodMapping, org.icepear.echarts.charts.pie.PieSeries.class, givenSeries
        );
        assertSame(this.mockedBuilderSeries, actual);

        verify(this.mockedBuilderSeries, times(1)).setAnimation(false);
        verify(this.mockedBuilderSeries, times(0)).setName(anyString());
        verify(this.mockedBuilderSeries, times(1)).setData((Object) this.builderSeriesValue);
    }

    @Test
    public void chartShouldBeBuilt()
            throws Exception {
        final String givenTitle = "title";

        final PieSeries givenSeries = new PieSeries(null);
        final Pie givenSource = new Pie(givenTitle, givenSeries);

        final PreparedChart actual = this.buildingService.build(givenSource);
        final PreparedChart expected = new PreparedChart(this.mockedBuilder);
        checkEquals(expected, actual);

        verify(this.mockedBuilder, times(1)).setTitle(givenTitle);
        verify(this.mockedBuilderSeries, times(1)).setAnimation(false);
        verify(this.mockedBuilder, times(1)).addSeries(same(this.mockedBuilderSeries));
        assertTrue(this.buildingService.isSpecialPropertiesWereAppended());
    }

    private static void checkEquals(PreparedChart expected, PreparedChart actual)
            throws Exception {
        assertSame(findChart(expected), findChart(actual));
    }

    private static org.icepear.echarts.Chart<?, ?> findChart(PreparedChart preparedChart)
            throws Exception {
        final Field field = PreparedChart.class.getDeclaredField(FIELD_NAME_CHART_OF_PREPARED_CHART);
        field.setAccessible(true);
        try {
            return (org.icepear.echarts.Chart<?, ?>) field.get(preparedChart);
        } finally {
            field.setAccessible(false);
        }
    }

    private Method findMethod(String methodName, Class<?>... parameterTypes)
            throws Exception {
        return AbstractChartBuildingService.class.getDeclaredMethod(methodName, parameterTypes);
    }

    private void callMethod(Method method, Object... arguments)
            throws Exception {
        callMethod(method, Void.class, arguments);
    }

    private <T> T callMethod(Method method, Class<T> resultType, Object... arguments)
            throws Exception {
        method.setAccessible(true);
        try {
            final Object result = method.invoke(this.buildingService, arguments);
            return resultType.cast(result);
        } finally {
            method.setAccessible(false);
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
