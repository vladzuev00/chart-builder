package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBarWithLine;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.Bar;
import org.icepear.echarts.components.coord.cartesian.ValueAxis;
import org.icepear.echarts.origin.coord.cartesian.AxisOption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class StackBarWithLineBuildingServiceTest {
    private static final String POSITION_VALUE_OF_AXIS_OF_LINE_VALUES = "right";
    private static final int INDEX_OF_AXIS_OF_LINE_VALUES = 1;
    private static final String TYPE_VALUE_OF_LINE = "line";

    private final StackBarWithLineBuildingService buildingService = new StackBarWithLineBuildingService();

    @Captor
    private ArgumentCaptor<AxisOption> axisOptionArgumentCaptor;

    @Captor
    private ArgumentCaptor<org.icepear.echarts.charts.bar.BarSeries> barSeriesArgumentCaptor;

    @Test
    public void specialPropertiesExceptAxisShouldBeAppended() {
        final Number[] givenLineValues = new Number[]{150, 230, 224, 218, 135, 147, 260};
        final StackBarWithLine givenSource = new StackBarWithLine(
                "title",
                new BarSeries[]{
                        new BarSeries("Direct", new Number[]{320, 302, 301, 334, 390, 330, 320}),
                        new BarSeries("Mail Ad", new Number[]{120, 132, 101, 134, 90, 230, 210}),
                        new BarSeries("Affiliate Ad", new Number[]{220, 182, 191, 234, 290, 330, 310}),
                        new BarSeries("Video Ad", new Number[]{150, 212, 201, 154, 190, 330, 410}),
                        new BarSeries("Search Engine", new Number[]{820, 832, 901, 934, 1290, 1330, 1320})
                },
                "axisXName",
                new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"},
                "axisYName",
                "line-value",
                givenLineValues
        );
        final Bar givenBuilder = mock(Bar.class);

        this.buildingService.appendSpecialPropertiesExceptAxis(givenSource, givenBuilder);

        verify(givenBuilder, times(1)).addYAxis(this.axisOptionArgumentCaptor.capture());
        verify(givenBuilder, times(1)).addSeries(this.barSeriesArgumentCaptor.capture());

        final AxisOption expectedAxisOfLineValues = new ValueAxis()
                .setName("line-value")
                .setPosition(POSITION_VALUE_OF_AXIS_OF_LINE_VALUES);
        assertEquals(expectedAxisOfLineValues, this.axisOptionArgumentCaptor.getValue());

        final org.icepear.echarts.charts.bar.BarSeries expectedAxisValuesOfLine = new org.icepear.echarts.charts.bar.BarSeries()
                .setYAxisIndex(INDEX_OF_AXIS_OF_LINE_VALUES)
                .setType(TYPE_VALUE_OF_LINE)
                .setData(givenLineValues)
                .setAnimation(false);
        assertEquals(expectedAxisValuesOfLine, this.barSeriesArgumentCaptor.getValue());
    }

}
