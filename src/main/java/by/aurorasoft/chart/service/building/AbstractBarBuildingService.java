package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.AbstractBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.Bar;

public abstract class AbstractBarBuildingService<CHART extends AbstractBar>
        extends CartesianCoordinateChartBuildingService<
        Number[], BarSeries, CHART, org.icepear.echarts.charts.bar.BarSeries, Bar
        > {

    public AbstractBarBuildingService(Class<CHART> sourceType) {
        super(sourceType);
    }

    @Override
    protected final org.icepear.echarts.Bar createBuilder() {
        return new org.icepear.echarts.Bar();
    }
}
