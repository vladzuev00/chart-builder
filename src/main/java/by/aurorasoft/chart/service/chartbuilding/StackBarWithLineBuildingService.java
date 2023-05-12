package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.StackBarWithLine;
import org.icepear.echarts.Bar;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.components.coord.cartesian.ValueAxis;
import org.springframework.stereotype.Service;

@Service
public final class StackBarWithLineBuildingService extends AbstractStackBarBuildingService<StackBarWithLine> {

    public StackBarWithLineBuildingService() {
        super(StackBarWithLine.class);
    }

    //TODO: refactor
    @Override
    protected void appendSpecialProperties(StackBarWithLine source, Bar builder) {
        super.appendSpecialProperties(source, builder);
        builder.addYAxis(new ValueAxis().setName(source.findLineAxisYName().get()).setPosition("right"));
        builder.addSeries(new BarSeries().setYAxisIndex(1).setType("line").setData(source.getLineValues()));
    }
}
