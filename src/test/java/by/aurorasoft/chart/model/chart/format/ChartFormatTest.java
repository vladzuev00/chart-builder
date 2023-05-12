package by.aurorasoft.chart.model.chart.format;

import by.aurorasoft.chart.model.chart.format.ChartFormat;
import by.aurorasoft.chart.model.chart.format.formatter.ChartFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToHtmlFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToImageFormatter;
import by.aurorasoft.chart.model.chart.format.formatter.ChartToJsonFormatter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static by.aurorasoft.chart.model.chart.format.ChartFormat.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ChartFormatTest {

    @ParameterizedTest
    @MethodSource("formatWithFormatterArgumentsProvider")
    public void jsonFormatShouldHaveSuitableFormatter(ChartFormat givenFormat,
                                                      Class<? extends ChartFormatter> expectedFormatterType) {
        final ChartFormatter actual = givenFormat.getFormatter();
        assertTrue(expectedFormatterType.isInstance(actual));
    }

    private static Stream<Arguments> formatWithFormatterArgumentsProvider() {
        return Stream.of(
                Arguments.of(JSON, ChartToJsonFormatter.class),
                Arguments.of(HTML, ChartToHtmlFormatter.class),
                Arguments.of(IMAGE, ChartToImageFormatter.class)
        );
    }
}
