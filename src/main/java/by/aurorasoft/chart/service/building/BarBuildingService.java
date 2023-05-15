package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.Bar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.springframework.stereotype.Service;

@Service
public final class BarBuildingService extends AbstractBarBuildingService<Bar> {
    public BarBuildingService() {
        super(Bar.class);
    }

    @Override
    protected org.icepear.echarts.charts.bar.BarSeries mapToBuilderSeries(BarSeries sourceSeries) {
        return new org.icepear.echarts.charts.bar.BarSeries()
                //TODO: put set data in superClass if possible
                .setData(sourceSeries.getValue());
    }

    @Override
    protected void appendSpecialPropertiesExceptBarAxis(Bar source, org.icepear.echarts.Bar bar) {

    }
}
