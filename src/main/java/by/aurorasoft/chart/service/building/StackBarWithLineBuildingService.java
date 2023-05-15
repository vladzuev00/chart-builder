package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBarWithLine;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.components.coord.cartesian.ValueAxis;
import org.icepear.echarts.origin.coord.cartesian.AxisOption;
import org.springframework.stereotype.Service;

import java.util.Optional;

//TODO: test
@Service
public final class StackBarWithLineBuildingService extends AbstractStackBarBuildingService<StackBarWithLine> {
    private static final String DEFAULT_LINE_AXIS_Y_NAME = "";
    private static final String POSITION_VALUE_OF_AXIS_OF_LINE_VALUES = "right";
    private static final int INDEX_OF_AXIS_OF_LINE_VALUES = 1;
    private static final String TYPE_VALUE_OF_LINE = "line";

    public StackBarWithLineBuildingService() {
        super(StackBarWithLine.class);
    }

    @Override
    protected void appendSpecialPropertiesExceptBarAxis(StackBarWithLine source, Bar builder) {
        appendAxisOfLineValues(source, builder);
        appendAxisValuesOfLine(source, builder);
    }

    private static void appendAxisOfLineValues(StackBarWithLine source, Bar builder) {
        final AxisOption axisOption = new ValueAxis()
                .setName(findLineAxisYName(source))
                .setPosition(POSITION_VALUE_OF_AXIS_OF_LINE_VALUES);
        builder.addYAxis(axisOption);
    }

    private static String findLineAxisYName(StackBarWithLine source) {
        final Optional<String> optionalName = source.findLineAxisYName();
        return optionalName.orElse(DEFAULT_LINE_AXIS_Y_NAME);
    }

    private static void appendAxisValuesOfLine(StackBarWithLine source, Bar builder) {
        final BarSeries barSeries = new BarSeries()
                .setYAxisIndex(INDEX_OF_AXIS_OF_LINE_VALUES)
                .setType(TYPE_VALUE_OF_LINE)
                .setData(source.getLineValues())
                .setAnimation(false);
        builder.addSeries(barSeries);
    }
}
