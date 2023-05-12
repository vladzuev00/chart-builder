package by.aurorasoft.chart.model.chart.format.formatter;

import lombok.RequiredArgsConstructor;
import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public final class ChartFormatterViaStringTest {

    @Test
    public void chartShouldBeFormatted() {
        final String givenFormattedString = "formatted";
        final ChartFormatterViaString givenFormatter = new TestChartFormatterViaString(givenFormattedString);

        final byte[] actual = givenFormatter.format(null, null);
        final byte[] expected = new byte[]{102, 111, 114, 109, 97, 116, 116, 101, 100};
        assertArrayEquals(expected, actual);
    }

    @RequiredArgsConstructor
    private static final class TestChartFormatterViaString extends ChartFormatterViaString {
        private final String formattedString;

        @Override
        protected String formatToString(Chart<?, ?> chart, Engine engine) {
            return this.formattedString;
        }
    }

}
