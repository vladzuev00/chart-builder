package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import org.icepear.echarts.Pie;
import org.icepear.echarts.charts.pie.PieSeries;
import org.junit.Test;

import static org.junit.Assert.*;

public final class PieBuildingServiceTest {
    private final PieBuildingService buildingService = new PieBuildingService();

    @Test
    public void builderShouldBeCreated() {
        final Pie actual = this.buildingService.createBuilder();
        assertNotNull(actual);
    }

    @Test
    public void builderSeriesShouldBeCreated() {
        final PieSeries actual = this.buildingService.createBuilderSeries();
        assertNotNull(actual);
    }

    @Test
    public void sourceSeriesValueShouldBeMappedToBuilderSeriesValue() {
        final PieDataItem[] givenSourceSeriesValue = new PieDataItem[]{
            new PieDataItem("first-name", 1),
            new PieDataItem("second-name", 2),
            new PieDataItem("third-name", 3)
        };

        final org.icepear.echarts.charts.pie.PieDataItem[] actual = this.buildingService.mapToBuilderSeriesValue(
                givenSourceSeriesValue
        );
        final org.icepear.echarts.charts.pie.PieDataItem[] expected = new org.icepear.echarts.charts.pie.PieDataItem[]{
                new org.icepear.echarts.charts.pie.PieDataItem().setName("first-name").setValue(1.),
                new org.icepear.echarts.charts.pie.PieDataItem().setName("second-name").setValue(2.),
                new org.icepear.echarts.charts.pie.PieDataItem().setName("third-name").setValue(3.)
        };
        assertArrayEquals(expected, actual);
    }

}
