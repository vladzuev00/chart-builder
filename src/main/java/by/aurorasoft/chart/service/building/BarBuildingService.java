package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.Bar;
import org.icepear.echarts.charts.bar.BarSeries;
import org.springframework.stereotype.Service;

@Service
public final class BarBuildingService extends AbstractBarBuildingService<Bar> {
    public BarBuildingService() {
        super(Bar.class);
    }

    @Override
    protected void configureBuilderSeries(BarSeries builderSeries) {

    }

    @Override
    protected void appendSpecialPropertiesExceptAxis(Bar source, org.icepear.echarts.Bar bar) {

    }
}
