package by.aurorasoft.chart.model.chart.format.formatter;

import org.icepear.echarts.render.Engine;

@FunctionalInterface
public interface ChartFormatter {
    byte[] format(org.icepear.echarts.Chart<?, ?> chart, Engine engine);
}
