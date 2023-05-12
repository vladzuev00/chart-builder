package by.aurorasoft.chart.model.series;

import lombok.*;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Series<V> {

    @Getter(NONE)
    private final String name;

    private final V value;

    public Series(V value) {
        this.name = null;
        this.value = value;
    }

    public Optional<String> findName() {
        return ofNullable(this.name);
    }
}

