package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.charts.bar.BarEmphasis;

public abstract class AbstractStackBarBuildingService<C extends StackBar>
        extends AbstractBarBuildingService<C> {
    private static final String BAR_SERIES_STACK_VALUE = "total";

    public AbstractStackBarBuildingService(Class<C> sourceType) {
        super(sourceType);
    }

    @Override
    protected final org.icepear.echarts.charts.bar.BarSeries mapToBuilderSeries(BarSeries series) {
        return new org.icepear.echarts.charts.bar.BarSeries()
                .setStack(BAR_SERIES_STACK_VALUE)
                .setEmphasis(new BarEmphasis())
                .setData(series.getValue());
    }

}


