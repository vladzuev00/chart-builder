package by.aurorasoft.chart.service.building.manager;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.model.series.Series;
import by.aurorasoft.chart.service.building.AbstractChartBuildingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class ChartBuildingServiceManagerTest {
    private static final String FIELD_NAME_BUILDING_SERVICES_BY_SOURCE_TYPES = "buildingServicesBySourceTypes";

    @Mock
    private AbstractChartBuildingService<?, ?, FirstTestChart, ?, ?, ?> firstBuildingService;

    @Mock
    private AbstractChartBuildingService<?, ?, SecondTestChart, ?, ?, ?> secondBuildingService;

    private ChartBuildingServiceManager manager;

    @Before
    public void initializeManager() {
        when(this.firstBuildingService.getSourceType()).thenReturn(FirstTestChart.class);
        when(this.secondBuildingService.getSourceType()).thenReturn(SecondTestChart.class);
        this.manager = new ChartBuildingServiceManager(
                List.of(this.firstBuildingService, this.secondBuildingService)
        );
    }

    @Test
    public void managerShouldBeCreated()
            throws Exception {
        final Map<Class<? extends Chart<?>>, AbstractChartBuildingService<?, ?, ?, ?, ?, ?>> actualServicesBySourceTypes
                = findBuildingServicesBySourceTypes(this.manager);
        final Map<Class<? extends Chart<?>>, AbstractChartBuildingService<?, ?, ?, ?, ?, ?>> expectedServicesBySourceTypes
                = Map.of(
                FirstTestChart.class, this.firstBuildingService,
                SecondTestChart.class, this.secondBuildingService
        );
        assertEquals(expectedServicesBySourceTypes, actualServicesBySourceTypes);
    }

    @Test
    public void chartShouldBeBuilt() {
        final FirstTestChart givenChart = new FirstTestChart("title", new TestSeries[]{});

        final PreparedChart givenPreparedChart = mock(PreparedChart.class);
        when(this.firstBuildingService.build(same(givenChart))).thenReturn(givenPreparedChart);

        final PreparedChart actual = this.manager.build(givenChart);
        assertSame(givenPreparedChart, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void chartShouldNotBeBuildBecauseOfNoBuildingServiceForGivenType() {
        final Chart<?> givenChart = new Chart<>("title", new Series[]{}) {
        };

        this.manager.build(givenChart);
    }

    private static final class TestSeries extends Series<Object> {

        public TestSeries(String name, Object value) {
            super(name, value);
        }

    }

    private static final class FirstTestChart extends Chart<TestSeries> {

        public FirstTestChart(String title, TestSeries[] series) {
            super(title, series);
        }

    }

    private static final class SecondTestChart extends Chart<TestSeries> {

        public SecondTestChart(String title, TestSeries[] series) {
            super(title, series);
        }

    }

    @SuppressWarnings("unchecked")
    private static Map<Class<? extends Chart<?>>, AbstractChartBuildingService<?, ?, ?, ?, ?, ?>>
    findBuildingServicesBySourceTypes(ChartBuildingServiceManager manager)
            throws Exception {
        final Field field = ChartBuildingServiceManager.class.getDeclaredField(
                FIELD_NAME_BUILDING_SERVICES_BY_SOURCE_TYPES
        );
        field.setAccessible(true);
        try {
            return (Map<Class<? extends Chart<?>>, AbstractChartBuildingService<?, ?, ?, ?, ?, ?>>)
                    field.get(manager);
        } finally {
            field.setAccessible(false);
        }
    }

}
