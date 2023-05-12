package by.aurorasoft.chart.model.chart;

import by.aurorasoft.chart.model.series.Series;
import lombok.*;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Chart<S extends Series<?>> {

    @Getter(NONE)
    private final String title;
    
    private final S[] series;

    public final Optional<String> findTitle() {
        return ofNullable(this.title);
    }
}
