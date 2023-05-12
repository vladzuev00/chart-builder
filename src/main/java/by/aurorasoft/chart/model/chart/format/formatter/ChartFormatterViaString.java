package by.aurorasoft.chart.model.chart.format.formatter;

import org.icepear.echarts.render.Engine;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class ChartFormatterViaString implements ChartFormatter {

    @Override
    public final byte[] format(org.icepear.echarts.Chart<?, ?> chart, Engine engine) {
        final String formattedString = this.formatToString(chart, engine);
        return formattedString.getBytes(UTF_8);
    }

    protected abstract String formatToString(org.icepear.echarts.Chart<?, ?> chart, Engine engine);
}
