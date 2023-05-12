package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.model.series.Series;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public abstract class AbstractChartBuildingService<
        V,
        S extends Series<V>,
        C extends Chart<S>,
        B extends org.icepear.echarts.Chart<?, ?>
        > {
    private final Class<C> sourceType;

    @SuppressWarnings("unchecked")
    public final PreparedChart build(Chart<?> source) {
        final C concreteSource = (C) source;
        final B builder = this.createBuilder();
        this.appendTitle(concreteSource, builder);
        this.appendSeries(concreteSource, builder);
        this.appendSpecialProperties(concreteSource, builder);
        return new PreparedChart(builder);
    }

    protected abstract B createBuilder();

    protected abstract void appendSpecialProperties(C source, B builder);

    protected abstract void appendSeries(S series, B builder);

    private void appendTitle(C source, B builder) {
        final Optional<String> optionalTitle = source.findTitle();
        optionalTitle.ifPresent(builder::setTitle);
    }

    private void appendSeries(C source, B builder) {
        final S[] series = source.getSeries();
        stream(series).forEach(appended -> this.appendSeries(appended, builder));
    }
}
