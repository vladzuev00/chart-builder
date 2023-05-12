package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.AbstractBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.Bar;

public abstract class AbstractBarBuildingService<C extends AbstractBar>
        extends CartesianCoordinateChartBuildingService<Number[], BarSeries, C, Bar> {

    public AbstractBarBuildingService(Class<C> sourceType) {
        super(sourceType);
    }

    @Override
    protected final org.icepear.echarts.Bar createBuilder() {
        return new org.icepear.echarts.Bar();
    }

    @Override
    protected final void appendSeries(BarSeries barSeries, Bar builder) {
        builder.addSeries(this.mapToEchartsBarSeries(barSeries));
    }

    protected abstract org.icepear.echarts.charts.bar.BarSeries mapToEchartsBarSeries(BarSeries barSeries);
}
