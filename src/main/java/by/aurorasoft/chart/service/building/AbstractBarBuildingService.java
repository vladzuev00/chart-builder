package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.AbstractBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.Bar;

public abstract class AbstractBarBuildingService<CHART extends AbstractBar>
        extends CartesianCoordinateChartBuildingService<
        Number[],
        BarSeries,
        CHART,
        Number[],
        org.icepear.echarts.charts.bar.BarSeries,
        Bar> {

    public AbstractBarBuildingService(Class<CHART> sourceType) {
        super(sourceType);
    }

    @Override
    protected final org.icepear.echarts.Bar createBuilder() {
        return new org.icepear.echarts.Bar()
                .setLegend();
    }

    @Override
    protected final Number[] mapToBuilderSeriesValue(Number[] mapped) {
        return mapped;
    }

    @Override
    protected final org.icepear.echarts.charts.bar.BarSeries createBuilderSeries() {
        final org.icepear.echarts.charts.bar.BarSeries builderSeries
                = new org.icepear.echarts.charts.bar.BarSeries();
        this.configureBuilderSeries(builderSeries);
        return builderSeries;
    }

    protected abstract void configureBuilderSeries(org.icepear.echarts.charts.bar.BarSeries builderSeries);
}
