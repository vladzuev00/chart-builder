package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.Bar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.springframework.stereotype.Service;

@Service
public final class BarBuildingService extends AbstractBarBuildingService<Bar> {

    public BarBuildingService() {
        super(Bar.class);
    }

    @Override
    protected org.icepear.echarts.charts.bar.BarSeries mapToEchartsBarSeries(BarSeries barSeries) {
        return new org.icepear.echarts.charts.bar.BarSeries()
                //TODO: refactor
                .setName(barSeries.findName().get())
                .setData(barSeries.getValue())
                .setAnimation(false);
    }
}
