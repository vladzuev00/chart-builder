package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.BarSeries;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class StackBarWithLine extends StackBar {
    private final String lineAxisYName;
    private final Number[] lineValues;

    @Builder(builderMethodName = "stackBarWithLineBuilder")
    public StackBarWithLine(String title,
                            BarSeries[] barSeries,
                            String axisXName,
                            String[] axisXValues,
                            String barAxisYName,
                            String lineAxisYName,
                            Number[] lineValues) {
        super(title, barSeries, axisXName, axisXValues, barAxisYName);
        this.lineAxisYName = lineAxisYName;
        this.lineValues = lineValues;
    }

    public Optional<String> findLineAxisYName() {
        return ofNullable(this.lineAxisYName);
    }
}
