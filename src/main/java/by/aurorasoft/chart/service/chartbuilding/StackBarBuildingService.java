package by.aurorasoft.chart.service.chartbuilding;

import by.aurorasoft.chart.model.chart.StackBar;
import org.springframework.stereotype.Service;

@Service
public final class StackBarBuildingService extends AbstractStackBarBuildingService<StackBar> {

    public StackBarBuildingService() {
        super(StackBar.class);
    }

}
