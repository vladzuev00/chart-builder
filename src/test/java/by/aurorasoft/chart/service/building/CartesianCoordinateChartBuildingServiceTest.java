package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.Bar;
import lombok.Getter;
import org.icepear.echarts.charts.bar.BarSeries;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class CartesianCoordinateChartBuildingServiceTest {

    private TestBuildingService buildingService;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<String[]> stringsArgumentCaptor;

    @Before
    public void initializeBuildingService() {
        this.buildingService = new TestBuildingService();
    }

    @Test
    public void specialPropertiesShouldBeAppended() {
        final String givenAxisXName = "axisXName";
        final String givenAxisYName = "axisYName";
        final String[] givenAxisXValues = new String[]{
                "Matcha Latte", "Milk Tea", "Cheese Cocoa", "Walnut Brownie"
        };
        final Bar givenSource = new Bar(
                "title",
                new by.aurorasoft.chart.model.series.BarSeries[]{
                        new by.aurorasoft.chart.model.series.BarSeries("2015", new Number[]{43.3, 83.1, 86.4, 72.4}),
                        new by.aurorasoft.chart.model.series.BarSeries("2016", new Number[]{44.3, 84.1, 87.4, 73.4}),
                        new by.aurorasoft.chart.model.series.BarSeries("2017", new Number[]{45.3, 85.1, 88.4, 74.4})
                },
                givenAxisXName,
                givenAxisXValues,
                givenAxisYName
        );
        final org.icepear.echarts.Bar givenBuilder = mock(org.icepear.echarts.Bar.class);

        this.buildingService.appendSpecialProperties(givenSource, givenBuilder);

        verify(givenBuilder, times(1)).addXAxis(
                this.stringArgumentCaptor.capture(), this.stringsArgumentCaptor.capture()
        );
        verify(givenBuilder, times(1)).addYAxis(this.stringArgumentCaptor.capture());

        final List<String> expectedCapturedString = List.of(givenAxisXName, givenAxisYName);
        assertEquals(expectedCapturedString, this.stringArgumentCaptor.getAllValues());

        assertArrayEquals(givenAxisXValues, this.stringsArgumentCaptor.getValue());

        assertTrue(this.buildingService.isAllSpecialPropertiesWereAppended());
    }

    @Test
    public void specialPropertiesShouldBeAppendedWithNotDefinedAxisNames() {
        final String[] givenAxisXValues = new String[]{
                "Matcha Latte", "Milk Tea", "Cheese Cocoa", "Walnut Brownie"
        };
        final Bar givenSource = new Bar(
                "title",
                new by.aurorasoft.chart.model.series.BarSeries[]{
                        new by.aurorasoft.chart.model.series.BarSeries("2015", new Number[]{43.3, 83.1, 86.4, 72.4}),
                        new by.aurorasoft.chart.model.series.BarSeries("2016", new Number[]{44.3, 84.1, 87.4, 73.4}),
                        new by.aurorasoft.chart.model.series.BarSeries("2017", new Number[]{45.3, 85.1, 88.4, 74.4})
                },
                null,
                givenAxisXValues,
                null
        );
        final org.icepear.echarts.Bar givenBuilder = mock(org.icepear.echarts.Bar.class);

        this.buildingService.appendSpecialProperties(givenSource, givenBuilder);

        verify(givenBuilder, times(1)).addXAxis(this.stringsArgumentCaptor.capture());
        verify(givenBuilder, times(0)).addYAxis(anyString());

        assertArrayEquals(givenAxisXValues, this.stringsArgumentCaptor.getValue());

        assertTrue(this.buildingService.isAllSpecialPropertiesWereAppended());
    }

    @Getter
    private static final class TestBuildingService extends AbstractBarBuildingService<Bar> {
        private boolean allSpecialPropertiesWereAppended;

        public TestBuildingService() {
            super(Bar.class);
        }

        @Override
        protected void configureBuilderSeries(BarSeries builderSeries) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void appendSpecialPropertiesExceptAxis(Bar source, org.icepear.echarts.Bar bar) {
            this.allSpecialPropertiesWereAppended = true;
        }
    }
}
