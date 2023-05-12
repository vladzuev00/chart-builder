package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.Bar;
import by.aurorasoft.chart.model.series.BarSeries;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class BarBuildingService extends AbstractBarBuildingService<Bar> {
    private static final String DEFAULT_SERIES_NAME = "";

    public BarBuildingService() {
        super(Bar.class);
    }

    @Override
    protected org.icepear.echarts.charts.bar.BarSeries mapToBuilderSeries(BarSeries series) {
        return new org.icepear.echarts.charts.bar.BarSeries()
                .setName(findName(series))
                .setData(series.getValue());
    }

    private static String findName(BarSeries series) {
        final Optional<String> optionalName = series.findName();
        return optionalName.orElse(DEFAULT_SERIES_NAME);
    }
}
