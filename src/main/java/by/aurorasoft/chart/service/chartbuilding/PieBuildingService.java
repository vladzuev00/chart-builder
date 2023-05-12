package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.Pie;
import by.aurorasoft.chart.model.series.PieSeries;
import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import org.springframework.stereotype.Service;

import static java.util.Arrays.stream;

@Service
public final class PieBuildingService
        extends AbstractChartBuildingService<PieDataItem[], PieSeries, Pie, org.icepear.echarts.Pie> {

    public PieBuildingService() {
        super(Pie.class);
    }

    @Override
    protected org.icepear.echarts.Pie createBuilder() {
        return new org.icepear.echarts.Pie();
    }

    @Override
    protected void appendSpecialProperties(Pie source, org.icepear.echarts.Pie builder) {

    }

    @Override
    protected void appendSeries(PieSeries series, org.icepear.echarts.Pie builder) {
        builder.addSeries(mapToEchartsDataItems(series));
    }

    private static org.icepear.echarts.charts.pie.PieDataItem[] mapToEchartsDataItems(PieSeries series) {
        final PieDataItem[] pieDataItems = series.getValue();
        return stream(pieDataItems)
                .map(PieBuildingService::mapToEchartsDataItem)
                .toArray(org.icepear.echarts.charts.pie.PieDataItem[]::new);
    }

    private static org.icepear.echarts.charts.pie.PieDataItem mapToEchartsDataItem(PieDataItem pieDataItem) {
        return new org.icepear.echarts.charts.pie.PieDataItem()
                .setName(pieDataItem.getName())
                .setValue(pieDataItem.getValue());
    }
}