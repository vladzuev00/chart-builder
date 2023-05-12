package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.StackBar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.icepear.echarts.charts.bar.BarEmphasis;

import java.util.Optional;

public abstract class AbstractStackBarBuildingService<C extends StackBar>
        extends AbstractBarBuildingService<C> {
    private static final String BAR_SERIES_STACK_VALUE = "total";

    public AbstractStackBarBuildingService(Class<C> sourceType) {
        super(sourceType);
    }

    @Override
    protected org.icepear.echarts.charts.bar.BarSeries mapToEchartsBarSeries(BarSeries barSeries) {
        final org.icepear.echarts.charts.bar.BarSeries echartsBarSeries
                = new org.icepear.echarts.charts.bar.BarSeries()
                .setStack(BAR_SERIES_STACK_VALUE)
                .setEmphasis(new BarEmphasis())
                .setData(barSeries.getValue())
                //TODO:
                .setAnimation(false);
        appendName(barSeries, echartsBarSeries);
        return echartsBarSeries;
    }

    private static void appendName(BarSeries source, org.icepear.echarts.charts.bar.BarSeries builder) {
        final Optional<String> optionalName = source.findName();
        optionalName.ifPresent(builder::setName);
    }

}


