package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBar;
import org.icepear.echarts.charts.bar.BarEmphasis;
import org.icepear.echarts.charts.bar.BarSeries;

public abstract class AbstractStackBarBuildingService<C extends StackBar>
        extends AbstractBarBuildingService<C> {
    private static final String BAR_SERIES_STACK_VALUE = "total";

    public AbstractStackBarBuildingService(Class<C> sourceType) {
        super(sourceType);
    }

    @Override
    protected final void configureBuilderSeries(BarSeries builderSeries) {
        builderSeries
                .setStack(BAR_SERIES_STACK_VALUE)
                .setEmphasis(new BarEmphasis());
    }

}


