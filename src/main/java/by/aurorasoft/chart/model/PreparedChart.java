package by.aurorasoft.chart.model;

import by.aurorasoft.chart.model.chart.format.ChartFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;

import java.util.EnumMap;
import java.util.Map;

import static lombok.AccessLevel.NONE;

/**
 * not thread safe
 */
@Getter
@EqualsAndHashCode
@ToString
public final class PreparedChart {
    private final Chart<?, ?> chart;

    @Getter(NONE)
    private final Map<ChartFormat, byte[]> formattedViewsByTypes;

    public PreparedChart(Chart<?, ?> chart) {
        this.chart = chart;
        this.formattedViewsByTypes = new EnumMap<>(ChartFormat.class);
    }

    public byte[] format(ChartFormat chartFormat) {
        return this.formattedViewsByTypes.computeIfAbsent(
                chartFormat,
                format -> format.getFormatter().format(this.chart, new Engine())
        );
    }
}
