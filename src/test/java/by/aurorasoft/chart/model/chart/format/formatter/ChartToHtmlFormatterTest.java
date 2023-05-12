package by.aurorasoft.chart.model.chart.format.formatter;

import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ChartToHtmlFormatterTest {

    private final ChartToHtmlFormatter formatter = new ChartToHtmlFormatter();

    @Test
    public void chartShouldBeFormatted() {
        final Chart<?, ?> givenChart = mock(Chart.class);
        final Engine givenEngine = mock(Engine.class);

        final String givenHtml = "html";
        when(givenEngine.renderHtml(any(Chart.class))).thenReturn(givenHtml);

        final String actual = this.formatter.formatToString(givenChart, givenEngine);
        assertEquals(givenHtml, actual);
    }

}
