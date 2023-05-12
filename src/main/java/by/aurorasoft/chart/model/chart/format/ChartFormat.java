package by.aurorasoft.chart.model.chart.format;

import by.aurorasoft.chart.model.chart.format.formatter.ChartFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToHtmlFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToImageFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToJsonFormatter;

public enum ChartFormat {
    JSON(new ChartToJsonFormatter()),
    HTML(new ChartToHtmlFormatter()),
    IMAGE(new ChartToImageFormatter());

    private final ChartFormatter formatter;

    ChartFormat(ChartFormatter formatter) {
        this.formatter = formatter;
    }

    public ChartFormatter getFormatter() {
        return this.formatter;
    }
}
