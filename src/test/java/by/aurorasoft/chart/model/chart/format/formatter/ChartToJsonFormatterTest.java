package by.aurorasoft.chart.model.chart.format.formatter;

import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ChartToJsonFormatterTest {

    private final ChartToJsonFormatter formatter = new ChartToJsonFormatter();

    @Test
    public void chartShouldBeFormattedToString() {
        final Chart<?, ?> givenChart = mock(Chart.class);
        final Engine givenEngine = mock(Engine.class);

        final String givenJson = "json";
        when(givenEngine.renderJsonOption(any(Chart.class))).thenReturn(givenJson);

        final String actual = this.formatter.formatToString(givenChart, givenEngine);
        assertEquals(givenJson, actual);
    }

}
