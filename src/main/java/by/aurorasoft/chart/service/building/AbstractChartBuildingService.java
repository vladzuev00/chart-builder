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
        BUILDER_SERIES extends SeriesOption,
        BUILDER extends org.icepear.echarts.Chart<?, BUILDER_SERIES>
        > {
    private final Class<CHART> sourceType;

    @SuppressWarnings("unchecked")
    public final PreparedChart build(Chart<?> source) {
        final CHART concreteSource = (CHART) source;
        final BUILDER builder = this.createBuilder();
        this.appendTitle(concreteSource, builder);
        this.appendSeries(concreteSource, builder);
        this.appendSpecialProperties(concreteSource, builder);
        return new PreparedChart(builder);
    }

    protected abstract BUILDER createBuilder();

    protected abstract void appendSpecialProperties(CHART source, BUILDER builder);

    protected abstract BUILDER_SERIES mapToBuilderSeries(SERIES series);

    private void appendTitle(CHART source, BUILDER builder) {
        final Optional<String> optionalTitle = source.findTitle();
        optionalTitle.ifPresent(builder::setTitle);
    }

    private void appendSeries(CHART source, BUILDER builder) {
        final SERIES[] series = source.getSeries();
        stream(series)
                .map(this::mapToBuilderSeriesWithNameAndWithoutAnimation)
                .forEach(builder::addSeries);
    }

    private BUILDER_SERIES mapToBuilderSeriesWithNameAndWithoutAnimation(SERIES sourceSeries) {
        final BUILDER_SERIES builderSeries = this.mapToBuilderSeries(sourceSeries);
        builderSeries.setAnimation(false);
        appendName(sourceSeries, builderSeries);
        return builderSeries;
    }

    private void appendName(SERIES sourceSeries, BUILDER_SERIES builderSeries) {
        final Optional<String> optionalName = sourceSeries.findName();
        optionalName.ifPresent(builderSeries::setName);
    }
}
