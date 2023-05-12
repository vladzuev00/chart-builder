package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.BarSeries;
import lombok.Builder;

public final class Bar extends AbstractBar {

    @Builder
    public Bar(String title,
               BarSeries[] series,
               String axisXName,
               String[] axisXValues,
               String axisYName) {
        super(title, series, axisXName, axisXValues, axisYName);
    }

}
