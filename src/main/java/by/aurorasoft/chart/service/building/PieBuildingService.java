package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.Pie;
import by.aurorasoft.chart.model.series.PieSeries;
import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import org.icepear.echarts.charts.pie.PieLabel;
import org.springframework.stereotype.Service;

import static java.util.Arrays.stream;

@Service
public final class PieBuildingService extends AbstractChartBuildingService<
        PieDataItem[],
        PieSeries,
        Pie,
        org.icepear.echarts.charts.pie.PieDataItem[],
        org.icepear.echarts.charts.pie.PieSeries,
        org.icepear.echarts.Pie
        > {

    /**
     * {b} - segment's name
     * {d} - value in percent
     */
    private static final String FORMAT_SEGMENT_DESCRIPTION = "{b} : {d}%";

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
    protected org.icepear.echarts.charts.pie.PieSeries createBuilderSeries() {
        return new org.icepear.echarts.charts.pie.PieSeries()
                .setLabel(createPieLabel());
    }

    @Override
    protected org.icepear.echarts.charts.pie.PieDataItem[] mapToBuilderSeriesValue(PieDataItem[] mapped) {
        return stream(mapped)
                .map(PieBuildingService::mapToEchartsDataItem)
                .toArray(org.icepear.echarts.charts.pie.PieDataItem[]::new);
    }

    private static PieLabel createPieLabel() {
        return new PieLabel().setFormatter(FORMAT_SEGMENT_DESCRIPTION);
    }

    private static org.icepear.echarts.charts.pie.PieDataItem mapToEchartsDataItem(PieDataItem pieDataItem) {
        return new org.icepear.echarts.charts.pie.PieDataItem()
                .setName(pieDataItem.getName())
                .setValue(pieDataItem.getValue());
    }
}
