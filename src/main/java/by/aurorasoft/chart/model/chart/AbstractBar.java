package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.BarSeries;

public abstract class AbstractBar extends CartesianCoordinateChart<BarSeries> {

    public AbstractBar(String title,
                       BarSeries[] series,
                       String axisXName,
                       String[] axisXValues,
                       String axisYName) {
        super(title, series, axisXName, axisXValues, axisYName);
    }

}
