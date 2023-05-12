package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.PieSeries;

public final class Pie extends Chart<PieSeries> {

    public Pie(String title, PieSeries series) {
        super(title, new PieSeries[]{series});
    }

}
