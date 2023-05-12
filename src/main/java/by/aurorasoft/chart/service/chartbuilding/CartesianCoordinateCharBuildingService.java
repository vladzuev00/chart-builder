package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.CartesianCoordinateChart;
import by.aurorasoft.chart.model.series.Series;
import org.icepear.echarts.CartesianCoordChart;

import java.util.Optional;

public abstract class CartesianCoordinateCharBuildingService<
        V,
        S extends Series<V>,
        C extends CartesianCoordinateChart<S>,
        B extends CartesianCoordChart<?, ?>
        >
        extends AbstractChartBuildingService<V, S, C, B> {

    public CartesianCoordinateCharBuildingService(Class<C> sourceType) {
        super(sourceType);
    }

    @Override
    protected void appendSpecialProperties(C source, B builder) {
        this.appendAxisX(source, builder);
        this.appendAxisY(source, builder);
    }

    private void appendAxisX(C source, B builder) {
        final Optional<String> optionalAxisXName = source.findAxisXName();
        final String[] axisXValues = source.getAxisXValues();
        optionalAxisXName.ifPresentOrElse(
                axisXName -> builder.addXAxis(axisXName, axisXValues),
                () -> builder.addXAxis(axisXValues)
        );
    }

    private void appendAxisY(C source, B builder) {
        final Optional<String> optionalAxisYName = source.findAxisYName();
        optionalAxisYName.ifPresent(builder::addYAxis);
    }
}
