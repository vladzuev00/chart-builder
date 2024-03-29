package by.aurorasoft.chart.service.building;

import by.aurorasoft.chart.model.chart.StackBar;
import org.icepear.echarts.Bar;
import org.springframework.stereotype.Service;

@Service
public final class StackBarBuildingService extends AbstractStackBarBuildingService<StackBar> {

    public StackBarBuildingService() {
        super(StackBar.class);
    }

    @Override
    protected void appendSpecialPropertiesExceptAxis(StackBar source, Bar bar) {

    }
}
