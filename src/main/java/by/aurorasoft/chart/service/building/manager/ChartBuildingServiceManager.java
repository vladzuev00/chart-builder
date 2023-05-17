package by.aurorasoft.chart.service.building.manager;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.Chart;
import by.aurorasoft.chart.service.building.AbstractChartBuildingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

@Service
public final class ChartBuildingServiceManager {
    private final Map<Class<? extends Chart<?>>, AbstractChartBuildingService<?, ?, ?, ?, ?, ?>> buildingServicesBySourceTypes;

    public ChartBuildingServiceManager(List<AbstractChartBuildingService<?, ?, ?, ?, ?, ?>> buildingServices) {
        this.buildingServicesBySourceTypes = buildingServices.stream()
                .collect(
                        collectingAndThen(
                                toMap(AbstractChartBuildingService::getSourceType, identity()),
                                Map::copyOf
                        )
                );
    }

    public PreparedChart build(Chart<?> source) {
        final AbstractChartBuildingService<?, ?, ?, ?, ?, ?> buildingService = this.findBuildingService(source);
        return buildingService.build(source);
    }

    private AbstractChartBuildingService<?, ?, ?, ?, ?, ?> findBuildingService(Chart<?> source) {
        final Class<?> chartType = source.getClass();
        var buildingService = this.buildingServicesBySourceTypes.get(chartType);
        if (buildingService == null) {
            throw new IllegalArgumentException(format("There is no service to build chart by source '%s'", source));
        }
        return buildingService;
    }
}
