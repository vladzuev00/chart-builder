package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.model.series.Series;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.icepear.echarts.origin.util.SeriesOption;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public abstract class AbstractChartBuildingService<
        SERIES_VALUE,
        SERIES extends Series<SERIES_VALUE>,
        CHART extends Chart<SERIES>,
        BUILDER_SERIES_VALUE,
        BUILDER_SERIES extends SeriesOption,
        BUILDER extends org.icepear.echarts.Chart<?, BUILDER_SERIES>
        > {
    private final Class<CHART> sourceType;

    public final PreparedChart build(Chart<?> source) {
        final CHART concreteSource = this.sourceType.cast(source);
        final BUILDER builder = this.createBuilder();
        this.appendTitle(concreteSource, builder);
        this.appendSeries(concreteSource, builder);
        this.appendSpecialProperties(concreteSource, builder);
        return new PreparedChart(builder);
    }

    protected abstract BUILDER createBuilder();

    protected abstract void appendSpecialProperties(CHART source, BUILDER builder);

    protected abstract BUILDER_SERIES createBuilderSeries();

    protected abstract BUILDER_SERIES_VALUE mapToBuilderSeriesValue(SERIES_VALUE mapped);

    private void appendTitle(CHART source, BUILDER builder) {
        final Optional<String> optionalTitle = source.findTitle();
        optionalTitle.ifPresent(builder::setTitle);
    }

    private void appendSeries(CHART source, BUILDER builder) {
        final SERIES[] series = source.getSeries();
        stream(series)
                .map(this::mapToBuilderSeries)
                .forEach(builder::addSeries);
    }

    private BUILDER_SERIES mapToBuilderSeries(SERIES sourceSeries) {
        final BUILDER_SERIES builderSeries = this.createBuilderSeries();
        builderSeries.setAnimation(false);
        this.appendName(sourceSeries, builderSeries);
        this.appendValue(sourceSeries, builderSeries);
        return builderSeries;
    }

    private void appendName(SERIES sourceSeries, BUILDER_SERIES builderSeries) {
        final Optional<String> optionalName = sourceSeries.findName();
        optionalName.ifPresent(builderSeries::setName);
    }

    private void appendValue(SERIES sourceSeries, BUILDER_SERIES builderSeries) {
        final SERIES_VALUE sourceSeriesValue = sourceSeries.getValue();
        final BUILDER_SERIES_VALUE builderSeriesValue = this.mapToBuilderSeriesValue(sourceSeriesValue);
        builderSeries.setData(builderSeriesValue);
    }
}
