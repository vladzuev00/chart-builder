package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.Series;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CartesianCoordinateChart<S extends Series<?>> extends Chart<S> {
    private final String axisXName;

    @Getter
    private final String[] axisXValues;
    private final String axisYName;

    public CartesianCoordinateChart(String title,
                                    S[] series,
                                    String axisXName,
                                    String[] axisXValues,
                                    String axisYName) {
        super(title, series);
        this.axisXName = axisXName;
        this.axisXValues = axisXValues;
        this.axisYName = axisYName;
    }

    public final Optional<String> findAxisXName() {
        return ofNullable(this.axisXName);
    }

    public final Optional<String> findAxisYName() {
        return ofNullable(this.axisYName);
    }
}
