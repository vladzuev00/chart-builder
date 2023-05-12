package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.CartesianCoordinateChart;
import by.aurorasoft.chart.model.series.Series;
import org.icepear.echarts.CartesianCoordChart;
import org.icepear.echarts.origin.util.SeriesOption;

import java.util.Optional;

public abstract class CartesianCoordinateChartBuildingService<
        SERIES_VALUE,
        SERIES extends Series<SERIES_VALUE>,
        CHART extends CartesianCoordinateChart<SERIES>,
        BUILDER_SERIES extends SeriesOption,
        BUILDER extends CartesianCoordChart<?, BUILDER_SERIES>
        >
        extends AbstractChartBuildingService<SERIES_VALUE, SERIES, CHART, BUILDER_SERIES, BUILDER> {

    public CartesianCoordinateChartBuildingService(Class<CHART> sourceType) {
        super(sourceType);
    }

    @Override
    protected void appendSpecialProperties(CHART source, BUILDER builder) {
        this.appendAxisX(source, builder);
        this.appendAxisY(source, builder);
    }

    private void appendAxisX(CHART source, BUILDER builder) {
        final Optional<String> optionalAxisXName = source.findAxisXName();
        final String[] axisXValues = source.getAxisXValues();
        optionalAxisXName.ifPresentOrElse(
                axisXName -> builder.addXAxis(axisXName, axisXValues),
                () -> builder.addXAxis(axisXValues)
        );
    }

    private void appendAxisY(CHART source, BUILDER builder) {
        final Optional<String> optionalAxisYName = source.findAxisYName();
        optionalAxisYName.ifPresent(builder::addYAxis);
    }
}
