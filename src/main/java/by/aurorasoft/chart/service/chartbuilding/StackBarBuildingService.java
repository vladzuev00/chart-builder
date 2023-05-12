package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.StackBar;
import org.icepear.echarts.Bar;
import org.springframework.stereotype.Service;

@Service
public final class StackBarBuildingService extends AbstractStackBarBuildingService<StackBar> {

    public StackBarBuildingService() {
        super(StackBar.class);
    }

    @Override
    protected void appendSpecialPropertiesExceptBarAxis(StackBar source, Bar bar) {

    }
}
