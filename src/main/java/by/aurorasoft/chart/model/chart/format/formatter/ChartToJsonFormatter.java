package by.aurorasoft.chart.model.chart.format.formatter;

import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;

public final class ChartToJsonFormatter extends ChartFormatterViaString {

    @Override
    protected String formatToString(Chart<?, ?> chart, Engine engine) {
        return engine.renderJsonOption(chart);
    }

}
